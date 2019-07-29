package br.com.miller.muckup.menuPrincipal.tasks;

import java.util.ArrayList;

import br.com.miller.muckup.domain.Buy;

public interface MyBuysTasks {

    interface Model{

        void onBuySuccess(ArrayList<Buy> buys);
        void onBuyEmpty();
        void onBuyFailed();
        void onBuyAdded(Buy buy);
        void onBuyUpdated(Buy buy);
        void onBuyRemoved(Buy buy);
    }

    interface Presenter{
        void onBuyEmpty();
        void onBuyFailed();
        void onBuySuccess(ArrayList<Buy> buys);
        void onBuyAdded(Buy buy);
        void onBuyUpdated(Buy buy);
        void onBuyRemoved(Buy buy);
    }

    interface View {
        void getBuys(String userCity, String userId);
    }
}
