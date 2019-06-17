package br.com.miller.muckup.api;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import br.com.miller.muckup.models.User;

public class RegisterFirebase {

    private FirebaseAuth firebaseAuth;
    private Context context;
    private FirebaseDatabase firebaseDatabase;
    private RegisterFirebaseListener registerFirebaseListener;

    public RegisterFirebase(Context context) {
        this.context = context;

        firebaseAuth = FirebaseAuth.getInstance();

        firebaseDatabase = FirebaseDatabase.getInstance();

        if(FirebaseAuth.getInstance().getCurrentUser() != null)
            Log.w("tste", FirebaseAuth.getInstance().getUid());

        registerFirebaseListener = (RegisterFirebaseListener) context;
    }

    public void configureFirstUser(){

        if(firebaseAuth.getCurrentUser() == null)
            firebaseAuth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    registerFirebaseListener.firstUserConfigured(true);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    registerFirebaseListener.firstUserConfigured(false);

                }
            });
        else {

             firebaseAuth.signOut();
             configureFirstUser();

        }
    }

    public void uploadImageFirebase(User user, Bitmap image){

        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

        StorageReference storageReference = firebaseStorage.getReference();

        StorageReference usuarioRef = storageReference.child("images")
                .child("users")
                .child("Lavras")
                .child(user.getId_firebase() + ".jpg");

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        if(image != null){

            image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

            byte[] data = byteArrayOutputStream.toByteArray();

            UploadTask uploadTask = usuarioRef.putBytes(data);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                   registerFirebaseListener.uploadImageListener(true, firebaseAuth.getCurrentUser());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                   registerFirebaseListener.uploadImageListener(false, null);
                }
            });
        }
    }

    public void destroyRegisterFirebase(){

        if(firebaseAuth.getCurrentUser() != null)
            if(firebaseAuth.getCurrentUser().isAnonymous())
                firebaseAuth.getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(context, "Usuário temporário excluído",Toast.LENGTH_LONG).show();
                    }
                });

    }

    private void generateRegisterDatabase(final User user){

        Map<String, Object> map = new HashMap<>();

        map.put(user.getId_firebase(), user);

        firebaseDatabase.getReference().child("users").child(user.getCity()).
                updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                Toast.makeText(context,"Usuário registrado!",Toast.LENGTH_LONG).show();

                registerFirebaseListener.onRegisterUser(user);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(context,"Erro ao registrar usuário!",Toast.LENGTH_LONG).show();

                registerFirebaseListener.onRegisterError();
            }
        });

    }

    public void registerCity(final User user){

        Map<String, Object> map = new HashMap<>();

        map.put(user.getId_firebase(), user.getCity());

        firebaseDatabase.getReference().child("city").updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public void registerUser(final User user, String password){

        final AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), password);

        if(firebaseAuth != null)
        Objects.requireNonNull(firebaseAuth.getCurrentUser()).linkWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        user.setId_firebase(firebaseAuth.getCurrentUser().getUid());

                        generateRegisterDatabase(user);

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                registerFirebaseListener.onRegisterError();
            }
        });

    }

    public void rollBack(){
        //TODO: implementar roolback
    }


    public interface RegisterFirebaseListener{

        void uploadImageListener(boolean state, FirebaseUser firebaseUser);
        void onRegisterError();
        void onRegisterUser(User user);
        void firstUserConfigured(boolean status);
    }

}
