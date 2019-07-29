package br.com.miller.muckup.jobs;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import br.com.miller.muckup.MainActivity;
import br.com.miller.muckup.R;
import br.com.miller.muckup.domain.Buy;
import br.com.miller.muckup.utils.StringUtils;
import br.com.miller.muckup.utils.image.ImageUtils;

public class FirebaseJobs extends JobService{

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    public static final String ID = FirebaseJobs.class.getName();
    private boolean foreground = true;
    private boolean isWorking = false;
    private boolean jobCancelled = false;

    @Override
    public boolean onStartJob(@NotNull JobParameters params) {

        Log.w(ID, "start");


        isWorking = true;

        startWork(params);

        return isWorking;
    }

    public void startWork(final JobParameters params){
        new Thread(new Runnable() {
            @Override
            public void run() {
                checkModifications(params);
            }
        }).start();
    }

    @Override
    public boolean onStopJob(@NotNull JobParameters params) {

        jobCancelled = true;

        Log.w(ID, "stop");

        removeListener(params);

        jobFinished(params, isWorking);

        return jobCancelled;
    }

    public void removeListener(JobParameters params){

        firebaseDatabase.getReference()
                .child("buys")
                .child(Objects.requireNonNull(Objects.requireNonNull(params.getExtras()).getString("city")))
                .child("users")
                .child(Objects.requireNonNull(params.getExtras().getString("userId")))
                .removeEventListener(childEventListener);

    }

    public void checkModifications(JobParameters params){

        firebaseDatabase.getReference()
                .child("buys")
                .child(Objects.requireNonNull(Objects.requireNonNull(params.getExtras()).getString("city")))
                .child("users")
                .child(Objects.requireNonNull(params.getExtras().getString("userId")))
                .addChildEventListener(childEventListener);

    }

    private ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            if(!isAppOnForeground(getApplicationContext())){
                try {
                    sendNotification(Objects.requireNonNull(dataSnapshot.getValue(Buy.class)));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    private void sendNotification(Buy buy){

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            NotificationCompat.Builder notificationCompat = null;

            switch (buy.getStatus()) {

                case "sended": {

                    notificationCompat = new NotificationCompat.Builder(getApplicationContext(),
                            getString(R.string.default_notification_channel_id))
                            .setTicker("Compra enviada")
                            .setContentText("Compra enviada")
                            .setContentTitle("De: ".concat(buy.getStoreName()))
                            .setSubText("Às: ".concat(StringUtils.formatDate(buy.getDeliverDate())))
                            .setAutoCancel(true)
                            .setSmallIcon(R.drawable.ic_icon_bula_small)
                            .setSound(defaultSoundUri)
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            .setVibrate(new long[]{1000, 1000, 1000, 1000});

                    break;
                }

                case "received": {

                    notificationCompat = new NotificationCompat.Builder(getApplicationContext(),
                            getString(R.string.default_notification_channel_id))
                            .setContentText("Compra recebida")
                            .setTicker("Compra recebida")
                            .setContentTitle("De: ".concat(buy.getStoreName()))
                            .setSubText("Às: ".concat(StringUtils.formatDate(buy.getDeliverDate())))
                            .setAutoCancel(true)
                            .setSmallIcon(R.drawable.ic_icon_bula_small)
                            .setSound(defaultSoundUri)
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            .setVibrate(new long[]{1000, 1000, 1000, 1000});

                    break;
                }

                case "canceled": {

                    notificationCompat = new NotificationCompat.Builder(getApplicationContext(),
                            getString(R.string.default_notification_channel_id))
                            .setContentText("Compra cancelada")
                            .setTicker("Compra recebida")
                            .setContentTitle("De: ".concat(buy.getStoreName()))
                            .setSubText("Às: ".concat(StringUtils.formatDate(buy.getDeliverDate())))
                            .setAutoCancel(true)
                            .setSmallIcon(R.drawable.ic_icon_bula_small)
                            .setLargeIcon(ImageUtils.drawableToBitMap(R.drawable.ic_launcher_foreground, getApplicationContext()))
                            .setSound(defaultSoundUri)
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            .setVibrate(new long[]{1000, 1000, 1000, 1000});

                    break;
                }

            }

            if (notificationCompat != null) {

                notificationCompat.setContentIntent(sendNotificationBundle(MainActivity.class));

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

                notificationManager.notify((int) System.currentTimeMillis(), notificationCompat.build());

            }


    }

    public PendingIntent sendNotificationBundle(Class classSolicitada){

        Intent intent = new Intent(getApplicationContext(), classSolicitada);

        return PendingIntent.getActivity(this, 23, intent, PendingIntent.FLAG_UPDATE_CURRENT);

    }


    private boolean isAppOnForeground(Context context) {

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        if (activityManager != null){

                List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
            if (appProcesses == null) {
                return false;
            }
            final String packageName = context.getPackageName();
            for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName.equals(packageName)) {
                    return true;
                }
            }
            return false;
        }else{
            return  true;
        }
    }

}
