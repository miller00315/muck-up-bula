package br.com.miller.muckup.store.fragments;

import android.content.Context;
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
import br.com.miller.muckup.models.Evaluate;
import br.com.miller.muckup.store.adapters.OpinionStoreRecyclerAdapter;
import br.com.miller.muckup.store.presenters.OpinionPresenter;
import br.com.miller.muckup.store.tasks.OpinionTasks;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OpinionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class OpinionFragment extends Fragment implements OpinionTasks.Presenter {

    private OnFragmentInteractionListener mListener;
    private OpinionStoreRecyclerAdapter opinionStoreRecyclerAdapter;

    public OpinionFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        opinionStoreRecyclerAdapter = new OpinionStoreRecyclerAdapter(getContext());
        OpinionPresenter opinionPresenter = new OpinionPresenter(this);

        if(getArguments() != null)
            opinionPresenter.getOpinions(getArguments().getString("id_store"), getArguments().getString("city"));

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_opinion, container, false);

        RecyclerView recyclerViewOpinion = view.findViewById(R.id.recycler_view_opinion);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        recyclerViewOpinion.setLayoutManager(linearLayoutManager);

        recyclerViewOpinion.setHasFixedSize(true);

        recyclerViewOpinion.setAdapter(opinionStoreRecyclerAdapter);

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
    public void onOpinionsSuccess(ArrayList<Evaluate> evaluates) { if(evaluates != null && this.isVisible()) opinionStoreRecyclerAdapter.setArray(evaluates); }

    @Override
    public void onOpinionsFiled() { }

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
