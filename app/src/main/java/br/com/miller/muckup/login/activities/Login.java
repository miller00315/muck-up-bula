package br.com.miller.muckup.login.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

import br.com.miller.muckup.R;
import br.com.miller.muckup.api.AuthVerification;
import br.com.miller.muckup.api.LoginFirebase;
import br.com.miller.muckup.menuPrincipal.activities.MenuPrincipal;
import br.com.miller.muckup.models.User;
import br.com.miller.muckup.register.activities.Register;

public class Login extends AppCompatActivity implements AuthVerification.AuthVerificationListener, LoginFirebase.LoginFirebseListener {

    private EditText loginEditText, passwordEditText;
    private AuthVerification authVerification;
    private LoginFirebase loginFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEditText = findViewById(R.id.login);
        passwordEditText = findViewById(R.id.password);
        loginFirebase = new LoginFirebase(this);

        authVerification = new AuthVerification(this);
        loginFirebase = new LoginFirebase(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        authVerification.removeListener();
    }

    public void register(View view) {

        Intent intent = new Intent(this, Register.class);

        startActivity(intent);
    }


    public void login(View view) {

        String login = this.loginEditText.getText().toString().trim();
        String password = this.passwordEditText.getText().toString().trim();

        if(TextUtils.isEmpty(login)) {
            Toast.makeText(this, "O usuário não pode ficar vazio", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)) {
            Toast.makeText(this, "A senha não pode ficar vazia", Toast.LENGTH_LONG).show();
            return;
        }

        loginFirebase.login(login, password);

    }

    @Override
    public void login(User user) {

        final Intent intent = new Intent(this, MenuPrincipal.class);

        new Thread(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
            }
        }).start();

        finish();

    }

    @Override
    public void errorLogin() {
        Toast.makeText(this, "Erro ao consultar dados, tente novamente",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLoginChanged(FirebaseUser firebaseUser) {

        if(firebaseUser != null){
           // authVerification.getDataLogin(firebaseUser);
            Toast.makeText(this, "Bem-vindo", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "Verifique usuário e senha", Toast.LENGTH_LONG).show();
        }
    }
}
