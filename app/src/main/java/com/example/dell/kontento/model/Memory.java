package com.example.dell.kontento.model;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class Memory {
    private static final float PREFERRED_WIDTH = 960;
    private static final float PREFERRED_HEIGHT = 640;
    private String title;
    private String image;
    private String date;

    public static final int COL_ID = 0;
    public static final int COL_TITLE = 1;
    public static final int COL_IMAGE = 2;
    // this is for the date and time
    public static final int COL_DATE = 3;

    public Memory(Cursor cursor) {
        this.title = cursor.getString(COL_TITLE);
        this.image = cursor.getString(COL_IMAGE);
        //this is for the date and time
        this.date = cursor.getString(COL_DATE);
    }

    public Memory(String title, Bitmap image, String date) {
        this.title = title;
        this.image = bitmapToString(resizeBitmap(image));
        //this is for the date and time
        this.date = date;
    }

    public String getTitle() {
        return this.title;
    }

    public Bitmap getImage() {
        return stringToBitmap(this.image);
    }

    // this is for the date and time
    public String getDate() { return this.date; }

    public String getImageAsString() {
        return this.image;
    }

    private static String bitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    private static Bitmap stringToBitmap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public static Bitmap resizeBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float scaleWidth = PREFERRED_WIDTH / width;
        float scaleHeight = PREFERRED_HEIGHT / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bitmap, 0, 0, width, height, matrix, false);
        bitmap.recycle();
        return resizedBitmap;
    }
}
