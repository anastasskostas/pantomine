package com.kostanas.pantomine.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kostanas.pantomine.R;

/**
 * Created by Anastasios on 2/20/2017.
 */
public class TimerActivity extends BaseActivity{
    private TextView movieTitle;
    private Button startTimerBtn;
    private TextView timer;
    private Button movieFound;
    private Button movieNotFound;
    private int roundTime;
    private ImageView hourglass;
    private boolean iconChanged;

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_timer);

        roundTime = application.getRoundTime()*1000;
        movieTitle = (TextView) findViewById(R.id.activity_timer_movieTitle);
        startTimerBtn = (Button) findViewById(R.id.activity_timer_startTimerBtn);
        timer = (TextView) findViewById(R.id.activity_timer_timer);
        movieFound = (Button) findViewById(R.id.activity_timer_movieFound);
        movieNotFound = (Button) findViewById(R.id.activity_timer_movieNotFound);
        hourglass = (ImageView) findViewById(R.id.activity_timer_hourglass);
        iconChanged = false;

        String movieTitleStr = getIntent().getStringExtra("movieTitleStr");
        movieTitle.setText(movieTitleStr);
        timer.setText("Ο χρόνος που απομένει: " + Integer.toString(roundTime/1000));

        startTimerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimerBtn.setEnabled(false);
                movieFound.setEnabled(true);
                movieNotFound.setEnabled(true);
                new CountDownTimer(roundTime, 1000){
                    @Override
                    public void onTick(long l) {
                        timer.setText("Ο χρόνος που απομένει: " + l / 1000);
                        if ((l < roundTime/2) && (!iconChanged)){
                            hourglass.setImageResource(R.drawable.hourglass_middle);
                            iconChanged = true;
                        }

                    }
                    @Override
                    public void onFinish() {
                        timer.setText("ΤΕΛΟΣ!");
                        hourglass.setImageResource(R.drawable.hourglass_finish);
                    }
                }.start();
            }
        });

        movieFound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startOverallScoreActivity(true);
            }
        });

        movieNotFound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startOverallScoreActivity(false);
            }
        });
    }

    public void startOverallScoreActivity(boolean flag){
        Intent intent = new Intent(TimerActivity.this, OverallScoreActivity.class);
        intent.putExtra("answer", flag);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
