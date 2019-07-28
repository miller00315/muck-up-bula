package br.com.miller.muckup.menuPrincipal.views.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.Objects;

import br.com.miller.muckup.R;
import br.com.miller.muckup.helpers.Constants;
import br.com.miller.muckup.menuPrincipal.adapters.Item;
import br.com.miller.muckup.menuPrincipal.presenters.DepartamentManagerPresenter;
import br.com.miller.muckup.menuPrincipal.tasks.DepartamentManagerTask;
import br.com.miller.muckup.domain.Departament;
import br.com.miller.muckup.menuPrincipal.adapters.DepartamentRecyclerAdapter;

public class DepartamentsFragment extends Fragment implements
        DepartamentManagerTask.Presenter,
        Item.OnAdapterInteract {

    private OnFragmentInteractionListener mListener;

    private DepartamentRecyclerAdapter departamentRecyclerAdapter;

    private  RecyclerView departamentsRecyclerView;

    private DepartamentManagerPresenter departamentPresenter;

    private SharedPreferences sharedPreferences;

    private ScrollView mainLayout;

    private RelativeLayout loadingLayout;

    public static final String ID = DepartamentsFragment.class.getName();

    public DepartamentsFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        departamentRecyclerAdapter = new DepartamentRecyclerAdapter(this, getContext());

        departamentPresenter = new DepartamentManagerPresenter(this);

        sharedPreferences = Objects.requireNonNull(getContext()).getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);

        departamentPresenter.getDepartaments(sharedPreferences.getString(Constants.USER_CITY, ""));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_offers, container, false);

        departamentsRecyclerView = view.findViewById(R.id.offerRecyclerView);

        mainLayout = view.findViewById(R.id.main_layout);

        loadingLayout = view.findViewById(R.id.loading_layout);

        showLoading();

        bindViews();

        return view;
    }

    private void showLoading(){

        mainLayout.setVisibility(View.INVISIBLE);
        loadingLayout.setVisibility(View.VISIBLE);
    }

    private void hideLoading(){

        mainLayout.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.INVISIBLE);
    }

    private void bindViews(){

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        departamentsRecyclerView.setLayoutManager(linearLayoutManager);

        departamentsRecyclerView.setHasFixedSize(true);

        departamentsRecyclerView.setAdapter(departamentRecyclerAdapter);

        if(departamentRecyclerAdapter.getItemCount() > 0) hideLoading();
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
    public void onDepartamentsSuccess(ArrayList<Departament> departaments) {

        hideLoading();

        if(this.isVisible()) {

            if(departamentRecyclerAdapter.getItemCount() > 0)
                departamentRecyclerAdapter.clear();

            departamentRecyclerAdapter.setArray(departaments);
        }

    }

    @Override
    public void onDepartamentsStoreSuccess(ArrayList<Departament> departaments) { }

    @Override
    public void onDepartamentsStoreFailed() {
            hideLoading();
    }

    @Override
    public void onSingleDepartamenteFailed() { }

    @Override
    public void onSingleDepartmentSuccess(Departament departament) { }

    @Override
    public void onDepartmentsFailed() { }

    @Override
    public void onAdapterInteract(Bundle bundle) {

        bundle.putString("code", ID);
        mListener.onFragmentInteraction(bundle);

    }

    public interface OnFragmentInteractionListener { void onFragmentInteraction(Bundle bundle); }
}
