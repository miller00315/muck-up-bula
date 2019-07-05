package br.com.miller.muckup.login.tasks;
import com.google.firebase.auth.FirebaseUser;

public interface LoginTasks {

    interface Model{

        void loginFail();
        void loginSuccess(FirebaseUser firebaseUser);

    }

    interface View{

        void onDestroy();
    }

    interface Presenter{

        void userEmpty();
        void passWordEmpty();
        void emailIncorrect();
        void loginFail();
        void loginSuccess(FirebaseUser firebaseUser);

    }

}
