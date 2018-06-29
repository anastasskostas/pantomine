package com.kostanas.pantomine.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kostanas.pantomine.R;
import com.kostanas.pantomine.infrastructure.Team;

import java.util.ArrayList;

/**
 * Created by Anastasios on 3/2/2017.
 */

public class NewRoundActivity extends BaseActivity {
    private TextView instructions;
    private Button continueBtn;
    private ArrayList<Team> teams;
    private int currentTeam, nextTeam;

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_new_round);

        teams = application.getSelectedTeams();
        instructions = (TextView) findViewById(R.id.activity_new_round_instructions);
        continueBtn = (Button) findViewById(R.id.activity_new_round_continue);

        currentTeam = application.getCurrentTeam();
        if (currentTeam + 1 == teams.size())
            nextTeam = 0;
        else
            nextTeam = currentTeam + 1;

        instructions.setText("Η ομάδα " + teams.get(currentTeam).getName().toString() + " πρέπει να επιλέξει μίμο για να παίξει. \n\n Η ομάδα "
                + teams.get(nextTeam).getName().toString() + " θα επιλέξει ταινία.");

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewRoundActivity.this, ChooseMovieActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }
}
