package br.com.miller.muckup.menuPrincipal.fragments;

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
import android.support.v7.widget.SearchView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import br.com.miller.muckup.R;
import br.com.miller.muckup.api.FirebaseAdvs;
import br.com.miller.muckup.api.FirebaseSearch;
import br.com.miller.muckup.helpers.Constants;
import br.com.miller.muckup.helpers.SearchHelper;
import br.com.miller.muckup.menuPrincipal.adapters.AdvRecyclerAdapter;
import br.com.miller.muckup.menuPrincipal.adapters.SearchResultAdapter;
import br.com.miller.muckup.models.Offer;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements FirebaseSearch.FirebaseSearchListener {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private OnFragmentInteractionListener mListener;

    private TextView header;

    private RelativeLayout loading;

    private SharedPreferences sharedPreferences;

    private SearchResultAdapter searchResultAdapter;

    private AdvRecyclerAdapter advRecyclerAdapter;

    private RecyclerView recyclerResult, recyclerAdv;

    private SearchView.SearchAutoComplete searchAutoComplete;

    private SearchView searchView;

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

        firebaseSearch = new FirebaseSearch(this);

        FirebaseAdvs firebaseAdvs = new FirebaseAdvs(getContext());

        sharedPreferences = Objects.requireNonNull(getContext()).getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);

        firebaseAdvs.getAdvsFromCity(sharedPreferences.getString(Constants.USER_CITY, ""), advRecyclerAdapter);


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerResult = view.findViewById(R.id.recyclerResult);
        recyclerAdv = view.findViewById(R.id.recycler_adv);
        header = view.findViewById(R.id.header);
        loading = view.findViewById(R.id.loading_layout);

        bindViews(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if(searchView != null)
            firebaseSearch.getSugestions(sharedPreferences.getString(Constants.USER_CITY, ""));
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

        searchView = view.findViewById(R.id.searchView);

        searchView.setActivated(true);

        searchView.setQueryHint("Digite o nome do medicamento");

        searchView.onActionViewExpanded();

        searchView.clearFocus();

        header.setText("Bem vindo ".concat(sharedPreferences.getString(Constants.USER_NAME, "").substring(0,1).toUpperCase() +
                sharedPreferences.getString(Constants.USER_NAME, "").substring(1)).concat("! Procure abaixo o que você precisa."));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                if(searchResultAdapter.getItemCount() > 0)
                    searchResultAdapter.clear();

                firebaseSearch.searchFirebase(s, sharedPreferences.getString(Constants.USER_CITY, ""));

                recyclerResult.setVisibility(View.INVISIBLE);
                loading.setVisibility(View.VISIBLE);
                recyclerAdv.setVisibility(View.INVISIBLE);

                return false;
            }


            @Override
            public boolean onQueryTextChange(String s) {

                if(searchResultAdapter.getItemCount() > 0) searchResultAdapter.clear();

                recyclerResult.setVisibility(View.INVISIBLE);
                loading.setVisibility(View.INVISIBLE);
                recyclerAdv.setVisibility(View.VISIBLE);

                return false;
            }
        });

        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int i) {

                if(searchResultAdapter.getItemCount() > 0) searchResultAdapter.clear();

                firebaseSearch.searchFirebase(searchAutoComplete.getAdapter().getItem(i).toString(), sharedPreferences.getString(Constants.USER_CITY, ""));

                recyclerResult.setVisibility(View.INVISIBLE);
                loading.setVisibility(View.VISIBLE);
                recyclerAdv.setVisibility(View.INVISIBLE);

                return false;
            }

            @Override
            public boolean onSuggestionClick(int i) {

                if(searchResultAdapter.getItemCount() > 0) searchResultAdapter.clear();

                firebaseSearch.searchFirebase(searchAutoComplete.getAdapter().getItem(i).toString(), sharedPreferences.getString(Constants.USER_CITY, ""));

                recyclerResult.setVisibility(View.INVISIBLE);
                loading.setVisibility(View.VISIBLE);
                recyclerAdv.setVisibility(View.INVISIBLE);

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

    @Override
    public void onFirebaseSearch(ArrayList<Offer> offers) {

        if(offers != null) {

            if(searchResultAdapter.getItemCount() > 0) searchResultAdapter.clear();

            if(offers.isEmpty()) {
                Toast.makeText(getContext(), "Nada encontrado", Toast.LENGTH_LONG).show();
                recyclerResult.setVisibility(View.INVISIBLE);
                loading.setVisibility(View.INVISIBLE);
                recyclerAdv.setVisibility(View.VISIBLE);
            }else{
                searchResultAdapter.setArray(offers);
                recyclerResult.setVisibility(View.VISIBLE);
                loading.setVisibility(View.INVISIBLE);
                recyclerAdv.setVisibility(View.INVISIBLE);
            }

        }else{

            Toast.makeText(getContext(), "Não escontramos ofertas", Toast.LENGTH_SHORT).show();
            recyclerResult.setVisibility(View.INVISIBLE);
            loading.setVisibility(View.INVISIBLE);
            recyclerAdv.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public void onSuggetions(String[] suggestions) {

        searchView.setSuggestionsAdapter(SearchHelper.setSuggestionCursor(getContext(), suggestions));

        searchAutoComplete = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);

        if(searchAutoComplete.getAdapter().getCount() <= 0) {

            searchAutoComplete = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), android.R.layout.simple_dropdown_item_1line, suggestions);
            searchAutoComplete.setAdapter(dataAdapter);

        }

    }

    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Bundle bundle);
    }
}
