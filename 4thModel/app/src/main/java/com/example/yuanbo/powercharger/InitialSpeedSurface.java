package com.example.yuanbo.powercharger;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by yuanbo on 11/26/17.
 */

public class InitialSpeedSurface extends SurfaceView implements SensorEventListener {
    private float x;
    private float y;
    private float distance;
    private long then;
    private long now;
    private SurfaceHolder surfaceHolder;
    private Canvas canvas;
    private Paint paint;
    /**
     * SensorManager
     **/
    private SensorManager mSensorMgr = null;
    Sensor mSensor = null;

    public InitialSpeedSurface(Context context) {
        super(context);
        surfaceHolder = getHolder();
        mSensorMgr = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorMgr.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_GAME);
        paint = new Paint();
        paint.setTextSize(30);
        paint.setColor(Color.WHITE);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            then = System.currentTimeMillis();
            x = event.getX();
            y = event.getY();

            return true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            now = System.currentTimeMillis();
            distance = y - event.getY();
            if (surfaceHolder.getSurface().isValid()) {
                canvas = surfaceHolder.lockCanvas();
                if (canvas != null) {

                    canvas.drawColor(Color.BLACK);
                    canvas.drawText("[Start]x:" + x + ",y:" + y, 0, 40, paint);
                    canvas.drawText("[End]x:" + event.getX() + ",y:" + event.getY(), 0, 80, paint);
                    canvas.drawText("[Time]then:" + then + ",now:" + now, 0, 120, paint);
                    canvas.drawText("[Gap]distance:" + distance, 0, 160, paint);
                    canvas.drawText("[Gap]time:" + (now - then), 0, 200, paint);
                    canvas.drawText("[Gap]rate:" + distance / (now - then), 0, 240, paint);
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }

            }
            return true;
        }

        return false;
    }

    /**
     * gravity
     **/
    private float gX = 0;
    private float gY = 0;
    private float gZ = 0;
    private float mGX = 0;
    private float mGY = 0;
    private float mGZ = 0;


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        gX = sensorEvent.values[0];
        gY = sensorEvent.values[1];
        gZ = sensorEvent.values[2];

        mGX = Math.max(mGX, gX);
        mGY = Math.max(mGY, gY);
        mGZ = Math.max(mGZ, gZ);


        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            if (canvas != null) {

                canvas.drawColor(Color.BLACK);
                canvas.drawText("[sensor]x:" + gX, 0, 40, paint);
                canvas.drawText("[sensor]y:" + gY, 0, 80, paint);
                canvas.drawText("[sensor]z:" + gZ, 0, 120, paint);
                canvas.drawText("[Max]x:" + mGX, 0, 160, paint);
                canvas.drawText("[Max]y:" + mGY, 0, 200, paint);
                canvas.drawText("[Max]z:" + mGZ, 0, 240, paint);
                surfaceHolder.unlockCanvasAndPost(canvas);

            }

        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
