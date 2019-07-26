package br.com.miller.muckup.store.views.viewHolders;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import br.com.miller.muckup.R;

public class MyBuysViewHolder extends RecyclerView.ViewHolder {

    private TextView idBuys, dateBuy, sendBuy, receiveBuy;
    private CardView cardSolicitation;

    public MyBuysViewHolder(@NonNull View itemView) {
        super(itemView);

        idBuys = itemView.findViewById(R.id.solicitation_id);
        dateBuy = itemView.findViewById(R.id.buy_date);
        sendBuy = itemView.findViewById(R.id.buy_send);
        receiveBuy = itemView.findViewById(R.id.buy_receive);
        cardSolicitation = itemView.findViewById(R.id.card_my_buys);
    }

    public TextView getSendBuy() {
        return sendBuy;
    }

    public void setSendBuy(String sendBuy) {
        this.sendBuy.setText(sendBuy);
    }

    public TextView getReceiveBuy() {
        return receiveBuy;
    }

    public void setReceiveBuy(String receiveBuy) {
        this.receiveBuy.setText(receiveBuy);
    }

    public TextView getNameProduct() {
        return idBuys;
    }

    public void setIdbuy(String string) {
        this.idBuys.setText(string);
    }

    public TextView getPriceProduct() {
        return dateBuy;
    }

    public void setDateBuy(String string) {
        this.dateBuy.setText(string);
    }

    public CardView getCardSolicitation() {
        return cardSolicitation;
    }

    public void setCardSolicitation(CardView layoutProduct) {
        this.cardSolicitation = layoutProduct;
    }
}
