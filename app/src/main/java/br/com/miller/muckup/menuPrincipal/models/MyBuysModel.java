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
    private ArrayList<Buy> buys;

    public MyBuysModel(MyBuysTasks.Model model) {

        this.model = model;

        buys = new ArrayList<>();

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
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

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

            buys.add(dataSnapshot.getValue(Buy.class));

            model.onBuySuccess(buys);

        }

        //TODO: Otimizar este array
        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            if(dataSnapshot.exists()) {

                Buy buy = dataSnapshot.getValue(Buy.class);

                if(buy != null)
                for (int i = 0; i < buys.size(); i++) {

                    if (buy.getId().equals(buys.get(i).getId())) {

                        buys.set(i, buy);
                        model.onBuySuccess(buys);
                        break;
                    }
                }
            }
        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

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
