package br.com.miller.muckup.menuPrincipal.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

import br.com.miller.muckup.R;
import br.com.miller.muckup.domain.Buy;
import br.com.miller.muckup.store.views.viewHolders.MyBuysViewHolder;
import br.com.miller.muckup.utils.StringUtils;

public class MyBuyRecyclerAdapter extends Item {

    private ArrayList<Buy> buys;
    private Context context;

    public MyBuyRecyclerAdapter(Context context) {
        if(context instanceof OnAdapterInteract) {
            this.context = context;
            listener = (OnAdapterInteract) context;
            buys = new ArrayList<>();
        }else{
            throw new RuntimeException(context.toString()
                    + " must implement OnAdapter");
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyBuysViewHolder(LayoutInflater.from(context).inflate(R.layout.view_holder_my_buys,
                viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        if(viewHolder instanceof MyBuysViewHolder){


            final MyBuysViewHolder buyViewHolder = (MyBuysViewHolder) viewHolder;

            buyViewHolder.setIdbuy(buys.get(i).getId() != null ?  buys.get(i).getId().substring(0,16) : "n/a");
            buyViewHolder.setDateBuy(buys.get(i).getSolicitationDate() != null ? StringUtils.formatDate(buys.get(i).getSolicitationDate()) : "n/a");
            buyViewHolder.setReceiveBuy(buys.get(i).getReceiverDate() != null ? StringUtils.formatDate( buys.get(i).getReceiverDate()) : "n/a");
            buyViewHolder.setSendBuy(buys.get(i).getDeliverDate() != null ? StringUtils.formatDate( buys.get(i).getDeliverDate()) : "n/a");

            buyViewHolder.getCardSolicitation().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showItem(buyViewHolder.getAdapterPosition());
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return buys != null ? buys.size() : 0;
    }

    @Override
    public void showItem(int i) {

        Bundle bundle = new Bundle();

        bundle.putInt("item", i);

        listener.onAdapterInteract(bundle);
    }

    public boolean addBuy(Buy buy){

        boolean add = buys.add(buy);

        if(add)
            notifyDataSetChanged();

        return add;
    }

    public Buy removeBuy(Buy buy){

        for(int i = 0; i< buys.size(); i++){

            if(buys.get(i).getId().equals(buy.getId())){

                Buy temp = buys.remove(i);

                notifyItemRemoved(i);

                return temp;

            }
        }


        return null;
    }

    public void updateBuy(Buy buy){

        for(int i = 0; i< buys.size(); i++){

            if(buys.get(i).getId().equals(buy.getId())){

                buys.add(i, buy);

                notifyItemRemoved(i);

                return;

            }
        }
    }


    public boolean setArray (ArrayList<Buy> buys){

        boolean addAll = this.buys.addAll(buys);

        notifyDataSetChanged();

        return addAll;
    }

    public ArrayList<Buy> getBuys() {
        return buys;
    }
}
