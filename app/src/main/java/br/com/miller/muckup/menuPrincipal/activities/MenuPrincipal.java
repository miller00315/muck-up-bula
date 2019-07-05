package br.com.miller.muckup.menuPrincipal.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import java.util.ArrayList;
import java.util.Objects;

import br.com.miller.muckup.R;
import br.com.miller.muckup.api.FirebaseDepartament;
import br.com.miller.muckup.api.FirebaseImage;
import br.com.miller.muckup.api.FirebaseSearch;
import br.com.miller.muckup.api.FirebaseStore;
import br.com.miller.muckup.api.FirebaseUser;
import br.com.miller.muckup.helpers.AlertContructor;
import br.com.miller.muckup.helpers.Constants;
import br.com.miller.muckup.medicine.activities.Medicine;
import br.com.miller.muckup.menuPrincipal.activities.items.DepartamentManager;
import br.com.miller.muckup.menuPrincipal.activities.items.MyBuys;
import br.com.miller.muckup.menuPrincipal.activities.items.MyCart;
import br.com.miller.muckup.menuPrincipal.adapters.Item.OnAdapterInteract;
import br.com.miller.muckup.menuPrincipal.fragments.HomeFragment;
import br.com.miller.muckup.menuPrincipal.fragments.OffersFragment;
import br.com.miller.muckup.menuPrincipal.adapters.TabPagerAdapter;
import br.com.miller.muckup.menuPrincipal.fragments.PerfilFragment;
import br.com.miller.muckup.menuPrincipal.fragments.StoresFragment;
import br.com.miller.muckup.models.Departament;
import br.com.miller.muckup.models.Offer;
import br.com.miller.muckup.models.User;
import br.com.miller.muckup.store.activities.Store;

public class MenuPrincipal extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener,
        OffersFragment.OnFragmentInteractionListener,
        StoresFragment.OnFragmentInteractionListener,
        PerfilFragment.OnFragmentInteractionListener,
        AlertContructor.OnAlertInteract,
        FirebaseSearch.FirebaseSearchListener,
        FirebaseUser.FirebaseUserListener,
        FirebaseImage.FirebaseImageListener,
        FirebaseStore.FirebaseStoreListener,
        FirebaseDepartament.FirebaseDepartamentListener,
        OnAdapterInteract {

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

        Objects.requireNonNull(getSupportActionBar()).setLogo(R.drawable.ic_pharma_seeklogo);
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

            switch (bundle.getInt("id")){

                case 0 :

                    break;

                case 1:
                    break;

                case 2:
                    intent = new Intent(this, Store.class);
                    startActivity(intent);
                    break;

                case 3:

                    intent = new Intent(this, MyBuys.class);
                    startActivity(intent);

                    break;

                case 4:

                    intent = new Intent(this, MyCart.class);
                    startActivity(intent);

                    break;


                    default:
                        break;
            }
        }
    }


    @Override
    public void onAdapterInteract(Bundle bundle) {

        if(bundle != null){

            Intent intent;

            switch(bundle.getInt("type")){

                case 0:
                    intent = new Intent(this, DepartamentManager.class);
                    intent.putExtra("data", bundle);
                    startActivity(intent);
                    break;
                case 1:
                    intent = new Intent(this, Medicine.class);
                    intent.putExtra("data", bundle);
                    startActivity(intent);
                    break;

                case 2:
                    intent = new Intent(this, Store.class);
                    intent.putExtra("data", bundle);
                    startActivity(intent);
                    break;

                case 3:
                    intent = new Intent(this, DepartamentManager.class);
                    intent.putExtra("data", bundle);
                    startActivity(intent);
                    break;

                    default:
                        break;

            }


        }
    }


    @Override
    public void onFirebaseSearch(Object o) {

        if(o != null){

            if(o instanceof  ArrayList){

                ArrayList<Offer> offers = (ArrayList<Offer>) o;

                for(Fragment fragment: getSupportFragmentManager().getFragments()){

                    if(fragment instanceof HomeFragment){

                        HomeFragment homeFragment = (HomeFragment) fragment;
                        homeFragment.receiveMsg(offers);

                        break;
                    }
                }

            }else{
                Toast.makeText(this, "Erro ao receber respostas, tente novamente", Toast.LENGTH_LONG).show();
            }


        }else{

            for(Fragment fragment: getSupportFragmentManager().getFragments()){

                if(fragment instanceof HomeFragment){

                    HomeFragment homeFragment = (HomeFragment) fragment;
                    homeFragment.receiveMsg(null);

                    break;
                }
            }

        }
    }

    @Override
    public void onSuggetions(String[] suggestions) {

        if(suggestions != null){

            for(Fragment fragment: getSupportFragmentManager().getFragments()){

                if(fragment instanceof  HomeFragment){

                    HomeFragment homeFragment = (HomeFragment) fragment;
                    homeFragment.setSuggestions(suggestions);
                }
            }
        }
    }

    @Override
    public void onStoresChanged(ArrayList<br.com.miller.muckup.models.Store> stores) {

        if(stores != null && stores.size() > 0){

            for(Fragment fragment: getSupportFragmentManager().getFragments()){

                if(fragment instanceof StoresFragment){

                    StoresFragment storesFragment = (StoresFragment) fragment;
                    storesFragment.onStoreReceiver(stores);

                    break;
                }
            }

        }else{
            Toast.makeText(this, "Erro ao obter os dados das lojas, tente novemente", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onStoreChanged(br.com.miller.muckup.models.Store store) {

    }

    @Override
    public void onUserDownload(User user) {

        if(user != null) {

            for(Fragment fragment: getSupportFragmentManager().getFragments()){

                if(fragment instanceof PerfilFragment){
                    PerfilFragment perfilFragment = (PerfilFragment) fragment;
                    perfilFragment.receiverUser(user);
                    break;
                }
            }

        }else{
            Toast.makeText(this, "Erro ao obter os dados do usuário, tente novamente", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onImageDownloadSuccess() {

    }

    @Override
    public void onImageDownloadError() {
        Toast.makeText(this, "Erro ao manipular imagem, tente novamente", Toast.LENGTH_LONG).show();
    }


    @Override
    public void onDepartamentReceived(Departament departament) {

    }

    @Override
    public void onDepartamentsReceived(ArrayList<Departament> departaments) {

        if(departaments != null && departaments.size() > 0){

            for(Fragment fragment : getSupportFragmentManager().getFragments()){

                if(fragment instanceof OffersFragment){

                    OffersFragment departamentFragment = (OffersFragment) fragment;
                    departamentFragment.departamentsReceiver(departaments);
                    break;
                }
            }

        }else{

            Toast.makeText(this, "Erro ao carregar os departamentos, tente novamente", Toast.LENGTH_LONG).show();
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
}
