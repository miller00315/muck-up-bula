package br.com.miller.muckup.api;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import br.com.miller.muckup.models.User;

public class FirebaseUser {

    private Context context;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseUserListener firebaseUserListener;

    public FirebaseUser(Context context) {
        this.context = context;

        firebaseDatabase = FirebaseDatabase.getInstance();

        if(context instanceof FirebaseUserListener)
            firebaseUserListener = (FirebaseUserListener) context;
    }


    public void getUserData(String city, String id_firebase){

        firebaseDatabase.getReference().child("users")
                .child(city)
                .child(id_firebase)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        User user = dataSnapshot.getValue(User.class);

                        if(dataSnapshot.exists() && user != null)
                            firebaseUserListener.onUserDownload(user);

                      //  Log.w("FirebaseUser", dataSnapshot.getValue().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                        firebaseUserListener.onUserDownload(null);
                    }
                });

    }

    public interface FirebaseUserListener{

        void onUserDownload(User user);

    }

}
