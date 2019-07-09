package br.com.miller.muckup.store.tasks;

import java.util.ArrayList;

import br.com.miller.muckup.models.Store;

public interface StoreTasks {

    interface Model{

        void onStoresSuccess(ArrayList<Store> stores);
        void onStoreSuccess(Store store);
        void onStoreFailed();
        void onStoresFailed();

    }

    interface Presenter{

        void onStoresSuccess(ArrayList<Store> stores);
        void onStoreSuccess(Store store);
        void onStoreFailed();
        void onStoresFailed();
    }

    interface View{
        void getStore(String id, String city);
        void getStores(String city);
    }

}
