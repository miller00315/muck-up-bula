package br.com.miller.muckup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import br.com.miller.muckup.api.AuthVerification;
import br.com.miller.muckup.helpers.Constants;
import br.com.miller.muckup.helpers.Permissions;
import br.com.miller.muckup.login.views.Login;
import br.com.miller.muckup.menuPrincipal.views.activities.MenuPrincipal;
import br.com.miller.muckup.domain.User;
import br.com.miller.muckup.utils.NotificationUtils;

public class MainActivity extends AppCompatActivity implements Permissions.OnPermissionChanged, AuthVerification.AuthVerificationListener {

    private AuthVerification authVerification;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       Permissions permissions = new Permissions(this, this);

        sharedPreferences   = getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);

       permissions.checkSelfPermission();

        NotificationUtils.createNotificationChannel(getApplicationContext());

    }

    private void startLogin(){

        Intent intent = new Intent(this, Login.class);

        startActivityForResult(intent, Constants.START_CODE);

        finish();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permission, @NonNull int[] grantsResults) {


        if(requestCode == Permissions.pCode){
            boolean flag = true;

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

                for (int i = 0, len = permission.length; i < len; i++){

                    if(grantsResults[i] != PackageManager.PERMISSION_GRANTED){
                        flag = false;
                    }

                }

                if(flag){

                    onPermissionChanged(true);

                }else{

                    onPermissionChanged(false);

                }

            }
        }

    }

    @Override
    public void onPermissionChanged(boolean state) {

        if(state){

            authVerification = new AuthVerification(this,this);

        }else{
            Toast.makeText(this,"As autorizações não foram liberadas!" ,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        authVerification.removeListener();
    }

    @Override
    public void login(User user) {

        Intent intent = new Intent(this, MenuPrincipal.class);

        startActivityForResult(intent, Constants.START_CODE);

        finish();
    }

    @Override
    public void errorLogin() {
        startLogin();
    }
}
