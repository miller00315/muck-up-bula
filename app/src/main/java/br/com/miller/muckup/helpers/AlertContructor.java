package br.com.miller.muckup.helpers;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.miller.muckup.R;

public class AlertContructor {

    private Context context;

    private OnAlertInteract onAlertInteract;


    public AlertContructor(Context context) {
        this.context = context;

        if(context instanceof OnAlertInteract)
            onAlertInteract = (OnAlertInteract) context;

    }

    public void personalizedAlert(View view){

        AlertDialog alertDialog;

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setView(view);

        final EditText editText = view.findViewById(R.id.message_sac);

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                onAlertInteract.onAlertNegative();
            }
        });

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String text = editText.getText().toString();

                if(!text.isEmpty()){
                    onAlertInteract.onAlertPositive(text);
                }else{
                    onAlertInteract.onAlertError();
                }
            }
        });

        alertDialog = builder.create();

        alertDialog.show();
    }

    public void personalizedAlert(View view, final Object o){

        AlertDialog alertDialog;

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setView(view);

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                onAlertInteract.onAlertNegative();
            }
        });

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(o != null){
                    onAlertInteract.onAlertPositive(o);
                }else{
                   onAlertInteract.onAlertError();
                }
            }
        });

        alertDialog = builder.create();

        alertDialog.show();
    }

    public void simpleAlert(String title, String message, final Object o){

        AlertDialog alertDialog;

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(title);

        builder.setMessage(message);

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(onAlertInteract != null)
                    onAlertInteract.onAlertNegative();

            }
        });

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(onAlertInteract != null)
                    onAlertInteract.onAlertPositive(o);

            }
        });

        alertDialog = builder.create();

        alertDialog.show();

    }

    public interface OnAlertInteract {

        void onAlertPositive(Object object);

        void onAlertNegative();

        void onAlertError();
    }
}
