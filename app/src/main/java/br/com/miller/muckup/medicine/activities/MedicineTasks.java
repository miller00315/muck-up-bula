package br.com.miller.muckup.medicine.activities;

import android.graphics.Bitmap;
import android.os.Bundle;

import br.com.miller.muckup.domain.Offer;

public interface MedicineTasks {

    interface Presenter{
        void onMedicineDataSuccess(Offer offer);
        void onMedicineDataFailed();
        void onImageDownloadSuccess(Bitmap bitmap);
        void onImageDownloadFailed();
        void onAddCartSuccess(Offer offer);
        void onAddCartFailed();
    }

    interface Model{

        void onMedicineDataSuccess(Offer offer);
        void onMedicineDataFailed();
        void onImageDownloadSuccess(Bitmap bitmap);
        void onImageDownloadFailed();
        void onAddCartSuccess(Offer offer);
        void onAddCartFailed();
    }

    interface View{
        void downloadImage(String type, String city, String image);
        void getMedicine(Bundle bundle);
        void addCartOffer(final Offer offer, String city, String idFirebase);
    }
}
