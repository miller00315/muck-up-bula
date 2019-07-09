package br.com.miller.muckup.store.models;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.com.miller.muckup.models.Store;
import br.com.miller.muckup.store.tasks.StoreTasks;

public class StoreModel {

    private FirebaseDatabase firebaseDatabase;
    private StoreTasks.Model model;

    public StoreModel(StoreTasks.Model model) {
        this.model = model;
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    public void getStore(String id, String city){
        firebaseDatabase.getReference().child("stores")
                .child(city)
                .child(id)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()){

                            Store store = dataSnapshot.getValue(Store.class);

                            model.onStoreSuccess(store);

                        }else{
                            model.onStoreFailed();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        model.onStoreFailed();
                    }
                });
    }

    public void getStores(String city){
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

                            model.onStoresSuccess(stores);

                        }else{

                            model.onStoreFailed();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                        model.onStoreFailed();
                    }
                });
    }

}
