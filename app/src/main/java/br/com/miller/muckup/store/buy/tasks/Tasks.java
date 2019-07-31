package br.com.miller.muckup.store.buy.tasks;

import android.os.Bundle;

import java.util.ArrayList;

import br.com.miller.muckup.domain.Buy;
import br.com.miller.muckup.domain.Offer;

public interface Tasks {

    interface Model{

        void onSuccessBuys(ArrayList<Buy> buys);
        void onFailedBuy();
        void onBuysGenerated(ArrayList<Buy> buys);
        void onOffersSuccess(ArrayList<Offer> offers);
        void onOffersFailed();
    }

    interface View{
        void makeBuy(String idFirebase, String city, String address, int payMode, String troco, int cardType, ArrayList<Offer> offers, String userName, String observation, String userPhone);
        void getOffer(Bundle bundle);
        void updateValues(ArrayList<Offer> offers);
    }

    interface Presenter{

        void onSuccessBuys(ArrayList<Buy> buys);
        void failedBuy(int type);
        void onOffersSuccess(ArrayList<Offer> offers);
        void onSendValueCalculated(String sendValue);
        void onTotalValueCalculated(Double totalValue);
        void onOffersFail();

    }
}
