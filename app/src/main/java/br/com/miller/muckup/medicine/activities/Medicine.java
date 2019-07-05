package br.com.miller.muckup.medicine.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import br.com.miller.muckup.R;
import br.com.miller.muckup.api.FirebaseCart;
import br.com.miller.muckup.api.FirebaseImage;
import br.com.miller.muckup.api.FirebaseOffer;
import br.com.miller.muckup.helpers.AlertContructor;
import br.com.miller.muckup.helpers.Constants;
import br.com.miller.muckup.models.Offer;
import br.com.miller.muckup.store.buy.view.BuyActivity;
import br.com.miller.muckup.store.activities.Store;

import static java.lang.Integer.getInteger;

public class Medicine extends AppCompatActivity implements
        AlertContructor.OnAlertInteract,
        FirebaseImage.FirebaseImageListener,
        FirebaseCart.FirebaseCartListener,
        FirebaseOffer.FirebaseOfferListener {

    private boolean isFabOpen;
    private FirebaseCart firebaseCart;
 //   private LinearLayout addToCart, buyNow;
    private ImageView imageMedicine;
    private Offer offer;
    private Bundle bundle;
    private FirebaseOffer firebaseOffer;
    private AlertContructor alertContructor;
    private FirebaseImage firebaseImage;
    private SharedPreferences sharedPreferences;
    private Button buyNow;
    private TextView textCart, textBuy,
            medicineStore, medicineName, medicineIndication,
            medicineNoIndication, medicineActive,
            valueSendMedicine, valueMedicine ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);

        Toolbar toolbar =  findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setLogo(R.drawable.ic_pharma_seeklogo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        sharedPreferences = getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);

        alertContructor = new AlertContructor(this);

        firebaseImage = new FirebaseImage(this);
        firebaseOffer = new FirebaseOffer(this);
        firebaseCart = new FirebaseCart(this);


        bundle = getIntent().getBundleExtra("data");

        bindViews();

     }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }

     private void bindViews(){

       //  FloatingActionButton mainFab = findViewById(R.id.main_float_button);
       //  addToCart = findViewById(R.id.add_cart);
        // buyNow = findViewById(R.id.buy_now);
        // textCart = findViewById(R.id.text_cart);
        // textBuy = findViewById(R.id.text_buy);
         buyNow = findViewById(R.id.buy_now);
         medicineName = findViewById(R.id.medicine_name);
         medicineStore = findViewById(R.id.medicine_store);
         medicineIndication = findViewById(R.id.medicine_utilization);
         medicineNoIndication = findViewById(R.id.medicine_indication);
         medicineActive = findViewById(R.id.medicine_active);
      //   valueMedicine = findViewById(R.id.value_medicine);
         valueSendMedicine = findViewById(R.id.value_send_medicine);
         imageMedicine = findViewById(R.id.image_medicine);

         firebaseOffer.firebaseGetOffer(bundle.getString("city"), bundle.getString("title"), bundle.getString("id_offer"));

        /* mainFab.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 if(!isFabOpen){
                     openFab();
                 }else{
                     closeFab();
                 }
             }
         });*/

     }

   /*  private void openFab(){

        isFabOpen = true;
        textBuy.setVisibility(View.VISIBLE);
        textCart.setVisibility(View.VISIBLE);
        addToCart.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        buyNow.animate().translationY(-getResources().getDimension(R.dimen.standard_105));

     }*/

   /*  private void closeFab(){

        isFabOpen = false;
         textBuy.setVisibility(View.INVISIBLE);
         textCart.setVisibility(View.INVISIBLE);
         addToCart.animate().translationY(0);
         buyNow.animate().translationY(0);
     } */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void buyNow(View view) {

        defineQuantity(2);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void addToCart(View view) {

        if(offer != null) {

            ViewGroup viewGroup = findViewById(android.R.id.content);

            View view1 = LayoutInflater.from(this).inflate(R.layout.layout_alert_buy_now, viewGroup, false);

            TextView offerValue = view1.findViewById(R.id.offer_value);

            TextView offerName = view1.findViewById(R.id.offer_name);

            offerName.setText(offer.getTitle());

            offerValue.setText("R$ ".concat(String.format(Locale.getDefault(), "%.2f", offer.getValue())));

            alertContructor.personalizedAlert(view1, offer);

        }else{
            Toast.makeText(this, "Erro ao obter o produto, tente novamente", Toast.LENGTH_LONG).show();
        }
    }

    public void defineQuantity(final int type){

        AlertDialog alertDialog;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        ViewGroup viewGroup = findViewById(android.R.id.content);

        View view = LayoutInflater.from(this).inflate(R.layout.layout_alert_quantity, viewGroup, false);

        final EditText quantity = view.findViewById(R.id.edit_text_quantidade);

        builder.setView(view);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String quantidade = quantity.getText().toString().trim();

                if(TextUtils.isEmpty(quantidade)){

                    offer.setQuantity(1);

                }else{

                    offer.setQuantity(Integer.parseInt(quantidade));
                }

                if(type == 1){

                    firebaseCart.firebaseCartAddOffer(offer, offer.getCity(), sharedPreferences.getString(Constants.USER_ID_FIREBASE, ""));

                }else if(type == 2){

                    goToBuy();

                }

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog = builder.create();

        alertDialog.show();
    }

    public void goToBuy(){

        Bundle bundle = new Bundle();
        Intent intent = new Intent(Medicine.this, BuyActivity.class);

        bundle.putInt("id",offer.getId());
        bundle.putString("city",offer.getCity());
        bundle.putString("type",offer.getTitle().toLowerCase());
        bundle.putInt("quantity", offer.getQuantity());
        bundle.putString("actvity", Medicine.class.getName());

        intent.putExtra("data", bundle);
        startActivityForResult(intent, 11);

    }


    public void goToStore(View view) {

        Bundle bundle = new Bundle();
        bundle.putString("id_store", String.valueOf(offer.getIdStore()));
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
    public void firebaseOfferReceiver(Offer offer) {


        if(offer != null){

            this.offer = offer;

            buyNow.setText("Compre agora - R$ ".concat(String.format(Locale.getDefault(),"%.2f",offer.getValue())));
            valueSendMedicine.setText("R$ ".concat(String.format(Locale.getDefault(),"%.2f",offer.getSendValue())));
            firebaseImage.downloadFirebaseImage("offers",offer.getCity(),offer.getImage(), imageMedicine);
            medicineName.setText(offer.getDescription());
            getSupportActionBar().setTitle(offer.getTitle());
            medicineActive.setText(offer.getActive());
            medicineIndication.setText(offer.getIndication());
            medicineNoIndication.setText(offer.getNoIndication());
            medicineStore.setText(offer.getStore());

        }else{
            Toast.makeText(this, "Erro ao procurar oferta, tente novamente", Toast.LENGTH_LONG).show();
             finish();
        }
    }

    @Override
    public void onImageDownloadSuccess() {

    }

    @Override
    public void onImageDownloadError() {

    }

    @Override
    public void onAlertPositive(Object object) {

        if(object != null){

            defineQuantity(1);

        }

    }

    @Override
    public void onAlertNegative() {


    }

    @Override
    public void onAlertError() {

        Toast.makeText(this, "Erro ao adicionar item ao carrinho, tente novamente", Toast.LENGTH_LONG).show();

    }

    @Override
    public void firebaseCartListnerReceiver(Offer offer) {

        if(offer != null){

            Toast.makeText(this, " Você adicionou ".concat(offer.getTitle()).concat(" ao seu carrinho"), Toast.LENGTH_LONG).show();

           // closeFab();

        }else{

            Toast.makeText(this, "Erro ao adicionar produto, tente novamente.", Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void firebaseCartListenerReceiverOffers(ArrayList<Offer> offers) {

    }

    @Override
    public void firebaseCartOnItemDeleted(boolean status) {

    }
}
