package br.com.miller.muckup.menuPrincipal.viewHolders;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import br.com.miller.muckup.R;

public class StoreViewHolder extends RecyclerView.ViewHolder {

    private TextView descriptionStore, nameStore;
    private ImageView imageStore;
    private  RelativeLayout layoutStore;

    public StoreViewHolder(@NonNull View itemView) {
        super(itemView);

        descriptionStore = itemView.findViewById(R.id.description_store);
        nameStore = itemView.findViewById(R.id.title_store);
        imageStore = itemView.findViewById(R.id.header_store);
        layoutStore = itemView.findViewById(R.id.layoutStore);
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
}
