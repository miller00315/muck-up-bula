package br.com.miller.muckup.store.views.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.miller.muckup.R;
import br.com.miller.muckup.domain.Store;
import br.com.miller.muckup.store.presenters.StorePresenter;
import br.com.miller.muckup.store.tasks.StoreTasks;

public class HomeStoreFragment extends Fragment implements
        StoreTasks.Presenter {

    public static final String ID = HomeStoreFragment.class.getName();
    private OnFragmentInteractionListener mListener;
    private StorePresenter storePresenter;
    private TextView storeCity, storeDescription,storeTime, classification;
    private ImageView storeImage;

    public HomeStoreFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        storePresenter = new StorePresenter(this);

    }

    @Override
    public void onResume() {
        super.onResume();

        if (getArguments() != null) {

            if(! getArguments().isEmpty()){

                storePresenter.getStore(getArguments().getString("id_store"), getArguments().getString("city"));
            }

        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home_store, container, false);

             storeCity = view.findViewById(R.id.store_address);

             storeDescription = view.findViewById(R.id.store_description);
             storeTime = view.findViewById(R.id.store_time);
             storeImage = view.findViewById(R.id.image_store);
             classification = view.findViewById(R.id.store_classification);

             bindViews();

        return view;
    }

    private void bindViews(){

        classification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();

                bundle.putString("code", ID);

                mListener.onFragmentInteraction(bundle);

            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onStoresSuccess(ArrayList<Store> stores) { }

    @Override
    public void onStoreSuccess(Store store) {

            if(this.isVisible()) {

                Bundle bundle = new Bundle();

                bundle.putString("storeName", store.getName());
                bundle.putString("code", ID);

                mListener.onFragmentInteraction(bundle);

                storeCity.setText(store.getCity());
                storeDescription.setText(store.getDescription());
                storeTime.setText(store.getTime());
                classification.setText(String.valueOf(store.getClassification()));

                storePresenter.getImage("stores", store.getCity(), store.getImage());

            }

            else Toast.makeText(getContext(), "Problemas ao encontrar a loja", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onDownloadImageSuccess(Bitmap bitmap) { storeImage.setImageBitmap(bitmap);}

    @Override
    public void onDownloadImageFailed() { Toast.makeText(getContext(), "Erro ao baixar imagem.", Toast.LENGTH_SHORT).show();}

    @Override
    public void onStoreFailed() { Toast.makeText(getContext(), "Problemas ao encontrar a loja", Toast.LENGTH_LONG).show(); }

    @Override
    public void onStoresFailed() { Toast.makeText(getContext(), "Erro ao obter dados", Toast.LENGTH_SHORT).show(); }

    public interface OnFragmentInteractionListener { void onFragmentInteraction(Bundle bundle); }
}
