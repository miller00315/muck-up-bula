package br.com.miller.muckup.register.presenters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import com.google.firebase.auth.FirebaseUser;


import br.com.miller.muckup.api.AuthVerification;
import br.com.miller.muckup.menuPrincipal.activities.MenuPrincipal;
import br.com.miller.muckup.models.User;
import br.com.miller.muckup.register.models.RegisterModel;
import br.com.miller.muckup.register.tasks.Task;
import br.com.miller.muckup.utils.StringUtils;

public class RegisterPresenter implements Task.Model, Task.View, AuthVerification.AuthVerificationListener {

    private Task.Presenter presenter;
    private User user;
    private RegisterModel model;
    private AuthVerification authVerification;

    public RegisterPresenter(Task.Presenter presenter) {
        this.presenter = presenter;
        this.model = new RegisterModel(this);
        this.user = new User();
    }

    public void configureFirstUser(){

        model.configureFirstUser();
    }

    public void uploadImage(User user, Bitmap bitmap){

        if(bitmap != null)
            model.uploadImageFirebase(user, bitmap);
        else
            presenter.uploaImageError();
    }

    public void checkRegister(String name, String surname, String email,String city, String phone, String password, String repeatPassword, String birthDate) {

        boolean dataOk = true;

        if(name.isEmpty()) {
            presenter.incompleteRegister( 0);
            dataOk = false;
        }

        if(surname.isEmpty()){
            presenter.incompleteRegister( 1);
            dataOk = false;
        }

        if(email.isEmpty()){

            presenter.incompleteRegister(2);
            dataOk = false;

        }else{

            if(!StringUtils.isValidEmail(email)){
                presenter.incompleteRegister( 2);
                dataOk = false;
            }
        }

        if(phone.isEmpty()){

            presenter.incompleteRegister( 3);
            dataOk = false;

        }else{

            if(!StringUtils.isValidPhone(phone)){
                presenter.incompleteRegister(3);
                dataOk = false;
            }
        }

        if(password.isEmpty()){
            presenter.incompleteRegister( 4);
            dataOk = false;
        }

        if(repeatPassword.isEmpty()){
            presenter.incompleteRegister( 5);
            dataOk = false;
        }

        if(!password.equals(repeatPassword))
        {
            presenter.incompleteRegister( 6);
            dataOk = false;
        }

        if(birthDate.isEmpty()){
            presenter.incompleteRegister(7);
            dataOk = false;

        }else{

            if(!StringUtils.isValidDate(birthDate)) {
                presenter.incompleteRegister(7);
                dataOk = false;
            }
        }

        if(dataOk)
            setupUser(name, surname, email, city, phone, password, birthDate);

    }



    @SuppressLint("SimpleDateFormat")
    public User setupUser(String name, String surname, String email, String city, String phone, String password, String birthDate){

        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setCity(city);
        user.setPhone(phone);
        user.setBirth_date(StringUtils.parseDate(birthDate));
        model.registerUser(user, password);

        return user;

    }

    @Override
    public void uploadImageListener(boolean state, FirebaseUser firebaseUser) {

        presenter.uploadImageListener(state, firebaseUser);

        authVerification = new AuthVerification((Context) presenter, this);
    }

    @Override
    public void onRegisterError(Exception e) {
        presenter.failedRegister();
    }

    @Override
    public void onRegisterSuccess(User user) {
        presenter.successRegister(user);
    }

    @Override
    public void firstUserConfigured(boolean status) {
        presenter.firstUserConfigured();
    }

    @Override
    public void destroyTemporaryUser() {
        presenter.tempraryUserDeleted();
    }

    @Override
    public void onDestroy() {
        if(authVerification != null)
            authVerification.removeListener();
        if(model != null)
            model.destroyRegisterFirebase();
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
        presenter.errorLogin();
    }
}
