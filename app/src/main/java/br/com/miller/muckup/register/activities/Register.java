package br.com.miller.muckup.register.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

import br.com.miller.muckup.R;
import br.com.miller.muckup.api.AuthVerification;
import br.com.miller.muckup.api.RegisterFirebase;
import br.com.miller.muckup.helpers.Constants;
import br.com.miller.muckup.helpers.ImageHelper;
import br.com.miller.muckup.menuPrincipal.activities.MenuPrincipal;
import br.com.miller.muckup.models.User;

public class Register extends AppCompatActivity implements RegisterFirebase.RegisterFirebaseListener,
        AuthVerification.AuthVerificationListener {

    private RegisterFirebase registerFirebase;
    private AuthVerification authVerification;
    private User user;
    private String password;
    private Bitmap user_image;
    private ImageView image;
    private EditText name, surname, email, phone, passwordInput, repeatPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        user = new User();

        registerFirebase = new RegisterFirebase(this);

        registerFirebase.configureFirstUser();

        name = findViewById(R.id.name_user);
        surname = findViewById(R.id.surname_user);
        email = findViewById(R.id.email_user);
        phone = findViewById(R.id.phone_user);
        passwordInput = findViewById(R.id.password_user);
        repeatPassword = findViewById(R.id.repeat_password_user);
        image = findViewById(R.id.image_user);

        bindViews();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.INTERNAL_IMAGE){

            if(resultCode == RESULT_OK){

                if(data != null){

                    ImageHelper.setImageFromMemory(data, this, image);
                }
            }
        }

    }

    public void bindViews(){

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, Constants.INTERNAL_IMAGE);
            }
        });
    }

    public void registerUser(View view) {

        user.setName(name.getText().toString());
        user.setSurname(surname.getText().toString());
        user.setEmail(email.getText().toString());
        user.setCity("Lavras");
        user.setPhone(phone.getText().toString());
        password = passwordInput.getText().toString();
        user_image = getImageUser();
        registerFirebase.registerUser(user, password);

    }

    public Bitmap getImageUser(){

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


    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        registerFirebase.destroyRegisterFirebase();

    }

    @Override
    public void uploadImageListener(boolean state, FirebaseUser user) {

        if(state && user != null) {

            authVerification = new AuthVerification(this);
        }else {

            Toast.makeText(this, "Erro ao enviar a imagem, saia e tente novamente", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onRegisterError() {
        Toast.makeText(this, "Erro no registro, saia e tente novamente",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRegisterUser(User user) {

        registerFirebase.registerCity(user);
        registerFirebase.uploadImageFirebase(user, user_image);

    }

    @Override
    public void firstUserConfigured(boolean status) {


    }

    @Override
    public void login(User user) {

        Intent intent = new Intent(this, MenuPrincipal.class);

        startActivityForResult(intent, Constants.START_CODE);

        finish();

    }

    @Override
    public void errorLogin() {

        Toast.makeText(this, "Erro ao consultar dados, saia e tente novamente",Toast.LENGTH_LONG).show();

    }


}
