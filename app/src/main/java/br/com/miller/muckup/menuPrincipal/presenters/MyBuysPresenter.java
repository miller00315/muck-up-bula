package br.com.miller.muckup.menuPrincipal.presenters;

import java.util.ArrayList;

import br.com.miller.muckup.domain.Buy;
import br.com.miller.muckup.menuPrincipal.models.MyBuysModel;
import br.com.miller.muckup.menuPrincipal.tasks.MyBuysTasks;

public class MyBuysPresenter implements MyBuysTasks.View, MyBuysTasks.Model {

    private MyBuysModel myBuysModel;
    private MyBuysTasks.Presenter presenter;

    public MyBuysPresenter(MyBuysTasks.Presenter presenter) {
        this.presenter = presenter;

        myBuysModel = new MyBuysModel(this);
    }

    @Override
    public void onBuySuccess(ArrayList<Buy> buys) { presenter.onBuySuccess(buys); }

    @Override
    public void onBuyEmpty() { presenter.onBuyEmpty();}

    @Override
    public void onBuyFailed() { presenter.onBuyFailed();}

    @Override
    public void getBuys(String userCity, String userId) { myBuysModel.getBuys(userCity, userId);}

    public void temporaryVerify(String userCity, String userId){myBuysModel.temporaryVerify(userCity, userId);}

    public void removeListener(String userCity, String userId){ myBuysModel.removeListener(userCity, userId); }
}
