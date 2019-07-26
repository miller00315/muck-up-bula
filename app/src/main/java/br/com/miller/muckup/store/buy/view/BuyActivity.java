package br.com.miller.muckup.store.buy.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.LinearLayoutManager;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import br.com.miller.muckup.R;
import br.com.miller.muckup.domain.Offer;
import br.com.miller.muckup.helpers.AlertContructor;
import br.com.miller.muckup.helpers.Constants;
import br.com.miller.muckup.medicine.activities.Medicine;
import br.com.miller.muckup.menuPrincipal.adapters.Item;
import br.com.miller.muckup.domain.Buy;
import br.com.miller.muckup.menuPrincipal.views.activities.MyBuys;
import br.com.miller.muckup.menuPrincipal.views.activities.MyCart;
import br.com.miller.muckup.store.buy.presenter.BuyPresenter;
import br.com.miller.muckup.store.buy.tasks.Tasks;
import br.com.miller.muckup.utils.MoneyTextWatcher;
import br.com.miller.muckup.utils.StringUtils;

public class BuyActivity extends AppCompatActivity implements Item.OnAdapterInteract, AlertContructor.OnAlertInteract,
        Tasks.Presenter{

    private BuyRecyclerAdapter buyRecyclerAdapter;
    private RadioButton money;
    private RadioButton card;
    private CardView cardTroco;
    private CardView cardFlag;
    private AlertContructor alertContructor;
    private TextView valueSend, textDeliverAddress, textPayMode, textCardFlag;
    private TextView totalValue;
    private TextView addressBuy;
    private SharedPreferences sharedPreferences;
    private Bundle bundle;
    private EditText editTextTroco, observacao;
    private RadioGroup payMethod, card_flag;
    private BuyPresenter buyPresenter;
    private RelativeLayout layoutLoading;
    private ScrollView main_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(alertContructor == null)
            alertContructor = new AlertContructor(this);

        if(sharedPreferences == null)
            sharedPreferences = getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);

        Objects.requireNonNull(getSupportActionBar()).setLogo(R.drawable.ic_icon_bula_small);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Compra");

        if(buyPresenter == null)
            buyPresenter = new BuyPresenter(this);

        if(bundle == null)
            bundle = getIntent().getBundleExtra("data");

        if(buyRecyclerAdapter == null)
            buyRecyclerAdapter = new BuyRecyclerAdapter(this);

        bindView();

    }

    private void bindView(){

        RecyclerView buyRecycler = findViewById(R.id.buy_recycler);
        CardView cardAddress = findViewById(R.id.card_address);

        money = findViewById(R.id.money);
        cardFlag = findViewById(R.id.card_flag);
        card = findViewById(R.id.card);
        cardTroco = findViewById(R.id.card_troco);
        valueSend = findViewById(R.id.value_send);
        TextView solicitationTime = findViewById(R.id.solicitation_time);
        totalValue = findViewById(R.id.total_value);
        addressBuy = findViewById(R.id.address_buy);
        editTextTroco = findViewById(R.id.edit_text_troco);
        card_flag = findViewById(R.id.card_flag_);
        textCardFlag = findViewById(R.id.text_card_flag);
        textDeliverAddress = findViewById(R.id.text_delivery_address);
        textPayMode = findViewById(R.id.text_pay_mode);
        layoutLoading = findViewById(R.id.loading_layout);
        main_layout = findViewById(R.id.main_layout);
        observacao = findViewById(R.id.solicitation_observation);

        payMethod = findViewById(R.id.pay_method);

        editTextTroco.addTextChangedListener(new MoneyTextWatcher(editTextTroco, Locale.getDefault()));

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

                buyPresenter.getOffer(Objects.requireNonNull(bundle.getString("city")),
                        Objects.requireNonNull(bundle.getString("type")),
                        String.valueOf(Objects.requireNonNull(bundle.getInt("id"))),
                                bundle.getInt("quantity", 1));

                showLoading();

            }else if(Objects.requireNonNull(bundle.getString("actvity")).equals(MyCart.class.getName())){


                showLoading();

                buyPresenter.getOffers(sharedPreferences.getString(Constants.USER_CITY, ""),
                        sharedPreferences.getString(Constants.USER_ID_FIREBASE, ""));

            }

            addressBuy.setText(sharedPreferences.getString(Constants.USER_ADDRESS, ""));

            solicitationTime.setText(StringUtils.formatDate(new Date()));

        }

    }

    public void showAlertAddress(){

        ViewGroup viewGroup = findViewById(android.R.id.content);

        View view1 = LayoutInflater.from(this).inflate(R.layout.layout_alert_address, viewGroup, false);

        EditText address = view1.findViewById(R.id.edit_text_address);

        address.setText(sharedPreferences.getString(Constants.USER_ADDRESS, ""));

        alertContructor.personalizedAlert(view1, address);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAdapterInteract(Bundle bundle) { }

    @Override
    public void onAlertPositive(Object object) {

        if(object instanceof  EditText){

            EditText editText = (EditText) object;

            addressBuy.setText(editText.getText().toString());
        }

    }

    public void showLoading(){
        layoutLoading.setVisibility(View.VISIBLE);
        main_layout.setVisibility(View.INVISIBLE);
    }

    public void hideLoading(){
        layoutLoading.setVisibility(View.INVISIBLE);
        main_layout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAlertNegative() { }

    @Override
    public void onAlertError() { }

    public void endBuy(View view) {

        showLoading();

        setTextColor();

        buyPresenter.makeBuy(sharedPreferences.getString(Constants.USER_ID_FIREBASE, ""),
                sharedPreferences.getString(Constants.USER_CITY, ""),
                addressBuy.getText().toString(),
                payMethod.getCheckedRadioButtonId(),
                editTextTroco.getText().toString(),
                card_flag.getCheckedRadioButtonId(),
                buyRecyclerAdapter.getOffers(),
                sharedPreferences.getString(Constants.USER_NAME, ""),
                observacao.getText().toString());
    }

    @Override
    public void onSuccessBuys(ArrayList<Buy> buys) {

        Toast.makeText(this, "Compra realizada com sucesso, aguarde o envio pela farmácia.", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, MyBuys.class);
        startActivity(intent);

        finish();
    }

    public void setTextColor(){
        textPayMode.setTextColor(getResources().getColor(R.color.dark));
        textDeliverAddress.setTextColor(getResources().getColor(R.color.dark));
        textCardFlag.setTextColor(getResources().getColor(R.color.dark));
    }

    @Override
    public void failedBuy(int type) {

        hideLoading();

        Toast.makeText(this, "Dados incompletos", Toast.LENGTH_SHORT).show();

        switch (type){

            case 0:{
                textDeliverAddress.setTextColor(getResources().getColor(R.color.red));
                break;
            }

            case 1 :{
                textPayMode.setTextColor(getResources().getColor(R.color.red));
                break;
            }

            case 2:{
                textCardFlag.setTextColor(getResources().getColor(R.color.red));
                break;
            }

            case 3:{
                Toast.makeText(this, "Erro ao registrar a compra, tente novamente", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }

    @Override
    public void onOffersSuccess(ArrayList<Offer> offers) {
        hideLoading();
        buyRecyclerAdapter.setArray(offers);
    }

    @Override
    public void onSendValueCalculated(Double sendValue, ArrayList<Offer> sendValues) {

        String valuesSend = "\nTotal: ".concat(String.format(Locale.getDefault(), "R$ %.2f",sendValue))
                .concat("\n\nPor entrega: \n\n");

        for(Offer offer : sendValues){

            valuesSend = valuesSend
                    .concat(offer.getStore())
                    .concat(": ")
                    .concat(String.format(Locale.getDefault(), "R$ %.2f", offer.getSendValue()))
                    .concat("\n");
        }

        this.valueSend.setText(valuesSend);
    }

    @Override
    public void onTotalValueCalculated(Double totalValue) { this.totalValue.setText(String.format(Locale.getDefault(), "R$ %.2f", totalValue ));}

    @Override
    public void onOffersFail() {
        Toast.makeText(this, "Erro ao recuperar ofertas, tente novamente", Toast.LENGTH_SHORT).show();
        finish();
    }

}
