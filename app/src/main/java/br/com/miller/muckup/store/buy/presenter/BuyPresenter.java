package br.com.miller.muckup.store.buy.presenter;


import br.com.miller.muckup.models.Buy;
import br.com.miller.muckup.store.buy.model.BuyModel;
import br.com.miller.muckup.store.buy.tasks.Tasks;

public class BuyPresenter implements Tasks.Model {

    private Tasks.Presenter presenter;
    private BuyModel buyModel;

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

      //  endBuy(buy);

    }

    private void endBuy(Buy buy){

        buyModel.registerBuy(buy);
    }

    @Override
    public void successBuy() {

        presenter.validBuy();
    }

    @Override
    public void failedBuy() {
        presenter.invalidBuy("Erro ao registrar a compra no servidor, tente novamente");
    }
}
