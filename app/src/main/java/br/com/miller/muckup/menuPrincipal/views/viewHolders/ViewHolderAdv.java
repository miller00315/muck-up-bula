package br.com.miller.muckup.menuPrincipal.views.viewHolders;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import br.com.miller.muckup.R;
import br.com.miller.muckup.utils.image.FirebaseImageUtils;

import static br.com.miller.muckup.utils.image.FirebaseImageUtils.*;

public class ViewHolderAdv extends RecyclerView.ViewHolder implements FirebaseImageTask {

    private ImageView imageAdv;
    private FirebaseImageUtils firebaseImageUtils;

    public ViewHolderAdv(@NonNull View itemView) {
        super(itemView);

        firebaseImageUtils = new FirebaseImageUtils(this);

        imageAdv = itemView.findViewById(R.id.image_adv);
    }

    public ImageView getImageAdv() {
        return imageAdv;
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
    public void onDownloadImageSuccess(Bitmap bitmap) { this.imageAdv.setImageBitmap(bitmap);}

    @Override
    public void onDowloadImageFail() {

    }

    @Override
    public void downloaImage(String type, String city, String image) { firebaseImageUtils.downloadImage(type, city, image); }
}
