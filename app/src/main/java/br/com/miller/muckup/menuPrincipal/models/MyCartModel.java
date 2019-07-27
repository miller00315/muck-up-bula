package br.com.miller.muckup.menuPrincipal.models;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.com.miller.muckup.domain.Offer;
import br.com.miller.muckup.menuPrincipal.tasks.MyCartTasks;

public class MyCartModel {

    private FirebaseDatabase firebaseDatabase;
    private MyCartTasks.Model model;

    private MyCartModel(MyCartTasks.Model model) {
        this.model = model;
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    public static MyCartModel newInstance(MyCartTasks.Model model){

        return new MyCartModel(model);
    }

    public void deleteItem(String city, String idFirebase, String item){

        firebaseDatabase.getReference()
                .child("carts")
                .child(city)
                .child(idFirebase)
                .child(item)
                .removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        model.onDeleteItemSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        model.onDeleteItemFailed();
                    }
                });
    }

    public void deleteAllItems(String city, String idFirebase){

        firebaseDatabase.getReference()
                .child("carts")
                .child(city)
                .child(idFirebase)
                .removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        model.onDeleteAllSuccess();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        model.onDeleteAllFailed();
                    }
                });

    }

    public void getOffers(String city, String idFirebase){

        firebaseDatabase.getReference()
                .child("carts")
                .child(city)
                .child(idFirebase)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        ArrayList<Offer> offers = new ArrayList<>();

                        if(dataSnapshot.exists()) {

                            for (DataSnapshot child : dataSnapshot.getChildren()) {

                                offers.add(child.getValue(Offer.class));
                            }

                            model.onGetOffersSuccess(offers);

                        }else{
                            model.onGetOffersFailed();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                        model.onGetOffersFailed();
                    }
                });
    }
}
