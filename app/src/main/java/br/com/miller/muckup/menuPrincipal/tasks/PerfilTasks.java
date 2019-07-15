package br.com.miller.muckup.menuPrincipal.tasks;

import android.graphics.Bitmap;

import br.com.miller.muckup.domain.User;

public interface PerfilTasks {

    interface Presenter{

        void getPerfilSuccess(User user);
        void getPerfilFaield();
        void onPerfilUpdatedFail();
        void onPerfilUpdatedSuccess(User user);
        void onImageUpdateSuccess(Bitmap bitmap);
        void onImageUpdateFailed();
        void onBuyCountSuccess(int buyCount);
        void onBuyCountFailded();
        void onCartCountSuccess(int cartCount);
        void onCartCountFailed();

    }

    interface View{

        void getImageFromMemory();
        void getCartCount(String userCity, String userId);
        void updateImage(User user, Bitmap image);
        void getUserDate(String city, String firebaseId);
        void updateUser(User user, Object o, int type);
    }

    interface Model{

        void getPerfilSuccess(User user);
        void getPerfilFaield();
        void onPerfilUpdatedFail();
        void onPerfilUpdatedSuccess(User user);
        void onImageUpdateSuccess(Bitmap bitmap);
        void onImageUpdateFailed();
        void onBuyCountSuccess(int buyCount);
        void onBuyCountFailded();
        void onCartCountSuccess(int cartCount);
        void onCartCountFailed();
    }
}
