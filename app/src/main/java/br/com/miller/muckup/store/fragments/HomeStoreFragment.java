package br.com.miller.muckup.store.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
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
import br.com.miller.muckup.api.FirebaseStore;
import br.com.miller.muckup.models.Store;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeStoreFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeStoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeStoreFragment extends Fragment implements FirebaseStore.FirebaseStoreListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SECTION_NUMBER = "section_number";

    private OnFragmentInteractionListener mListener;

    private FirebaseImage firebaseImage;

    private FirebaseStore firebaseStore;

    private TextView storeCity, storeDescription,storeTime;
    ImageView storeImage;

    public HomeStoreFragment() {
        // Required empty public constructor
    }

    public static HomeStoreFragment newInstance(int param1) {
        HomeStoreFragment fragment = new HomeStoreFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseStore = new FirebaseStore(this);

        firebaseImage = new FirebaseImage(getContext());

        if (getArguments() != null) {

           if(! getArguments().isEmpty()){
               firebaseStore.firebaseGetStore(getArguments().getString("id_store"), getArguments().getString("city"));
           }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home_store, container, false);

             storeCity = view.findViewById(R.id.store_address);
             storeDescription = view.findViewById(R.id.store_description);
             storeTime = view.findViewById(R.id.store_time);
             storeImage = view.findViewById(R.id.image_store);


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
    public void onStoresChanged(ArrayList<Store> stores) {

    }

    @Override
    public void onStoreChanged(Store store) {

        if(store != null)
        if(this.isVisible()) {

            mListener.onFragmentInteraction(store.getName());
            storeCity.setText(store.getCity());
            storeDescription.setText(store.getDescription());
            storeTime.setText(store.getTime());
            firebaseImage.downloadFirebaseImage("stores", store.getCity(), store.getImage(), storeImage);
        }

        else {
            Toast.makeText(getContext(), "Problemas ao encontrar a loja", Toast.LENGTH_LONG).show();
        }

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String storeName);
    }
}
