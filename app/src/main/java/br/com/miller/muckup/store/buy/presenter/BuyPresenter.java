package br.com.miller.muckup.store.buy.presenter;

import java.util.ArrayList;
import java.util.Locale;

import br.com.miller.muckup.R;
import br.com.miller.muckup.domain.Buy;
import br.com.miller.muckup.domain.Offer;
import br.com.miller.muckup.store.buy.model.BuyModel;
import br.com.miller.muckup.store.buy.tasks.Tasks;
import br.com.miller.muckup.utils.StringUtils;

public class BuyPresenter implements Tasks.Model, Tasks.View {

    private Tasks.Presenter presenter;
    private BuyModel buyModel;

    public BuyPresenter(Tasks.Presenter presenter) {
        this.presenter = presenter;
        buyModel = new BuyModel(this);
    }

    public void getOffer(String city, String type, String offerId, int quantity){ buyModel.getOffer(city, type, offerId, quantity); }

    public void getOffers(String city, String idFirebase){ buyModel.getOffers(city, idFirebase); }

    @Override
    public void onSuccessBuys(ArrayList<Buy> buys) { presenter.onSuccessBuys(buys);}

    private void generateSendValue(ArrayList<Offer> offers){

        ArrayList<String> storesIds = new ArrayList<>();
        ArrayList<Offer> sendValues = new ArrayList<>();

        double sendValue = 0.0;

        for(Offer offer : offers){

            if(storesIds.size() == 0){

                storesIds.add(offer.getStoreId());

                sendValue += offer.getSendValue();

                sendValues.add(offer);

            }else{

                boolean exist = false;

                for(String storeId : storesIds){

                    if(storeId.equals(offer.getStoreId())) {
                        exist = true;
                        break;
                    }

                }

                if(!exist){
                    storesIds.add(offer.getStoreId());
                    sendValues.add(offer);
                    sendValue += offer.getSendValue();
                }
            }
        }

        presenter.onSendValueCalculated(sendValue, sendValues);
    }

    private void calculateTotalValue(ArrayList<Offer> offers){

        double totalValue = 0.0;

        for(Offer offer : offers){ totalValue += (offer.getValue() * offer.getQuantity()); }

        presenter.onTotalValueCalculated(totalValue);
    }


    @Override
    public void onFailedBuy() { presenter.failedBuy(3); }
    @Override
    public void onBuysGenerated(ArrayList<Buy> buys) {

        if(buys.size() > 0) buyModel.registerBuy(buys);
        else presenter.failedBuy(3);

    }

    @Override
    public void onOffersSuccess(ArrayList<Offer> offers) {

        calculateTotalValue(offers);
        generateSendValue(offers);
        presenter.onOffersSuccess(offers);

    }

    @Override
    public void onOffersFailed() { presenter.onOffersFail(); }

    @Override
    public void makeBuy(String idFirebase, String city, String address, int payMode, String troco, int cardFlag, ArrayList<Offer> offers, String userName, String observation) {

        boolean ok = true;

        if(address.isEmpty()){

            presenter.failedBuy(0);
            ok = false;

        }

        if (payMode == -1) {
            presenter.failedBuy(1);
            ok = false;

        }

        if(payMode == R.id.card && cardFlag == -1){

            presenter.failedBuy(2);
            ok = false;

        }

        if(!ok) return;

        buyModel.generateBuys(offers, city, idFirebase, address, payMode,
                Double.valueOf(StringUtils.cleanMoneyString(troco, Locale.getDefault())),
                cardFlag, userName, observation);
    }
}
