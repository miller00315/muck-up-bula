package br.com.miller.muckup.helpers;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

public class Permissions{

    private Activity act;
    private OnPermissionChanged listener;
    private String[] permissions;
    public static int pCode = 666;

    public Permissions(Activity act, OnPermissionChanged context) {

            this.act = act;
            listener =  context;

        permissions = new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.CAMERA
        };

    }

    public void checkSelfPermission(){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

            boolean flag = false;

            for(String s : permissions)
                if(ActivityCompat.checkSelfPermission(act, s) != PackageManager.PERMISSION_GRANTED)
                    flag = true;


                if(flag)
                    ActivityCompat.requestPermissions(act, permissions, pCode );
                else
                    listener.onPermissionChanged(true);

        }else{
            listener.onPermissionChanged(true);
        }
    }

    public interface OnPermissionChanged{

        void onPermissionChanged(boolean state);

    }

}
