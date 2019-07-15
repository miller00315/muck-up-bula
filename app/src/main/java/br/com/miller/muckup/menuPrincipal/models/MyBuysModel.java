package br.com.miller.muckup.menuPrincipal.models;

import android.support.annotation.NonNull;

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

    public void getBuys(String userCity, String userId){

        firebaseDatabase.getReference()
                .child("buys")
                .child(userCity)
                .child("users")
                .child(userId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()){

                            ArrayList<Buy> buys = new ArrayList<>();

                            for (DataSnapshot child : dataSnapshot.getChildren()) {

                                buys.add(child.getValue(Buy.class));
                            }

                            if(buys.size() > 0){

                                model.onBuySuccess(buys);

                            }else{
                                model.onBuyEmpty();
                            }
                        }else{

                            model.onBuyEmpty();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                        model.onBuyFailed();
                    }
                });
    }

}
