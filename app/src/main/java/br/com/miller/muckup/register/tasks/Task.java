package br.com.miller.muckup.register.tasks;

import com.google.firebase.auth.FirebaseUser;

import br.com.miller.muckup.models.User;

public interface Task {

    interface View{
        void onDestroy();
    }

    interface Presenter{

        void successRegister(User user);

        void failedRegister();

        void incompleteRegister(int code);

        void tempraryUserDeleted();

        void uploadImageListener(boolean state, FirebaseUser firebaseUser);

        void errorLogin();

        void firstUserConfigured();

        void uploaImageError();
    }

    interface Model{

        void uploadImageListener(boolean state, FirebaseUser firebaseUser);
        void onRegisterError(Exception e);
        void onRegisterSuccess(User user);
        void firstUserConfigured(boolean status);
        void destroyTemporaryUser();
    }
}
