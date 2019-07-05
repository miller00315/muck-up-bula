package br.com.miller.muckup.login.models;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import br.com.miller.muckup.login.tasks.LoginTasks;

public class LoginModel {

    private LoginTasks.Model model;
    private FirebaseAuth mAuth;

    public LoginModel(LoginTasks.Model model) {
        this.mAuth = FirebaseAuth.getInstance();
        this.model = model;
        mAuth = FirebaseAuth.getInstance();
    }

    public void login(String email, String password){

        mAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                model.loginSuccess(authResult.getUser());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                model.loginFail();
            }
        });

    }

}
