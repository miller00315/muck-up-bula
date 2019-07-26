package br.com.miller.muckup.store.tasks;

import android.graphics.Bitmap;

import java.util.ArrayList;

import br.com.miller.muckup.domain.Store;

public interface StoreTasks {

    interface Model{

        void onStoresSuccess(ArrayList<Store> stores);
        void onStoreSuccess(Store store);
        void onDownloadImageSuccess(Bitmap bitmap);
        void onDownloadImageFailed();
        void onStoreFailed();

    }

    interface Presenter{

        void onStoresSuccess(ArrayList<Store> stores);
        void onStoreSuccess(Store store);
        void onDownloadImageSuccess(Bitmap bitmap);
        void onDownloadImageFailed();
        void onStoreFailed();
        void onStoresFailed();
    }

    interface View{
        void getStore(String id, String city);
        void getStores(String city);
        void getImage(String type, String city, String image);
    }

}
