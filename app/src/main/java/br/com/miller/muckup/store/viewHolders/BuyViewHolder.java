package br.com.miller.muckup.store.viewHolders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import br.com.miller.muckup.R;

public class BuyViewHolder extends RecyclerView.ViewHolder {

    private TextView nameProduct, priceProduct, storeProduct;
    private RelativeLayout layoutProduct;
    private ImageView imageProduct;

    public BuyViewHolder(@NonNull View itemView) {
        super(itemView);

        nameProduct = itemView.findViewById(R.id.name_product);
        priceProduct = itemView.findViewById(R.id.price_product);
        layoutProduct = itemView.findViewById(R.id.layout_product);
        storeProduct = itemView.findViewById(R.id.store_product);
        imageProduct = itemView.findViewById(R.id.image_product);
    }

    public ImageView getImageProduct() {
        return imageProduct;
    }

    public void setImageProduct(ImageView imageProduct) {
        this.imageProduct = imageProduct;
    }

    public TextView getStoreProduct() {
        return storeProduct;
    }

    public void setStoreProduct(String store) {
        this.storeProduct.setText(store);
    }

    public TextView getNameProduct() {
        return nameProduct;
    }

    public void setIdbuy(String string) {
        this.nameProduct.setText(string);
    }

    public TextView getPriceProduct() {
        return priceProduct;
    }

    public void setDateBuy(String string) {
        this.priceProduct.setText(string);
    }

    public RelativeLayout getLayoutBuy() {
        return layoutProduct;
    }

    public void setLayoutProduct(RelativeLayout layoutProduct) {
        this.layoutProduct = layoutProduct;
    }
}
