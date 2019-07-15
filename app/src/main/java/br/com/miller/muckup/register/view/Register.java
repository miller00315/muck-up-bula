package br.com.miller.muckup.register.view;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

import br.com.miller.muckup.R;
import br.com.miller.muckup.helpers.Constants;
import br.com.miller.muckup.helpers.ImageHelper;
import br.com.miller.muckup.domain.User;
import br.com.miller.muckup.register.presenters.RegisterPresenter;
import br.com.miller.muckup.register.tasks.Task;
import br.com.miller.muckup.utils.ImageUtils;
import br.com.miller.muckup.utils.Mask;

public class Register extends AppCompatActivity implements Task.Presenter {

    private RegisterPresenter registerPresenter;
    private ImageView image;
    private ScrollView mainLayout;
    private RelativeLayout loadingLayout;
    private EditText name, surname, email, phone, passwordInput, repeatPassword, birthDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerPresenter = new RegisterPresenter(this);

        registerPresenter.configureFirstUser();

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

        name = findViewById(R.id.name_user);
        surname = findViewById(R.id.surname_user);
        email = findViewById(R.id.email_user);
        phone = findViewById(R.id.phone_user);
        loadingLayout = findViewById(R.id.loading_layout);
        mainLayout = findViewById(R.id.main_layout);
        passwordInput = findViewById(R.id.password_user);
        repeatPassword = findViewById(R.id.repeat_password_user);
        birthDate = findViewById(R.id.birth_date_user);
        image = findViewById(R.id.image_user);

        phone.addTextChangedListener(Mask.insert(Mask.CELULAR_MASK, phone));
        birthDate.addTextChangedListener(Mask.insert(Mask.DATA_MASK, birthDate));

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, Constants.INTERNAL_IMAGE);
            }
        });

        loadingLayout.setVisibility(View.VISIBLE);
        mainLayout.setVisibility(View.INVISIBLE);
    }

    public void registerUser(View view){

        loadingLayout.setVisibility(View.VISIBLE);
        mainLayout.setVisibility(View.INVISIBLE);

        setInputColor();

        registerPresenter.checkRegister(name.getText().toString(),
                surname.getText().toString(),
                email.getText().toString(),
                "Lavras",
                phone.getText().toString(),
                passwordInput.getText().toString(),
                repeatPassword.getText().toString(),
                birthDate.getText().toString());

    }

    public void setInputColor(){

        name.setHintTextColor(getResources().getColor(R.color.gray));
        surname.setHintTextColor(getResources().getColor(R.color.gray));
        email.setHintTextColor(getResources().getColor(R.color.gray));
        birthDate.setHintTextColor(getResources().getColor(R.color.gray));
        phone.setHintTextColor(getResources().getColor(R.color.gray));
        passwordInput.setHintTextColor(getResources().getColor(R.color.gray));
        repeatPassword.setHintTextColor(getResources().getColor(R.color.gray));

        email.setTextColor(getResources().getColor(R.color.dark));
        birthDate.setTextColor(getResources().getColor(R.color.dark));
        phone.setTextColor(getResources().getColor(R.color.dark));

    }


    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(registerPresenter != null)
            registerPresenter.onDestroy();

    }

    @Override
    public void successRegister(User user) {
        Toast.makeText(this, "Você já está registrado, agora vamos prepara sua foto.", Toast.LENGTH_SHORT).show();
        registerPresenter.uploadImage(user, ImageUtils.getImageUser(image));

    }

    @Override
    public void failedRegister() {

        loadingLayout.setVisibility(View.INVISIBLE);
        mainLayout.setVisibility(View.VISIBLE);
        Toast.makeText(this, "Erro ao realizar registro, tente novamente", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void incompleteRegister( int code) {

        loadingLayout.setVisibility(View.INVISIBLE);
        mainLayout.setVisibility(View.VISIBLE);
        Toast.makeText(this, "As informações em vermelho estão incorretas ou faltando!", Toast.LENGTH_LONG).show();

        switch (code){

            case 0:
                name.setHintTextColor(getResources().getColor(R.color.red));
                break;
            case 1:
                surname.setHintTextColor(getResources().getColor(R.color.red));
                break;
            case 2:
                email.setHintTextColor(getResources().getColor(R.color.red));
                email.setTextColor(getResources().getColor(R.color.red));
                break;
            case 3:
                phone.setHintTextColor(getResources().getColor(R.color.red));
                phone.setTextColor(getResources().getColor(R.color.red));
                break;
            case 4:
                passwordInput.setHintTextColor(getResources().getColor(R.color.red));
                break;
            case 5:
                repeatPassword.setHintTextColor(getResources().getColor(R.color.red));
                break;
            case 6:
                passwordInput.setHintTextColor(getResources().getColor(R.color.red));
                repeatPassword.setHintTextColor(getResources().getColor(R.color.red));
                break;

            case 7:
                birthDate.setHintTextColor(getResources().getColor(R.color.red));
                birthDate.setTextColor(getResources().getColor(R.color.red));
                break;

                default: break;
        }
    }

    @Override
    public void tempraryUserDeleted() { Toast.makeText(this, "Usuário temporário excluído", Toast.LENGTH_SHORT).show(); }

    @Override
    public void uploadImageListener(boolean state, FirebaseUser firebaseUser) {

        if(state && firebaseUser != null) {

            Toast.makeText(this, "Tudo ok, pronto para começar", Toast.LENGTH_LONG).show();
            finishActivity(123);

        }else {

            Toast.makeText(this, "Erro ao enviar a imagem, saia e tente novamente", Toast.LENGTH_LONG).show();

        }

    }

    @Override
    public void errorLogin() {

        loadingLayout.setVisibility(View.INVISIBLE);
        mainLayout.setVisibility(View.VISIBLE);
        Toast.makeText(this, "Erro no login, saia e entre novamente", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void firstUserConfigured() {

        loadingLayout.setVisibility(View.INVISIBLE);
        mainLayout.setVisibility(View.VISIBLE);

    }

    @Override
    public void uploaImageError() {

        loadingLayout.setVisibility(View.INVISIBLE);
        mainLayout.setVisibility(View.VISIBLE);
        Toast.makeText(this, "Erro ao tratar a imagem",Toast.LENGTH_SHORT).show();

    }

}
