package br.com.miller.muckup.store.buy.model;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import br.com.miller.muckup.models.Buy;
import br.com.miller.muckup.store.buy.tasks.Tasks;

public class BuyModel {

    private Tasks.Model model;
    private FirebaseDatabase firebaseDatabase;

    public BuyModel(Tasks.Model model) {
        this.model = model;
        firebaseDatabase = FirebaseDatabase.getInstance();

    }

    public void registerBuy(final Buy buy){

        String id = new Date().toString();

        Map<String, Object> map = new HashMap<>();

        map.put(id, buy);

        firebaseDatabase.getReference()
                .child("buys")
                .child(buy.getStoreCity())
                .child("stores")
                .child(String.valueOf(buy.getStoreId()))
                .updateChildren(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        registerStoreBuy(buy);

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                model.failedBuy();

            }
        });

    }

    private void registerStoreBuy(Buy buy){

        Map<String, Object> map = new HashMap<>();

        map.put(buy.getId(), buy);

        firebaseDatabase.getReference()
                .child("buys")
                .child(buy.getStoreCity())
                .child("stores")
                .child(String.valueOf(buy.getStoreId()))
                .updateChildren(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        model.successBuy();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                model.failedBuy();
            }
        });
    }


}
