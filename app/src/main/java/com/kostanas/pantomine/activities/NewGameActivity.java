package com.kostanas.pantomine.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.kostanas.pantomine.R;
import com.kostanas.pantomine.infrastructure.Team;

import java.util.ArrayList;

/**
 * Created by Anastasios on 2/18/2017.
 */
public class NewGameActivity extends BaseActivity {
    private EditText teamName;
    private Button addNewTeamBtn;
    private Button continueBtn;
    private ArrayList<Team> selectedTeams;
    private TeamsAdapter adapter;
    private ActionMode actionMode;
    private int counter;
    private boolean existTeamName;

    @Override
    protected void onCreate(Bundle savedState){
        super.onCreate(savedState);
        setContentView(R.layout.activity_new_game);

        counter = 0;
        teamName = (EditText) findViewById(R.id.activity_main_name);
        addNewTeamBtn = (Button) findViewById(R.id.activity_new_game_addNewTeam);
        continueBtn = (Button) findViewById(R.id.activity_new_game_continue);
        ListView listView = (ListView) findViewById(R.id.activity_new_game_listView);

        selectedTeams = new ArrayList<>();
        adapter = new TeamsAdapter();
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Team team = adapter.getItem(i);

                if (actionMode != null){
                    toggleTeamsSelection(team);
                }
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Team team = adapter.getItem(i);
                toggleTeamsSelection(team);
                return true;
            }
        });

        addNewTeamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (teamName.getText().toString().matches("")) {
                    Toast.makeText(NewGameActivity.this, "Εισάγετε όνομα για την ομάδα.", Toast.LENGTH_SHORT).show();
                }else{
                    existTeamName = false;
                    for (int i=0;i<adapter.getCount();i++){
                        if (adapter.getItem(i).getName().toString().equalsIgnoreCase(teamName.getText().toString())){
                            Toast.makeText(NewGameActivity.this, "Υπάρχει ήδη ομάδα με αυτό το όνομα.", Toast.LENGTH_SHORT).show();
                            existTeamName = true;
                            break;
                        }
                    }
                    if (!existTeamName){
                        adapter.insert(new Team(counter, teamName.getText().toString(),0),0);
                        teamName.setText("");
                        counter++;
                    }
                }
            }
        });

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapter.getCount() == 0){
                    Toast.makeText(NewGameActivity.this, "Δεν υπάρχει καμία ομάδα για να συνεχίσετε", Toast.LENGTH_SHORT).show();
                }else{
                    for (int i = 0; i < adapter.getCount(); i++){
                        selectedTeams.add(adapter.getItem(i));
                    }
                    Intent intent = new Intent(NewGameActivity.this, SettingsActivity.class);

                    application.setSelectedTeams(selectedTeams);
                    application.setCurrentTeam(0);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/

    private void deleteTeams(Iterable<Team> teams){
        adapter.setNotifyOnChange(false);
        for (Team team : teams){
            adapter.remove(team);
        }

        adapter.setNotifyOnChange(true);
        adapter.notifyDataSetChanged();
    }

    private void toggleTeamsSelection(Team team){
        if (selectedTeams.contains(team)){
            selectedTeams.remove(team);
        }else{
            selectedTeams.add(team);
        }

        if (selectedTeams.size() == 0 && actionMode != null){
            actionMode.finish();
            return;
        }

        if (actionMode == null){
            actionMode = startSupportActionMode(new NewTeamActionModeCallback());
        }else{
            actionMode.invalidate();
        }

        adapter.notifyDataSetChanged();
    }

    private class TeamsAdapter extends ArrayAdapter<Team>{
        public TeamsAdapter(){
            super(NewGameActivity.this,R.layout.list_item_team);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);
            Team team = adapter.getItem(position);

            if (selectedTeams.contains(team)){
                view.setBackgroundColor(Color.parseColor("#B2EBF2"));
            }else{
                view.setBackground(null);
            }

            return view;
        }
    }

    private class NewTeamActionModeCallback implements ActionMode.Callback{

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            getMenuInflater().inflate(R.menu.menu_new_game_teams, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            int id = item.getItemId();

            if (id == R.id.menu_new_game_delete){
                deleteTeams(selectedTeams);
                mode.finish();
                return true;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            actionMode = null;
            selectedTeams.clear();
            adapter.notifyDataSetChanged();

        }
    }
}
