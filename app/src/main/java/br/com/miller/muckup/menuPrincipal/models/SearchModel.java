package br.com.miller.muckup.menuPrincipal.models;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import br.com.miller.muckup.domain.Departament;
import br.com.miller.muckup.menuPrincipal.tasks.SearchTask;
import br.com.miller.muckup.domain.Offer;

public class SearchModel {

    private SearchTask.Model model;
    private FirebaseDatabase firebaseDatabase;

    public SearchModel(SearchTask.Model model) {
        this.model = model;
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    public void getSugestions(final String city){

        firebaseDatabase.getReference()
                .child("searchHint")
                .child(city)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()){

                            getDepartaments(city);

                            String[] temp = new String[(int) dataSnapshot.getChildrenCount()];

                            int i = 0;

                            for(DataSnapshot suggestion : dataSnapshot.getChildren()){

                                temp[i] = Objects.requireNonNull(suggestion.child("title").getValue()).toString();

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


    public void searchFirebase(String search, String city, String departamentId){

        firebaseDatabase.getReference()
                .child("offersDepartaments")
                .child(city)
                .child(departamentId)
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

                                Departament departament = new Departament(child.getValue());

                                departaments.add(departament);
                            }

                           model.onDepartamentsSuccess(departaments);

                        }else{

                            model.onDepartamentsFailed();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                        model.onDepartamentsFailed();
                    }
                });

    }

}
