package br.com.miller.muckup.store.fragments;

import android.content.Context;
import android.net.Uri;
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
import br.com.miller.muckup.menuPrincipal.presenters.DepartamentPresenter;
import br.com.miller.muckup.menuPrincipal.tasks.DepartamentTask;
import br.com.miller.muckup.models.Departament;
import br.com.miller.muckup.store.adapters.DepartamentRecyclerAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DepartamentFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class DepartamentFragment extends Fragment implements DepartamentTask.Presenter {

    private OnFragmentInteractionListener mListener;

    private DepartamentRecyclerAdapter departamentRecyclerAdapter;

    private DepartamentPresenter departamentPresenter;

    public DepartamentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        departamentRecyclerAdapter = new DepartamentRecyclerAdapter(getContext());

        departamentPresenter = new DepartamentPresenter(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_departament, container, false);

        RecyclerView departamentRecycler = view.findViewById(R.id.departamento);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        departamentRecycler.setLayoutManager(linearLayoutManager);

        departamentRecycler.setHasFixedSize(true);

        departamentRecycler.setAdapter(departamentRecyclerAdapter);

        assert getArguments() != null;

        departamentPresenter.getDepartamentByStore(getArguments().getString("city"), getArguments().getString("id_store"));

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
    public void onDepartmentSuccess(Departament departament) { }

    @Override
    public void onDepartamentsSuccess(ArrayList<Departament> departaments) { }

    @Override
    public void onDepartamentByStoreSuccess(ArrayList<Departament> departaments) {

        if (departamentRecyclerAdapter.getItemCount() > 0) departamentRecyclerAdapter.clear();

        if (this.isVisible()) departamentRecyclerAdapter.setArray(departaments);

    }

    @Override
    public void onDepartamentByStoreFailed() { }

    @Override
    public void onDepartmentsFailed() { }

    @Override
    public void onDepartamentFailed() { }

    public interface OnFragmentInteractionListener { void onFragmentInteraction(Uri uri);}
}
