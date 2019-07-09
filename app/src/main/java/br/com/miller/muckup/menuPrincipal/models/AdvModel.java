package br.com.miller.muckup.menuPrincipal.models;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.com.miller.muckup.menuPrincipal.tasks.AdvTasks;
import br.com.miller.muckup.models.Adv;

public class AdvModel {

    private AdvTasks.Model model;

    private FirebaseDatabase firebaseDatabase;

    public AdvModel(AdvTasks.Model model) {
        this.model = model;
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    public void getAdv(String city){

        firebaseDatabase.getReference().child("advs").child(city)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()){

                            ArrayList<Adv> advs = new ArrayList<>();

                            for(DataSnapshot child: dataSnapshot.getChildren()){

                                if(child != null)
                                    advs.add(child.getValue(Adv.class));
                            }

                            model.onAdvsSuccess(advs);

                        }else{
                            model.onAdvFialed();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        model.onAdvFialed();
                    }
                });

    }

}
