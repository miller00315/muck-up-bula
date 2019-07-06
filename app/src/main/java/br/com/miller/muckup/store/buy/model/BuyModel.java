package br.com.miller.muckup.store.buy.model;

import android.support.annotation.NonNull;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
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

    private void registerStoreBuy(final Buy buy){

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

                        model.onSuccessBuy(buy);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                model.failedBuy();
            }
        });
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
                                model.onSuccessBuys(buys);
                            }else{
                                model.onSuccessBuys(null);
                            }
                        }else{
                            model.onSuccessBuys(null);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        model.failedBuys();
                    }
                });
    }

    public void getBuysCount(String userCity, String userId){

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        firebaseDatabase.getReference()
                .child("buys")
                .child(userCity)
                .child("users")
                .child(userId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()){
                           model.onBuyCountSuccess(String.valueOf(dataSnapshot.getChildrenCount()));
                        }else{
                            model.onBuyCountSuccess(String.valueOf(0));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        model.failedBuy();
                    }
                });
    }
}
