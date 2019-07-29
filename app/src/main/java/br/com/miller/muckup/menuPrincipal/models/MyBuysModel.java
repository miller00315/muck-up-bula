package br.com.miller.muckup.menuPrincipal.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.com.miller.muckup.domain.Buy;
import br.com.miller.muckup.menuPrincipal.tasks.MyBuysTasks;

public class MyBuysModel {

    private FirebaseDatabase firebaseDatabase;
    private MyBuysTasks.Model model;
    public MyBuysModel(MyBuysTasks.Model model) {

        this.model = model;

        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    public void temporaryVerify(String userCity, String userId){

        firebaseDatabase.getReference()
                .child("buys")
                .child(userCity)
                .child("users")
                .child(userId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(!dataSnapshot.exists()){
                            model.onBuyEmpty();
                        }else{

                            ArrayList<Buy> buys = new ArrayList<>();

                            for(DataSnapshot child: dataSnapshot.getChildren()){

                                buys.add(child.getValue(Buy.class));
                            }

                            model.onBuySuccess(buys);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        model.onBuyFailed();
                    }
                });

    }

    public void removeListener(String userCity, String userId){

        firebaseDatabase.getReference()
                .child("buys")
                .child(userCity)
                .child("users")
                .child(userId)
                .removeEventListener(childEventListener);

    }


    public void getBuys(String userCity, String userId){

        firebaseDatabase.getReference()
                .child("buys")
                .child(userCity)
                .child("users")
                .child(userId)
                .addChildEventListener(childEventListener);

    }

    private ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            if(dataSnapshot.exists())
                model.onBuyAdded(dataSnapshot.getValue(Buy.class));

        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            if(dataSnapshot.exists())
                model.onBuyUpdated(dataSnapshot.getValue(Buy.class));

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            if(dataSnapshot.exists())
                model.onBuyRemoved(dataSnapshot.getValue(Buy.class));
        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            model.onBuyFailed();
        }
    };

}
