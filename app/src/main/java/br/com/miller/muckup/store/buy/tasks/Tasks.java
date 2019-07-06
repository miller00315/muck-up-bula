package br.com.miller.muckup.store.buy.tasks;

import java.util.ArrayList;

import br.com.miller.muckup.models.Buy;

public interface Tasks {

    interface Model{

        void onSuccessBuys(ArrayList<Buy> buys);
        void onSuccessBuy(Buy buy);
        void failedBuy();
        void failedBuys();
        void onBuyCountSuccess(String countBuys);
        void onBuyCountFailed();
    }

    interface View{}

    interface Presenter{

        void invalidBuy(String message);
        void validBuy();

    }
}
