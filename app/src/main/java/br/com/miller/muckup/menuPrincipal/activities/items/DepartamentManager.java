package br.com.miller.muckup.menuPrincipal.activities.items;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import java.util.Objects;

import br.com.miller.muckup.R;
import br.com.miller.muckup.api.FirebaseOffer;
import br.com.miller.muckup.helpers.HelperStore;
import br.com.miller.muckup.menuPrincipal.adapters.Item;
import br.com.miller.muckup.menuPrincipal.adapters.OffersRecyclerAdapter;

public class DepartamentManager extends AppCompatActivity implements Item.OnAdapterInteract {

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

        Objects.requireNonNull(getSupportActionBar()).setLogo(R.drawable.ic_pharma_seeklogo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        bindViews();
    }

    private void bindViews(){

        if(!bundle.isEmpty()){


            Objects.requireNonNull(getSupportActionBar()).setTitle(bundle.getString("name_departament", ""));

            if(bundle.containsKey("id_departament") && bundle.containsKey("city"))
                firebaseOffer.FirebaseGetOffers(bundle.getString("city"),
                        String.valueOf(bundle.getInt("id_departament")),
                        offersRecyclerAdapter);

        }

        GridLayoutManager linearLayoutManager = new GridLayoutManager(this, 2);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(offersRecyclerAdapter);

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

        if(this.bundle.containsKey(""))
            managerBuy(bundle);
        else
            manegerStore(bundle);


    }

    private void managerBuy(Bundle bundle){

    }

    private void manegerStore (Bundle bundle){

    }

}
