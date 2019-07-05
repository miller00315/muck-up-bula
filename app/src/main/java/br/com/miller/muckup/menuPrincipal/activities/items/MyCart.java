package br.com.miller.muckup.menuPrincipal.activities.items;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import br.com.miller.muckup.R;
import br.com.miller.muckup.api.FirebaseCart;
import br.com.miller.muckup.helpers.AlertContructor;
import br.com.miller.muckup.helpers.Constants;
import br.com.miller.muckup.menuPrincipal.adapters.Item;
import br.com.miller.muckup.menuPrincipal.adapters.OffersRecyclerAdapter;
import br.com.miller.muckup.models.Offer;
import br.com.miller.muckup.store.buy.view.BuyActivity;

public class MyCart extends AppCompatActivity implements Item.OnAdapterInteract,
        FirebaseCart.FirebaseCartListener,
        AlertContructor.OnAlertInteract {

    private boolean isFabOpen;
    private RecyclerView recyclerCart;
    private AlertContructor alertContructor;
    private OffersRecyclerAdapter offersRecyclerAdapter;
    private FirebaseCart firebaseCart;
    private LinearLayout cleanCart, buyAll;
    private TextView textClean, textBuyAll;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);

        offersRecyclerAdapter = new OffersRecyclerAdapter(this);
        firebaseCart = new FirebaseCart(this);

        isFabOpen = false;

        alertContructor = new AlertContructor(this);

        sharedPreferences = getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);

        bindViews();
    }


    private void bindViews(){

        Toolbar toolbar =  findViewById(R.id.toolbar);
        recyclerCart = findViewById(R.id.recycler_cart);
        textClean = findViewById(R.id.text_clean);
        textBuyAll = findViewById(R.id.text_buy_all);
        cleanCart = findViewById(R.id.clean_cart);
        buyAll = findViewById(R.id.buy_all);

        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setLogo(R.drawable.ic_pharma_seeklogo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Meu carrinho");

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);

        recyclerCart.setLayoutManager(gridLayoutManager);

        recyclerCart.setHasFixedSize(true);

        recyclerCart.setAdapter(offersRecyclerAdapter);

        firebaseCart.firebaseCartgetOffers(sharedPreferences.getString(Constants.USER_CITY, ""),
                sharedPreferences.getString(Constants.USER_ID_FIREBASE, ""));
    }


    private void openFab(){

        isFabOpen = true;
        textClean.setVisibility(View.VISIBLE);
        textBuyAll.setVisibility(View.VISIBLE);
        cleanCart.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        buyAll.animate().translationY(-getResources().getDimension(R.dimen.standard_105));

    }

    private void closeFab(){

        isFabOpen = false;
        textClean.setVisibility(View.INVISIBLE);
        textBuyAll.setVisibility(View.INVISIBLE);
        cleanCart.animate().translationY(0);
        buyAll.animate().translationY(0);
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

        switch (bundle.getInt("type")){

            case 1:
                specialAlert(bundle);
                break;

                default:
                    break;

        }

    }

    private void specialAlert(Bundle bundle){

        AlertDialog alertDialog;

        final Offer offer = offersRecyclerAdapter.getOffer(bundle.getInt("adapter_position"));

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Deseja excluir do seu carrinho:");

        builder.setMessage(offer.getTitle());

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                firebaseCart.firebaseCartDeleteItem(sharedPreferences.getString(Constants.USER_CITY, ""),
                        sharedPreferences.getString(Constants.USER_ID_FIREBASE, ""), offer.getCartId());
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog = builder.create();

        alertDialog.show();
    }

    @Override
    public void onAlertPositive(Object object) {

        if(object instanceof ArrayList){

            firebaseCart.firebaseCartDeleteAll(sharedPreferences.getString(Constants.USER_CITY, ""),
                    sharedPreferences.getString(Constants.USER_ID_FIREBASE, ""));

        }

    }

    @Override
    public void onAlertNegative() {

    }

    @Override
    public void onAlertError() {

    }

    @Override
    public void firebaseCartListnerReceiver(Offer offer) {

    }

    @Override
    public void firebaseCartListenerReceiverOffers(ArrayList<Offer> offers) {

        if(getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.RESUMED))
        if(offers != null)
            if(offers.size() > 0)
                offersRecyclerAdapter.setArray(offers);
            else
                Toast.makeText(this, "Você não possui produtos no carrinho, tente novamente", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Erro ao obter seus produto, tente novamente", Toast.LENGTH_LONG).show();

    }

    @Override
    public void firebaseCartOnItemDeleted(boolean status) {

        if(status) {
            Toast.makeText(this, "Item(ns) exluido(s)", Toast.LENGTH_LONG).show();
            offersRecyclerAdapter.clear();
            firebaseCart.firebaseCartgetOffers(sharedPreferences.getString(Constants.USER_CITY, ""),
                    sharedPreferences.getString(Constants.USER_ID_FIREBASE, ""));
        }

        else
            Toast.makeText(this, "Erro na exclusão, tente novamente.", Toast.LENGTH_LONG).show();

    }

    public void buyAll(View view) {

        if(offersRecyclerAdapter.getOffers().size() > 0){

            Bundle bundle = new Bundle();
            Intent intent = new Intent(this, BuyActivity.class);

            bundle.putString("actvity", MyCart.class.getName());

            intent.putExtra("data", bundle);
            startActivityForResult(intent, 11);

        }else{
            Toast.makeText(this, "Seu carrinho está vazio, adicione produtos", Toast.LENGTH_LONG).show();
        }
    }

    public void cleanCart(View view) {

        if(offersRecyclerAdapter.getOffers().size() > 0)
            alertContructor.simpleAlert("Limpar carrinho", "você tem certeza que deseja excluir todos os itens?",
                offersRecyclerAdapter.getOffers());
        else
            Toast.makeText(this, "Seu carrinho já está vazio", Toast.LENGTH_LONG).show();
    }

    public void showButtons(View view) {

        if(isFabOpen)
            closeFab();
        else
            openFab();
    }
}
