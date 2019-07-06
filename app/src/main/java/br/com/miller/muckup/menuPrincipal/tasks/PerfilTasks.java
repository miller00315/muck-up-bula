package br.com.miller.muckup.menuPrincipal.tasks;

import android.graphics.Bitmap;

import br.com.miller.muckup.models.User;

public interface PerfilTasks {

    interface Presenter{

        void getPerfilSuccess(User user);
        void getPerfilFaield();
        void onPerfilUpdatedFail();
        void onPerfilUpdatedSuccess(User user);
        void onImageUpdateSuccess(Bitmap bitmap);
        void onImageUpdateFailed();

    }

    interface View{

        void getImageFromMemory();
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
    }
}
