package br.com.miller.muckup.register.presenters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.miller.muckup.api.AuthVerification;
import br.com.miller.muckup.menuPrincipal.activities.MenuPrincipal;
import br.com.miller.muckup.models.User;
import br.com.miller.muckup.register.models.RegisterModel;
import br.com.miller.muckup.register.tasks.Task;

public class RegisterPresenter implements Task.Model, Task.View, AuthVerification.AuthVerificationListener {

    private Task.Presenter presenter;
    private User user;
    private RegisterModel model;
    private AuthVerification authVerification;

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);

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

    public void checkRegister(String name, String surname, String email,String city, String phone, String password, String repeatPassword){

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

            if(!validarEmail(email)){
                presenter.incompleteRegister( 2);
                dataOk = false;
            }
        }

        if(phone.isEmpty()){
            presenter.incompleteRegister( 3);
            dataOk = false;
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

        if(dataOk)
            setupUser(name, surname, email, city, phone, password);

    }

    private boolean validarEmail(String email){

        Matcher matcher = pattern.matcher(email);
        return matcher.matches();

    }

    public Bitmap getImageUser(ImageView image){

        Bitmap bitmap;

        if (image.getDrawable() instanceof BitmapDrawable) {
            bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        } else {
            Drawable d = image.getDrawable();
            bitmap = Bitmap.createBitmap(d.getIntrinsicWidth(), d.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            d.draw(canvas);
        }

        return bitmap;

    }

    private void setupUser(String name, String surname, String email, String city, String phone, String password){

        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setCity(city);
        user.setPhone(phone);

        model.registerUser(user, password);

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
