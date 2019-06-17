package br.com.miller.muckup.api;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;

public class FirebaseImage {

    private Context context;
    private FirebaseStorage firebaseStorage;
    private FirebaseImageListener firebaseImageListener;

    public FirebaseImage(Context context) {
        this.context = context;
        firebaseStorage = FirebaseStorage.getInstance();

        if(context instanceof FirebaseImageListener)
            firebaseImageListener = (FirebaseImageListener) context;

    }

    public void downloadFirebaseImage(String type, String city, String image,final ImageView imageView){

        try{

            firebaseStorage.getReference()
                .child("images/".concat(type).concat("/").concat(city).concat("/").concat(image))
                .getBytes(Long.MAX_VALUE)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {

                        imageView.setImageBitmap(convertByteArraytoBytmap(bytes));


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    firebaseImageListener.onImageDownloadError();

                    }
                });

            }catch (Exception e){
                e.printStackTrace();
            }

    }

    private Bitmap convertByteArraytoBytmap(byte[] bytes){

        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public interface FirebaseImageListener{

        void onImageDownloadSuccess();

        void onImageDownloadError();
    }

}
