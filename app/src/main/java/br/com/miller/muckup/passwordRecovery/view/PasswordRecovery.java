package br.com.miller.muckup.passwordRecovery.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

import br.com.miller.muckup.R;
import br.com.miller.muckup.helpers.AlertContructor;
import br.com.miller.muckup.passwordRecovery.presenter.PasswordRecoveryPresenter;
import br.com.miller.muckup.passwordRecovery.tasks.PasswordRecoveryTask;

public class PasswordRecovery extends AppCompatActivity implements PasswordRecoveryTask.PasswordRecoveryPresenter, AlertContructor.OnAlertInteract {

    private EditText email;
    private PasswordRecoveryPresenter passwordRecoveryPresenter;
    private AlertContructor alertContructor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_recovery);

        Toolbar toolbar =  findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setLogo(R.drawable.ic_pharma_seeklogo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Recuperar senha");

        alertContructor = new AlertContructor(this);

        email = findViewById(R.id.email);
        passwordRecoveryPresenter = new PasswordRecoveryPresenter(this);
    }

    public void reset(View view) {

        passwordRecoveryPresenter.checkString(email.getText().toString());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.logout: {

                finish();

                break;
            }

            case android.R.id.home: {
                finish();
                break;
            }
            case R.id.sac:{

                ViewGroup viewGroup = findViewById(android.R.id.content);

                alertContructor.personalizedAlert(LayoutInflater.from(this).inflate(R.layout.layout_alert_sac, viewGroup, false));
                break;
            }

            default:
                break;
        }


        return super.onOptionsItemSelected(item);

    }

    @Override
    public void recoveryOk() {
        Toast.makeText(
                this,
                "Recuperação de acesso iniciada. Email enviado.",
                Toast.LENGTH_LONG
        ).show();
    }

    @Override
    public void recoveryFailed() {

        Toast.makeText(
                this,
                "Falhou! Tente novamente.",
                Toast.LENGTH_LONG
        ).show();

    }

    @Override
    public void stringEmpty() {
        Toast.makeText(
                this,
                "Precisamos de um email para recuperar a senha",
                Toast.LENGTH_LONG
        ).show();
    }

    @Override
    public void onAlertPositive(Object object) {

        if(object instanceof String){


        }

    }

    @Override
    public void onAlertNegative() {

    }

    @Override
    public void onAlertError() {

    }
}
