package br.com.miller.muckup.api;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
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

import br.com.miller.muckup.domain.Offer;

public class FirebaseCart {

    private Context context;

    private FirebaseDatabase firebaseDatabase;

    private FirebaseCartListener firebaseCartListener;

    public FirebaseCart(Context context) {
        this.context = context;

        firebaseDatabase = FirebaseDatabase.getInstance();

        if(context instanceof  FirebaseCartListener)
            firebaseCartListener = (FirebaseCartListener) context;
    }

    public void firebaseCartAddOffer(final Offer offer, String city, String idFirebase){

        Map<String, Object> map = new HashMap<>();

        offer.setCartId(new Date().toString());

        map.put(offer.getCartId(), offer);

        firebaseDatabase.getReference().child("carts")
                .child(city)
                .child(idFirebase)
                .updateChildren(map)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        firebaseCartListener.firebaseCartListnerReceiver(null);
                    }
                }).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        firebaseCartListener.firebaseCartListnerReceiver(offer);
                    }
                });

    }

    public void firebaseCartDeleteAll(String city, String idFirebase){

        firebaseDatabase.getReference()
                .child("carts")
                .child(city)
                .child(idFirebase)
                .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                firebaseCartListener.firebaseCartOnItemDeleted(true);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                firebaseCartListener.firebaseCartOnItemDeleted(false);

            }
        });

    }

    public void firebaseCartDeleteItem(String city, String idFirebase, String item){

        firebaseDatabase.getReference()
                .child("carts")
                .child(city)
                .child(idFirebase)
                .child(item)
                .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                firebaseCartListener.firebaseCartOnItemDeleted(true);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                firebaseCartListener.firebaseCartOnItemDeleted(false);

            }
        });
    }

    public void firebaseCartgetOffers(String city, String idFirebase){

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

                        }

                        firebaseCartListener.firebaseCartListenerReceiverOffers(offers);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                        firebaseCartListener.firebaseCartListenerReceiverOffers(null);
                    }
                });
    }


    public interface FirebaseCartListener{

        void firebaseCartListnerReceiver(Offer offer);

        void firebaseCartListenerReceiverOffers(ArrayList<Offer> offers);

        void firebaseCartOnItemDeleted(boolean status);

    }

}
