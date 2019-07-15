package br.com.miller.muckup.store.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Locale;

import br.com.miller.muckup.R;
import br.com.miller.muckup.menuPrincipal.adapters.Item;
import br.com.miller.muckup.domain.Offer;
import br.com.miller.muckup.store.viewHolders.ProductsViewHolder;

public class ProductsDetailsRecyclerAdapter extends Item {

    private Context context;
    private ArrayList<Offer> products;

    public ProductsDetailsRecyclerAdapter(Context context) {

        this.context = context;
        products = new ArrayList<>();
    }

    @Override
    public void showItem(int i) {


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ProductsViewHolder(LayoutInflater.from(context).inflate(R.layout.view_holder_products,
                viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        if(viewHolder instanceof ProductsViewHolder){

            ProductsViewHolder productsViewHolder = (ProductsViewHolder) viewHolder;

            productsViewHolder.setNameProduct(products.get(i).getTitle());
            productsViewHolder.setNameStore(products.get(i).getDescription());
            productsViewHolder.setPriceProduct("R$ " .concat(String.format(Locale.getDefault(),"%.2f",products.get(i).getValue())));

            if(i % 2 == 0){
                productsViewHolder.getMainLayout().setBackgroundColor(context.getResources().getColor(R.color.gray));
            }else{
                productsViewHolder.getMainLayout().setBackgroundColor(context.getResources().getColor(R.color.backgroundColor));
            }

        }
    }

    public void clear(){

        products.clear();

        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return products != null ? products.size() : 0;
    }

    public boolean addProduct(Offer offer){

        boolean add = products.add(offer);

        if(add)
            notifyDataSetChanged();

        return add;
    }

    public boolean removeProduct(Offer offer){

        boolean remove = products.remove(offer);

        if(remove)
            notifyDataSetChanged();

        return remove;
    }

    public void removeProduct(int position){

        products.remove(position);

        notifyDataSetChanged();
        notifyItemRangeChanged(position, products.size());
    }

    public boolean setArray (ArrayList<Offer> products){

        boolean addAll = this.products.addAll(products);

        notifyDataSetChanged();

        return addAll;
    }
}
