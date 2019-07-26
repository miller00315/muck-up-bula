package br.com.miller.muckup.medicine.activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

import java.util.Objects;

import br.com.miller.muckup.domain.Offer;
import br.com.miller.muckup.menuPrincipal.views.fragments.DepartamentsFragment;
import br.com.miller.muckup.menuPrincipal.views.fragments.SearchFragment;
import br.com.miller.muckup.store.views.fragments.DepartamentStoreFragment;
import br.com.miller.muckup.utils.StringUtils;

public class MedicinePresenter implements MedicineTasks.View, MedicineTasks.Model{

    private MedicineTasks.Presenter presenter;
    private MedicineModel medicineModel;

    public MedicinePresenter(MedicineTasks.Presenter presenter) {
        this.presenter = presenter;
        medicineModel = new MedicineModel(this);
    }

    @Override
    public void onMedicineDataSuccess(Offer offer) { presenter.onMedicineDataSuccess(offer);}

    @Override
    public void onMedicineDataFailed() { presenter.onMedicineDataFailed(); }

    @Override
    public void onImageDownloadSuccess(Bitmap bitmap) { presenter.onImageDownloadSuccess(bitmap);}

    @Override
    public void onImageDownloadFailed() { presenter.onImageDownloadFailed(); }

    @Override
    public void downloadImage(String type, String city, String image) { medicineModel.downloaImage(type, city, image);}

    @Override
    public void getMedicine(Bundle bundle) {

        Log.w("tr", bundle.getString("code"));

        if(Objects.equals(bundle.getString("code"), DepartamentStoreFragment.ID))
            medicineModel.getMedicineByStore(bundle.getString("city"), bundle.getString("storeId"), bundle.getString("departamentId"), bundle.getString("id_offer"));
        else if(Objects.equals(bundle.getString("code"), SearchFragment.ID) || Objects.equals(bundle.getString("code"), DepartamentsFragment.ID))
            medicineModel.getMedicine(bundle.getString("city"), StringUtils.normalizer(Objects.requireNonNull(bundle.getString("title"))),bundle.getString("id_offer"), bundle.getString("departamentId") );


    }
}
