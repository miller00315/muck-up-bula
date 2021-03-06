package br.com.miller.muckup.menuPrincipal.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import br.com.miller.muckup.R;
import br.com.miller.muckup.menuPrincipal.views.viewHolders.ViewHolderAdv;
import br.com.miller.muckup.domain.Adv;
import br.com.miller.muckup.utils.image.FirebaseImageUtils;

public class AdvRecyclerAdapter extends Item implements FirebaseImageUtils.FirebaseImageTask {

    private ArrayList<Adv> advs;

    private Context context;

    public static String ID = AdvRecyclerAdapter.class.getName();

    public AdvRecyclerAdapter( Item.OnAdapterInteract onAdapterInteract, Context context) {

            this.context = context;
            listener = onAdapterInteract;
            advs = new ArrayList<>();

    }

    @NonNull
    @Override
    public ViewHolderAdv onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolderAdv(LayoutInflater.from(context).inflate(R.layout.view_holder_adv,
                viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        if(viewHolder instanceof ViewHolderAdv){

            final ViewHolderAdv viewHolderAdv = (ViewHolderAdv) viewHolder;

            viewHolderAdv.downloaImage(advs.get(i).getType(), advs.get(i).getCity(), advs.get(i).getImage());

            viewHolderAdv.getImageAdv().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) { showItem(viewHolderAdv.getAdapterPosition()); }
                });


        }
    }

    @Override
    public int getItemCount() {
        return advs != null ? advs.size() : 0;
    }

    @Override
    public void showItem(int i) {

        Bundle bundle = new Bundle();
        bundle.putString("type", ID);
        bundle.putString("id_store", String.valueOf(advs.get(i).getStoreId()));
        bundle.putString("city", advs.get(i).getCity());

        listener.onAdapterInteract(bundle);

    }

    public boolean addAdv(Adv adv){

        boolean add = advs.add(adv);

        if(add)
            notifyDataSetChanged();

        return add;
    }

    public boolean removeAdv(Adv adv){

        boolean remove = advs.remove(adv);

        if(remove)
            notifyDataSetChanged();

        return remove;
    }

    public void removeAdv(int position){

        advs.remove(position);

        notifyDataSetChanged();
        notifyItemRangeChanged(position, advs.size());
    }

    public boolean setArray (ArrayList<Adv> advs){

        boolean addAll = this.advs.addAll(advs);

        notifyDataSetChanged();

        return addAll;
    }

    public void clear(){
        advs.clear();
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

    }

    @Override
    public void onDowloadImageFail() {

    }

    @Override
    public void downloaImage(String type, String city, String image) {

    }
}
