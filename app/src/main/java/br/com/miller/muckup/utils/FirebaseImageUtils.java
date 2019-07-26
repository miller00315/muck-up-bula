package br.com.miller.muckup.utils;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import br.com.miller.muckup.domain.User;

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
                .child(user.getId_firebase().concat(".jpg"));

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

    public void downloadImage(String type, String city, String image){

        storageReference.child("images")
                .child(type)
                .child(city)
                .child(image.concat(".jpg"))
                .getBytes(Long.MAX_VALUE)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        firebaseImageTask.onDownloadImageSuccess(ImageUtils.convertByteArraytoBitmap(bytes));
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                firebaseImageTask.onDowloadImageFail();
            }
        });
    }


    public interface FirebaseImageTask{

        void onImageUploadSucces(Bitmap bitmap);
        void onImageUploadFails();
        void onImageDeleteSuccess();
        void onImageDeleteFailed();
        void onDownloadImageSuccess(Bitmap bitmap);
        void onDowloadImageFail();
        void downloaImage(String type, String city, String image);
    }


}
