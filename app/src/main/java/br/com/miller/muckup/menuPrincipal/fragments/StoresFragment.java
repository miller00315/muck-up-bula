package br.com.miller.muckup.menuPrincipal.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import br.com.miller.muckup.R;
import br.com.miller.muckup.api.FirebaseStore;
import br.com.miller.muckup.helpers.Constants;
import br.com.miller.muckup.menuPrincipal.adapters.StoreRecyclerAdapter;
import br.com.miller.muckup.models.Store;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StoresFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StoresFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StoresFragment extends Fragment implements FirebaseStore.FirebaseStoreListener {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private StoreRecyclerAdapter storeRecyclerAdapter;

    private OnFragmentInteractionListener mListener;

    private RecyclerView storesRecyclerView;

    private FirebaseStore firebaseStore;

    private SharedPreferences sharedPreferences;

    public StoresFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param sectionNumber Parameter 1.
     * @return A new instance of fragment StoresFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StoresFragment newInstance(int sectionNumber) {
        StoresFragment fragment = new StoresFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER,sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        storeRecyclerAdapter = new StoreRecyclerAdapter(getContext());

        firebaseStore = new FirebaseStore(this);

        sharedPreferences = getContext().getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stores_, container, false);

        storesRecyclerView = view.findViewById(R.id.recyclerStore);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        storesRecyclerView.setLayoutManager(linearLayoutManager);

        storesRecyclerView.setHasFixedSize(true);

        storesRecyclerView.setAdapter(storeRecyclerAdapter);

        firebaseStore.firebaseGetStores(sharedPreferences.getString(Constants.USER_CITY, ""));

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Bundle bundle) {
        if (mListener != null) {
            mListener.onFragmentInteraction(bundle);
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


    @Override
    public void onStoresChanged(ArrayList<Store> stores) {

        if(stores != null) {

            if (storeRecyclerAdapter.getItemCount() > 0)
                storeRecyclerAdapter.clear();

            if (this.isVisible())
                storeRecyclerAdapter.setArray(stores);
        }

    }

    @Override
    public void onStoreChanged(Store store) {

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
        void onFragmentInteraction(Bundle bundle);
    }
}
