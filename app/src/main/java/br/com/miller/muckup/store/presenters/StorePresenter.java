package br.com.miller.muckup.store.presenters;

import android.graphics.Bitmap;

import java.util.ArrayList;

import br.com.miller.muckup.domain.Store;
import br.com.miller.muckup.store.models.StoreModel;
import br.com.miller.muckup.store.tasks.StoreTasks;

public class StorePresenter implements StoreTasks.Model, StoreTasks.View {

    private StoreModel storeModel;
    private StoreTasks.Presenter presenter;

    public StorePresenter(StoreTasks.Presenter presenter) {
        this.presenter = presenter;
        storeModel = new StoreModel(this);
    }

    @Override
    public void onStoresSuccess(ArrayList<Store> stores) { presenter.onStoresSuccess(stores);}

    @Override
    public void onStoreSuccess(Store store) { presenter.onStoreSuccess(store); }

    @Override
    public void onDownloadImageSuccess(Bitmap bitmap) { presenter.onDownloadImageSuccess(bitmap); }

    @Override
    public void onDownloadImageFailed() { presenter.onDownloadImageFailed(); }

    @Override
    public void onStoreFailed() { presenter.onStoreFailed(); }

    @Override
    public void getStore(String id, String city) {
        if(id != null)
            if(!id.isEmpty())
                storeModel.getStore(id, city);
    }

    @Override
    public void getStores(String city) { storeModel.getStores(city); }

    @Override
    public void getImage(String type, String city, String image) { storeModel.downloaImage(type, city, image);}
}
