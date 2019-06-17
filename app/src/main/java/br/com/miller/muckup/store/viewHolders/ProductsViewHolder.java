package br.com.miller.muckup.store.viewHolders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import br.com.miller.muckup.R;

public class ProductsViewHolder extends RecyclerView.ViewHolder {

    private TextView nameProduct, nameStore, priceProduct;
    private LinearLayout mainLayout;

    public ProductsViewHolder(@NonNull View itemView) {
        super(itemView);

        nameStore = itemView.findViewById(R.id.name_store_);
        nameProduct = itemView.findViewById(R.id.name_product);
        priceProduct = itemView.findViewById(R.id.price_product_);
        mainLayout = itemView.findViewById(R.id.layout_products);

    }

    public LinearLayout getMainLayout() {
        return mainLayout;
    }

    public void setMainLayout(LinearLayout mainLayout) {
        this.mainLayout = mainLayout;
    }

    public TextView getPriceProduct() {
        return priceProduct;
    }

    public void setPriceProduct(String priceProduct) {
        this.priceProduct.setText(priceProduct);
    }

    public TextView getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct.setText(nameProduct);
    }

    public TextView getNameStore() {
        return nameStore;
    }

    public void setNameStore(String nameStore) {
        this.nameStore.setText(nameStore);
    }
}
