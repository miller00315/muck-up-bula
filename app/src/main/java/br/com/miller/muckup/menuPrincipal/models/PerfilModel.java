package br.com.miller.muckup.menuPrincipal.models;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import br.com.miller.muckup.menuPrincipal.tasks.PerfilTasks;
import br.com.miller.muckup.domain.User;
import br.com.miller.muckup.utils.image.FirebaseImageUtils;

public class PerfilModel implements FirebaseImageUtils.FirebaseImageTask {

    private PerfilTasks.Model model;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseImageUtils firebaseImageUtils;

    public PerfilModel(PerfilTasks.Model model) {

        this.model = model;

        firebaseDatabase = FirebaseDatabase.getInstance();

        firebaseImageUtils = new FirebaseImageUtils(this);
    }

    public void updateUser(final User user){

        Map<String, Object> map = new HashMap<>();

        map.put(user.getId_firebase(), user);

        firebaseDatabase.getReference().child("users")
                .child(user.getCity()).
                updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {

                model.onPerfilUpdatedSuccess(user);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                model.onPerfilUpdatedFail();
            }
        });

    }

    public void updateImage(User user, Bitmap image){

        firebaseImageUtils.uploadImageFirebase(user, image);

    }

    public void getUserData(String city, String id_firebase){

        firebaseDatabase.getReference().child("users")
                .child(city)
                .child(id_firebase)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()) {
                            User user = dataSnapshot.getValue(User.class);

                            if (user != null)
                                model.getPerfilSuccess(user);
                            else
                                model.getPerfilFaield();
                        }else{
                            model.getPerfilFaield();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        model.getPerfilFaield();
                    }
                });

    }

    public void getCartCount(String city, String idFirebase){

        firebaseDatabase.getReference()
                .child("carts")
                .child(city)
                .child(idFirebase)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        model.onCartCountSuccess((int) dataSnapshot.getChildrenCount());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                        model.onCartCountFailed();

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
                             model.onBuyCountSuccess((int) dataSnapshot.getChildrenCount());
                        }else{
                          model.onBuyCountFailded();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                      model.onBuyCountFailded();
                    }
                });
    }

    @Override
    public void onImageUploadSucces(Bitmap bitmap) {
        model.onImageUpdateSuccess(bitmap);
    }

    @Override
    public void onImageUploadFails() {
        model.onImageUpdateFailed();
    }

    @Override
    public void onImageDeleteSuccess() { }

    @Override
    public void onImageDeleteFailed() { }

    @Override
    public void onDownloadImageSuccess(Bitmap bitmap) { model.onDownloadImgeSucess(bitmap);}

    @Override
    public void onDowloadImageFail() { model.onDownloadImageFailed(); }

    @Override
    public void downloaImage(String type, String city, String image) { firebaseImageUtils.downloadImage(type, city, image); }
}
