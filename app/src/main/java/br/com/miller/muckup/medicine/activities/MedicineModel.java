package br.com.miller.muckup.medicine.activities;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import br.com.miller.muckup.domain.Offer;
import br.com.miller.muckup.utils.image.FirebaseImageUtils;

public class MedicineModel implements FirebaseImageUtils.FirebaseImageTask {

    private FirebaseDatabase firebaseDatabase;
    private MedicineTasks.Model model;
    private FirebaseImageUtils firebaseImageUtils;

    public MedicineModel(MedicineTasks.Model model) {

        this.model = model;
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseImageUtils = new FirebaseImageUtils(this);

    }

    public void getMedicineByStore(String city, String storeId, String departamentId, String offerId){

        firebaseDatabase.getReference()
                .child("storeDepartaments")
                .child(city)
                .child(storeId)
                .child(departamentId)
                .child("offers")
                .child(offerId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists())
                            model.onMedicineDataSuccess(dataSnapshot.getValue(Offer.class));
                        else
                            model.onMedicineDataFailed();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        model.onMedicineDataFailed();
                    }
                });

    }

    public void firebaseCartAddOffer(final Offer offer, String city, String idFirebase) {

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

                        model.onAddCartFailed();

                    }
                }).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        model.onAddCartSuccess(offer);
                    }
                });

    }

    public void getMedicine(String city, String type, String id, String departamentId){

        firebaseDatabase.getReference()
                .child("offersDepartaments")
                .child(city)
                .child(departamentId)
                .child(type)
                .child(id)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()){

                            model.onMedicineDataSuccess(new Offer(dataSnapshot.getValue()));

                        }else{

                            model.onMedicineDataFailed();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                        model.onMedicineDataFailed();
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
    public void onDownloadImageSuccess(Bitmap bitmap) { model.onImageDownloadSuccess(bitmap); }

    @Override
    public void onDowloadImageFail() { model.onImageDownloadFailed(); }

    @Override
    public void downloaImage(String type, String city, String image) { firebaseImageUtils.downloadImage(type, city, image); }
}
