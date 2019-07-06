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
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import br.com.miller.muckup.models.User;

public class FirebaseImage {

    private FirebaseStorage firebaseStorage;
    private FirebaseImageListener firebaseImageListener;

    public FirebaseImage(Context context) {

        firebaseStorage = FirebaseStorage.getInstance();

        if(context instanceof FirebaseImageListener)
            firebaseImageListener = (FirebaseImageListener) context;

    }

    public void uploadImageFirebase(User user, Bitmap image){

        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

        StorageReference storageReference = firebaseStorage.getReference();

        StorageReference usuarioRef = storageReference.child("images")
                .child("users")
                .child(user.getCity())
                .child(user.getId_firebase() + ".jpg");

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        if(image != null){

            image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

            byte[] data = byteArrayOutputStream.toByteArray();

            UploadTask uploadTask = usuarioRef.putBytes(data);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });
        }
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
