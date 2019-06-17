package br.com.miller.muckup.menuPrincipal.viewHolders;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import br.com.miller.muckup.R;

public class ViewHolderAdv extends RecyclerView.ViewHolder {

    private ImageView imageAdv;

    public ViewHolderAdv(@NonNull View itemView) {
        super(itemView);

        imageAdv = itemView.findViewById(R.id.image_adv);
    }

    public ImageView getImageAdv() {
        return imageAdv;
    }

    public void setImageAdvBitmap(Bitmap bitmap){

        this.imageAdv.setImageBitmap(bitmap);
    }

    public void setImageAdv(ImageView imageAdv) {
        this.imageAdv = imageAdv;
    }
}
