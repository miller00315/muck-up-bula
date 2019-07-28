package br.com.miller.muckup.store.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import java.util.Objects;

import br.com.miller.muckup.R;
import br.com.miller.muckup.medicine.activities.Medicine;
import br.com.miller.muckup.menuPrincipal.adapters.OffersRecyclerAdapter;
import br.com.miller.muckup.menuPrincipal.views.activities.DepartamentManager;
import br.com.miller.muckup.store.adapters.TabPagerStoreAdapter;
import br.com.miller.muckup.store.views.fragments.DepartamentStoreFragment;
import br.com.miller.muckup.store.views.fragments.HomeStoreFragment;
import br.com.miller.muckup.store.views.fragments.OpinionFragment;

public class Store extends AppCompatActivity implements
        DepartamentStoreFragment.OnFragmentInteractionListener,
        HomeStoreFragment.OnFragmentInteractionListener,
        OpinionFragment.OnFragmentInteractionListener {

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
                case R.id.store_classification_menu:
                    storePager.setCurrentItem(2);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        Toolbar toolbar =  findViewById(R.id.toolbar);

        Bundle bundle = getIntent().getBundleExtra("data");

        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setLogo(R.drawable.ic_icon_bula_small);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        storePager = findViewById(R.id.pager);

        final PagerAdapter pagerAdapter = new TabPagerStoreAdapter(getSupportFragmentManager(),
                3, this, bundle);

        storePager.setAdapter(pagerAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()  == android.R.id.home)
            finish();


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Bundle bundle) {

        Intent intent;

        if(Objects.equals(bundle.getString("type"), OffersRecyclerAdapter.ID)){

            intent = new Intent(this, Medicine.class);
            intent.putExtra("data", bundle);
            startActivity(intent);

        }else if(Objects.equals(bundle.getString("code"), DepartamentStoreFragment.ID)){

            intent = new Intent(this, DepartamentManager.class);
            intent.putExtra("data", bundle);
            startActivity(intent);

        }else if(Objects.equals(bundle.getString("code"), HomeStoreFragment.ID)){

            Log.w("this", "lo");

            if(bundle.containsKey("storeName")){

                Objects.requireNonNull(getSupportActionBar()).setTitle(bundle.getString("storeName"));

            }else{
                storePager.setCurrentItem(2);
            }
        }

    }
}
