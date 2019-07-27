package br.com.miller.muckup.menuPrincipal.views.viewHolders;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import br.com.miller.muckup.R;
import br.com.miller.muckup.utils.image.FirebaseImageUtils;

public class OffersViewHolder extends RecyclerView.ViewHolder implements FirebaseImageUtils.FirebaseImageTask {

    private TextView titleOffer, descriptionOffer, valueOffer, valueSendOffer;
    private ImageView imageOffer;
    private RelativeLayout mainLayout;
    private FirebaseImageUtils firebaseImageUtils;

    public OffersViewHolder(@NonNull View itemView) {
        super(itemView);

        firebaseImageUtils = new FirebaseImageUtils(this);

        titleOffer = itemView.findViewById(R.id.title_offer);
        descriptionOffer = itemView.findViewById(R.id.description_offer);
        valueOffer = itemView.findViewById(R.id.value_offer);
        valueSendOffer = itemView.findViewById(R.id.value_send_offer);
        imageOffer = itemView.findViewById(R.id.image_offer);
        mainLayout = itemView.findViewById(R.id.offersLayout);
    }

  //  public void getImage(String city, String image){ firebaseImageUtils.downloadImage("offers", city, image); }

    @Override
    public void onImageUploadSucces(Bitmap bitmap) {  }

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
    public void onDownloadImageSuccess(Bitmap bitmap) { imageOffer.setImageBitmap(bitmap);}

    @Override
    public void onDowloadImageFail() { }

    @Override
    public void downloaImage(String type, String city, String image) { firebaseImageUtils.downloadImage(type, city, image);}

    public RelativeLayout getMainLayout() {
        return mainLayout;
    }

    public TextView getTitleOffer() {
        return titleOffer;
    }

    public TextView getDescriptionOffer() {
        return descriptionOffer;
    }

    public TextView getValueOffer() {
        return valueOffer;
    }
    public TextView getValueSendOffer() {
        return valueSendOffer;
    }
}
