package br.com.miller.muckup.menuPrincipal.views.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import br.com.miller.muckup.R;
import br.com.miller.muckup.evaluate.view.EvaluateAct;
import br.com.miller.muckup.helpers.Constants;
import br.com.miller.muckup.menuPrincipal.adapters.Item;
import br.com.miller.muckup.menuPrincipal.adapters.MyBuyRecyclerAdapter;
import br.com.miller.muckup.domain.Buy;
import br.com.miller.muckup.menuPrincipal.presenters.MyBuysPresenter;
import br.com.miller.muckup.menuPrincipal.tasks.MyBuysTasks;
import br.com.miller.muckup.utils.alerts.MyBuysDialogFragment;

public class MyBuys extends AppCompatActivity implements Item.OnAdapterInteract,
        MyBuysDialogFragment.MyBuysDialogListener,
        MyBuysTasks.Presenter {

    public static final String ID = MyBuys.class.getName();

    private RecyclerView recyclerBuys;

    private SharedPreferences sharedPreferences;

    private MyBuyRecyclerAdapter buyRecyclerAdapter;

    private MyBuysPresenter presenter;

    private RelativeLayout loadingLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_buys);

        sharedPreferences = getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);

        buyRecyclerAdapter = new MyBuyRecyclerAdapter(this);

        loadingLayout = findViewById(R.id.loading_layout);

        presenter = new MyBuysPresenter(this);

        Toolbar toolbar =  findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        recyclerBuys = findViewById(R.id.recycler_my_buys);

        showLoading();

        bindViews();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.removeListener(sharedPreferences.getString(Constants.USER_CITY, ""),
                sharedPreferences.getString(Constants.USER_ID_FIREBASE, ""));
    }

    public void showLoading(){

        recyclerBuys.setVisibility(View.INVISIBLE);
        loadingLayout.setVisibility(View.VISIBLE);

    }

    public void hideLoading(){
        recyclerBuys.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.INVISIBLE);
    }

    private void bindViews(){

        if(loadingLayout.getVisibility() ==View.VISIBLE)
            presenter.temporaryVerify(sharedPreferences.getString(Constants.USER_CITY, ""),
                    sharedPreferences.getString(Constants.USER_ID_FIREBASE, ""));

        Objects.requireNonNull(getSupportActionBar()).setLogo(R.drawable.ic_icon_bula_small);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Minhas compras");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        recyclerBuys.setLayoutManager(linearLayoutManager);

        recyclerBuys.setAdapter(buyRecyclerAdapter);

        presenter.getBuys(sharedPreferences.getString(Constants.USER_CITY, ""),
                        sharedPreferences.getString(Constants.USER_ID_FIREBASE, ""));

        recyclerBuys.setHasFixedSize(true);

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

        MyBuysDialogFragment myBuysDialogFragment = MyBuysDialogFragment.newInstance(bundle);

        myBuysDialogFragment.setMyBuysDialogListener(this);

        myBuysDialogFragment.setProductAdapter(buyRecyclerAdapter.getBuys().get(bundle.getInt("item")), this);

        myBuysDialogFragment.openDialog(getSupportFragmentManager());
    }

    @Override
    public void onBuyEmpty() {
        hideLoading();
        Toast.makeText(this, "Compras vazias", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBuySuccess(ArrayList<Buy> buys) {
        hideLoading();
        if(buyRecyclerAdapter.getItemCount() > 0) buyRecyclerAdapter.getBuys().clear();
        buyRecyclerAdapter.setArray(buys);
    }

    @Override
    public void onBuyFailed() {
        hideLoading();
        Toast.makeText(this, "Você não possui compras, tente novamente", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onEvaluetedItem(Buy buy) {

        Intent intent = new Intent(this, EvaluateAct.class);
        intent.putExtra("buy_id", buy.getId());
        startActivity(intent);

    }
}
