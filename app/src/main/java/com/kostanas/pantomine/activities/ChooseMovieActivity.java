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
 * Created by Anastasios on 2/19/2017.
 */
public class ChooseMovieActivity extends BaseActivity {
    private ArrayList<Team> teams;
    private ArrayList<String> movies;
    private TextView movieTitle;
    private Button movieOneBtn, movieTwoBtn;
    private int random, random2;
    private int currentTeam;

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_choose_movie);

        teams = application.getSelectedTeams();
        movies = application.getMovies();

        movieTitle = (TextView) findViewById(R.id.activity_choose_movie_movieTitle);
        movieOneBtn = (Button) findViewById(R.id.activity_choose_movie_movieOneBtn);
        movieTwoBtn = (Button) findViewById(R.id.activity_choose_movie_movieTwoBtn);

        currentTeam = application.getCurrentTeam();
        movieTitle.setText(teams.get(currentTeam).getName());

        random = (int) (Math.random() * movies.size());
        movieOneBtn.setText(movies.get(random));
        do {
            random2 = (int) (Math.random() * movies.size());
        }while (random2 == random);
        movieTwoBtn.setText(movies.get(random2));

        movieOneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimerActivity(random, movieOneBtn);
            }
        });

        movieTwoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimerActivity(random2, movieTwoBtn);
            }
        });

    }

    public void startTimerActivity(int rand, View clickedBtn){
        clickedBtn.setBackgroundResource(R.drawable.screen_tv_click);
        Intent intent = new Intent(ChooseMovieActivity.this, TimerActivity.class);
        intent.putExtra("movieTitleStr", movies.get(rand));
        if (random > random2) {
            movies.remove(random);
            movies.remove(random2);
        }else{
            movies.remove(random2);
            movies.remove(random);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/

}
