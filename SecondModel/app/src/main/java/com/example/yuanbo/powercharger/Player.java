package com.example.yuanbo.powercharger;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

/**
 * Created by yuanbo on 10/21/17.
 */

public class Player {

    //Bitmap to get character from image
    private Bitmap bitmap;

    //coordinates
    private int x;
    private int y;

    //motion speed of the character in vertical direction
    private int speed = 0;
    //motion speed of the character in horizontal direction
    private int hSpeed = 0;

    //boolean variable to track the ship is boosting or not
    private boolean boosting;
    private boolean hBoosting;

    //Gravity Value to add gravity effect on the ship
    private final int GRAVITY = 0;

    //Controlling Y coordinate so that ship won't go outside the screen
    private int maxY;
    private int minY;

    //Limit the bounds of the ship's speed
    private final int MIN_SPEED = -20;
    private final int MAX_SPEED = 20;

    private Rect detectCollision;

    public Player(Context context) {
        x = 75;
        y = 50;
        speed = 0;
        hSpeed = 10;

        //Getting bitmap from drawable resource
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.player);

        boosting = false;
    }

    public Player(Context context, int screenX, int screenY) {
        x = 75;
        y = screenY / 2;
        speed = 0;
        hSpeed = 10;

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.player);

        //calculating maxY
        maxY = screenY - bitmap.getHeight();

        //top edge's y point is 0 so min y will always be zero
        minY = 0;

        //setting the boosting value to false initially
        boosting = false;

        //initializing rect object
        detectCollision =  new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());
    }

    //setting boosting true
    public void setBoosting() {
        boosting = true;
    }

    //setting boosting false
    public void stopBoosting() {
        boosting = false;
    }

    public void sethBoosting() {
        hBoosting = true;
    }

    public void stop() {
        hBoosting = false;
        speed = 0;
    }


    //Method to update coordinate of character
    public void update(){


        if (hBoosting) {
            //if the ship is boosting
            if (boosting) {
                //speeding up the ship
                speed += 5;
            } else {
                //slowing down if not boosting
                speed -= 5;
            }
        }


        //controlling the top speed
        if (speed > MAX_SPEED) {
            speed = MAX_SPEED;
        }
        //if the speed is less than min speed
        //controlling it so that it won't stop completely
        if (speed < MIN_SPEED) {
            speed = MIN_SPEED;
        }

        //moving the ship down
        y -= speed + GRAVITY;

        //but controlling it also so that it won't go off the screen
        if (y < minY) {
            y = minY;
        }
        if (y > maxY) {
            y = maxY;
        }


        //updating y coordinate
//        y++;

        //adding top, left, bottom and right to the rect object
        detectCollision.left = x;
        detectCollision.top = y;
        detectCollision.right = x + bitmap.getWidth();
        detectCollision.bottom = y + bitmap.getHeight();
    }


    /*
    * These are getters you can generate it autmaticallyl
    * click on Code -> generate -> getters
    * */

    //one more getter for getting the rect object
    public Rect getDetectCollision() {
        return detectCollision;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
    //    return speed;
        return hSpeed;
    }
}
