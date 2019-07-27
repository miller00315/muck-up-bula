package br.com.miller.muckup.store.buy.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.LinearLayoutManager;
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
import br.com.miller.muckup.utils.alerts.EditTextDialogFragment;

public class BuyActivity extends AppCompatActivity implements
        Item.OnAdapterInteract,
        EditTextDialogFragment.EditTextFragmentListener,
        Tasks.Presenter{

    private BuyRecyclerAdapter buyRecyclerAdapter;
    private RadioButton money;
    private RadioButton card;
    private CardView cardTroco;
    private CardView cardFlag;
    private TextView valueSend, textDeliverAddress, textPayMode, textCardFlag;
    private TextView totalValue, textPhone;
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
        textPhone = findViewById(R.id.phone_buy);
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

        LinearLayoutManager linearLayout = new LinearLayoutManager(this);

        buyRecycler.setLayoutManager(linearLayout);

        buyRecycler.setHasFixedSize(true);

        buyRecycler.setAdapter(buyRecyclerAdapter);

        addressBuy.setText(sharedPreferences.getString(Constants.USER_ADDRESS, ""));

        textPhone.setText(sharedPreferences.getString(Constants.USER_PHONE, ""));

        if(!bundle.isEmpty()){

            if(Objects.equals(bundle.getString("actvity"), Medicine.ID)){

                showLoading();

                buyPresenter.getOffer(bundle);

            }else if(Objects.requireNonNull(bundle.getString("actvity")).equals(MyCart.ID)){

                showLoading();

                if(bundle.getInt("order") == 1) {

                    buyPresenter.getOffers(sharedPreferences.getString(Constants.USER_CITY, ""),
                            sharedPreferences.getString(Constants.USER_ID_FIREBASE, ""));

                }else if(bundle.getInt("order") == 2){

                    buyPresenter.getOffer(bundle);

                }

            }

            addressBuy.setText(sharedPreferences.getString(Constants.USER_ADDRESS, ""));

            solicitationTime.setText(StringUtils.formatDate(new Date()));

        }

    }

    public void showAlertAddress(View view){

        Bundle bundle = new Bundle();

        bundle.putInt("view", R.layout.layout_single_edit_text_alert_fragment);

        bundle.putInt("inputType", InputType.TYPE_TEXT_VARIATION_POSTAL_ADDRESS);

        bundle.putString("type", Constants.USER_ADDRESS);

        bundle.putString("hint", "Endereço");

        bundle.putString("text", addressBuy.getText().toString());

        EditTextDialogFragment editTextDialogFragment = EditTextDialogFragment.newInstance(bundle);

        editTextDialogFragment.setListener(this);

        editTextDialogFragment.openDialog(getSupportFragmentManager());

    }

    public void showAlertPhone(View view){

        Bundle bundle = new Bundle();

        bundle.putInt("view", R.layout.layout_single_edit_text_alert_fragment);

        bundle.putInt("inputType", InputType.TYPE_CLASS_PHONE);

        bundle.putString("type", Constants.USER_PHONE);

        bundle.putString("hint", "Telefone");

        bundle.putString("text", textPhone.getText().toString());

        EditTextDialogFragment editTextDialogFragment = EditTextDialogFragment.newInstance(bundle);

        editTextDialogFragment.setListener(this);

        editTextDialogFragment.openDialog(getSupportFragmentManager());


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

    public void showLoading(){
        layoutLoading.setVisibility(View.VISIBLE);
        main_layout.setVisibility(View.INVISIBLE);
    }

    public void hideLoading(){
        layoutLoading.setVisibility(View.INVISIBLE);
        main_layout.setVisibility(View.VISIBLE);
    }

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
                observacao.getText().toString(),
                textPhone.getText().toString());
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

    @Override
    public void onEditTextDialogFragmentResult(Bundle bundle) {

        if(!Objects.requireNonNull(bundle.getString("result")).isEmpty()) {
            if (Objects.equals(bundle.getString("type"), Constants.USER_ADDRESS))
                addressBuy.setText(bundle.getString("result"));
            else if (Objects.equals(bundle.getString("type"), Constants.USER_PHONE))
                textPhone.setText(bundle.getString("result"));
        }
    }
}
