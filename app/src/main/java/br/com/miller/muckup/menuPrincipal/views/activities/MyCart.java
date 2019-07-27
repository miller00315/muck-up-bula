package br.com.miller.muckup.menuPrincipal.views.activities;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import br.com.miller.muckup.helpers.AlertContructor;
import br.com.miller.muckup.helpers.Constants;
import br.com.miller.muckup.menuPrincipal.adapters.Item;
import br.com.miller.muckup.menuPrincipal.adapters.OffersRecyclerAdapter;
import br.com.miller.muckup.domain.Offer;
import br.com.miller.muckup.menuPrincipal.presenters.MyCartPresenter;
import br.com.miller.muckup.menuPrincipal.tasks.MyCartTasks;
import br.com.miller.muckup.store.buy.view.BuyActivity;
import br.com.miller.muckup.utils.alerts.CartItemDialogFragment;

public class MyCart extends AppCompatActivity implements Item.OnAdapterInteract,
        MyCartTasks.Presenter,
        CartItemDialogFragment.CartItemListener,
        AlertContructor.OnAlertInteract{

    public static final String ID = MyCart.class.getName();

    private boolean isFabOpen;
    private AlertContructor alertContructor;
    private OffersRecyclerAdapter offersRecyclerAdapter;
    private MyCartPresenter presenter;
    private LinearLayout cleanCart, buyAll;
    private TextView textClean, textBuyAll;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);

        offersRecyclerAdapter = new OffersRecyclerAdapter(this, this);

        presenter = MyCartPresenter.newInstance(this);

        isFabOpen = false;

        alertContructor = new AlertContructor(this);

        sharedPreferences = getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);

        bindViews();
    }


    private void bindViews(){

        Toolbar toolbar =  findViewById(R.id.toolbar);
        RecyclerView recyclerCart = findViewById(R.id.recycler_cart);
        textClean = findViewById(R.id.text_clean);
        textBuyAll = findViewById(R.id.text_buy_all);
        cleanCart = findViewById(R.id.clean_cart);
        buyAll = findViewById(R.id.buy_all);

        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setLogo(R.drawable.ic_icon_bula_small);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Meu carrinho");

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);

        recyclerCart.setLayoutManager(gridLayoutManager);

        recyclerCart.setHasFixedSize(true);

        recyclerCart.setAdapter(offersRecyclerAdapter);

        presenter.getOffers(sharedPreferences.getString(Constants.USER_CITY, ""),
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
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAdapterInteract(Bundle bundle) {

        bundle.putParcelable("offer", offersRecyclerAdapter.getOffer(bundle.getInt("adapter_position")));

        CartItemDialogFragment cartItemDialogFragment = CartItemDialogFragment.newInstance(bundle);

        cartItemDialogFragment.setCartItemListener(this);

        cartItemDialogFragment.openDialog(getSupportFragmentManager());

    }

    @Override
    public void onAlertPositive(Object object) {

        if(object instanceof ArrayList){

            presenter.deleteAllItems(sharedPreferences.getString(Constants.USER_CITY, ""),
                          sharedPreferences.getString(Constants.USER_ID_FIREBASE, ""));
        }

    }

    @Override
    public void onAlertNegative() { }

    @Override
    public void onAlertError() { }

    public void buyAll(View view) {

        if(offersRecyclerAdapter.getOffers().size() > 0){

            Bundle bundle = new Bundle();
            Intent intent = new Intent(this, BuyActivity.class);

            bundle.putString("actvity", ID);
            bundle.putInt("order", 1);

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

    @Override
    public void onGetOffersSuccess(ArrayList<Offer> offers) {

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
    public void onGetOffersFailed() {  Toast.makeText(this, "Você não possui produtos no carrinho, tente novamente", Toast.LENGTH_LONG).show();}

    @Override
    public void onDeleteAllSuccess() {

        presenter.getOffers(sharedPreferences.getString(Constants.USER_CITY, ""),
                sharedPreferences.getString(Constants.USER_ID_FIREBASE, ""));

    }

    @Override
    public void onDeleteAllFailed() { }

    @Override
    public void onDeleteItemSuccess() {

        Toast.makeText(this, "Item(ns) exluido(s)", Toast.LENGTH_LONG).show();
        offersRecyclerAdapter.clear();

        presenter.getOffers(sharedPreferences.getString(Constants.USER_CITY, ""),
                sharedPreferences.getString(Constants.USER_ID_FIREBASE, ""));

    }

    @Override
    public void onDeleteItemFailed() { }

    @Override
    public void deleteItem(Offer offer) {

        presenter.deleteItem(sharedPreferences.getString(Constants.USER_CITY, ""),
                sharedPreferences.getString(Constants.USER_ID_FIREBASE, ""), offer.getCartId());
    }

    @Override
    public void buyItem(Offer offer) {

        Bundle bundle = new Bundle();
        Intent intent = new Intent(this, BuyActivity.class);

        bundle.putString("offerId",offer.getId());
        bundle.putString("city",offer.getCity());
        bundle.putString("departamentId",offer.getDepartamentId());
        bundle.putString("storeId", offer.getStoreId());
        bundle.putInt("quantity", offer.getQuantity());
        bundle.putInt("order", 2);
        bundle.putString("actvity", ID);

        intent.putExtra("data", bundle);
        startActivity(intent);

    }
}
