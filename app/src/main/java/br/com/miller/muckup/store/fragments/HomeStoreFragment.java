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
public class HomeStoreFragment extends Fragment {
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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment HomeStoreFragment.
     */
    // TODO: Rename and change types and number of parameters
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

        firebaseStore = new FirebaseStore(getContext());

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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_store, container, false);

             storeCity = view.findViewById(R.id.store_address);
             storeDescription = view.findViewById(R.id.store_description);
             storeTime = view.findViewById(R.id.store_time);
             storeImage = view.findViewById(R.id.image_store);


        return view;
    }

    public void storeReceiver(Store store){

        if(this.isVisible()) {

            storeCity.setText(store.getCity());
            storeDescription.setText(store.getDescription());
            storeTime.setText(store.getTime());
            firebaseImage.downloadFirebaseImage("stores", store.getCity(), store.getImage(), storeImage);
        }

    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
