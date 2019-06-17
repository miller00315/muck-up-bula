package br.com.miller.muckup.menuPrincipal.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import br.com.miller.muckup.R;
import br.com.miller.muckup.models.Buy;
import br.com.miller.muckup.store.viewHolders.MyBuysViewHolder;

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

            SimpleDateFormat fmt = new SimpleDateFormat("dd/mm",Locale.getDefault());

            SimpleDateFormat clc = new SimpleDateFormat("HH:mm",Locale.getDefault());

            final MyBuysViewHolder buyViewHolder = (MyBuysViewHolder) viewHolder;

            buyViewHolder.setIdbuy(buys.get(i).getId());
            buyViewHolder.setDateBuy(buys.get(i).getSolicitationDate() != null ? fmt.format(buys.get(i).getSolicitationDate()) : "n/a");
            buyViewHolder.setReceiveBuy(buys.get(i).getReceiverDate() != null ? clc.format( buys.get(i).getReceiverDate()) : "n/a");
            buyViewHolder.setSendBuy(buys.get(i).getDeliverDate() != null ?clc.format( buys.get(i).getDeliverDate()) : "n/a");

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

    public boolean addProduct(Buy buy){

        boolean add = buys.add(buy);

        if(add)
            notifyDataSetChanged();

        return add;
    }

    public boolean removeProduct(Buy buy){

        boolean remove = buys.remove(buy);

        if(remove)
            notifyDataSetChanged();

        return remove;
    }

    public void removeProdcut(int position){

        buys.remove(position);

        notifyDataSetChanged();
        notifyItemRangeChanged(position, buys.size());
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