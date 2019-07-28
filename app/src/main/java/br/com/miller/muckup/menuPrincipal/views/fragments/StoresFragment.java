package br.com.miller.muckup.menuPrincipal.views.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Objects;

import br.com.miller.muckup.R;
import br.com.miller.muckup.helpers.Constants;
import br.com.miller.muckup.menuPrincipal.adapters.Item;
import br.com.miller.muckup.menuPrincipal.adapters.StoreRecyclerAdapter;
import br.com.miller.muckup.domain.Store;
import br.com.miller.muckup.store.presenters.StorePresenter;
import br.com.miller.muckup.store.tasks.StoreTasks;


public class StoresFragment extends Fragment implements StoreTasks.Presenter,
        Item.OnAdapterInteract {

    private StoreRecyclerAdapter storeRecyclerAdapter;

    private OnFragmentInteractionListener mListener;

    private StorePresenter storePresenter;

    private SharedPreferences sharedPreferences;

    private RelativeLayout mainLayout, loadingLayout;

    public StoresFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        storeRecyclerAdapter = new StoreRecyclerAdapter(this, getContext());

        storePresenter = new StorePresenter(this);

        sharedPreferences = Objects.requireNonNull(getContext()).getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_stores_, container, false);

        RecyclerView storesRecyclerView = view.findViewById(R.id.recyclerStore);

        mainLayout = view.findViewById(R.id.main_layout);

        loadingLayout = view.findViewById(R.id.loading_layout);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        storesRecyclerView.setLayoutManager(linearLayoutManager);

        storesRecyclerView.setHasFixedSize(true);

        storesRecyclerView.setAdapter(storeRecyclerAdapter);

        if(storesRecyclerView.getAdapter() != null)
            if (storeRecyclerAdapter.getItemCount() > 0) hideLoading();
            else showLoading();

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

        hideLoading();

        if (storeRecyclerAdapter.getItemCount() > 0) storeRecyclerAdapter.clear();

        if (this.isVisible()) storeRecyclerAdapter.setArray(stores);
    }

    @Override
    public void onStoreSuccess(Store store) { }

    private void showLoading(){

        mainLayout.setVisibility(View.INVISIBLE);
        loadingLayout.setVisibility(View.VISIBLE);
    }

    private void hideLoading(){

        mainLayout.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onDownloadImageSuccess(Bitmap bitmap) {

    }

    @Override
    public void onDownloadImageFailed() {

    }

    @Override
    public void onStoreFailed() { hideLoading(); }

    @Override
    public void onStoresFailed() { hideLoading(); }

    @Override
    public void onAdapterInteract(Bundle bundle) {

        bundle.putString("code", this.getClass().getName());

        mListener.onFragmentInteraction(bundle);
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Bundle bundle);
    }
}
