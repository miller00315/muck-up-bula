package br.com.miller.muckup.store.buy.tasks;

public interface Tasks {

    public interface Model{

        void successBuy();
        void failedBuy();
    }

    public interface View{}

    public interface Presenter{

        void invalidBuy(String message);
        void validBuy();

    }
}
