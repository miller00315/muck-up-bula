package br.com.miller.muckup.api;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import br.com.miller.muckup.menuPrincipal.adapters.OffersRecyclerAdapter;
import br.com.miller.muckup.models.Offer;

public class FirebaseOffer {

    private FirebaseOfferListener firebaseOfferListener;
    private FirebaseDatabase firebaseDatabase;

    public FirebaseOffer(FirebaseOfferListener firebaseOfferListener) {

        firebaseDatabase = FirebaseDatabase.getInstance();
            this.firebaseOfferListener = firebaseOfferListener;
    }

    public void firebaseGetOffer(String city, String type, String id){

        firebaseDatabase.getReference().child("offers").child(city)
                .child(type)
                .child(id)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()){

                            firebaseOfferListener.firebaseOfferReceiver(dataSnapshot.getValue(Offer.class));

                        }else{
                            firebaseOfferListener.firebaseOfferReceiver(null);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        firebaseOfferListener.firebaseOfferReceiver(null);
                    }
                });

    }

    public void FirebaseGetOffers(String city, final String departament_id, final OffersRecyclerAdapter item){

        firebaseDatabase.getReference()
                .child("offers")
                .child(city)
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {

                    for(DataSnapshot child : dataSnapshot.getChildren()) {

                        for (DataSnapshot grandson : child.getChildren()){

                            Offer offer = grandson.getValue(Offer.class);

                            if(offer != null && departament_id != null)

                                if(String.valueOf(offer.getDepartamentId()).equals(departament_id)){

                                   item.addOffer(offer);
                                }
                        }
                    }

                }else
                    Log.w("test", "erro snapshot");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("test", "erro snapshot");
            }
        });

    }

    public void firebaseGetOffersByDepartamentandStore(String city, final String store_id, final String departament_id,
                                                       final OffersRecyclerAdapter offersRecyclerAdapter){

        firebaseDatabase.getReference()
                .child("offers")
                .child(city)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()) {

                            for(DataSnapshot child : dataSnapshot.getChildren()) {

                                for (DataSnapshot grandson : child.getChildren()){

                                    Offer offer = grandson.getValue(Offer.class);

                                    if(offer != null && departament_id != null)

                                        if(String.valueOf(offer.getStoreId()).equals(store_id)
                                        && String.valueOf(offer.getDepartamentId()).equals(departament_id)){

                                            offersRecyclerAdapter.addOffer(offer);
                                        }
                                }
                            }

                        }else
                            Log.w("test", "erro snapshot");

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


    }



    public interface FirebaseOfferListener{

        void firebaseOfferReceiver(Offer offer);


    }
}
