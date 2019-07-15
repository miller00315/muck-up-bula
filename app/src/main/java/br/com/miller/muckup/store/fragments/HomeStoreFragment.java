package br.com.miller.muckup.store.fragments;

import android.content.Context;
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
import br.com.miller.muckup.api.FirebaseImage;
import br.com.miller.muckup.domain.Store;
import br.com.miller.muckup.store.presenters.StorePresenter;
import br.com.miller.muckup.store.tasks.StoreTasks;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeStoreFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class HomeStoreFragment extends Fragment implements StoreTasks.Presenter{

    private OnFragmentInteractionListener mListener;

    private FirebaseImage firebaseImage;

    private StorePresenter storePresenter;
    private TextView storeCity, storeDescription,storeTime, classification;
    private ImageView storeImage;

    public HomeStoreFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        storePresenter = new StorePresenter(this);

        firebaseImage = new FirebaseImage(getContext());

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

        return view;
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

                mListener.onFragmentInteraction(store.getName());
                storeCity.setText(store.getCity());
                storeDescription.setText(store.getDescription());
                storeTime.setText(store.getTime());
                classification.setText(String.valueOf(store.getClassification()));
                firebaseImage.downloadFirebaseImage("stores", store.getCity(), store.getImage(), storeImage);
            }

            else Toast.makeText(getContext(), "Problemas ao encontrar a loja", Toast.LENGTH_LONG).show();


    }

    @Override
    public void onStoreFailed() { Toast.makeText(getContext(), "Problemas ao encontrar a loja", Toast.LENGTH_LONG).show(); }

    @Override
    public void onStoresFailed() { }

    public interface OnFragmentInteractionListener { void onFragmentInteraction(String storeName);}
}
