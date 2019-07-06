package br.com.miller.muckup.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import br.com.miller.muckup.R;

public class ImageUtils {

    public static Bitmap getImageUser(ImageView image){

        Bitmap bitmap;

        if (image.getDrawable() instanceof BitmapDrawable) {
            bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        } else {
            Drawable d = image.getDrawable();
            bitmap = Bitmap.createBitmap(d.getIntrinsicWidth(), d.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            d.draw(canvas);
        }

        return bitmap;

    }

    public static void setImageFromMemory(Intent data, Activity act, ImageView imageView){

        Uri uri;

        if(data != null){

            Bundle bundle = data.getExtras();

            if(bundle != null){

                uri = (Uri) bundle.get(Intent.EXTRA_STREAM);

            }else{

                uri = data.getData();
            }

            if(uri != null){

                Picasso.get().load(uri).error(R.mipmap.ic_launcher).into(imageView);

            }
        }



    }
}
