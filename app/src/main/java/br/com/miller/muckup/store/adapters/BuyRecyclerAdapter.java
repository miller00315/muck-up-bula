package br.com.miller.muckup.store.adapters;

import android.content.Context;
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
import br.com.miller.muckup.menuPrincipal.adapters.Item;
import br.com.miller.muckup.domain.Offer;
import br.com.miller.muckup.store.views.viewHolders.BuyViewHolder;

public class BuyRecyclerAdapter extends Item {

    private ArrayList<Offer> products;
    private Context context;

    public BuyRecyclerAdapter(Context context) {
        if(context instanceof OnAdapterInteract) {
            this.context = context;
            listener = (OnAdapterInteract) context;
            products = new ArrayList<>();
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
            buyViewHolder.downloaImage(products.get(i).getType(), products.get(i).getCity(), products.get(i).getImage());
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

        bundle.putParcelable("offer", products.get(i));

        listener.onAdapterInteract(bundle);
    }

    public boolean addProduct(Offer offer){

        boolean add = products.add(offer);

        if(add) {

            notifyDataSetChanged();
        }

        return add;
    }

    public void clear(){
        products.clear();
        notifyDataSetChanged();
    }


    public boolean removeProduct(Offer offer){

        for(int i = 0; i< products.size(); i++){

            if(products.get(i).getId().equals(offer.getId())){

               if(products.remove(i) != null){
                   notifyDataSetChanged();

                   return true;
               }else
                   return false;

            }
        }

        return false;

    }

    public boolean updateProduct(Offer offer){

        for(int i = 0; i< products.size(); i++){

            if(products.get(i).getId().equals(offer.getId())){

                products.get(i).setQuantity(offer.getQuantity());

                notifyItemChanged(i);

                return true;

            }
        }

        return false;
    }

    public void removeProdcut(int position){

        products.remove(position);

        notifyDataSetChanged();
        notifyItemRangeChanged(position, products.size());
    }


    public ArrayList<Offer> getOffers(){ return this.products; }

    public boolean setArray (ArrayList<Offer> products){

        boolean addAll = this.products.addAll(products);

        notifyDataSetChanged();

        return addAll;
    }

}
