package com.example.yuanbo.powercharger;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private ImageButton buttonPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setting the orientation to landscape
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // Get the button
        buttonPlay = (ImageButton) findViewById(R.id.buttonPlay);

        // Adding a click listener
        buttonPlay.setOnClickListener(buttonPlayClickListener);

    }


    ImageButton.OnClickListener buttonPlayClickListener = new ImageButton.OnClickListener() {
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, GameActivity.class);
                    startActivity(intent);
                }
            };
}
