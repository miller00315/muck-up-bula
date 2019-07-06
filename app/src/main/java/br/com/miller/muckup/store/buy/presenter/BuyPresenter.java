package br.com.miller.muckup.store.buy.presenter;


import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

import br.com.miller.muckup.models.Buy;
import br.com.miller.muckup.models.Offer;
import br.com.miller.muckup.store.buy.model.BuyModel;
import br.com.miller.muckup.store.buy.tasks.Tasks;

public class BuyPresenter implements Tasks.Model {

    private Tasks.Presenter presenter;
    private BuyModel buyModel;
    private ArrayList<Buy> buys;

    public BuyPresenter(Tasks.Presenter presenter) {
        this.presenter = presenter;

        buyModel = new BuyModel(this);
    }

    public void validBuy(Buy buy){

        if(buy.getAddress() == null || buy.getAddress().isEmpty()){

            presenter.invalidBuy("Insira um enredeço");

            return;
        }

        if(buy.getPayMode() == 0){

            presenter.invalidBuy("Defina um método de pagamento");

            return;
        }

        if(buy.getOffers().size() > 0){

            presenter.invalidBuy("Não existem compras a serem registradas");

            return;
        }

        endBuy(buy);

    }

    public Boolean defineOfferByStoreId(ArrayList<Offer> offers, String userId, String userCity){

        for(Offer offer: offers) {

            if (buys.size() > 0) {

                boolean test = false;

                for(Buy buy : buys){

                    if(buy.getStoreId() == offer.getStoreId()){

                        buy.getOffers().add(offer);
                        buy.setTotalValue(offer.getValue(), offer.getQuantity());

                        test = true;
                    }

                }

                if (!test) {
                    buys.add(createBuy(offer, userId, userCity));
                }

            } else {

                buys.add(createBuy(offer, userId, userCity));
                Log.w("offer", "criar nova buy");
            }
        }

        Log.w("offer", String.valueOf(buys.size()));

        return buys.size() > 0;

    }

    private void endBuy(Buy buy){

        buyModel.registerBuy(buy);
    }


    private Buy createBuy(Offer offer, String userId, String userCity){

        Buy buy = new Buy();

        buy.setId(new Date().toString());
        buy.setStoreId(offer.getStoreId());
        buy.setOffers(new ArrayList<Offer>());
        buy.setStoreCity(offer.getCity());
        buy.setUserCity(userCity);
        buy.setUserId(userId);
        buy.setSendValue(offer.getSendValue());
        buy.setSolicitationDate(new Date());

        buy.getOffers().add(offer);
        buy.setTotalValue(offer.getValue(), offer.getQuantity());

        return buy;
    }

    @Override
    public void onSuccessBuys(ArrayList<Buy> buys) {

    }

    @Override
    public void onSuccessBuy(Buy buy) {

    }

    @Override
    public void failedBuy() {
        presenter.invalidBuy("Erro ao registrar a compra no servidor, tente novamente");
    }

    @Override
    public void failedBuys() {
        presenter.invalidBuy("Erro ao registrar a compra no servidro, tente novamente");
    }

    @Override
    public void onBuyCountSuccess(String countBuys) {

    }

    @Override
    public void onBuyCountFailed() {

    }
}
