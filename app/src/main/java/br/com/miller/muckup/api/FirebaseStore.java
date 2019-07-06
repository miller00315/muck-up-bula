package br.com.miller.muckup.api;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.com.miller.muckup.models.Store;

public class FirebaseStore {

    private FirebaseDatabase firebaseDatabase;
    private FirebaseStoreListener firebaseStoreListener;

    public FirebaseStore(FirebaseStoreListener firebaseStoreListener) {

        firebaseDatabase = FirebaseDatabase.getInstance();

        this.firebaseStoreListener = firebaseStoreListener;
    }

    public void firebaseGetStore(String id, String city){

        firebaseDatabase.getReference().child("stores")
        .child(city)
        .child(id)
        .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    Store store = dataSnapshot.getValue(Store.class);

                    firebaseStoreListener.onStoreChanged(store);

                }else{
                    firebaseStoreListener.onStoreChanged(null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                firebaseStoreListener.onStoreChanged(null);
            }
        });

    }

    public void firebaseGetStores(String city){

        firebaseDatabase.getReference().child("stores").child(city)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()){

                            ArrayList<Store> stores = new ArrayList<>();

                            for(DataSnapshot child : dataSnapshot.getChildren()){

                                Store store = child.getValue(Store.class);

                                if(store != null)
                                    stores.add(store);

                            }

                            firebaseStoreListener.onStoresChanged(stores);

                        }else{

                            firebaseStoreListener.onStoresChanged(null);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                        firebaseStoreListener.onStoresChanged(null);
                    }
                });

    }

    public interface FirebaseStoreListener{

        void onStoresChanged(ArrayList<Store> stores);

        void onStoreChanged(Store store);
    }
}
