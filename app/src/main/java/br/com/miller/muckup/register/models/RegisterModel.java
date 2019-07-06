package br.com.miller.muckup.register.models;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import br.com.miller.muckup.models.User;
import br.com.miller.muckup.register.tasks.Task;

public class RegisterModel {

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private Task.Model model;

    public RegisterModel(Task.Model model) {
        this.model = model;

        this.firebaseAuth = FirebaseAuth.getInstance();
        this.firebaseDatabase = FirebaseDatabase.getInstance();

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
                    model.uploadImageListener(true, firebaseAuth.getCurrentUser());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    model.uploadImageListener(false, null);
                }
            });
        }
    }

    public void destroyRegisterFirebase(){

        if(firebaseAuth.getCurrentUser() != null)
            if(firebaseAuth.getCurrentUser().isAnonymous())
                firebaseAuth.getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                        model.destroyTemporaryUser();
                    }
                });

    }

    public void configureFirstUser() {
        if(firebaseAuth.getCurrentUser() == null)
            firebaseAuth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull com.google.android.gms.tasks.Task<AuthResult> task) {

                    model.firstUserConfigured(true);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    model.firstUserConfigured(false);

                }
            });
        else {

            firebaseAuth.signOut();
            configureFirstUser();

        }
    }

    private void generateRegisterDatabase(final User user){

        Map<String, Object> map = new HashMap<>();

        map.put(user.getId_firebase(), user);

        firebaseDatabase.getReference().child("users").child(user.getCity()).
                updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {

                model.onRegisterSuccess(user);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                model.onRegisterError(e);
            }
        });

    }

    private void registerCity(final User user){

        Map<String, Object> map = new HashMap<>();

        map.put(user.getId_firebase(), user.getCity());

        firebaseDatabase.getReference().child("city").updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                generateRegisterDatabase(user);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                model.onRegisterError(e);

            }
        });
    }

    public void registerUser(final User user, String password){

        final AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), password);

        if(firebaseAuth != null)
            Objects.requireNonNull(firebaseAuth.getCurrentUser()).linkWithCredential(credential)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull com.google.android.gms.tasks.Task<AuthResult> task) {

                            user.setId_firebase(firebaseAuth.getCurrentUser().getUid());

                            registerCity(user);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    model.onRegisterError(e);
                }
            });

    }

    public void rollBack(User user){

    }

}
