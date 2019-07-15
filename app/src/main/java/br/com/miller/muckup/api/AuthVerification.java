package br.com.miller.muckup.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import br.com.miller.muckup.helpers.Constants;
import br.com.miller.muckup.domain.User;

public class AuthVerification {

    private FirebaseAuth mAuth;
    private SharedPreferences sharedPreferences;
    private FirebaseAuth.AuthStateListener authStateListener;
    private AuthVerificationListener authVerificationListener;
    private FirebaseDatabase firebaseDatabase;

    public AuthVerification(Context context, AuthVerificationListener authVerificationListener) {

        mAuth = FirebaseAuth.getInstance();
        authStateListener = getAuthStateListener();
        firebaseDatabase = FirebaseDatabase.getInstance();
        sharedPreferences   = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);

        this.authVerificationListener = authVerificationListener ;

        addListener();
    }

    private void getDataLogin(final FirebaseUser user){

        if(sharedPreferences.contains("city"))
            login(user.getUid(), sharedPreferences.getString("city", ""));
        else {

            firebaseDatabase.getReference().child("city").child(user.getUid()).
                    addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if(dataSnapshot.exists())
                                login(user.getUid(), Objects.requireNonNull(dataSnapshot.getValue()).toString());
                            else {
                                authVerificationListener.errorLogin();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                            authVerificationListener.errorLogin();

                        }
                    });
        }

    }

    private FirebaseAuth.AuthStateListener getAuthStateListener (){

        return  new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                final FirebaseUser user = firebaseAuth.getCurrentUser();

                if(user != null && !user.isAnonymous()){

                    getDataLogin(user);

                }else{
                    authVerificationListener.errorLogin();
                }

            }
        };
    }

    public void addListener(){
        if(authStateListener != null)
            mAuth.addAuthStateListener(authStateListener);
    }

    public void removeListener(){
        if(authStateListener != null)
            mAuth.removeAuthStateListener(authStateListener);
    }

    private void login(final String id_firebase, String city){

        firebaseDatabase.getReference().child("users").child(city)
                .child(id_firebase).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    final User user = dataSnapshot.getValue(User.class);

                    if(user != null){

                        updateUser(user);

                    }else{

                        authVerificationListener.errorLogin();
                    }

                }else{
                    authVerificationListener.errorLogin();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                authVerificationListener.errorLogin();
            }
        });
    }

    private void updateUser(final User user){

        Map<String, Object> map = new HashMap<>();

        assert user != null;

        map.put(user.getId_firebase(), user.getCity());

        firebaseDatabase.getReference().child("city").updateChildren(map)
                .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                authVerificationListener.errorLogin();
            }
        }).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString(Constants.USER_NAME, user.getName());
                editor.putString(Constants.USER_SURNAME, user.getSurname());
                editor.putString(Constants.USER_EMAIL, user.getEmail());
                editor.putString(Constants.USER_CITY, user.getCity());
                editor.putString(Constants.USER_ADDRESS, user.getAddress() != null ? user.getAddress().getAddress() : "");
                editor.putString(Constants.USER_ID_FIREBASE, user.getId_firebase());

                editor.apply();

                authVerificationListener.login(user);
            }
        });
    }

    public interface AuthVerificationListener {

        void login(User user);

        void errorLogin();
    }

}
