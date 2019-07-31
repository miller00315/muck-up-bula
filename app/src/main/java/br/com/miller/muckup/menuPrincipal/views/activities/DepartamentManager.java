package br.com.miller.muckup.menuPrincipal.views.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import br.com.miller.muckup.R;
import br.com.miller.muckup.domain.Departament;
import br.com.miller.muckup.medicine.activities.Medicine;
import br.com.miller.muckup.menuPrincipal.adapters.Item;
import br.com.miller.muckup.menuPrincipal.adapters.OffersRecyclerAdapter;
import br.com.miller.muckup.menuPrincipal.presenters.DepartamentManagerPresenter;
import br.com.miller.muckup.menuPrincipal.tasks.DepartamentManagerTask;

public class DepartamentManager extends AppCompatActivity implements
        Item.OnAdapterInteract,
        DepartamentManagerTask.Presenter {

    private RecyclerView recyclerView;
    private OffersRecyclerAdapter offersRecyclerAdapter;
    private Bundle bundle;
    private DepartamentManagerPresenter departamentManagerPresenter;
    private RelativeLayout loadingLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departament);

        Toolbar toolbar =  findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recycler_cart_manager);
        loadingLayout = findViewById(R.id.loading_layout);

        departamentManagerPresenter = new DepartamentManagerPresenter(this);

        bundle = getIntent().getBundleExtra("data");

        offersRecyclerAdapter = new OffersRecyclerAdapter(this, this);

        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setLogo(R.drawable.ic_icon_bula_small);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        bindViews();
    }

    private void showLoading(){
        loadingLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
    }

    private void hideLoading(){
        loadingLayout.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void bindViews(){

        if(!bundle.isEmpty()){

            Objects.requireNonNull(getSupportActionBar()).setTitle(bundle.getString("name_departament", ""));

            showLoading();

            departamentManagerPresenter.getDepartamentCheck(bundle);

        }

        GridLayoutManager linearLayoutManager = new GridLayoutManager(this, 2);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(offersRecyclerAdapter);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAdapterInteract(Bundle bundle) {

        bundle.putString("code", this.bundle.getString("code"));

        Intent intent = new Intent(this, Medicine.class);
        intent.putExtra("data", bundle);

        startActivity(intent);

    }


    @Override
    public void onDepartamentsSuccess(ArrayList<Departament> departaments) { }

    @Override
    public void onDepartamentsStoreSuccess(ArrayList<Departament> departaments) { }

    @Override
    public void onDepartamentsStoreFailed() { }

    @Override
    public void onSingleDepartamenteFailed() {
        Toast.makeText(this, "Nada a ser exibido neste departamento",Toast.LENGTH_SHORT).show();
        hideLoading();
    }

    @Override
    public void onSingleDepartmentSuccess(Departament departament) {
        hideLoading();
        offersRecyclerAdapter.setArray(departament.getOffers()); }

    @Override
    public void onDepartmentsFailed() { }

}
