package br.com.miller.muckup.store.models;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.com.miller.muckup.domain.Store;
import br.com.miller.muckup.store.tasks.StoreTasks;
import br.com.miller.muckup.utils.image.FirebaseImageUtils;

public class StoreModel implements FirebaseImageUtils.FirebaseImageTask {

    private FirebaseDatabase firebaseDatabase;
    private StoreTasks.Model model;
    private FirebaseImageUtils firebaseImageUtils;

    public StoreModel(StoreTasks.Model model) {
        this.model = model;
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseImageUtils = new FirebaseImageUtils(this);
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

    @Override
    public void onImageUploadSucces(Bitmap bitmap) { }

    @Override
    public void onImageUploadFails() { }

    @Override
    public void onImageDeleteSuccess() { }

    @Override
    public void onImageDeleteFailed() { }

    @Override
    public void onDownloadImageSuccess(Bitmap bitmap) { model.onDownloadImageSuccess(bitmap); }

    @Override
    public void onDowloadImageFail() { model.onDownloadImageFailed(); }

    @Override
    public void downloaImage(String type, String city, String image) { firebaseImageUtils.downloadImage(type, city, image);}
}
