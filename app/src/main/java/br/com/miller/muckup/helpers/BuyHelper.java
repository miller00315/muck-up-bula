package br.com.miller.muckup.helpers;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

import br.com.miller.muckup.api.FirebaseBuy;
import br.com.miller.muckup.models.Buy;
import br.com.miller.muckup.models.Offer;

public class BuyHelper {

    private ArrayList<Buy> buies;
    private Context context;
    private BuyHelperListener buyHelperListener;
    private FirebaseBuy firebaseBuy;

    public BuyHelper(Context context) {
        this.context = context;

        buies = new ArrayList<>();

        if(context instanceof  BuyHelperListener)
            buyHelperListener = (BuyHelperListener) context;

        firebaseBuy = new FirebaseBuy(context);

    }

    public void endBuy(String userId, String city, String address){

        firebaseBuy.registerBuy(buies, userId, city, address);

    }


    public ArrayList<Buy> getBuies(){

        return buies;
    }

    public Boolean singleBuy(Offer offer, String userId, String userCity){

        return buies.add(createBuy(offer, userId, userCity));
    }


    public Boolean defineOfferByStoreId(ArrayList<Offer> offers, String userId, String userCity){

        for(Offer offer: offers) {

            if (buies.size() > 0) {

                boolean test = false;

                for(Buy buy : buies){

                    Log.w("offer", String.valueOf(buy.getStoreId()) + " " + String.valueOf(offer.getStoreId()));

                    if(buy.getStoreId() == offer.getStoreId()){

                        buy.getProducts().add(offer);
                        buy.setTotalValue(offer.getValue(), offer.getQuantity());

                        test = true;
                    }

                }

                if (!test) {
                    Log.w("offer", "dados diferentes");
                    buies.add(createBuy(offer, userId, userCity));
                }else
                    Log.w("offer", "todas as compras foram adicionadas");

            } else {

                buies.add(createBuy(offer, userId, userCity));
                Log.w("offer", "criar nova buy");
            }
        }

        Log.w("offer", String.valueOf(buies.size()));

        return buies.size() > 0;

    }

    public void clear(){
        buies.clear();
    }

    public Double calculateSendValue(){

        Double sendValue = 0.0;

        if(buies.size() > 0)

            for (Buy buy : buies){

                sendValue += buy.getSendValue();
            }

        return sendValue;
    }

    public Double calculateTotalValue(){

        Double custoTotal = 0.0;

        if(buies.size() > 0){

            for(Buy buy : buies){

                custoTotal += buy.getTotalValue() ;
            }
        }

        return custoTotal;
    }


    public Buy createBuy(Offer offer, String userId, String userCity){

        Buy buy = new Buy();

        buy.setId(new Date().toString());
        buy.setStoreId(offer.getStoreId());
        buy.setProducts(new ArrayList<Offer>());
        buy.setStoreCity(offer.getCity());
        buy.setUserCity(userCity);
        buy.setUserId(userId);
        buy.setSendValue(offer.getSendValue());
        buy.setSolicitationDate(new Date());

        buy.getProducts().add(offer);
        buy.setTotalValue(offer.getValue(), offer.getQuantity());

        return buy;
    }

    public interface BuyHelperListener{

        void onBuyRegistred();

    }
}
