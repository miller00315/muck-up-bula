package br.com.miller.muckup.menuPrincipal.models;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.com.miller.muckup.menuPrincipal.tasks.SearchTask;
import br.com.miller.muckup.domain.Offer;

public class SearchModel {

    private SearchTask.Model model;
    private FirebaseDatabase firebaseDatabase;

    public SearchModel(SearchTask.Model model) {
        this.model = model;
        firebaseDatabase = FirebaseDatabase.getInstance();
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

                            int i = 0;

                            for(DataSnapshot suggestion : dataSnapshot.getChildren()){

                                temp[i] = suggestion.getKey();

                                i++;
                            }
                                model.onSuggetionsSuccess(temp);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        model.onSuggestionFailed();
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

                            model.onSearchSuccess(offers);

                        }else{
                            model.onSearchSuccess(null);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        model.onSearchFailed();
                    }
                });

    }
}
