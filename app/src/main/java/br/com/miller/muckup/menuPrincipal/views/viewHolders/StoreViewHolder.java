package br.com.miller.muckup.menuPrincipal.views.viewHolders;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import br.com.miller.muckup.R;
import br.com.miller.muckup.utils.image.FirebaseImageUtils;

public class StoreViewHolder extends RecyclerView.ViewHolder implements FirebaseImageUtils.FirebaseImageTask {

    private TextView descriptionStore, nameStore, classificatioStore;
    private ImageView imageStore;
    private  RelativeLayout layoutStore;
    private FirebaseImageUtils firebaseImageUtils;

    public StoreViewHolder(@NonNull View itemView) {
        super(itemView);

        firebaseImageUtils = new FirebaseImageUtils(this);

        descriptionStore = itemView.findViewById(R.id.description_store);
        nameStore = itemView.findViewById(R.id.title_store);
        imageStore = itemView.findViewById(R.id.image_user);
        layoutStore = itemView.findViewById(R.id.layoutStore);
        classificatioStore = itemView.findViewById(R.id.classification_store);
    }

    public void setDescriptionStore(TextView descriptionStore) {
        this.descriptionStore = descriptionStore;
    }

    public void setNameStore(TextView nameStore) {
        this.nameStore = nameStore;
    }

    public void setClassificatioStore(TextView classificatioStore) {
        this.classificatioStore = classificatioStore;
    }

    public void setImageStore(ImageView imageStore) {
        this.imageStore = imageStore;
    }

    public FirebaseImageUtils getFirebaseImageUtils() {
        return firebaseImageUtils;
    }

    public void setFirebaseImageUtils(FirebaseImageUtils firebaseImageUtils) {
        this.firebaseImageUtils = firebaseImageUtils;
    }

    public TextView getClassificatioStore() {
        return classificatioStore;
    }

    public void setClassificatioStore(int classification) {
        this.classificatioStore.setText(String.valueOf(classification));
    }

    public TextView getDescriptionStore() {
        return descriptionStore;
    }

    public void setDescriptionStore(String descriptionStore) {
        this.descriptionStore.setText(descriptionStore);
    }

    public TextView getNameStore() {
        return nameStore;
    }

    public void setNameStore(String nameStore) {
        this.nameStore.setText(nameStore);
    }

    public ImageView getImageStore() {
        return imageStore;
    }

    public void setImageStore(Uri uri) {
        this.imageStore.setImageURI(uri);
    }

    public RelativeLayout getLayoutStore() {
        return layoutStore;
    }

    public void setLayoutStore(RelativeLayout layoutStore) {
        this.layoutStore = layoutStore;
    }

    @Override
    public void onImageUploadSucces(Bitmap bitmap) { }

    @Override
    public void onImageUploadFails() { }

    @Override
    public void onImageDeleteSuccess() { }

    @Override
    public void onImageDeleteFailed() { }

    @Override
    public void onDownloadImageSuccess(Bitmap bitmap) { imageStore.setImageBitmap(bitmap);}

    @Override
    public void onDowloadImageFail() { }

    @Override
    public void downloaImage(String type, String city, String image) { firebaseImageUtils.downloadImage(type, city, image);}
}
