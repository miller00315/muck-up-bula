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
import br.com.miller.muckup.menuPrincipal.views.viewHolders.StoreViewHolder;
import br.com.miller.muckup.domain.Store;

public class StoreRecyclerAdapter extends Item {

    private ArrayList<Store> stores;
    private Context context;
    public static final String ID = StoreRecyclerAdapter.class.getName();

    public StoreRecyclerAdapter(Item.OnAdapterInteract onAdapterInteract,Context context) {

            this.context = context;
            listener = onAdapterInteract;
            stores = new ArrayList<>();

    }

    @NonNull
    @Override
    public StoreViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new StoreViewHolder(LayoutInflater.from(context).inflate(R.layout.view_holder_store,
                viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        if(viewHolder instanceof StoreViewHolder){

            final StoreViewHolder storeViewHolder = (StoreViewHolder) viewHolder;

            storeViewHolder.setDescriptionStore(stores.get(i).getDescription());
            storeViewHolder.setNameStore(stores.get(i).getName());
            storeViewHolder.setClassificatioStore(stores.get(i).getClassification());

            storeViewHolder.downloaImage("stores", stores.get(i).getCity(), stores.get(i).getImage());

            storeViewHolder.getLayoutStore().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showItem(storeViewHolder.getAdapterPosition());
                }
            });
        }
    }

    @Override
    public void showItem(int i){

        Bundle bundle = new Bundle();
        bundle.putString("type", this.getClass().getName());
        bundle.putString("id_store", String.valueOf(stores.get(i).getId()));
        bundle.putString("city", stores.get(i).getCity());

        listener.onAdapterInteract(bundle);

    }

    @Override
    public int getItemCount() {
        return stores != null ? stores.size() : 0;
    }

    public boolean addStore(Store store){

        boolean add = stores.add(store);

        if(add)
            notifyDataSetChanged();

        return add;
    }

    public boolean removeStore(Store store){

        boolean remove = stores.remove(store);

        if(remove)
            notifyDataSetChanged();

        return remove;
    }

    public void removeStore(int position){

        stores.remove(position);

        notifyDataSetChanged();
        notifyItemRangeChanged(position, stores.size());
    }

    public void clear(){

        stores.clear();

        notifyDataSetChanged();
    }

    public boolean setArray (ArrayList<Store> stores){

        boolean addAll = this.stores.addAll(stores);

        notifyDataSetChanged();

        return addAll;
    }
}
