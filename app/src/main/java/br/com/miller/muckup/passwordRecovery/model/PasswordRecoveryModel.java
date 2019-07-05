package br.com.miller.muckup.passwordRecovery.model;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import br.com.miller.muckup.passwordRecovery.tasks.PasswordRecoveryTask;

public class PasswordRecoveryModel {

    private PasswordRecoveryTask.PasswordRecoveryModel passwordRecoveryModelListener;
    private FirebaseAuth firebaseAuth;

    public PasswordRecoveryModel(PasswordRecoveryTask.PasswordRecoveryModel passwordRecoveryModelListener) {

        firebaseAuth = FirebaseAuth.getInstance();

        this.passwordRecoveryModelListener = passwordRecoveryModelListener;

    }

    public void reset(String email){

        firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){

                            passwordRecoveryModelListener.recoveryOk();

                        }else{

                            passwordRecoveryModelListener.recoveryFailed();

                        }

                    }
                });
    }

}
