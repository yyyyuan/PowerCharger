package com.example.yuanbo.powercharger;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

/**
 * Created by yuanbo on 10/21/17.
 */

public class Spirit {
    //view
    protected Bitmap bitmap;
    //position
    protected int x, y;
    //size
    protected int width, height;
    //limit
    protected int minX, minY, maxX, maxY;

    public Spirit(int width, int height, int x, int y, int color, int minX, int maxX, int minY, int maxY) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.eraseColor(color);
    }

    public void update(int x, int y) {
        this.x = x < minX ? minX : x > maxX ? maxX : x;
        this.y = y < minY ? minY : y > maxY ? maxY : y;
    }

    public boolean impact(Spirit s2) {
        if (this.getX2() < s2.getX() || this.getX() > s2.getX2() || this.getY2() < s2.getY() || this.getY() > s2.getY2()) {
            return false;
        } else {
            return true;
        }
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

    public int getX2() {
        return x + width;
    }

    public int getY2() {
        return y + height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getMinX() {
        return minX;
    }

    public int getMinY() {
        return minY;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMaxY() {
        return maxY;
    }
}
