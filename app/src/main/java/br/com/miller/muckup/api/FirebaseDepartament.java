package br.com.miller.muckup.api;

import android.support.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.com.miller.muckup.models.Departament;

public class FirebaseDepartament {

    private FirebaseDatabase firebaseDatabase;

    private FirebaseDepartamentListener firebaseDepartamentListener;

    public FirebaseDepartament(FirebaseDepartamentListener firebaseDepartamentListener) {

        firebaseDatabase = FirebaseDatabase.getInstance();

        this.firebaseDepartamentListener = firebaseDepartamentListener;
    }

    public void getDepartamentsFirebase(String city){

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

                            firebaseDepartamentListener.onDepartamentsReceived(departaments);

                        }else{

                            firebaseDepartamentListener.onDepartamentsReceived(null);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                        firebaseDepartamentListener.onDepartamentsReceived(null);
                    }
                });

    }

    public void getDepartamentsFirebaseByStore(String city, final String storeId){

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

                            firebaseDepartamentListener.onDepartamentsReceived(departaments);

                        }else{
                            firebaseDepartamentListener.onDepartamentsReceived(null);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                        firebaseDepartamentListener.onDepartamentsReceived(null);
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

    public interface FirebaseDepartamentListener{

        void onDepartamentReceived(Departament departament);

        void onDepartamentsReceived(ArrayList<Departament> departaments);
    }

}
