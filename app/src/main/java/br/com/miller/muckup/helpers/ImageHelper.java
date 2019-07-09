package br.com.miller.muckup.helpers;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import br.com.miller.muckup.R;

public class ImageHelper {

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

                Picasso.get()
                        .load(uri)
                        .error(R.drawable.ic_icon_bula)
                        .into(imageView);

            }
        }
    }
}
