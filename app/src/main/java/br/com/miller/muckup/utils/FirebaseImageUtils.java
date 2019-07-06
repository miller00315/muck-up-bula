package br.com.miller.muckup.utils;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import br.com.miller.muckup.models.User;

public class FirebaseImageUtils {

    private FirebaseImageTask firebaseImageTask;
    private StorageReference storageReference;

    public FirebaseImageUtils(FirebaseImageTask firebaseImageTask) {
        this.firebaseImageTask = firebaseImageTask;
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    public void uploadImageFirebase(User user, final Bitmap image){

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
                    firebaseImageTask.onImageUploadSucces(image);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    firebaseImageTask.onImageUploadFails();
                }
            });
        }
    }

    public void deleteImage(User user){

        storageReference.child("image")
                .child("user")
                .child(user.getCity())
                .child(user.getId_firebase().concat(".jpg"))
                .delete().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                firebaseImageTask.onImageDeleteFailed();
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                firebaseImageTask.onImageDeleteSuccess();
            }
        });
    }

    public interface FirebaseImageTask{

        void onImageUploadSucces(Bitmap bitmap);
        void onImageUploadFails();
        void onImageDeleteSuccess();
        void onImageDeleteFailed();
    }

}
