package br.com.miller.muckup.menuPrincipal.tasks;

import java.util.ArrayList;

import br.com.miller.muckup.domain.Buy;

public interface MyBuysTasks {

    interface Model{

        void onBuySuccess(ArrayList<Buy> buys);
        void onBuyEmpty();
        void onBuyFailed();
    }

    interface Presenter{
        void onBuyEmpty();
        void onBuySuccess(ArrayList<Buy> buys);
        void onBuyFailed();
    }

    interface View {
        void getBuys(String userCity, String userId);
    }
}
