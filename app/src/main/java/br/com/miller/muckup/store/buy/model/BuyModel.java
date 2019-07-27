package br.com.miller.muckup.store.buy.model;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import br.com.miller.muckup.R;
import br.com.miller.muckup.domain.Buy;
import br.com.miller.muckup.domain.Offer;
import br.com.miller.muckup.store.buy.tasks.Tasks;

public class BuyModel {

    private Tasks.Model model;
    private FirebaseDatabase firebaseDatabase;

    public BuyModel(Tasks.Model model) {
        this.model = model;
        firebaseDatabase = FirebaseDatabase.getInstance();

    }

    public void generateBuys(ArrayList<Offer> offers, String userCity, String userId, String address, int payMode, Double troco, int cardFlag, String userName, String observation){

        ArrayList<Buy> buys = new ArrayList<>();

        for(Offer offer : offers){

           if(buys.size() == 0) {

               buys.add(createBuy(offer, userCity, userId, address, payMode, troco, cardFlag, userName, observation));

           }else{

               boolean ok = false;

               for(Buy buy: buys){

                   if(buy.getStoreId() == Integer.valueOf(offer.getStoreId())){

                       buy.getOffers().add(offer);
                       buy.setTotalValue(offer.getValue(), offer.getQuantity());

                       ok = true;

                       break;
                   }
               }

               if(!ok)
               {
                   buys.add(createBuy(offer, userCity, userId, address, payMode, troco, cardFlag, userName, observation));
               }

           }
        }

        if(buys.size() > 0)
            model.onBuysGenerated(buys);
    }

    private Buy createBuy(Offer offer, String userCity, String userId, String address, int payMode, Double troco, int cardFlag, String userName, String observation){

        Buy buy = new Buy();

        buy.setId(String.valueOf(new Date().getTime()).concat(String.valueOf(offer.getStoreId())).concat(userId));
        buy.setStoreId(Integer.valueOf(offer.getStoreId()));
        buy.setOffers(new ArrayList<Offer>());
        buy.setStoreCity(offer.getCity());
        buy.setUserCity(userCity);
        buy.setUserId(userId);
        buy.setUserName(userName);
        buy.setObservations(observation);
        buy.setSendValue(offer.getSendValue());
        buy.setSolicitationDate(new Date());
        buy.setStoreName(offer.getStore());
        buy.getOffers().add(offer);
        buy.setAddress(address);
        buy.setStatus("news");

        switch (payMode){

            case R.id.money: {
                buy.setPayMode(1);
            }
                break;
            case R.id.card: {
                buy.setPayMode(2);
                break;
            }

            default:
                buy.setPayMode(0);
                break;
        }

        buy.setTroco(troco);

        switch (cardFlag){

            case R.id.master:{
                buy.setCardFlag(1);
                break;
            }

            case R.id.visa:{
                buy.setCardFlag(2);
                break;
            }

            case R.id.elo:{
                buy.setCardFlag(3);
                break;
            }

            case R.id.outers:{
                buy.setCardFlag(4);
                break;
            }

            default:
                buy.setCardFlag(0);
                break;
        }

        buy.setTotalValue(offer.getValue(), offer.getQuantity());

        return buy;

    }

    public void registerBuy(final ArrayList<Buy> buys){


        for(final Buy buy: buys){

            Map<String, Object> map = new HashMap<>();

            map.put(buy.getId(), buy);

            firebaseDatabase.getReference()
            .child("buys")
            .child(buy.getUserCity())
            .child("users")
            .child(buy.getUserId())
            .updateChildren(map).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) { model.onFailedBuy(); }
            }).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                        if(buys.remove(buy)){

                            registerBuyStore(buy);

                            if(buys.size() == 0)
                                model.onSuccessBuys(buys);
                        }
                }
            });

        }

    }

    private void registerBuyStore(Buy buy) {

            Map<String, Object> map = new HashMap<>();

            map.put(buy.getId(), buy);

            firebaseDatabase.getReference()
                    .child("buys")
                    .child(buy.getStoreCity())
                    .child("stores")
                    .child(String.valueOf(buy.getStoreId()))
                    .child("news")
                    .updateChildren(map)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    }).addOnFailureListener(new OnFailureListener() {

                @Override
                public void onFailure(@NonNull Exception e) { model.onFailedBuy(); }
            });



    }

    public void getOffer(String city, String storeId, String departamentId, String offerId, final int quantity){

        firebaseDatabase.getReference()
                .child("storeDepartaments")
                .child(city)
                .child(storeId)
                .child(departamentId)
                .child("offers")
                .child(offerId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) proccessOffers(dataSnapshot, 1, quantity);
                else model.onOffersFailed();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                model.onOffersFailed();
            }
        });

    }

    public void getOffers(String city, String idFirebase){

        firebaseDatabase
                .getReference()
                .child("carts")
                .child(city)
                .child(idFirebase).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) proccessOffers(dataSnapshot, 0, 0);
                else model.onOffersFailed();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                model.onOffersFailed();
            }
        });

    }

    private void proccessOffers(DataSnapshot dataSnapshot, int type, int quantity){

            ArrayList<Offer> offers = new ArrayList<>();

            if(type == 0)

                for (DataSnapshot child : dataSnapshot.getChildren()) {

                    offers.add(child.getValue(Offer.class));
                }


                else if(type == 1){

                    Offer offer = dataSnapshot.getValue(Offer.class);

                    if(offer != null) {

                        offer.setQuantity(quantity);

                        offers.add(offer);

                    }

                }
            model.onOffersSuccess(offers);

    }

}
