package br.com.miller.muckup.store.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import br.com.miller.muckup.R;
import br.com.miller.muckup.api.FirebaseDepartament;
import br.com.miller.muckup.helpers.Constants;
import br.com.miller.muckup.models.Departament;
import br.com.miller.muckup.store.adapters.DepartamentRecyclerAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DepartamentFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DepartamentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DepartamentFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SECTION_NUMBER = "section_number";

    private OnFragmentInteractionListener mListener;

    private DepartamentRecyclerAdapter departamentRecyclerAdapter;

    private RecyclerView departamentRecycler;

    private FirebaseDepartament firebaseDepartament;
    private SharedPreferences sharedPreferences;

    public DepartamentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment DepartamentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DepartamentFragment newInstance(int param1) {
        DepartamentFragment fragment = new DepartamentFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        departamentRecyclerAdapter = new DepartamentRecyclerAdapter(getContext());

        firebaseDepartament = new FirebaseDepartament(getContext());

        sharedPreferences = getContext().getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_departament, container, false);

        departamentRecycler = view.findViewById(R.id.departamento);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        departamentRecycler.setLayoutManager(linearLayoutManager);

        departamentRecycler.setHasFixedSize(true);

        departamentRecycler.setAdapter(departamentRecyclerAdapter);

       // Log.w("teste Fragment", getArguments().toString());

        assert getArguments() != null;
        firebaseDepartament.getDepartamentsFirebaseByStore(getArguments().getString("city"), getArguments().getString("id_store"));

        return view;
    }


    public void departamentReceiver(ArrayList<Departament> departament){

        if(departamentRecyclerAdapter.getItemCount() > 0) departamentRecyclerAdapter.clear();

        if(this.isVisible())
            departamentRecyclerAdapter.setArray(departament);
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
