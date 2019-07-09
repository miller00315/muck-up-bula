package br.com.miller.muckup.store.viewHolders;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import br.com.miller.muckup.R;

public class OpinionUserViewHolder extends RecyclerView.ViewHolder {

    private TextView messageUser, nameUser, classificatioStore;
    private ImageView imageUser;
    private  RelativeLayout layoutStore;

    public OpinionUserViewHolder(@NonNull View itemView) {
        super(itemView);

        messageUser = itemView.findViewById(R.id.message_user);
        nameUser = itemView.findViewById(R.id.name_user);
        imageUser = itemView.findViewById(R.id.image_user);
        layoutStore = itemView.findViewById(R.id.layout_opinion);
        classificatioStore = itemView.findViewById(R.id.classification_store);
    }

    public TextView getClassificatioStore() {
        return classificatioStore;
    }

    public void setClassificatioStore(int classification) {
        this.classificatioStore.setText(String.valueOf(classification));
    }

    public TextView getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser.setText(messageUser);
    }

    public TextView getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser.setText(nameUser);
    }

    public ImageView getImageUser() {
        return imageUser;
    }

    public void setImageUser(Uri uri) {
        this.imageUser.setImageURI(uri);
    }

    public RelativeLayout getLayoutStore() {
        return layoutStore;
    }

    public void setLayoutStore(RelativeLayout layoutStore) {
        this.layoutStore = layoutStore;
    }
}
