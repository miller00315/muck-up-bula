package br.com.miller.muckup.menuPrincipal.views.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;
import java.util.Objects;

import br.com.miller.muckup.R;
import br.com.miller.muckup.api.FirebaseImage;
import br.com.miller.muckup.helpers.AlertContructor;
import br.com.miller.muckup.helpers.Constants;
import br.com.miller.muckup.medicine.activities.Medicine;
import br.com.miller.muckup.menuPrincipal.adapters.AdvRecyclerAdapter;
import br.com.miller.muckup.menuPrincipal.adapters.SearchResultAdapter;
import br.com.miller.muckup.menuPrincipal.adapters.StoreRecyclerAdapter;
import br.com.miller.muckup.menuPrincipal.views.fragments.DepartamentsFragment;
import br.com.miller.muckup.menuPrincipal.views.fragments.SearchFragment;
import br.com.miller.muckup.menuPrincipal.adapters.TabPagerAdapter;
import br.com.miller.muckup.menuPrincipal.views.fragments.PerfilFragment;
import br.com.miller.muckup.menuPrincipal.views.fragments.StoresFragment;
import br.com.miller.muckup.store.views.activities.Store;

public class MenuPrincipal extends AppCompatActivity implements
        SearchFragment.OnFragmentInteractionListener,
        DepartamentsFragment.OnFragmentInteractionListener,
        StoresFragment.OnFragmentInteractionListener,
        PerfilFragment.OnFragmentInteractionListener,
        AlertContructor.OnAlertInteract,
        FirebaseImage.FirebaseImageListener {

    private ViewPager menuPrincipal;

    private AlertContructor alertContructor;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    menuPrincipal.setCurrentItem(0);
                    return true;
                case R.id.navigation_dashboard:
                    menuPrincipal.setCurrentItem(1);
                    return true;
                case R.id.navigation_notifications:
                    menuPrincipal.setCurrentItem(2);
                    return true;
                case R.id.navigation_perfil:
                    menuPrincipal.setCurrentItem(3);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        Toolbar toolbar =  findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setLogo(R.drawable.ic_icon_bula_small);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Bula");

        alertContructor = new AlertContructor(this);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        menuPrincipal = findViewById(R.id.pager);

        PagerAdapter pagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), 4, this);

        menuPrincipal.setAdapter(pagerAdapter);

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

                SharedPreferences.Editor editor = getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE).edit();

                editor.clear();

                editor.apply();

                FirebaseAuth.getInstance().signOut();

                finish();

                break;
            }

            case R.id.sac:{

                ViewGroup viewGroup = findViewById(android.R.id.content);

                alertContructor.personalizedAlert(LayoutInflater.from(this).inflate(R.layout.layout_alert_sac, viewGroup, false));
            }

            default:
                break;
        }


        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onFragmentInteraction(Bundle bundle) {

        if(bundle != null){

            Intent intent;

           if(Objects.equals(bundle.getString("type"), AdvRecyclerAdapter.ID)){

               intent = new Intent(this, Store.class);
               intent.putExtra("data", bundle);
               startActivity(intent);

           } else if(Objects.equals(bundle.getString("type"), StoreRecyclerAdapter.ID)){

               intent = new Intent(this, Store.class);
               intent.putExtra("data", bundle);
               startActivity(intent);

           } else if(Objects.equals(bundle.getString("code"), PerfilFragment.ID)){

               intent = new Intent(Intent.ACTION_GET_CONTENT);
               intent.setType("image/*");
               startActivityForResult(intent, Constants.INTERNAL_IMAGE);

           } else if(Objects.equals(bundle.getString("type"), SearchResultAdapter.ID)){

               intent = new Intent(this, Medicine.class);
               intent.putExtra("data", bundle);
               startActivity(intent);

           }else if(Objects.equals(bundle.getString("code"), DepartamentsFragment.ID)){

               intent = new Intent(this, DepartamentManager.class);
               intent.putExtra("data", bundle);
               startActivity(intent);

           }

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.INTERNAL_IMAGE){

            if(resultCode == RESULT_OK){

                if(data != null){

                    List<Fragment> fragments = getSupportFragmentManager().getFragments();

                    for (Fragment fragment : fragments){

                        if(fragment instanceof PerfilFragment){

                            PerfilFragment perfilFragment = (PerfilFragment) fragment;
                            perfilFragment.setImageAlert(data);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onAlertPositive(Object object) {

        if(object instanceof String){

            Toast.makeText(this, "Obrigado pela sua mensagem", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAlertNegative() {

    }

    @Override
    public void onAlertError() {

    }


    @Override
    public void onImageDownloadSuccess() {

    }

    @Override
    public void onImageDownloadError() {

    }

}
