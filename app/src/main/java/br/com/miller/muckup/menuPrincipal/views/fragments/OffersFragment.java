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
import br.com.miller.muckup.menuPrincipal.presenters.DepartamentPresenter;
import br.com.miller.muckup.menuPrincipal.tasks.DepartamentTask;
import br.com.miller.muckup.models.Departament;
import br.com.miller.muckup.store.adapters.DepartamentRecyclerAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OffersFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OffersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OffersFragment extends Fragment implements DepartamentTask.Presenter{

    private static final String ARG_SECTION_NUMBER = "section_number";

    private OnFragmentInteractionListener mListener;

    private DepartamentRecyclerAdapter departamentRecyclerAdapter;

    private  RecyclerView offersRecyclerView;

    private DepartamentPresenter departamentPresenter;

    private SharedPreferences sharedPreferences;

    public OffersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param sectionNumber Parameter 1.
     * @return A new instance of fragment OffersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OffersFragment newInstance(int sectionNumber) {
        OffersFragment fragment = new OffersFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER,sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        departamentRecyclerAdapter = new DepartamentRecyclerAdapter(getContext());

        departamentPresenter = new DepartamentPresenter(this);

        sharedPreferences = getContext().getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_offers, container, false);

        offersRecyclerView = view.findViewById(R.id.offerRecyclerView);

        bindViews();

        return view;
    }

    private void bindViews(){

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        offersRecyclerView.setLayoutManager(linearLayoutManager);

        offersRecyclerView.setHasFixedSize(true);

        offersRecyclerView.setAdapter(departamentRecyclerAdapter);

        departamentPresenter.getDepartaments(sharedPreferences.getString(Constants.USER_CITY, ""));

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
    public void onDepartamentsSuccess(ArrayList<Departament> departaments) {

        if(this.isVisible()) {
            if (departamentRecyclerAdapter.getItemCount() > 0)
                departamentRecyclerAdapter.clear();

            departamentRecyclerAdapter.setArray(departaments);
        }

    }

    @Override
    public void onDepartamentByStoreSuccess(ArrayList<Departament> departaments) { }

    @Override
    public void onDepartamentByStoreFailed() { }

    @Override
    public void onDepartmentsFailed() { }

    @Override
    public void onDepartamentFailed() { }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Bundle bundle);
    }
}
