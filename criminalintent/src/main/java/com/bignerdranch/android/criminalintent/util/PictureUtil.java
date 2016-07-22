package com.bignerdranch.android.criminalintent.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * Created by joseph on 2016/7/22.
 */
public class PictureUtil {
    private static final String TAG = "PictureUtil";

    public static BitmapDrawable getScaledBitmap(Context context, String path){
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        int screenWidth = wm.getDefaultDisplay().getWidth();
        int screenHeight = wm.getDefaultDisplay().getHeight();
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(path, opts);
        int width = opts.outWidth;
        int height = opts.outHeight;
        int scaleX = width/screenWidth;
        int scaleY = height/screenHeight;
        int scale = scaleX > scaleY?scaleX:scaleY;
        opts.inSampleSize = scale;
        opts.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(path, opts);

        return new BitmapDrawable(context.getResources(), bitmap);
    }

    public static void cleanImageView(ImageView imageView){
        Drawable drawable = imageView.getDrawable();
        if(!(drawable instanceof BitmapDrawable)){
            return;
        }

        ((BitmapDrawable) drawable).getBitmap().recycle();
        imageView.setImageDrawable(null);
    }
}
