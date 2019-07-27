package br.com.miller.muckup.menuPrincipal.presenters;

import java.util.ArrayList;

import br.com.miller.muckup.domain.Offer;
import br.com.miller.muckup.menuPrincipal.models.MyCartModel;
import br.com.miller.muckup.menuPrincipal.tasks.MyCartTasks;

public class MyCartPresenter implements MyCartTasks.Model, MyCartTasks.View {

    private MyCartModel myCartModel;
    private MyCartTasks.Presenter presenter;

    public static MyCartPresenter newInstance(MyCartTasks.Presenter presenter){

        return  new MyCartPresenter(presenter);
    }

    private MyCartPresenter(MyCartTasks.Presenter presenter) {
        this.presenter = presenter;
        myCartModel = MyCartModel.newInstance(this);
    }

    @Override
    public void onGetOffersSuccess(ArrayList<Offer> offers) { presenter.onGetOffersSuccess(offers);}

    @Override
    public void onGetOffersFailed() { presenter.onGetOffersFailed(); }

    @Override
    public void onDeleteAllSuccess() { presenter.onDeleteAllSuccess(); }

    @Override
    public void onDeleteAllFailed() { presenter.onDeleteAllFailed(); }

    @Override
    public void onDeleteItemSuccess() { presenter.onDeleteItemSuccess(); }

    @Override
    public void onDeleteItemFailed() { presenter.onDeleteAllFailed(); }

    @Override
    public void getOffers(String city, String idFirebase) { myCartModel.getOffers(city, idFirebase); }

    @Override
    public void deleteItem(String city, String idFirebase, String item) { myCartModel.deleteItem(city, idFirebase, item);}

    @Override
    public void deleteAllItems(String city, String idFirebase) { myCartModel.deleteAllItems(city, idFirebase);}
}
