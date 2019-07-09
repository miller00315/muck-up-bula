package br.com.miller.muckup.menuPrincipal.views.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import java.util.Objects;

import br.com.miller.muckup.R;
import br.com.miller.muckup.api.FirebaseOffer;
import br.com.miller.muckup.medicine.activities.Medicine;
import br.com.miller.muckup.menuPrincipal.adapters.Item;
import br.com.miller.muckup.menuPrincipal.adapters.OffersRecyclerAdapter;
import br.com.miller.muckup.models.Offer;

public class DepartamentManager extends AppCompatActivity implements Item.OnAdapterInteract, FirebaseOffer.FirebaseOfferListener {

    private RecyclerView recyclerView;
    private OffersRecyclerAdapter offersRecyclerAdapter;
    private Bundle bundle;
    private FirebaseOffer firebaseOffer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart_manager);

        Toolbar toolbar =  findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recycler_cart_manager);

        firebaseOffer = new FirebaseOffer(this);

        bundle = getIntent().getBundleExtra("data");

        offersRecyclerAdapter = new OffersRecyclerAdapter(this);

        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setLogo(R.drawable.ic_icon_bula_small);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        bindViews();
    }

    private void bindViews(){

        if(!bundle.isEmpty()){

            Objects.requireNonNull(getSupportActionBar()).setTitle(bundle.getString("name_departament", ""));

            if(bundle.containsKey("id_store") && bundle.containsKey("id_departament") && bundle.containsKey("city")){

                firebaseOffer.firebaseGetOffersByDepartamentandStore(bundle.getString("city"),
                        String.valueOf(bundle.getInt("id_store")),
                        String.valueOf(bundle.getInt("id_departament")),
                        offersRecyclerAdapter
                        );

            }else if(bundle.containsKey("id_departament") && bundle.containsKey("city")) {

                firebaseOffer.FirebaseGetOffers(bundle.getString("city"),
                        String.valueOf(bundle.getInt("id_departament")),
                        offersRecyclerAdapter);
            }

        }

        GridLayoutManager linearLayoutManager = new GridLayoutManager(this, 2);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(offersRecyclerAdapter);

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
                Intent intent = new Intent(this, Medicine.class);
                intent.putExtra("data", bundle);
                startActivity(intent);
                break;

                default:
                    break;
        }

    }

    @Override
    public void firebaseOfferReceiver(Offer offer) {

    }
}
