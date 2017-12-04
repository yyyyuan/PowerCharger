package com.example.yuanbo.powercharger;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private ImageButton buttonPlay;
    //high score button
    private ImageButton buttonScore;

    //draw
    private Paint paint = new Paint();
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        SurfaceView surface = (SurfaceView) findViewById(R.id.surface);
        surface.getHolder().addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                //Getting display object
                Display display = getWindowManager().getDefaultDisplay();

                //Getting the screen resolution into point object
                Point size = new Point();
                display.getSize(size);

                if (holder.getSurface().isValid()) {
                    canvas = holder.lockCanvas();
                    drawBackground(size);
                    holder.unlockCanvasAndPost(canvas);
                }

                // Do some drawing when surface is ready
                //Canvas canvas = holder.lockCanvas();
                //canvas.drawColor(Color.RED);
                //holder.unlockCanvasAndPost(canvas);
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }
        });

        //setting the orientation to landscape
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

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
            //Intent intent = new Intent(MainActivity.this, HighScore.class);
            Intent intent = new Intent(MainActivity.this, TermsActivity.class);
            startActivity(intent);
        }
    };

    ImageButton.OnClickListener buttonPlayClickListener = new ImageButton.OnClickListener() {
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, GameActivity.class);
            startActivity(intent);
        }
    };

    private void drawBackground(Point Size) {
        canvas.drawRGB(0xFF, 0xFF, 0xCC);
        paint.setColor(Color.rgb(0x66,0x00,0x00));
        final int space = 30;   //gap
        int vertz = 0, hortz = 0;
        for (int i = 0; i < 100; i++) {
            canvas.drawLine(0, vertz, Size.x, vertz, paint);
            canvas.drawLine(hortz, 0, hortz, Size.y, paint);
            vertz += space;
            hortz += space;
        }
    }
}
