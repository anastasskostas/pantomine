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
 * Created by Anastasios on 3/3/2017.
 */

public class FinishGameActivity extends BaseActivity {
    private TextView winnerTxt;
    private Button gameContinue, gameEnd;
    private ArrayList<Team> teams;
    private int winner;
    private String tieText;

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_finish_game);


        winnerTxt = (TextView) findViewById(R.id.activity_finish_game_winner);
        gameContinue = (Button) findViewById(R.id.activity_finish_game_continue);
        gameEnd = (Button) findViewById(R.id.activity_finish_game_end);

        winner = application.getWinner();
        teams = application.getSelectedTeams();
        if (winner >= 0)
            winnerTxt.setText("Συγχαρήτηρια, " + teams.get(winner) + "!\nΕίστε οι νικητές.");
        else{
            tieText = "Συγχαρήτηρια, ";
            for (int i = 0; i < teams.size();i++){
                if (teams.get(i).getScore()==application.getTotalWins())
                    tieText = tieText + teams.get(i).getName() + ", ";
            }
            tieText = tieText.substring(0, tieText.length() - 2);
            tieText = tieText + "!\nΕίστε οι νικητές με ισοπαλία.";
            winnerTxt.setText(tieText);
        }



        gameContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                application.setNewGame(false);
                Intent intent = new Intent(FinishGameActivity.this, SettingsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        gameEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                application.setNewGame(true);
                Intent intent = new Intent(FinishGameActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }
}
