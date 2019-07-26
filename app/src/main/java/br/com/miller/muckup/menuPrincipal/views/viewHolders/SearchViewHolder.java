package br.com.miller.muckup.menuPrincipal.views.viewHolders;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import br.com.miller.muckup.R;
import br.com.miller.muckup.utils.FirebaseImageUtils;

public class SearchViewHolder extends RecyclerView.ViewHolder implements FirebaseImageUtils.FirebaseImageTask {

    private TextView valueSendResult, titleResullt, descriptionResult, valueResult;
    private ImageView  imageResult;
    private RelativeLayout mainLayout;
    private FirebaseImageUtils firebaseImageUtils;

    public SearchViewHolder(@NonNull View itemView) {
        super(itemView);

        firebaseImageUtils = new FirebaseImageUtils(this);

        mainLayout = itemView.findViewById(R.id.mainLayout);
        valueSendResult = itemView.findViewById(R.id.value_send_result);
        titleResullt = itemView.findViewById(R.id.title_result);
        descriptionResult = itemView.findViewById(R.id.description_result);
        valueResult = itemView.findViewById(R.id.value_result);
        imageResult = itemView.findViewById(R.id.image_result);
    }

    public void getImage(String city, String image){ firebaseImageUtils.downloadImage("offers", city, image); }

    public TextView getValueSendResult() {
        return valueSendResult;
    }

    public TextView getTitleResullt() {
        return titleResullt;
    }

    public TextView getDescriptionResult() {
        return descriptionResult;
    }

    public TextView getValueResult() {
        return valueResult;
    }

    public ImageView getImageResult() {
        return imageResult;
    }

    public RelativeLayout getMainLayout() {
        return mainLayout;
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
    public void onDownloadImageSuccess(Bitmap bitmap) {

        imageResult.setImageBitmap(bitmap);

    }

    @Override
    public void onDowloadImageFail() {

    }

    @Override
    public void downloaImage(String type, String city, String image) {

    }
}
