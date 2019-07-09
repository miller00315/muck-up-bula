package br.com.miller.muckup.evaluate.model;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import br.com.miller.muckup.evaluate.task.Task;
import br.com.miller.muckup.models.Buy;
import br.com.miller.muckup.models.Evaluate;

public class EvaluateModel{

    private Task.Model model;
    private FirebaseDatabase firebaseDatabase;

    public EvaluateModel(Task.Model model) {
        this.model = model;
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    public Evaluate createEvaluate(Buy buy, int value, String message, String username){

        Evaluate evaluate = new Evaluate();

        evaluate.setUserName(username);
        evaluate.setCity(buy.getUserCity());
        evaluate.setDate(new Date());
        evaluate.setValue(value);
        evaluate.setBuyId(buy.getId());
        evaluate.setIdUser(buy.getUserId());
        evaluate.setIdStore(String.valueOf(buy.getStoreId()));
        evaluate.setMessage(message);

        return evaluate;
    }

    public void makeEvaluation(final Evaluate evaluate){

        Map<String, Object> map = new HashMap<>();

        map.put(evaluate.getBuyId(), evaluate );

        firebaseDatabase.getReference()
                .child("evaluate")
                .child(evaluate.getCity())
                .child(evaluate.getIdStore())
                .updateChildren(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                    model.onEvaluateSuccess();

                }
            }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                    model.onEvaluateFailed();

                }
            });
    }

    public void recoveryBuy(String city, String userId, String buyId){

        firebaseDatabase.getReference()
                .child("buys")
                .child(city)
                .child("users")
                .child(userId)
                .child(buyId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()) {

                            model.onBuyRecoverySuccess(dataSnapshot.getValue(Buy.class));

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                            model.onBuyRecoveryFailed();

                    }
                });

    }

    public void verifyAvaliation(final Buy buy){

        firebaseDatabase.getReference()
                .child("evaluate")
                .child(buy.getUserCity())
                .child(String.valueOf(buy.getStoreId()))
                .child(buy.getId())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists())
                            model.buyAlreadEvaluated(dataSnapshot.getValue(Evaluate.class));
                        else
                            model.canEvaluate();
                            //makeEvaluation(evaluate);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                        //makeEvaluation(evaluate);
                        model.canEvaluate();

                    }
                });

    }

}
