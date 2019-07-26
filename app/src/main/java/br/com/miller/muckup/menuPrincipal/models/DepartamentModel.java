package br.com.miller.muckup.menuPrincipal.models;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.com.miller.muckup.domain.Offer;
import br.com.miller.muckup.menuPrincipal.tasks.DepartamentManagerTask;
import br.com.miller.muckup.domain.Departament;

public class DepartamentModel {

    private FirebaseDatabase firebaseDatabase;
    private DepartamentManagerTask.Model model;

    public DepartamentModel(DepartamentManagerTask.Model model) {
        this.model = model;

        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    public void getDepartaments(String city){

        firebaseDatabase.getReference()
                .child("avaliablesDepartaments")
                .child(city)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()){

                            ArrayList<Departament> departaments = new ArrayList<>();

                            for(DataSnapshot child : dataSnapshot.getChildren()){

                                Departament departament = child.getValue(Departament.class);

                                departaments.add(departament);
                            }

                            model.onDepartamentsSuccess(departaments);

                        }else{

                            model.onDepartmentsFailed();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                       model.onDepartmentsFailed();
                    }
                });

    }

    public void getSingleDepartament(String city, String storeId, String departamentId){

        firebaseDatabase.getReference()
                .child("storeDepartaments")
                .child(city)
                .child(storeId)
                .child(departamentId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()){

                            model.onSingleDepartmentSuccess(new Departament(dataSnapshot.getValue()));

                        }else
                            model.onSingleDepartamenteFailed();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        model.onSingleDepartamenteFailed();
                    }
                });

    }



    public void getDepartamentCity(String city, String departamentId){

        firebaseDatabase.getReference()
                .child("avaliablesDepartaments")
                .child(city)
                .child(departamentId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()){

                            Departament departament = dataSnapshot.getValue(Departament.class);

                            getOffersDepartament(departament);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                        model.onSingleDepartamenteFailed();

                    }
                });
    }

    public void getOffersDepartament(final Departament departament){

        firebaseDatabase.getReference()
                .child("offersDepartaments")
                .child(departament.getCity())
                .child(departament.getId())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()){

                            ArrayList<Offer> offers = new ArrayList<>();

                            for (DataSnapshot child : dataSnapshot.getChildren()){

                              for(DataSnapshot neto : child.getChildren()){

                                  offers.add(neto.getValue(Offer.class));
                              }

                            }

                            departament.setOffers(offers);

                            model.onSingleDepartmentSuccess(departament);

                        }else{
                            model.onSingleDepartamenteFailed();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        model.onSingleDepartamenteFailed();
                    }
                });

    }

    public void getDepartmentStore(String city, final String storeId){

        firebaseDatabase.getReference()
                .child("storeDepartaments")
                .child(city)
                .child(storeId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()){

                            ArrayList<Departament> departaments = new ArrayList<>();

                            for(DataSnapshot child : dataSnapshot.getChildren()){

                                departaments.add(new Departament(child.getValue()));
                            }

                            model.onDepartamentsStoreSuccess(departaments);

                        }else{
                           model.onDepartamentsStoreFailed();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                        model.onDepartamentsStoreFailed();
                    }
                });
    }


    private Boolean checkDepartament(Departament departament, ArrayList<Departament> departaments){

        for(Departament dp : departaments){

            if(dp.getId() == departament.getId())
                return true;
        }

        return false;
    }

    public void getDepartament(){

    }
}
