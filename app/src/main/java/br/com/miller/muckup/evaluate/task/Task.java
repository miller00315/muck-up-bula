package br.com.miller.muckup.evaluate.task;

import br.com.miller.muckup.models.Buy;
import br.com.miller.muckup.models.Evaluate;

public interface Task {

    interface View {
        void recoveryBuy(String city, String userId, String buyId);
        void createEvaluate(Buy buy, int Value, String message, String userName);
        void onEvaluateCreated(Evaluate evaluate);
        void evaluateBuy(Evaluate evaluate);
        void verifyAvaliation(Buy buy);

    }

    interface Presenter {

        void evaluateBuy(Evaluate evaluate);
        void onBuyRecoverySuccess(Buy buy);
        void onBuyRecoveryFailed();
        void buyAlreadEvaluated(Evaluate evaluate);
        void recoveryBuy(String city, String userId, String buyId);
        void createEvaluate(Buy buy, int value, String message);
        void onEvaluateCreated(Evaluate evaluate);
        void onEvaluateSuccess();
        void onEvaluateFailed();
        void canEvaluate();

    }

    interface Model {

        void evaluateBuy(Evaluate evaluate);
        void onBuyRecoverySuccess(Buy buy);
        void onBuyRecoveryFailed();
        void onEvaluateSuccess();
        void onEvaluateFailed();
        void buyAlreadEvaluated(Evaluate evaluate);
        void canEvaluate();

    }
}
