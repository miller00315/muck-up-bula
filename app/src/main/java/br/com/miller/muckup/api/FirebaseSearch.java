package br.com.miller.muckup.api;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.com.miller.muckup.menuPrincipal.adapters.SearchResultAdapter;
import br.com.miller.muckup.models.Offer;

public class FirebaseSearch {

    private Context context;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseSearchListener firebaseSearchListener;

    public FirebaseSearch(Context context) {
        this.context = context;
        firebaseDatabase = FirebaseDatabase.getInstance();

        if(context instanceof FirebaseSearchListener)
            firebaseSearchListener = (FirebaseSearchListener) context;
    }

    public void getSugestions(String city){

        firebaseDatabase.getReference()
                .child("offers")
                .child(city)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()){

                            String[] temp = new String[(int) dataSnapshot.getChildrenCount()];

                            ArrayList<String> suggestions = new ArrayList<>();

                            for(DataSnapshot suggestion : dataSnapshot.getChildren()){

                                suggestions.add(suggestion.getKey());
                            }

                            if(suggestions.size() > 0){

                                for(int i = 0; i< suggestions.size();i++){

                                    temp[i] = suggestions.get(i);
                                }

                                firebaseSearchListener.onSuggetions(temp);

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }


    public void searchFirebase(String search, String city){

        firebaseDatabase.getReference()
                .child("offers")
                .child(city)
                .child(search)
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {

                    ArrayList<Offer> offers= new ArrayList<>();

                    for (DataSnapshot child : dataSnapshot.getChildren()) {

                        offers.add(child.getValue(Offer.class));
                    }

                    firebaseSearchListener.onFirebaseSearch(offers);

                }else{
                    firebaseSearchListener.onFirebaseSearch(null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                firebaseSearchListener.onFirebaseSearch(null);
            }
        });

    }


    public interface FirebaseSearchListener{

        void onFirebaseSearch(ArrayList<Offer> offers);

        void onSuggetions(String[] suggestions);
    }
}
