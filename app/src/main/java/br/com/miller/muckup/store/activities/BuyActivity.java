package br.com.miller.muckup.store.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.LinearLayoutManager;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import br.com.miller.muckup.R;
import br.com.miller.muckup.api.FirebaseBuy;
import br.com.miller.muckup.api.FirebaseCart;
import br.com.miller.muckup.api.FirebaseOffer;
import br.com.miller.muckup.helpers.AlertContructor;
import br.com.miller.muckup.helpers.BuyHelper;
import br.com.miller.muckup.helpers.Constants;
import br.com.miller.muckup.medicine.activities.Medicine;
import br.com.miller.muckup.menuPrincipal.activities.items.MyBuys;
import br.com.miller.muckup.menuPrincipal.activities.items.MyCart;
import br.com.miller.muckup.menuPrincipal.adapters.Item;
import br.com.miller.muckup.models.Buy;
import br.com.miller.muckup.models.Offer;
import br.com.miller.muckup.store.adapters.BuyRecyclerAdapter;

public class BuyActivity extends AppCompatActivity implements Item.OnAdapterInteract, AlertContructor.OnAlertInteract,
        FirebaseOffer.FirebaseOfferListener,
        FirebaseCart.FirebaseCartListener,
        FirebaseBuy.FirebaseBuyListener,
        BuyHelper.BuyHelperListener {

    private BuyRecyclerAdapter buyRecyclerAdapter;
    private RecyclerView buyRecycler;
    private RadioButton money;
    private RadioButton card;
    private CardView cardTroco, cardFlag, cardAddress;
    private AlertContructor alertContructor;
    private TextView valueSend, solicitationTime, totalValue, addressBuy;
    private SharedPreferences sharedPreferences;
    private Bundle bundle;
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        alertContructor = new AlertContructor(this);

        sharedPreferences = getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);

        Objects.requireNonNull(getSupportActionBar()).setLogo(R.drawable.ic_pharma_seeklogo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        bundle = getIntent().getBundleExtra("data");

        buyRecyclerAdapter = new BuyRecyclerAdapter(this);

        bindView();

    }

    private void bindView(){

        buyRecycler = findViewById(R.id.buy_recycler);
        cardAddress = findViewById(R.id.card_address);

        money = findViewById(R.id.money);
        cardFlag = findViewById(R.id.card_flag);
        card = findViewById(R.id.card);
        cardTroco = findViewById(R.id.card_troco);
        valueSend = findViewById(R.id.value_send);
        solicitationTime = findViewById(R.id.solicitation_time);
        totalValue = findViewById(R.id.total_value);
        addressBuy = findViewById(R.id.address_buy);

        money.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    cardTroco.setVisibility(View.VISIBLE);
                    cardFlag.setVisibility(View.INVISIBLE);
                    card.setChecked(false);
                }else{
                    cardTroco.setVisibility(View.INVISIBLE);
                    cardFlag.setVisibility(View.VISIBLE);
                    card.setChecked(true);
                }

            }
        });

        card.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    money.setChecked(false);
                    cardTroco.setVisibility(View.INVISIBLE);
                    cardFlag.setVisibility(View.VISIBLE);
                }else{
                    money.setChecked(true);
                    cardTroco.setVisibility(View.VISIBLE);
                    cardFlag.setVisibility(View.INVISIBLE);
                }
            }
        });

        cardAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertAddress();
            }
        });

        LinearLayoutManager linearLayout = new LinearLayoutManager(this);

        buyRecycler.setLayoutManager(linearLayout);

        buyRecycler.setHasFixedSize(true);

        buyRecycler.setAdapter(buyRecyclerAdapter);

        addressBuy.setText(sharedPreferences.getString(Constants.USER_ADDRESS, ""));

        if(!bundle.isEmpty()){

            if(Objects.equals(bundle.getString("actvity"), Medicine.class.getName())){

                FirebaseOffer firebaseOffer = new FirebaseOffer(this);

                firebaseOffer.firebaseGetOffer(Objects.requireNonNull(bundle.getString("city")),
                            Objects.requireNonNull(bundle.getString("type")),
                            String.valueOf(Objects.requireNonNull(bundle.getInt("id"))));

            }else if(Objects.requireNonNull(bundle.getString("actvity")).equals(MyCart.class.getName())){
                FirebaseCart firebaseCart = new FirebaseCart(this);

                firebaseCart.firebaseCartgetOffers(sharedPreferences.getString(Constants.USER_CITY, ""),
                        sharedPreferences.getString(Constants.USER_ID_FIREBASE, ""));

            }
        }
    }

    public void showAlertAddress(){

        ViewGroup viewGroup = findViewById(android.R.id.content);

        View view1 = LayoutInflater.from(this).inflate(R.layout.layout_alert_address, viewGroup, false);

        EditText address = view1.findViewById(R.id.edit_text_address);

        alertContructor.personalizedAlert(view1, address);
    }

    private void goToMyBuys(){
        Intent intent = new Intent(this, MyBuys.class);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);
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

    }

    @Override
    public void firebaseOfferReceiver(Offer offer) {

        buyRecyclerAdapter.clear();

        if(offer != null) {

            offer.setQuantity(bundle.getInt("quantity"));

            buyRecyclerAdapter.addProduct(offer);

            @SuppressLint("SimpleDateFormat") SimpleDateFormat formataData = new SimpleDateFormat("HH:mm dd/MM/yyyy");

            solicitationTime.setText(formataData.format(new Date()));

            valueSend.setText("R$ " .concat(String.format(Locale.getDefault(),"%.2f", offer.getSendValue())));

            totalValue.setText("R$ ".concat(String.format(Locale.getDefault(), "%.2f", offer.getValue() * offer.getQuantity())));
        }

    }

    @Override
    public void onAlertPositive(Object object) {

        if(object instanceof  EditText){

            EditText editText = (EditText) object;

            addressBuy.setText(editText.getText().toString());
        }

    }

    @Override
    public void onAlertNegative() {

    }

    @Override
    public void onAlertError() {

    }

    @Override
    public void onBuyRegistred() {

    }

    @Override
    public void registerBuy(Boolean registered) {


        if(registered){

            Toast.makeText(this, "Compras registradas com sucesso", Toast.LENGTH_LONG).show();
            goToMyBuys();

            finish();

        }else{

            Toast.makeText(this, "Algum erro ocorreu ao registrar a compra, tente novamente", Toast.LENGTH_LONG).show();

        }

    }

    @Override
    public void onReceiverBuy(ArrayList<Buy> buys) {

    }

    @Override
    public void firebaseCartListnerReceiver(Offer offer) {

    }

    @Override
    public void firebaseCartListenerReceiverOffers(ArrayList<Offer> offers) {

        buyRecyclerAdapter.clear();

        if(offers != null) {
            if(buyRecyclerAdapter.setArray(offers)){

                valueSend.setText("R$ ".concat(String.format(Locale.getDefault(),"%.2f",buyRecyclerAdapter.getBuyHelper().calculateSendValue())));

                @SuppressLint("SimpleDateFormat") SimpleDateFormat formataData = new SimpleDateFormat("HH:mm dd/MM/yyyy");

                this.totalValue.setText("R$ ".concat(String.format(Locale.getDefault(), "%.2f", buyRecyclerAdapter.getBuyHelper().calculateTotalValue())));

                solicitationTime.setText(formataData.format(new Date()));
            }
        } else
            Toast.makeText(this, "Erro ao obter produtos do carrinho, tente novamente", Toast.LENGTH_LONG).show();
    }

    @Override
    public void firebaseCartOnItemDeleted(boolean status) {

    }

    public void endBuy(View view) {

        String address = addressBuy.getText().toString().trim();

        if(!TextUtils.isEmpty(address)) {

            buyRecyclerAdapter.getBuyHelper().endBuy(sharedPreferences.getString(Constants.USER_ID_FIREBASE, ""),
                    sharedPreferences.getString(Constants.USER_CITY, ""), address);

            if(sharedPreferences.getString(Constants.USER_ADDRESS, "").isEmpty()){

                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString(Constants.USER_ADDRESS, address);

                editor.apply();
            }


        }else

            Toast.makeText(this, "Insira um endreço válido para proseguir", Toast.LENGTH_LONG).show();
    }
}
