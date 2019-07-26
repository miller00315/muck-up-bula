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
    }

    interface Model{

        void onMedicineDataSuccess(Offer offer);
        void onMedicineDataFailed();
        void onImageDownloadSuccess(Bitmap bitmap);
        void onImageDownloadFailed();
    }

    interface View{
        void downloadImage(String type, String city, String image);
        void getMedicine(Bundle bundle);
    }
}
