package br.com.miller.muckup.evaluate.presenter;

import br.com.miller.muckup.evaluate.model.EvaluateModel;
import br.com.miller.muckup.evaluate.task.Task;
import br.com.miller.muckup.domain.Buy;
import br.com.miller.muckup.domain.Evaluate;

public class EvaluatePresenter implements Task.Model, Task.View {

    private Task.Presenter presenter;
    private EvaluateModel model;

    public EvaluatePresenter(Task.Presenter presenter) {
        this.presenter = presenter;

        this.model = new EvaluateModel(this);
    }

    @Override
    public void createEvaluate(Buy buy, int value, String message, String username) {

        if(buy == null || value < 0){
            this.onEvaluateFailed();
            return;
        }
        this.onEvaluateCreated(model.createEvaluate(buy, value, message, username));
    }

    @Override
    public void onEvaluateCreated(Evaluate evaluate) {
        if(evaluate != null)
            presenter.onEvaluateCreated(evaluate);
        else
            this.onEvaluateFailed();
    }

    @Override
    public void evaluateBuy(Evaluate evaluate) { model.makeEvaluation(evaluate); }

    @Override
    public void verifyAvaliation(Buy buy) { model.verifyAvaliation(buy);}

    @Override
    public void onBuyRecoverySuccess(Buy buy) { presenter.onBuyRecoverySuccess(buy);}

    @Override
    public void onBuyRecoveryFailed() { presenter.onBuyRecoveryFailed();}

    @Override
    public void recoveryBuy(String city, String userId, String buyId) { model.recoveryBuy(city, userId, buyId);}

    @Override
    public void onEvaluateSuccess() { presenter.onEvaluateSuccess(); }

    @Override
    public void onEvaluateFailed() { presenter.onEvaluateFailed(); }

    @Override
    public void buyAlreadEvaluated(Evaluate evaluate) { presenter.buyAlreadEvaluated(evaluate); }

    @Override
    public void canEvaluate() { presenter.canEvaluate(); }
}
