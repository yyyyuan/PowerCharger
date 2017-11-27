package com.example.yuanbo.powercharger;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private ImageButton buttonPlay;
    //high score button
    private ImageButton buttonScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setting the orientation to landscape
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // Get the button
        buttonPlay = (ImageButton) findViewById(R.id.buttonPlay);
        //initializing the highscore button
        buttonScore = (ImageButton) findViewById(R.id.buttonScore);

        // Adding a click listener
        buttonPlay.setOnClickListener(buttonPlayClickListener);
        // Adding a click listener
        buttonScore.setOnClickListener(buttonScoreClickListener);

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        GameView.stopMusic();
                        Intent startMain = new Intent(Intent.ACTION_MAIN);
                        startMain.addCategory(Intent.CATEGORY_HOME);
                        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(startMain);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

    ImageButton.OnClickListener buttonScoreClickListener = new ImageButton.OnClickListener() {
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, HighScore.class);
            startActivity(intent);
        }
    };

    ImageButton.OnClickListener buttonPlayClickListener = new ImageButton.OnClickListener() {
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, GameActivity.class);
            startActivity(intent);
        }
    };
}
