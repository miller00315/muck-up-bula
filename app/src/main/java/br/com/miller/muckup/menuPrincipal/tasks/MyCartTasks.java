package br.com.miller.muckup.menuPrincipal.tasks;

import java.util.ArrayList;

import br.com.miller.muckup.domain.Offer;

public interface MyCartTasks {
    interface Presenter{
        void onGetOffersSuccess(ArrayList<Offer> offers);
        void onGetOffersFailed();
        void onDeleteAllSuccess();
        void onDeleteAllFailed();
        void onDeleteItemSuccess();
        void onDeleteItemFailed();

    }
    interface View{
        void getOffers(String city, String idFirebase);
        void deleteItem(String city, String idFirebase, String item);
        void deleteAllItems(String city, String idFirebase);
    }

    interface Model{
        void onGetOffersSuccess(ArrayList<Offer> offers);
        void onGetOffersFailed();
        void onDeleteAllSuccess();
        void onDeleteAllFailed();
        void onDeleteItemSuccess();
        void onDeleteItemFailed();
    }
}
