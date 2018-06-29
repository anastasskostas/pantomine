package com.kostanas.pantomine.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kostanas.pantomine.R;
import com.kostanas.pantomine.infrastructure.Team;

import java.util.ArrayList;

/**
 * Created by Anastasios on 2/21/2017.
 */
public class OverallScoreActivity extends BaseActivity{
    private ArrayList<Team> teams;
    private ListView listView;
    private Button okBtn;
    private int currentTeam;
    boolean isEndOfGame = false;
    private TextView winnerTxt, scoreTxt;
    private RelativeLayout winnerIconLayout;

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_overall_score);

        okBtn = (Button) findViewById(R.id.activity_overall_score_ok);
        listView = (ListView) findViewById(R.id.activity_overall_score_listView);
        winnerIconLayout = (RelativeLayout) findViewById(R.id.activity_overall_score_winnerIconLayout);
        winnerTxt = (TextView) findViewById(R.id.activity_overall_score_winnerTxt);
        scoreTxt = (TextView) findViewById(R.id.activity_overall_score_score);
        teams = application.getSelectedTeams();
        currentTeam = application.getCurrentTeam();
        Boolean answer = getIntent().getExtras().getBoolean("answer");

        if (answer)
            teams.get(currentTeam).setScore(teams.get(currentTeam).getScore()+1);

        winnerTxt.setText("ΝΙΚΕΣ: " + Integer.toString(application.getTotalWins()));
        if (teams.get(currentTeam).getScore() == application.getTotalWins()){

            if (application.getWinner()==-1)
                application.setWinner(currentTeam);
            else
                application.setWinner(-99);
        }/*else{
            winnerTxt.setText("ΝΙΚΕΣ: " + Integer.toString(application.getTotalWins()));
        }*/

        if ((application.getWinner()>=0) && (currentTeam==teams.size()-1)){
            isEndOfGame = true;
            scoreTxt.setText("ΤΕΛΙΚΗ ΒΑΘΜΟΛΟΓΙΑ");
            winnerTxt.setText(teams.get(application.getWinner()).getName());
            okBtn.setText("ΤΕΛΟΣ");
        }
        else if ((application.getWinner()==-99) && (currentTeam==teams.size()-1)) {
            isEndOfGame = true;
            scoreTxt.setText("ΤΕΛΙΚΗ ΒΑΘΜΟΛΟΓΙΑ");
            winnerTxt.setText("ΙΣΟΠΑΛΙΑ");
            okBtn.setText("ΤΕΛΟΣ");
        }

        TeamAdapter adapter = new TeamAdapter(); //adapter = new TeamListAdapter(this);//
        for (int i = 0; i < teams.size(); i++){
            adapter.add(new Team(teams.get(i).getId(), teams.get(i).getName(), teams.get(i).getScore()));
        }
        listView.setAdapter(adapter);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentTeam++;
                if (currentTeam == teams.size())
                    currentTeam = 0;
                application.setCurrentTeam(currentTeam);
                if (isEndOfGame){
                    Intent intent = new Intent(OverallScoreActivity.this, FinishGameActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
                else{
                    Intent intent = new Intent(OverallScoreActivity.this, NewRoundActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private class TeamAdapter extends ArrayAdapter<Team>{
        public TeamAdapter(){
            super(OverallScoreActivity.this, R.layout.list_item_team_with_scores);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;

            if (convertView == null){
                convertView = getLayoutInflater().inflate(R.layout.list_item_team_with_scores, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.teamNameView = (TextView) convertView.findViewById(R.id.list_item_team_score_name);
                viewHolder.teamScoreView = (TextView) convertView.findViewById(R.id.list_item_team_score_score);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }
            Team team = getItem(position);
            viewHolder.teamNameView.setText(team.getName());
            viewHolder.teamScoreView.setText(Integer.toString(team.getScore()));
            if (position == currentTeam){
                //viewHolder.teamNameView.setTypeface(null, Typeface.BOLD_ITALIC);
                //viewHolder.teamScoreView.setTypeface(null, Typeface.BOLD_ITALIC);
                viewHolder.teamNameView.setTextColor(Color.rgb(255,153,255));
                viewHolder.teamScoreView.setTextColor(Color.rgb(255,153,255));
            }

            else{
                //viewHolder.teamNameView.setTypeface(null, Typeface.NORMAL);
                //viewHolder.teamScoreView.setTypeface(null, Typeface.NORMAL);
                viewHolder.teamNameView.setTextColor(Color.rgb(255,255,255));
                viewHolder.teamScoreView.setTextColor(Color.rgb(255,255,255));
            }

            return convertView;
        }

        private class ViewHolder{
            public TextView teamNameView;
            public TextView teamScoreView;
        }
    }
}
