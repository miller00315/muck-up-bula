package br.com.miller.muckup.menuPrincipal.viewHolders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import br.com.miller.muckup.R;

public class SearchViewHolder extends RecyclerView.ViewHolder {

    public TextView valueSendResult, titleResullt, descriptionResult, valueResult;
    public ImageView  ImageResult;
    public RelativeLayout mainLayout;

    public SearchViewHolder(@NonNull View itemView) {
        super(itemView);

        mainLayout = itemView.findViewById(R.id.mainLayout);
        valueSendResult = itemView.findViewById(R.id.value_send_result);
        titleResullt = itemView.findViewById(R.id.title_result);
        descriptionResult = itemView.findViewById(R.id.description_result);
        valueResult = itemView.findViewById(R.id.value_result);
        ImageResult = itemView.findViewById(R.id.image_result);
    }
}
