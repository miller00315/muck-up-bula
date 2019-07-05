package br.com.miller.muckup.store.buy.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Locale;

import br.com.miller.muckup.R;
import br.com.miller.muckup.api.FirebaseImage;
import br.com.miller.muckup.helpers.BuyHelper;
import br.com.miller.muckup.helpers.Constants;
import br.com.miller.muckup.menuPrincipal.adapters.Item;
import br.com.miller.muckup.models.Offer;
import br.com.miller.muckup.store.viewHolders.BuyViewHolder;

public class BuyRecyclerAdapter extends Item {

    private ArrayList<Offer> products;
    private Context context;
    private FirebaseImage firebaseImage;
    private BuyHelper buyHelper;
    private SharedPreferences sharedPreferences;

    public BuyRecyclerAdapter(Context context) {
        if(context instanceof OnAdapterInteract) {
            this.context = context;
            listener = (OnAdapterInteract) context;
            products = new ArrayList<>();
            firebaseImage = new FirebaseImage(context);
            sharedPreferences = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
            buyHelper = new BuyHelper(context);
        }else{
            throw new RuntimeException(context.toString()
                    + " must implement OnAdapter");
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new BuyViewHolder(LayoutInflater.from(context).inflate(R.layout.view_holder_buy,
                viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        if(viewHolder instanceof BuyViewHolder){

            final BuyViewHolder buyViewHolder = (BuyViewHolder) viewHolder;

            buyViewHolder.setIdbuy(products.get(i).getTitle().concat(", Quant.: ").concat(String.valueOf(products.get(i).getQuantity())));
            buyViewHolder.setDateBuy("R$ " .concat(String.format(Locale.getDefault(),"%.2f",products.get(i).getValue())));
            firebaseImage.downloadFirebaseImage(products.get(i).getType(), products.get(i).getCity(), products.get(i).getImage(), buyViewHolder.getImageProduct());
            buyViewHolder.setStoreProduct(products.get(i).getStore());
            buyViewHolder.getLayoutBuy().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    showItem(buyViewHolder.getAdapterPosition());
                }
            });


        }

    }

    @Override
    public int getItemCount() {
        return products != null ? products.size() : 0;
    }

    @Override
    public void showItem(int i) {

        Bundle bundle = new Bundle();

        listener.onAdapterInteract(bundle);
    }

    public boolean addProduct(Offer offer){

        boolean add = products.add(offer);

        if(add) {

            notifyDataSetChanged();

            buyHelper.singleBuy(offer,sharedPreferences.getString(Constants.USER_ID_FIREBASE, ""),
                    sharedPreferences.getString(Constants.USER_CITY, ""));
        }

        return add;
    }

    public void clear(){
        products.clear();
        buyHelper.clear();
        notifyDataSetChanged();
    }


    public boolean removeProduct(Offer offer){

        boolean remove = products.remove(offer);

        if(remove)
            notifyDataSetChanged();

        return remove;
    }

    public void removeProdcut(int position){

        products.remove(position);

        notifyDataSetChanged();
        notifyItemRangeChanged(position, products.size());
    }


    public ArrayList<Offer> getOffers(){

        return this.products;
    }

    public BuyHelper getBuyHelper(){
        return buyHelper;
    }

    public boolean setArray (ArrayList<Offer> products){

        boolean addAll = this.products.addAll(products);

        if(addAll){

            if(buyHelper.defineOfferByStoreId(this.products, sharedPreferences.getString(Constants.USER_ID_FIREBASE, ""),
                    sharedPreferences.getString(Constants.USER_CITY, "")))

                Log.w("BuyRecylcer", "Deu certo");
        }

        notifyDataSetChanged();

        return addAll;
    }


}
