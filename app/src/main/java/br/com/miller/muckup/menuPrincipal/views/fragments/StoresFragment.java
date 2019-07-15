package br.com.miller.muckup.menuPrincipal.views.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import br.com.miller.muckup.R;
import br.com.miller.muckup.helpers.Constants;
import br.com.miller.muckup.menuPrincipal.adapters.StoreRecyclerAdapter;
import br.com.miller.muckup.domain.Store;
import br.com.miller.muckup.store.presenters.StorePresenter;
import br.com.miller.muckup.store.tasks.StoreTasks;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StoresFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class StoresFragment extends Fragment implements StoreTasks.Presenter {

    private StoreRecyclerAdapter storeRecyclerAdapter;

    private OnFragmentInteractionListener mListener;

    private StorePresenter storePresenter;

    private SharedPreferences sharedPreferences;

    public StoresFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        storeRecyclerAdapter = new StoreRecyclerAdapter(getContext());

        storePresenter = new StorePresenter(this);

        sharedPreferences = getContext().getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stores_, container, false);

        RecyclerView storesRecyclerView = view.findViewById(R.id.recyclerStore);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        storesRecyclerView.setLayoutManager(linearLayoutManager);

        storesRecyclerView.setHasFixedSize(true);

        storesRecyclerView.setAdapter(storeRecyclerAdapter);

        storePresenter.getStores(sharedPreferences.getString(Constants.USER_CITY, ""));

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
    public void onStoresSuccess(ArrayList<Store> stores) {

        if (storeRecyclerAdapter.getItemCount() > 0) storeRecyclerAdapter.clear();

        if (this.isVisible()) storeRecyclerAdapter.setArray(stores);
    }

    @Override
    public void onStoreSuccess(Store store) { }

    @Override
    public void onStoreFailed() { }

    @Override
    public void onStoresFailed() { }

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
        void onFragmentInteraction(Bundle bundle);
    }
}
