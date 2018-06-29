package com.kostanas.pantomine.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.kostanas.pantomine.R;
import com.kostanas.pantomine.infrastructure.PantoApplication;

/**
 * Created by Anastasios on 2/20/2017.
 */

public class BaseActivity extends AppCompatActivity {
    protected PantoApplication application;

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        application = (PantoApplication) getApplication();

        //View decorView = getWindow().getDecorView();
        // Hide the status bar.
        //int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        //decorView.setSystemUiVisibility(uiOptions);
    }

    @Override
    public void onBackPressed(){
        if (this.getClass().getSimpleName().equals("MainActivity")){
            finish();
        }else{
            new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Κλείσιμο")
                    .setMessage("Τέλος παιχνιδιού;")
                    .setPositiveButton("ΝΑΙ", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            application.setNewGame(true);
                            Intent intent = new Intent(BaseActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }

                    })
                    .setNegativeButton("ΟΧΙ", null)
                    .show();
        }

    }
}
