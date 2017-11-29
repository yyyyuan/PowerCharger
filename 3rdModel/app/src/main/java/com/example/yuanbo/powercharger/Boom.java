package com.example.yuanbo.powercharger;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by yuanbo on 10/22/17.
 */

public class Boom {

    //bitmap object
    private Bitmap bitmap;

    //coordinate variables
    private int x;
    private int y;

    //constructor
    public Boom(Context context) {
        //getting boom image from drawable resource
        bitmap = BitmapFactory.decodeResource
                (context.getResources(), R.drawable.boom);

        //setting the coordinate outside the screen
        //so that it won't shown up in the screen
        //it will be only visible for a fraction of second
        //after collission
        x = -250;
        y = -250;
    }


    // Setters
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    // Getters

    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
