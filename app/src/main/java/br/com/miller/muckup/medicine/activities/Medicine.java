package br.com.miller.muckup.medicine.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.Objects;

import br.com.miller.muckup.R;
import br.com.miller.muckup.helpers.AlertContructor;
import br.com.miller.muckup.helpers.Constants;
import br.com.miller.muckup.domain.Offer;
import br.com.miller.muckup.store.buy.view.BuyActivity;
import br.com.miller.muckup.store.views.activities.Store;
import br.com.miller.muckup.utils.alerts.NumberPickerDialogFragment;

public class Medicine extends AppCompatActivity implements
        NumberPickerDialogFragment.NumberPickerDialogFragmentListener,

        MedicineTasks.Presenter{

    public static final String ID = Medicine.class.getName();
    private ImageView imageMedicine;
    private Offer offer;
    private Bundle bundle;
    private MedicinePresenter medicinePresenter;
    private AlertContructor alertContructor;
    private SharedPreferences sharedPreferences;
    private Button buyNow;
    private TextView medicineStore, medicineDescription, medicineIndication,
            medicineNoIndication, medicineActive,
            valueSendMedicine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);

        Toolbar toolbar =  findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setLogo(R.drawable.ic_icon_bula_small);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        sharedPreferences = getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);

        alertContructor = new AlertContructor(this);

        medicinePresenter = new MedicinePresenter(this);

        bundle = getIntent().getBundleExtra("data");

        bindViews();

     }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }

     private void bindViews(){

         buyNow = findViewById(R.id.buy_now);
         medicineDescription = findViewById(R.id.medicine_description);
         medicineStore = findViewById(R.id.medicine_store);
         medicineIndication = findViewById(R.id.medicine_utilization);
         medicineNoIndication = findViewById(R.id.medicine_indication);
         medicineActive = findViewById(R.id.medicine_active);
         valueSendMedicine = findViewById(R.id.value_send_medicine);
         imageMedicine = findViewById(R.id.image_medicine);

         medicinePresenter.getMedicine(bundle);

     }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void buyNow(View view) {

        Bundle bundle = new Bundle();

        bundle.putInt("type", 1);

        defineQuantity(bundle);

    }

    public void addToCart(View view) {

        Bundle bundle = new Bundle();

        bundle.putInt("type", 2);

        defineQuantity(bundle);

    }

    public void defineQuantity(final Bundle bundle){

        NumberPickerDialogFragment numberPickerDialogFragment = NumberPickerDialogFragment.newInstance(bundle);

        numberPickerDialogFragment.setArguments(bundle);

        numberPickerDialogFragment.setListener(this);

        numberPickerDialogFragment.openDialog(getSupportFragmentManager());

    }

    public void goToBuy(){

        Bundle bundle = new Bundle();
        Intent intent = new Intent(Medicine.this, BuyActivity.class);

        bundle.putString("offerId",offer.getId());
        bundle.putString("city",offer.getCity());
        bundle.putString("departamentId",offer.getDepartamentId());
        bundle.putString("storeId", offer.getStoreId());
        bundle.putInt("quantity", offer.getQuantity());
        bundle.putString("actvity", ID);

        intent.putExtra("data", bundle);
        startActivityForResult(intent, 11);

    }
    public void goToStore(View view) {

        Bundle bundle = new Bundle();
        bundle.putString("id_store", String.valueOf(offer.getStoreId()));
        bundle.putString("city", offer.getCity());

        Intent intent = new Intent(this, Store.class);
        intent.putExtra("data", bundle);
        startActivity(intent);
    }

    public void showAlert(View view) {

        alertContructor.simpleAlert("Atenção", "Os dados são de inteira responsabilidade da farmácia", null);

    }

    public void showProduct(View view) {

        alertContructor.simpleAlert("Atenção", "Os dados são de inteira responsabilidade da farmácia", null);
    }

    @Override
    public void onMedicineDataSuccess(Offer offer) {

        this.offer = offer;

        buyNow.setText(getResources().getString(R.string.comprar_agora).concat(String.format(Locale.getDefault(),"%.2f",offer.getValue())));
        valueSendMedicine.setText("R$ ".concat(String.format(Locale.getDefault(),"%.2f",offer.getSendValue())));
        medicineDescription.setText(offer.getDescription());
        Objects.requireNonNull(getSupportActionBar()).setTitle(offer.getTitle());
        medicineActive.setText(offer.getActive());
        medicineIndication.setText(offer.getIndication());
        medicineNoIndication.setText(offer.getNoIndication());
        medicineStore.setText(offer.getStore());

        medicinePresenter.downloadImage("offers", offer.getCity(), offer.getImage());

    }

    @Override
    public void onMedicineDataFailed() { }

    @Override
    public void onImageDownloadSuccess(Bitmap bitmap) { imageMedicine.setImageBitmap(bitmap);}

    @Override
    public void onImageDownloadFailed() { Toast.makeText(this, "Erro ao realizar download da image", Toast.LENGTH_SHORT).show(); }

    @Override
    public void onAddCartSuccess(Offer offer) { Toast.makeText(this, " Você adicionou ".concat(offer.getTitle()).concat(" ao seu carrinho"), Toast.LENGTH_LONG).show(); }

    @Override
    public void onAddCartFailed() { Toast.makeText(this, "Erro ao adicionar produto, tente novamente.", Toast.LENGTH_LONG).show(); }

    @Override
    public void onDialogFragmentListener(Bundle bundle) {

        if (bundle.containsKey("quantity")) {
            offer.setQuantity(bundle.getInt("quantity"));

            if (bundle.getInt("type") == 1) {

                goToBuy();

            } else if (bundle.getInt("type") == 2) {

                medicinePresenter.addCartOffer(offer,
                        sharedPreferences.getString(Constants.USER_CITY, ""),
                        sharedPreferences.getString(Constants.USER_ID_FIREBASE, ""));

            }
        }
    }
}
