package com.example.yuanbo.powercharger;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Created by yuanbo on 10/21/17.
 */

public class GameView extends SurfaceView implements Runnable, SurfaceHolder.Callback, SensorEventListener {
    //context.
    Context context;
    //SensorManager
    private SensorManager mSensorMgr = null;
    private Sensor mSensor = null;
    //game thread
    private Thread gameThread = null;
    //draw
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    //game parameter
    private int playerWidth = 200;
    private int playerHeight = 200;
    private int enemyWidth = 200;
    private int enemyHeight = 200;
    private Spirit player;//player
    private List<Spirit> enemyList = new ArrayList<>();//enemies
    private long sleepTime = 10;
    private int controlSpeed = 0;
    private float sensorX = 0;
    private int screenX, screenY;
    private Random random = new Random();
    //game status
    private int status = 0;
    private static int STATUS_INIT = 0;
    private static int STATUS_PLAY = 1;
    private static int STATUS_PAUSE = 2;
    private static int STATUS_END = 3;
    //game data
    private int score = 0;
    private float speed = 0;
    private float distance = 0;
    private int highScore[] = new int[4];
    SharedPreferences sharedPreferences;//Shared Prefernces to store the High Scores
    //game sound
    static MediaPlayer gameOnsound;
    static MediaPlayer gameOversound;


    public GameView(Context context, int screenX, int screenY) {
        //init
        super(context);
        this.context = context;
        surfaceHolder = getHolder();
        paint = new Paint();
        this.screenX = screenX;
        this.screenY = screenY;
        //init score
        sharedPreferences = context.getSharedPreferences("SHAR_PREF_NAME", Context.MODE_PRIVATE);
        highScore[0] = sharedPreferences.getInt("score1", 0);
        highScore[1] = sharedPreferences.getInt("score2", 0);
        highScore[2] = sharedPreferences.getInt("score3", 0);
        highScore[3] = sharedPreferences.getInt("score4", 0);
        //init sound
        gameOnsound = MediaPlayer.create(context, R.raw.gameon);
        gameOversound = MediaPlayer.create(context, R.raw.gameover);
        //gameOnsound.start();
        //init game
        status = STATUS_INIT;
        player = new Spirit(playerWidth, playerHeight, (screenX - playerWidth) / 2, screenY - playerHeight * 2, Color.argb(255, random.nextInt(255), random.nextInt(255), random.nextInt(255)), 0, screenX - playerWidth, 0, screenY);
        //init screen
        getHolder().addCallback(this);
        //init sensorManager
        mSensorMgr = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorMgr.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void run() {
        while (status == STATUS_PLAY) {
            //update the frame
            update();
            //draw the frame
            draw();
            //control
            control();
        }
    }

    private int addEnemyFlag = 0;

    private void update() {
        change();
        if (speed <= 0) {
            gameOver();
        }
        //add enemy
        if (addEnemyFlag < distance / (enemyHeight + playerHeight * 2)) {
            addEnemyFlag++;
            enemyList.add(new Spirit(enemyWidth, enemyHeight, random.nextInt(screenX - enemyWidth), 0, Color.argb(255, random.nextInt(255), random.nextInt(255), random.nextInt(255)), 0, screenX - enemyWidth, 0, screenY));
        }
        //update player
        player.update(player.getX() + controlSpeed, player.getY());
        //update enemies
        Iterator<Spirit> iterator = enemyList.iterator();
        while (iterator.hasNext()) {
            Spirit enemy = iterator.next();
            enemy.update(enemy.getX(), (int) (enemy.getY() + speed));
            //boom
            if (player.impact(enemy)) {
                //TODO stop or slow
                gameOver();
            }
            //out of screeen
            if (enemy.getY() == enemy.getMaxY()) {
                iterator.remove();
            }
        }
    }

    //TODO strategy
    private void change() {
        controlSpeed = (int) (-sensorX) * 2;//sensorX<0 move right;sensorX>0 move left
        speed -= 0.2;//decrease gentle
        distance += speed;
        score = (int) distance / 1000;
    }

    private void draw() {
        //checking if surface is valid
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            canvas.drawRGB(255, 255, 255);
            //draw score
            paint.setTextSize(30);
            canvas.drawText("Score: " + score, 100, 50, paint);
            //draw player
            canvas.drawBitmap(player.getBitmap(), player.getX(), player.getY(), paint);
            //draw enemies
            for (Spirit enemy : enemyList) {
                canvas.drawBitmap(enemy.getBitmap(), enemy.getX(), enemy.getY(), paint);
            }
            //draw game Over when the game is over
            if (status == STATUS_END) {
                paint.setTextSize(150);
                paint.setTextAlign(Paint.Align.CENTER);
                int yPos = (int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2));
                canvas.drawText("Game Over", canvas.getWidth() / 2, yPos, paint);
            }
            //Unlocking the canvas
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void gameOver() {
        status = STATUS_END;
        //Assigning the scores to the highscore integer array
        for (int j = 0; j < 4; j++) {
            if (highScore[j] < score) {

                final int finalI = j;
                highScore[j] = score;
                break;
            }
        }
        //storing the scores through shared Preferences
        SharedPreferences.Editor e = sharedPreferences.edit();
        for (int j = 0; j < 4; j++) {
            int k = j + 1;
            e.putInt("score" + k, highScore[j]);
        }
        e.apply();
    }

    private void control() {
        try {
            gameThread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //stop the music on exit
    public static void stopMusic() {
        gameOnsound.stop();
    }

    private float initX;
    private float initY;
    private long then;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //init speed
        if (status == STATUS_INIT) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                then = System.currentTimeMillis();
                initX = event.getX();
                initY = event.getY();
                return true;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                speed = (initY - event.getY()) / (System.currentTimeMillis() - then) * 10;
                status = STATUS_PLAY;
                gameThread = new Thread(this);
                gameThread.start();
                return true;
            }
        } else if (status == STATUS_END) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                context.startActivity(new Intent(context, MainActivity.class));
            }
        }
        return false;
    }

    // generate speed using gravity accelerator.
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (status == STATUS_PLAY) {
            sensorX = sensorEvent.values[0];
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            canvas.drawRGB(255, 255, 255);
            Bitmap arrow = BitmapFactory.decodeResource(context.getResources(), R.drawable.arrow);
            canvas.drawBitmap(player.getBitmap(), player.getX(), player.getY(), paint);
            canvas.drawBitmap(arrow, (screenX - arrow.getWidth()) / 2, player.getY() - playerHeight - arrow.getHeight(), paint);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }
}
