package br.com.miller.muckup.login.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

import br.com.miller.muckup.R;
import br.com.miller.muckup.login.presenter.LoginPresenter;
import br.com.miller.muckup.login.tasks.LoginTasks;
import br.com.miller.muckup.passwordRecovery.view.PasswordRecovery;
import br.com.miller.muckup.register.activities.Register;

public class Login extends AppCompatActivity implements  LoginTasks.Presenter {

    private EditText loginEditText, passwordEditText;
    private LoginPresenter loginPresenter;
    private ScrollView mainLayout;
    private RelativeLayout layoutLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEditText = findViewById(R.id.login);
        passwordEditText = findViewById(R.id.password);
        mainLayout = findViewById(R.id.main_layout);
        layoutLoading = findViewById(R.id.loading_layout);

        if(loginPresenter == null)
            loginPresenter = new LoginPresenter(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        loginPresenter.onDestroy();

    }

    @Override
    protected void onPause() {
        super.onPause();
        loginPresenter.removeAuthListener();
    }

    @Override
    protected void onResume() {
        super.onResume();

        layoutLoading.setVisibility(View.INVISIBLE);
        mainLayout.setVisibility(View.VISIBLE);

        loginPresenter.addAuthListener();
    }

    public void register(View view) {

        Intent intent = new Intent(this, Register.class);
        startActivityForResult(intent, 123);
    }


    public void login(View view) {

        layoutLoading.setVisibility(View.VISIBLE);
        mainLayout.setVisibility(View.INVISIBLE);

        String login = this.loginEditText.getText().toString().trim();
        String password = this.passwordEditText.getText().toString().trim();
        loginPresenter.checkString(login, password);
    }

    public void recuperarSenha(View view) {

        Intent intent = new Intent(this, PasswordRecovery.class);
        startActivity(intent);
    }

    @Override
    public void userEmpty() {
        layoutLoading.setVisibility(View.INVISIBLE);
        mainLayout.setVisibility(View.VISIBLE);
        Toast.makeText(this, "Insira o usuário", Toast.LENGTH_LONG).show();
    }

    @Override
    public void passWordEmpty() {
        layoutLoading.setVisibility(View.INVISIBLE);
        mainLayout.setVisibility(View.VISIBLE);
        Toast.makeText(this, "Insira a senha", Toast.LENGTH_LONG).show(); }

    @Override
    public void emailIncorrect() {
        layoutLoading.setVisibility(View.INVISIBLE);
        mainLayout.setVisibility(View.VISIBLE);

        Toast.makeText(this, "Email incorreto", Toast.LENGTH_LONG).show(); }

    @Override
    public void loginFail() {

        layoutLoading.setVisibility(View.INVISIBLE);
        mainLayout.setVisibility(View.VISIBLE);

        Toast.makeText(this, "Verifique usuário e senha", Toast.LENGTH_LONG).show();
    }

    @Override
    public void loginSuccess(FirebaseUser firebaseUser) { Toast.makeText(this, "Bem-vindo", Toast.LENGTH_LONG).show(); }
}
