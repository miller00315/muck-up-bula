package br.com.miller.muckup.menuPrincipal.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Locale;

import br.com.miller.muckup.R;
import br.com.miller.muckup.menuPrincipal.views.viewHolders.OffersViewHolder;
import br.com.miller.muckup.menuPrincipal.views.activities.MyCart;
import br.com.miller.muckup.domain.Offer;

public class OffersRecyclerAdapter extends Item {

    private ArrayList<Offer> offers;
    private Context context;
    public static final String ID = OffersRecyclerAdapter.class.getName();

    public OffersRecyclerAdapter(Item.OnAdapterInteract onAdapterInteract, Context context) {

            this.context = context;
            listener = onAdapterInteract;
            offers = new ArrayList<>();

    }

    @NonNull
    @Override
    public OffersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new OffersViewHolder(LayoutInflater.from(context).inflate(R.layout.view_holder_offers,
                viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        if(viewHolder instanceof OffersViewHolder){

            final OffersViewHolder offersViewHolder = (OffersViewHolder) viewHolder;

            if(context instanceof MyCart){
                offersViewHolder.getValueSendOffer().setVisibility(View.INVISIBLE);
                offersViewHolder.getValueSendOffer().setHeight(0);
                offersViewHolder.getValueSendOffer().setWidth(0);
            }

            offersViewHolder.getValueSendOffer().setText("R$ " .concat(String.format(Locale.getDefault(),"%.2f", offers.get(i).getSendValue())));
            offersViewHolder.getDescriptionOffer().setText(offers.get(i).getDescription());
            offersViewHolder.getTitleOffer().setText(offers.get(i).getTitle().concat(offers.get(i).getQuantity() > 0 ? ", " + offers.get(i).getQuantity() : ""));
            offersViewHolder.getValueOffer().setText("R$ " .concat(String.format(Locale.getDefault(),"%.2f", offers.get(i).getValue())));

            offersViewHolder.downloaImage("offers",offers.get(i).getCity(), offers.get(i).getImage());

            offersViewHolder.getMainLayout().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showItem(offersViewHolder.getAdapterPosition());
                }
            });

        }
    }

    public void showItem(int i){

        Bundle bundle = new Bundle();

        bundle.putString("type", ID);
        bundle.putInt("adapter_position", i);
        bundle.putString("id_offer", String.valueOf(offers.get(i).getId()));
        bundle.putString("city", offers.get(i).getCity());
        bundle.putString("title", offers.get(i).getTitle().toLowerCase());
        bundle.putString("storeId", offers.get(i).getStoreId());
        bundle.putString("departamentId", offers.get(i).getDepartamentId());

        listener.onAdapterInteract(bundle);
    }

    @Override
    public int getItemCount() {
        return offers != null ? offers.size() : 0;
    }

    public boolean addOffer(Offer offer){

        boolean add = offers.add(offer);

        if(add)
            notifyDataSetChanged();

        return add;
    }

    public void clear(){

        offers.clear();
        notifyDataSetChanged();
    }

    public Offer getOffer(int i){

        return offers.get(i);
    }


    public boolean removeOffer(Offer offer){

        boolean remove = offers.remove(offer);

        if(remove)
            notifyDataSetChanged();

        return remove;
    }

    public void removeOffer(int position){

        offers.remove(position);

        notifyDataSetChanged();
        notifyItemRangeChanged(position, offers.size());
    }

    public ArrayList<Offer> getOffers(){

        return offers;
    }

    public boolean setArray (ArrayList<Offer> offers){

        boolean addAll = this.offers.addAll(offers);

        notifyDataSetChanged();

        return addAll;
    }

}
