package br.com.miller.muckup.login.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import br.com.miller.muckup.api.AuthVerification;
import br.com.miller.muckup.login.models.LoginModel;
import br.com.miller.muckup.login.tasks.LoginTasks;
import br.com.miller.muckup.menuPrincipal.views.activities.MenuPrincipal;
import br.com.miller.muckup.domain.User;

public class LoginPresenter implements LoginTasks.Model, AuthVerification.AuthVerificationListener, LoginTasks.View {

    private LoginTasks.Presenter presenter;
    private LoginModel loginModel;
    private AuthVerification authVerification;

    public LoginPresenter(LoginTasks.Presenter presenter) {
        this.presenter = presenter;
        loginModel = new LoginModel(this);
        authVerification = new AuthVerification((Context) presenter, this);
    }

    public void checkString(String email, String password){

        if(email.isEmpty() || password.isEmpty()){

            if(email.isEmpty())
                presenter.userEmpty();

            if(password.isEmpty())
                presenter.userEmpty();
        }else{

            login(email, password);

        }

    }

    public void login(String email, String password){

        loginModel.login(email, password);

    }

    public void removeAuthListener(){
        authVerification.removeListener();
    }

    public void addAuthListener(){
        authVerification.addListener();
    }

    @Override
    public void loginFail() {

         presenter.loginFail();
    }

    @Override
    public void loginSuccess(com.google.firebase.auth.FirebaseUser firebaseUser) {
        presenter.loginSuccess(firebaseUser);
    }

    @Override
    public void login(User user) {

        final Activity act = (Activity) presenter;

        final Intent intent = new Intent((Context)presenter, MenuPrincipal.class);

        new Thread(new Runnable() {
            @Override
            public void run() {
                act.startActivity(intent);
            }
        }).start();

        act.finish();

    }

    @Override
    public void errorLogin() {
        presenter.loginFail();
    }

    @Override
    public void onDestroy() {

        if (authVerification != null) authVerification.removeListener();

    }
}
