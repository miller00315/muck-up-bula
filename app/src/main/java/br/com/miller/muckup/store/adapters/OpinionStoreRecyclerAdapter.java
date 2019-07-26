package br.com.miller.muckup.store.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import br.com.miller.muckup.R;
import br.com.miller.muckup.api.FirebaseImage;
import br.com.miller.muckup.menuPrincipal.adapters.Item;
import br.com.miller.muckup.domain.Evaluate;
import br.com.miller.muckup.store.views.viewHolders.OpinionUserViewHolder;
import br.com.miller.muckup.utils.StringUtils;

public class OpinionStoreRecyclerAdapter extends Item {

    private Context context;
    private ArrayList<Evaluate> evaluates;
    private FirebaseImage firebaseImage;

    public OpinionStoreRecyclerAdapter(Context context) {

        this.context = context;
        evaluates = new ArrayList<>();
        firebaseImage = new FirebaseImage(context);

    }

    @Override
    public void showItem(int i) {

    }

    @NonNull
    @Override
    public OpinionUserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new OpinionUserViewHolder(LayoutInflater.from(context).inflate(R.layout.view_holder_user_opinion,
                viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        if(viewHolder instanceof  OpinionUserViewHolder){

            final OpinionUserViewHolder opinionUserViewHolder = (OpinionUserViewHolder) viewHolder;

            opinionUserViewHolder.setClassificatioStore(evaluates.get(i).getValue());
            opinionUserViewHolder.setMessageUser(evaluates.get(i).getMessage());
            opinionUserViewHolder.setNameUser(evaluates.get(i).getUserName().concat(", ").concat(StringUtils.formatDate(evaluates.get(i).getDate())));

            firebaseImage.downloadFirebaseImage("users",
                    evaluates.get(i).getCity(),
                    evaluates.get(i).getIdUser().concat(".jpg"),
                    opinionUserViewHolder.getImageUser()
                    );

        }

    }

    @Override
    public int getItemCount() {
        return evaluates.size();
    }

    public void clear(){
        evaluates.clear();
        notifyDataSetChanged();
    }

    public boolean setArray(ArrayList<Evaluate> evaluates){

        boolean ok = this.evaluates.addAll(evaluates);

        notifyDataSetChanged();

        return ok;

    }
}
