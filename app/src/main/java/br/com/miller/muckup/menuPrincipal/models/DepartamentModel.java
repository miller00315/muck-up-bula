package br.com.miller.muckup.menuPrincipal.models;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.com.miller.muckup.menuPrincipal.tasks.DepartamentTask;
import br.com.miller.muckup.models.Departament;

public class DepartamentModel {

    private FirebaseDatabase firebaseDatabase;
    private DepartamentTask.Model model;

    public DepartamentModel(DepartamentTask.Model model) {
        this.model = model;

        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    public void getDepartaments(String city){

        firebaseDatabase.getReference().child("departaments")
                .child(city)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()){

                            ArrayList<Departament> departaments = new ArrayList<>();

                            for(DataSnapshot child : dataSnapshot.getChildren()){

                                Departament departament = child.getValue(Departament.class);

                                if(!checkDepartament(departament, departaments))
                                    departaments.add(departament);
                            }

                            model.onDepartamentsSuccess(departaments);

                        }else{

                            model.onDepartmentsFailed();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                       model.onDepartamentFailed();
                    }
                });

    }

    public void getDepartmentByStoreId(String city, final String storeId){

        firebaseDatabase.getReference().child("departaments")
                .child(city)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()){

                            ArrayList<Departament> departaments = new ArrayList<>();

                            for(DataSnapshot child : dataSnapshot.getChildren()){

                                Departament departament = child.getValue(Departament.class);

                                if(departament != null && String.valueOf(departament.getStoreId()).equals(storeId))
                                    departaments.add(departament);

                            }

                            model.onDepartamentByStoreSuccess(departaments);

                        }else{
                            model.onDepartamentByStoreFailed();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                        model.onDepartamentByStoreFailed();
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
