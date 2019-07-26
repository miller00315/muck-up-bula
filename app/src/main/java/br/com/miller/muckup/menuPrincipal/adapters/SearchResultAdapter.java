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
import br.com.miller.muckup.menuPrincipal.views.viewHolders.SearchViewHolder;
import br.com.miller.muckup.domain.Offer;

public class SearchResultAdapter extends Item{

    private ArrayList<Offer> results;
    private Context context;
    public static String ID = SearchResultAdapter.class.getName();

    public SearchResultAdapter( Item.OnAdapterInteract onAdapterInteract, Context context) {

            this.context = context;
            listener = onAdapterInteract;
            results = new ArrayList<>();

    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SearchViewHolder(LayoutInflater.from(context).
                inflate(R.layout.view_holder_search, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        if(viewHolder instanceof SearchViewHolder){

            final SearchViewHolder searchViewHolder = (SearchViewHolder) viewHolder;

            searchViewHolder.getValueSendResult().setText("R$ " .concat(String.format(Locale.getDefault(),"%.2f",results.get(i).getSendValue())));
            searchViewHolder.getDescriptionResult().setText(results.get(i).getDescription());
            searchViewHolder.getTitleResullt().setText(results.get(i).getTitle());
            searchViewHolder.getValueResult().setText("R$ " .concat(String.format(Locale.getDefault(),"%.2f",results.get(i).getValue())));

            searchViewHolder.getImage(results.get(i).getCity(), results.get(i).getImage());

            searchViewHolder.getMainLayout().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showItem(searchViewHolder.getAdapterPosition());
                }
            });

        }
    }

    @Override
    public void showItem(int i){

        Bundle bundle = new Bundle();

        bundle.putString("type", ID);
        bundle.putString("id_offer", results.get(i).getId());
        bundle.putString("city", results.get(i).getCity());
        bundle.putString("title",  results.get(i).getTitle().toLowerCase());
        bundle.putString("departamentId", results.get(i).getDepartamentId());

        listener.onAdapterInteract(bundle);
    }

    @Override
    public int getItemCount() {
        return results != null ? results.size() : 0;
    }

    public boolean addResult(Offer result){
        boolean add = results.add(result);

        if(add)
            notifyDataSetChanged();

        return add;
    }

    public boolean setArray (ArrayList<Offer> results){

        boolean addAll = this.results.addAll(results);

        notifyDataSetChanged();

        return addAll;
    }


    public boolean removeResult(Offer result){
        boolean remove = results.remove(result);

        if(remove)
            notifyDataSetChanged();

        return remove;
    }

    public void removeResult(int position){

        results.remove(position);
        notifyDataSetChanged();
        notifyItemRangeChanged(position, results.size());
    }

    public Offer getResult(int position){
        return results.get(position);
    }

    public void clear(){

        results.clear();
        notifyDataSetChanged();
    }

}
