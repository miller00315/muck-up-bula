package br.com.miller.muckup.store.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import br.com.miller.muckup.R;
import br.com.miller.muckup.api.FirebaseDepartament;
import br.com.miller.muckup.api.FirebaseImage;
import br.com.miller.muckup.api.FirebaseStore;
import br.com.miller.muckup.medicine.activities.Medicine;
import br.com.miller.muckup.menuPrincipal.activities.items.DepartamentManager;
import br.com.miller.muckup.menuPrincipal.adapters.Item;
import br.com.miller.muckup.models.Departament;
import br.com.miller.muckup.store.adapters.TabPagerStoreAdapter;
import br.com.miller.muckup.store.fragments.DepartamentFragment;
import br.com.miller.muckup.store.fragments.HomeStoreFragment;

public class Store extends AppCompatActivity implements
        DepartamentFragment.OnFragmentInteractionListener,
        HomeStoreFragment.OnFragmentInteractionListener,
        FirebaseDepartament.FirebaseDepartamentListener,
        FirebaseImage.FirebaseImageListener,
        FirebaseStore.FirebaseStoreListener,
        Item.OnAdapterInteract {

    private ViewPager storePager;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    storePager.setCurrentItem(0);
                    return true;
                case R.id.navigation_dashboard:
                    storePager.setCurrentItem(1);
                    return true;
            }
            return false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        Toolbar toolbar =  findViewById(R.id.toolbar);

        Bundle bundle = getIntent().getBundleExtra("data");

        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setLogo(R.drawable.ic_pharma_seeklogo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        storePager = findViewById(R.id.pager);

        final PagerAdapter pagerAdapter = new TabPagerStoreAdapter(getSupportFragmentManager(),
                2, this, bundle);

        storePager.setAdapter(pagerAdapter);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAdapterInteract(Bundle bundle) {

        if(bundle != null){

            Intent intent;

            switch (bundle.getInt("type")){

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
                    break;

                case 3:
                    break;

                    default:
                        break;

            }
        }
    }

    @Override
    public void onStoresChanged(ArrayList<br.com.miller.muckup.models.Store> stores) {

    }

    @Override
    public void onStoreChanged(br.com.miller.muckup.models.Store store) {

        if(store != null){

            if(getSupportActionBar() != null)
                getSupportActionBar().setTitle(store.getTime());

            for(Fragment fragment : getSupportFragmentManager().getFragments())
                if(fragment instanceof HomeStoreFragment){

                    HomeStoreFragment homeStoreFragment = (HomeStoreFragment) fragment;
                    homeStoreFragment.storeReceiver(store);
                    break;
                }

        }else
            Toast.makeText(this, "Erro ao obter os dados da loja, tente novamente", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onImageDownloadSuccess() {

    }

    @Override
    public void onImageDownloadError() {

    }

    @Override
    public void onDepartamentReceived(Departament departament) {

    }

    @Override
    public void onDepartamentsReceived(ArrayList<Departament> departaments) {

        if(departaments != null){

                for(Fragment fragment : getSupportFragmentManager().getFragments())
                    if(fragment instanceof DepartamentFragment) {
                        DepartamentFragment departamentFragment = (DepartamentFragment) fragment;
                        departamentFragment.departamentReceiver(departaments);
                    }

        }else{
            Toast.makeText(this, "Esta loja não possui departamentos cadastrados", Toast.LENGTH_LONG).show();
        }

    }
}
