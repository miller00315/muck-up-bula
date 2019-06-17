package br.com.miller.muckup.api;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFirebase {

    private FirebaseAuth mAuth;
    private Context context;
    private LoginFirebseListener loginFirebseListener;

    public LoginFirebase(Context context) {
        this.mAuth = FirebaseAuth.getInstance();

        if(context instanceof  LoginFirebseListener)
            this.loginFirebseListener = (LoginFirebseListener) context;
    }

    public void login(String user, String password){

        mAuth.signInWithEmailAndPassword(user, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                loginFirebseListener.onLoginChanged(authResult.getUser());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loginFirebseListener.onLoginChanged(null);
            }
        });

    }


    public interface  LoginFirebseListener{

        void onLoginChanged(FirebaseUser firebaseUser);

    }

}
