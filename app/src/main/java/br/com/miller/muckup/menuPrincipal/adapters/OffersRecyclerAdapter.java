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
import br.com.miller.muckup.api.FirebaseImage;
import br.com.miller.muckup.menuPrincipal.viewHolders.OffersViewHolder;
import br.com.miller.muckup.menuPrincipal.views.activities.MyCart;
import br.com.miller.muckup.domain.Offer;

public class OffersRecyclerAdapter extends Item {

    private ArrayList<Offer> offers;

    private Context context;

    private FirebaseImage firebaseImage;

    public OffersRecyclerAdapter(Context context) {

        if(context instanceof OnAdapterInteract) {
            this.context = context;
            listener = (OnAdapterInteract) context;
            firebaseImage = new FirebaseImage(context);
            offers = new ArrayList<>();
        }else{
            throw new RuntimeException(context.toString()
                    + " must implement OnAdapter");
        }
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
                offersViewHolder.valueSendOffer.setVisibility(View.INVISIBLE);
                offersViewHolder.valueSendOffer.setHeight(0);
                offersViewHolder.valueSendOffer.setWidth(0);
            }

            offersViewHolder.valueSendOffer.setText("R$ " .concat(String.format(Locale.getDefault(),"%.2f", offers.get(i).getSendValue())));
            offersViewHolder.descriptionOffer.setText(offers.get(i).getDescription());
            offersViewHolder.titleOffer.setText(offers.get(i).getTitle().concat(offers.get(i).getQuantity() > 0 ? ", " + String.valueOf(offers.get(i).getQuantity()) : ""));
            offersViewHolder.valueOffer.setText("R$ " .concat(String.format(Locale.getDefault(),"%.2f", offers.get(i).getValue())));
            firebaseImage.downloadFirebaseImage("offers", offers.get(i).getCity(), offers.get(i).getImage(), offersViewHolder.imageOffer);

            offersViewHolder.mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showItem(offersViewHolder.getAdapterPosition());
                }
            });

        }
    }

    public void showItem(int i){

        Bundle bundle = new Bundle();

        bundle.putInt("type", 1);
        bundle.putInt("adapter_position", i);
        bundle.putString("id_offer", String.valueOf(offers.get(i).getId()));
        bundle.putString("city", offers.get(i).getCity());
        bundle.putString("title", offers.get(i).getTitle().toLowerCase());

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
