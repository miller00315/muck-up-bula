package br.com.miller.muckup.store.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import br.com.miller.muckup.R;
import br.com.miller.muckup.api.FirebaseOffer;
import br.com.miller.muckup.menuPrincipal.activities.MenuPrincipal;
import br.com.miller.muckup.menuPrincipal.adapters.Item;
import br.com.miller.muckup.menuPrincipal.adapters.OffersRecyclerAdapter;
import br.com.miller.muckup.models.Departament;
import br.com.miller.muckup.store.activities.Store;
import br.com.miller.muckup.store.viewHolders.StoreViewHolder;

public class DepartamentRecyclerAdapter extends Item {

    private ArrayList<Departament> departments;

    private Context context;

    private FirebaseOffer firebaseOffer;

    public DepartamentRecyclerAdapter(Context context) {
        if(context instanceof OnAdapterInteract) {
            this.context = context;
            firebaseOffer = new FirebaseOffer(context);
            listener = (OnAdapterInteract) context;
            departments = new ArrayList<>();
        }else{
            throw new RuntimeException(context.toString()
                    + " must implement OnAdapter");
        }
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

            storeViewHolder.setDepartamentTitle(departments.get(i).getName());

            OffersRecyclerAdapter offersRecyclerAdapter = new OffersRecyclerAdapter(context);

            if(context instanceof MenuPrincipal)

                firebaseOffer.FirebaseGetOffers(departments.get(i).getCity(), String.valueOf(departments.get(i).getId()), offersRecyclerAdapter);

            else if(context instanceof Store)
               firebaseOffer.FirebaseGetOffersByDepartamentandStore(departments.get(i).getCity(),
                       String.valueOf(departments.get(i).getStoreId()),
                        String.valueOf(departments.get(i).getId()), offersRecyclerAdapter);

            storeViewHolder.setDepartamentRecyclerOffer(offersRecyclerAdapter, context);

            if(i % 2 == 0){
                storeViewHolder.getLayoutOffer().setBackgroundColor(context.getResources().getColor(R.color.gray));
            }else{
                storeViewHolder.getLayoutOffer().setBackgroundColor(context.getResources().getColor(R.color.backgroundColor));
            }

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
        bundle.putInt("type", 0);
        bundle.putString("name_departament", departments.get(i).getName());
        bundle.putString("city", departments.get(i).getCity());
        bundle.putInt("id_departament", departments.get(i).getId());
        bundle.putInt("id_store", departments.get(i).getStoreId());

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
