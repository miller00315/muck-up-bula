package br.com.miller.muckup.store.models;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.com.miller.muckup.models.Evaluate;
import br.com.miller.muckup.store.tasks.OpinionTasks;

public class OpinionsModel {

    private OpinionTasks.Model model;
    private FirebaseDatabase firebaseDatabase;

    public OpinionsModel(OpinionTasks.Model model) {
        this.model = model;
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    public void getOpnions(String storeId, String city){

        Log.w("test1", storeId);

        firebaseDatabase.getReference().
                child("evaluate")
                .child(city)
                .child(storeId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()){

                            ArrayList<Evaluate> evaluates = new ArrayList<>();

                            for(DataSnapshot child: dataSnapshot.getChildren()){
                                evaluates.add(child.getValue(Evaluate.class));
                            }

                            model.onOpinionsSuccess(evaluates);

                        }else{
                            model.onOpinionsFiled();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        model.onOpinionsFiled();
                    }
                });
    }

}
