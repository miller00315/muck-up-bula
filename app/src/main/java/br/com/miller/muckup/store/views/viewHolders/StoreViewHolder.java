package br.com.miller.muckup.store.views.viewHolders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import br.com.miller.muckup.R;
import br.com.miller.muckup.menuPrincipal.adapters.OffersRecyclerAdapter;

public class StoreViewHolder extends RecyclerView.ViewHolder {

    private ImageView departamentMenu;
    private TextView departamentTitle;
    private RecyclerView departamentRecyclerOffer;
    private LinearLayout layoutOffer;

    public StoreViewHolder(@NonNull View itemView) {
        super(itemView);

        departamentMenu = itemView.findViewById(R.id.departament_menu);
        departamentTitle = itemView.findViewById(R.id.departament_title);
        departamentRecyclerOffer = itemView.findViewById(R.id.departament_recycler_offer);
        layoutOffer = itemView.findViewById(R.id.layout_offers);

    }

    public ImageView getDepartamentMenu() {
        return departamentMenu;
    }

    public void setDepartamentMenu(ImageView departamentMenu) {
        this.departamentMenu = departamentMenu;
    }

    public TextView getDepartamentTitle() {
        return departamentTitle;
    }

    public void setDepartamentTitle(String departamentTitle) {
        this.departamentTitle.setText(departamentTitle);
    }

    public LinearLayout getLayoutOffer() {
        return layoutOffer;
    }

    public void setLayoutOffer(LinearLayout layoutOffer) {
        this.layoutOffer = layoutOffer;
    }

    public RecyclerView getDepartamentRecyclerOffer() {
        return departamentRecyclerOffer;
    }

    public void setDepartamentRecyclerOffer(OffersRecyclerAdapter departamentRecyclerOffer, Context context) {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);

        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        this.departamentRecyclerOffer.setLayoutManager(linearLayoutManager);

        this.departamentRecyclerOffer.setHasFixedSize(true);

        this.departamentRecyclerOffer.setAdapter(departamentRecyclerOffer);
    }
}
