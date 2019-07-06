package br.com.miller.muckup.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import br.com.miller.muckup.R;

@SuppressLint("Registered")
public class AlertDialogUtils extends Application {

    private AlertDialogUtils.AltertDialogUtilsTask alertTask;
    private Context context;

    public AlertDialogUtils(Context context, AltertDialogUtilsTask alertTask) {
        this.alertTask = alertTask;
        this.context = context;
    }

    public void creatAlertImageView(final View view, final int type){

        AlertDialog alertDialog;

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setView(view);

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

              alertTask.onAlertNegative();

            }
        });

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                ImageView imageView = view.findViewById(R.id.image_memory);
                alertTask.onAlertPositive(ImageUtils.getImageUser(imageView), type);
            }
        });

        alertDialog = builder.create();

        alertDialog.show();
    }

    public void createAlertEditText(final View view, Object o, final int type){

        AlertDialog alertDialog;

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setView(view);

        final EditText editText = view.findViewById(R.id.edit_text);
        editText.setText((String) o);

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                alertTask.onAlertNegative();
            }
        });

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                alertTask.onAlertPositive(editText.getText().toString(), type);

            }
        });

        alertDialog = builder.create();

        alertDialog.show();

    }


    public interface AltertDialogUtilsTask{

        void onAlertPositive(Object o, int type);
        void onAlertNegative();
    }
}
