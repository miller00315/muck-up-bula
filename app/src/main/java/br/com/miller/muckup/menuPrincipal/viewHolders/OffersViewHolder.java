package br.com.miller.muckup.menuPrincipal.viewHolders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import br.com.miller.muckup.R;

public class OffersViewHolder extends RecyclerView.ViewHolder {

    public TextView titleOffer, descriptionOffer, valueOffer, valueSendOffer;
    public ImageView imageOffer;
    public RelativeLayout mainLayout;

    public OffersViewHolder(@NonNull View itemView) {
        super(itemView);

        titleOffer = itemView.findViewById(R.id.title_offer);
        descriptionOffer = itemView.findViewById(R.id.description_offer);
        valueOffer = itemView.findViewById(R.id.value_offer);
        valueSendOffer = itemView.findViewById(R.id.value_send_offer);
        imageOffer = itemView.findViewById(R.id.image_offer);
        mainLayout = itemView.findViewById(R.id.offersLayout);
    }
}
