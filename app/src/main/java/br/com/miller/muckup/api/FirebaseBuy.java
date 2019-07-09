package br.com.miller.muckup.api;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import br.com.miller.muckup.models.Buy;

public class FirebaseBuy {

    private Context context;
    private FirebaseBuyListener firebaseBuyListener;
    private FirebaseDatabase firebaseDatabase;

    public FirebaseBuy(Context context) {
        this.context = context;

        firebaseDatabase = FirebaseDatabase.getInstance();
        if(context instanceof  FirebaseBuyListener)
            firebaseBuyListener = (FirebaseBuyListener) context;
    }

    public void registerBuy(final ArrayList<Buy> buies, String userId, String userCity, String address){

        for(final Buy buy : buies){

            String id = new Date().toString()
                    .concat(String.valueOf(buy.getStoreId()))
                    .concat(userId);

            Map<String, Object> map = new HashMap<>();

            buy.setId(id);
            buy.setAddress(address);

            map.put(id, buy);

            firebaseDatabase.getReference()
                    .child("buys")
                    .child(userCity)
                    .child("users")
                    .child(userId)
                    .updateChildren(map)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                    registerStoreBuy(buy);

                    if(buies.remove(buy))
                        if(buies.size() == 0)
                            firebaseBuyListener.registerBuy(true);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    firebaseBuyListener.registerBuy(false);
                }
            });
        }
    }

    public void registerStoreBuy(Buy buy){

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

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public static void getBuysCount(String userCity, String userId, final TextView textView){

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
                            textView.setText(String.valueOf(dataSnapshot.getChildrenCount()));
                        }else{
                            textView.setText(String.valueOf(0));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        textView.setText(String.valueOf(0));
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
                                firebaseBuyListener.onReceiverBuy(buys);
                            }else{
                                firebaseBuyListener.onReceiverBuy(null);
                            }
                        }else{
                            firebaseBuyListener.onReceiverBuy(null);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        firebaseBuyListener.onReceiverBuy(null);
                    }
                });
    }

    public void editBuy(){}

    public void deletBuy(){}

    public interface FirebaseBuyListener{

        void registerBuy(Boolean registered);

        void onReceiverBuy(ArrayList<Buy> buys);

        void evaluateBuy(Buy buy);
    }

}
