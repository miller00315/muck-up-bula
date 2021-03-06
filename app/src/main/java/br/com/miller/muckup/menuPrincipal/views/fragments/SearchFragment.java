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
import android.support.v7.widget.SearchView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import br.com.miller.muckup.R;
import br.com.miller.muckup.domain.Departament;
import br.com.miller.muckup.helpers.Constants;
import br.com.miller.muckup.helpers.SearchHelper;
import br.com.miller.muckup.menuPrincipal.adapters.AdvRecyclerAdapter;
import br.com.miller.muckup.menuPrincipal.adapters.Item;
import br.com.miller.muckup.menuPrincipal.adapters.SearchResultAdapter;
import br.com.miller.muckup.menuPrincipal.adapters.SpinnerArrayAdapter;
import br.com.miller.muckup.menuPrincipal.presenters.AdvPresenter;
import br.com.miller.muckup.menuPrincipal.presenters.SearchPresenter;
import br.com.miller.muckup.menuPrincipal.tasks.AdvTasks;
import br.com.miller.muckup.menuPrincipal.tasks.SearchTask;
import br.com.miller.muckup.domain.Adv;
import br.com.miller.muckup.domain.Offer;


public class SearchFragment extends Fragment implements
        SearchTask.Presenter,
        AdvTasks.Presenter,
        Item.OnAdapterInteract {

    private OnFragmentInteractionListener mListener;

    private TextView header;

    private RelativeLayout loading;

    private SharedPreferences sharedPreferences;

    private SearchResultAdapter searchResultAdapter;

    private AdvRecyclerAdapter advRecyclerAdapter;

    private RecyclerView recyclerResult, recyclerAdv;

    private AdvPresenter advPresenter;

    private SearchView.SearchAutoComplete searchAutoComplete;

    private SearchView searchView;

    private SearchPresenter searchPresenter;

    private Spinner spinner;

    public static final String ID = SearchFragment.class.getName();

    private ScrollView mainLayout;

    private RelativeLayout loadingLayout;

    public SearchFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        searchResultAdapter = new SearchResultAdapter(this, getContext());

        advRecyclerAdapter = new AdvRecyclerAdapter(this, getContext());

        searchPresenter = new SearchPresenter(this);

        sharedPreferences = Objects.requireNonNull(getContext()).getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);

        advPresenter = new AdvPresenter(this);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        recyclerResult = view.findViewById(R.id.recyclerResult);
        recyclerAdv = view.findViewById(R.id.recycler_adv);
        header = view.findViewById(R.id.header);
        loading = view.findViewById(R.id.loading_layout);
        spinner = view.findViewById(R.id.spinner_departament);
        mainLayout = view.findViewById(R.id.main_layout);
        loadingLayout = view.findViewById(R.id.search_loading);

        showLoading();

        bindViews(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if(searchView != null){

            searchPresenter.getSuggetions(sharedPreferences.getString(Constants.USER_CITY, ""));

            if(advRecyclerAdapter.getItemCount() > 0) advRecyclerAdapter.clear();
                advPresenter.getAdvs(sharedPreferences.getString(Constants.USER_CITY, ""));
        }
    }

    public void bindViews(View view){

        if(spinner.getAdapter() != null)
            if(!spinner.getAdapter().isEmpty()) hideLoading();

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

        header.setText(getResources().getString(R.string.ola).concat(" ").concat(sharedPreferences.getString(Constants.USER_NAME, "").substring(0,1).toUpperCase() +
                sharedPreferences.getString(Constants.USER_NAME, "").substring(1)).concat(getResources().getString(R.string.escolha_o_departamento)));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                if(searchResultAdapter.getItemCount() > 0)
                    searchResultAdapter.clear();

                searchPresenter.makeSearch(s, sharedPreferences.getString(Constants.USER_CITY, ""), spinner.getSelectedItem());

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

                searchPresenter.makeSearch(searchAutoComplete.getAdapter().getItem(i).toString(), sharedPreferences.getString(Constants.USER_CITY, ""), spinner.getSelectedItem());

                recyclerResult.setVisibility(View.INVISIBLE);
                loading.setVisibility(View.VISIBLE);
                recyclerAdv.setVisibility(View.INVISIBLE);

                return false;
            }

            @Override
            public boolean onSuggestionClick(int i) {

                if(searchResultAdapter.getItemCount() > 0) searchResultAdapter.clear();

                searchPresenter.makeSearch(searchAutoComplete.getAdapter().getItem(i).toString(), sharedPreferences.getString(Constants.USER_CITY, ""), spinner.getSelectedItem());

                recyclerResult.setVisibility(View.INVISIBLE);
                loading.setVisibility(View.VISIBLE);
                recyclerAdv.setVisibility(View.INVISIBLE);

                return false;
            }
        });

    }

    private void showLoading(){

        mainLayout.setVisibility(View.INVISIBLE);
        loadingLayout.setVisibility(View.VISIBLE);
    }

    private void hideLoading(){

        mainLayout.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.INVISIBLE);
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
    public void onSearchSuccess(ArrayList<Offer> offers) {

        if(offers != null) {

            if(searchResultAdapter.getItemCount() > 0) searchResultAdapter.clear();


            if(offers.isEmpty()) {
                Toast.makeText(getContext(), "Nada encontramos ofertas neste departamento", Toast.LENGTH_LONG).show();
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

            Toast.makeText(getContext(), "Não encontramos ofertas neste departamento.", Toast.LENGTH_SHORT).show();
            recyclerResult.setVisibility(View.INVISIBLE);
            loading.setVisibility(View.INVISIBLE);
            recyclerAdv.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSearchFailed() {

        Toast.makeText(getContext(), "Não encontramos ofertas, tente novamente.", Toast.LENGTH_SHORT).show();
        recyclerResult.setVisibility(View.INVISIBLE);
        loading.setVisibility(View.INVISIBLE);
        recyclerAdv.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSuggetionsSuccess(String[] suggestions) {

        hideLoading();

        searchView.setSuggestionsAdapter(SearchHelper.setSuggestionCursor(getContext(), suggestions));

        searchAutoComplete = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);

        if(searchAutoComplete.getAdapter().getCount() <= 0) {

            searchAutoComplete = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), android.R.layout.simple_dropdown_item_1line, suggestions);
            searchAutoComplete.setAdapter(dataAdapter);

        }

    }

    @Override
    public void onSuggestionFailed() { hideLoading(); }

    @Override
    public void emptySearch() { Toast.makeText(getContext(), "O campo de busca está vazio, insira algo.", Toast.LENGTH_SHORT).show(); }

    @Override
    public void onDepartamentsSuccess(ArrayList<Departament> departaments) {

        SpinnerArrayAdapter spinnerArrayAdapter = SpinnerArrayAdapter.newInstance(getContext(), departaments);

        spinner.setAdapter(spinnerArrayAdapter);
    }

    @Override
    public void onDepartamentsFailed() { }

    @Override
    public void onAdvsSuccess(ArrayList<Adv> advs) { advRecyclerAdapter.setArray(advs);}

    @Override
    public void onAdvFialed() { }

    @Override
    public void onAdapterInteract(Bundle bundle) {

        bundle.putString("code", ID);

        mListener.onFragmentInteraction(bundle);
    }

    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Bundle bundle);
    }
}
