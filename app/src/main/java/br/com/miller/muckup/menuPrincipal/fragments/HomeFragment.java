package br.com.miller.muckup.menuPrincipal.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.SearchView;

import java.util.ArrayList;

import br.com.miller.muckup.R;
import br.com.miller.muckup.api.FirebaseAdvs;
import br.com.miller.muckup.api.FirebaseSearch;
import br.com.miller.muckup.menuPrincipal.adapters.AdvRecyclerAdapter;
import br.com.miller.muckup.menuPrincipal.adapters.SearchResultAdapter;
import br.com.miller.muckup.helpers.SearchHelper;
import br.com.miller.muckup.models.Offer;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private OnFragmentInteractionListener mListener;

    private SearchResultAdapter searchResultAdapter;

    private AdvRecyclerAdapter advRecyclerAdapter;

    private RecyclerView recyclerResult, recyclerAdv;

    private FirebaseSearch firebaseSearch;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param sectionNumber Parameter 1.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(int sectionNumber) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER,sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        searchResultAdapter = new SearchResultAdapter(getContext());

        advRecyclerAdapter = new AdvRecyclerAdapter(getContext());

        firebaseSearch = new FirebaseSearch(getContext());

        FirebaseAdvs firebaseAdvs = new FirebaseAdvs(getContext());

        firebaseAdvs.getAdvsFromCity("Lavras", advRecyclerAdapter);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerResult = view.findViewById(R.id.recyclerResult);
        recyclerAdv = view.findViewById(R.id.recycler_adv);

        bindViews(view);

        return view;
    }

    public void bindViews(View view){

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext());

        recyclerResult.setLayoutManager(linearLayoutManager);

        recyclerResult.setHasFixedSize(true);

        recyclerResult.setAdapter(searchResultAdapter);

        recyclerAdv.setLayoutManager(linearLayoutManager1);

        recyclerAdv.setHasFixedSize(true);

        recyclerAdv.setAdapter(advRecyclerAdapter);

        SearchView searchView = view.findViewById(R.id.searchView);

        searchView.setSuggestionsAdapter(SearchHelper.setSuggestionCursor(this.getContext()));

        searchView.setActivated(true);

        searchView.setQueryHint("Digite o nome do medicamento");

        searchView.onActionViewExpanded();

        searchView.clearFocus();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                if(searchResultAdapter.getItemCount() > 0)
                    searchResultAdapter.clear();

                firebaseSearch.searchFirebase(s, "Lavras");

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                if(searchResultAdapter.getItemCount() > 0){
                    searchResultAdapter.clear();
                }

                recyclerResult.setVisibility(View.INVISIBLE);
                recyclerAdv.setVisibility(View.VISIBLE);

                return false;
            }
        });

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

    public void receiveMsg(ArrayList<Offer> arrayList) {

        Log.w("lolreira", "teste");

        if(arrayList != null) {
            searchResultAdapter.setArray(arrayList);
            recyclerResult.setVisibility(View.VISIBLE);
            recyclerAdv.setVisibility(View.INVISIBLE);
        }
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

        void onFragmentInteraction(Bundle bundle);
    }
}