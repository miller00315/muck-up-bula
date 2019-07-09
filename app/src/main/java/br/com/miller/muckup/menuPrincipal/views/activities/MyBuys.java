package br.com.miller.muckup.menuPrincipal.views.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import br.com.miller.muckup.R;
import br.com.miller.muckup.api.FirebaseBuy;
import br.com.miller.muckup.evaluate.view.EvaluateAct;
import br.com.miller.muckup.helpers.Constants;
import br.com.miller.muckup.menuPrincipal.adapters.Item;
import br.com.miller.muckup.menuPrincipal.adapters.MyBuyRecyclerAdapter;
import br.com.miller.muckup.models.Buy;
import br.com.miller.muckup.store.adapters.ProductsDetailsRecyclerAdapter;
import br.com.miller.muckup.utils.AlertDialogUtils;

public class MyBuys extends AppCompatActivity implements Item.OnAdapterInteract, AlertDialogUtils.AltertDialogUtilsTask,
        FirebaseBuy.FirebaseBuyListener {

    private RecyclerView recyclerBuys, recyclerMyBuy;

    private AlertDialogUtils alertDialogUtils;

    private FirebaseBuy firebaseBuy;

    private SharedPreferences sharedPreferences;

    private ProductsDetailsRecyclerAdapter productsDetailsRecyclerAdapter;

    private MyBuyRecyclerAdapter buyRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_buys);
        alertDialogUtils = new AlertDialogUtils(this,this);

        firebaseBuy = new FirebaseBuy(this);

        productsDetailsRecyclerAdapter = new ProductsDetailsRecyclerAdapter(this);

        sharedPreferences = getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);

        buyRecyclerAdapter = new MyBuyRecyclerAdapter(this);

        Toolbar toolbar =  findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        recyclerBuys = findViewById(R.id.recycler_my_buys);

        bindViews();

    }

    private void bindViews(){

        Objects.requireNonNull(getSupportActionBar()).setLogo(R.drawable.ic_icon_bula_small);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Minhas compras");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        recyclerBuys.setLayoutManager(linearLayoutManager);

        recyclerBuys.setAdapter(buyRecyclerAdapter);

        firebaseBuy.getBuys(sharedPreferences.getString(Constants.USER_CITY, ""),
                sharedPreferences.getString(Constants.USER_ID_FIREBASE, ""));

        recyclerBuys.setHasFixedSize(true);

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

        ViewGroup viewGroup = findViewById(android.R.id.content);

        productsDetailsRecyclerAdapter.setArray(buyRecyclerAdapter.getBuys().get(bundle.getInt("item")).getOffers());

        View view1 = LayoutInflater.from(this).inflate(R.layout.layout_alert_my_buy, viewGroup, false);

        alertDialogUtils.creatAlertNeutralButton(view1,buyRecyclerAdapter.getBuys().get(bundle.getInt("item")) , 0);

        recyclerMyBuy = view1.findViewById(R.id.recycler_my_buy);

        TextView header = view1.findViewById(R.id.header_buy);
        TextView address = view1.findViewById(R.id.address_my_buy);
        TextView totalValue = view1.findViewById(R.id.total_value_my_buy);
        TextView storeName = view1.findViewById(R.id.store_name);
        TextView sendValue = view1.findViewById(R.id.send_value);

        header.setText("Compra: ".concat(buyRecyclerAdapter.getBuys().get(bundle.getInt("item")).getId()));
        storeName.setText(buyRecyclerAdapter.getBuys().get(bundle.getInt("item")).getOffers().get(0).getStore());
        sendValue.setText("Taxa envio: R$ ".concat( String.format(Locale.getDefault(),"%.2f",buyRecyclerAdapter.getBuys().get(bundle.getInt("item")).getSendValue())));
        totalValue.setText("Total: R$ ".concat(String.format(Locale.getDefault(),"%.2f", buyRecyclerAdapter.getBuys().get(bundle.getInt("item")).getTotalValue())));

       if(buyRecyclerAdapter.getBuys().get(bundle.getInt("item")).getAddress() != null){

           address.setText("Enviado para: ".concat(buyRecyclerAdapter.getBuys().get(bundle.getInt("item")).getAddress()));
       }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        recyclerMyBuy.setLayoutManager(linearLayoutManager);

        recyclerMyBuy.setHasFixedSize(true);

        recyclerMyBuy.setAdapter(productsDetailsRecyclerAdapter);
    }

    @Override
    public void onAlertPositive(Object o, int type) { if(productsDetailsRecyclerAdapter.getItemCount() > 0) productsDetailsRecyclerAdapter.clear();}

    @Override
    public void onAlertNeutral(Object o, int type) {

        if(productsDetailsRecyclerAdapter.getItemCount() > 0) productsDetailsRecyclerAdapter.clear();

        if(o instanceof Buy){
            this.evaluateBuy( (Buy) o);
        }

    }

    @Override
    public void onAlertNegative() { if(productsDetailsRecyclerAdapter.getItemCount() > 0) productsDetailsRecyclerAdapter.clear(); }

    @Override
    public void registerBuy(Boolean registered) { }

    @Override
    public void onReceiverBuy(ArrayList<Buy> buys) {

        if(buys != null)
            buyRecyclerAdapter.setArray(buys);
        else
            Toast.makeText(this, "Você não possui compras, tente novamente", Toast.LENGTH_LONG).show();
    }

    @Override
    public void evaluateBuy(Buy buy) {

        if(buy != null){
            Intent intent = new Intent(this, EvaluateAct.class);
            intent.putExtra("buy_id", String.valueOf(buy.getId()));
            startActivity(intent);
        }
    }
}
