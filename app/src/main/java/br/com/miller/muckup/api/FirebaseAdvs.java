package br.com.miller.muckup.api;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.com.miller.muckup.menuPrincipal.adapters.AdvRecyclerAdapter;
import br.com.miller.muckup.models.Adv;

public class FirebaseAdvs {

    private Context context;
    private FirebaseDatabase firebaseDatabase;

    public FirebaseAdvs(Context context) {
        this.context = context;

        firebaseDatabase = FirebaseDatabase.getInstance();

    }

    public void getAdvsFromCity(String city,final AdvRecyclerAdapter advRecycler){

        firebaseDatabase.getReference().child("advs").child(city)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()){

                            for(DataSnapshot child: dataSnapshot.getChildren()){

                                if(child != null)
                                    advRecycler.addAdv(child.getValue(Adv.class));
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }

}
