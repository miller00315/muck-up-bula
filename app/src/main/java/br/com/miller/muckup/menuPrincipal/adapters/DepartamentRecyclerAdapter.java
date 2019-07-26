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
import br.com.miller.muckup.domain.Departament;
import br.com.miller.muckup.store.views.viewHolders.StoreViewHolder;


public class DepartamentRecyclerAdapter extends Item  {

    private ArrayList<Departament> departments;

    private Context context;

    public DepartamentRecyclerAdapter(Item.OnAdapterInteract onAdapterInteract, Context context) {

            listener = onAdapterInteract;
            departments = new ArrayList<>();
            this.context = context;

    }

    @NonNull
    @Override
    public StoreViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new StoreViewHolder(LayoutInflater.from(context).inflate(R.layout.view_holder_list_offer,
                viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        if(viewHolder instanceof  StoreViewHolder){

            final StoreViewHolder storeViewHolder = (StoreViewHolder) viewHolder;

            storeViewHolder.setDepartamentTitle(departments.get(i).getTitle());

            OffersRecyclerAdapter offersRecyclerAdapter = new OffersRecyclerAdapter(listener, context);

            if(departments.get(i).getOffers() != null)
                offersRecyclerAdapter.setArray(departments.get(i).getOffers());

            storeViewHolder.setDepartamentRecyclerOffer(offersRecyclerAdapter, context);

            if(i % 2 == 0){
                storeViewHolder.getLayoutOffer().setBackgroundColor(context.getResources().getColor(R.color.gray));
            }else{
                storeViewHolder.getLayoutOffer().setBackgroundColor(context.getResources().getColor(R.color.backgroundColor));
            }

            storeViewHolder.getDepartamentTitle().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showItem(storeViewHolder.getAdapterPosition());
                }
            });

            storeViewHolder.getDepartamentMenu().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showItem(storeViewHolder.getAdapterPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return departments != null ? departments.size() : 0;
    }

    @Override
    public void showItem(int i) {

        Bundle bundle = new Bundle();
        bundle.putString("type", this.getClass().getName());
        bundle.putString("name_departament", departments.get(i).getTitle());
        bundle.putString("city", departments.get(i).getCity());
        bundle.putString("id_departament", departments.get(i).getId());
        bundle.putString("id_store", departments.get(i).getIdStore());

        listener.onAdapterInteract(bundle);
    }


    public boolean setArray (ArrayList<Departament> departments){

        boolean addAll = this.departments.addAll(departments);

        notifyDataSetChanged();

        return addAll;
    }

    public void clear(){

        departments.clear();

        notifyDataSetChanged();

    }


}
