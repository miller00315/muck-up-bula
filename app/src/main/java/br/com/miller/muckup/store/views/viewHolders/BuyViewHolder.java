package br.com.miller.muckup.store.views.viewHolders;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import br.com.miller.muckup.R;
import br.com.miller.muckup.utils.image.FirebaseImageUtils;

public class BuyViewHolder extends RecyclerView.ViewHolder implements FirebaseImageUtils.FirebaseImageTask {

    private TextView nameProduct, priceProduct, storeProduct;
    private RelativeLayout layoutProduct;
    private ImageView imageProduct;
    private FirebaseImageUtils firebaseImageUtils;

    public BuyViewHolder(@NonNull View itemView) {
        super(itemView);

        firebaseImageUtils = new FirebaseImageUtils(this);

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

    @Override
    public void onImageUploadSucces(Bitmap bitmap) {

    }

    @Override
    public void onImageUploadFails() {

    }

    @Override
    public void onImageDeleteSuccess() {

    }

    @Override
    public void onImageDeleteFailed() {

    }

    @Override
    public void onDownloadImageSuccess(Bitmap bitmap) { imageProduct.setImageBitmap(bitmap);}

    @Override
    public void onDowloadImageFail() {

    }

    @Override
    public void downloaImage(String type, String city, String image) { firebaseImageUtils.downloadImage(type, city, image);}
}
