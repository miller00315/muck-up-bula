package br.com.miller.muckup.store.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.miller.muckup.R;
import br.com.miller.muckup.menuPrincipal.adapters.Item;
import br.com.miller.muckup.menuPrincipal.presenters.DepartamentManagerPresenter;
import br.com.miller.muckup.menuPrincipal.tasks.DepartamentManagerTask;
import br.com.miller.muckup.domain.Departament;
import br.com.miller.muckup.menuPrincipal.adapters.DepartamentRecyclerAdapter;

public class DepartamentStoreFragment extends Fragment implements
        Item.OnAdapterInteract,
        DepartamentManagerTask.Presenter {

    private OnFragmentInteractionListener mListener;

    private DepartamentRecyclerAdapter departamentRecyclerAdapter;

    private DepartamentManagerPresenter departamentManagerPresenter;

    private ScrollView mainLayout;
    private RelativeLayout layoutLoading;

    public static final String ID = DepartamentStoreFragment.class.getName();

    public DepartamentStoreFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        departamentRecyclerAdapter = new DepartamentRecyclerAdapter(this, getContext());

        departamentManagerPresenter = new DepartamentManagerPresenter(this);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_departament, container, false);

        mainLayout = view.findViewById(R.id.main_layout);
        layoutLoading = view.findViewById(R.id.loading_layout);

        RecyclerView departamentRecycler = view.findViewById(R.id.departamento);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        departamentRecycler.setLayoutManager(linearLayoutManager);

        departamentRecycler.setHasFixedSize(true);

        departamentRecycler.setAdapter(departamentRecyclerAdapter);

        assert getArguments() != null;

        showLoading();

        departamentManagerPresenter.getDepartamentsStore(getArguments().getString("city"), getArguments().getString("id_store"));

        return view;
    }

    private void showLoading(){
        layoutLoading.setVisibility(View.VISIBLE);
        mainLayout.setVisibility(View.INVISIBLE);
    }

    private  void hideLoading(){
        layoutLoading.setVisibility(View.INVISIBLE);
        mainLayout.setVisibility(View.VISIBLE);
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
    public void onDepartamentsSuccess(ArrayList<Departament> departaments) { }

    @Override
    public void onDepartamentsStoreSuccess(ArrayList<Departament> departaments) {

        hideLoading();

        if (departamentRecyclerAdapter.getItemCount() > 0) departamentRecyclerAdapter.clear();

        if (this.isVisible()) departamentRecyclerAdapter.setArray(departaments);

    }

    @Override
    public void onDepartamentsStoreFailed() {
        Toast.makeText(getContext(), "Não obtemos departamentos", Toast.LENGTH_SHORT).show();
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

    public interface OnFragmentInteractionListener { void onFragmentInteraction(Bundle bundle);}
}
