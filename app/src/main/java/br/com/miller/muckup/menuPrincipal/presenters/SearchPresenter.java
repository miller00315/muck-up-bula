package br.com.miller.muckup.menuPrincipal.presenters;

import java.util.ArrayList;

import br.com.miller.muckup.menuPrincipal.models.SearchModel;
import br.com.miller.muckup.menuPrincipal.tasks.SearchTask;
import br.com.miller.muckup.models.Offer;

public class SearchPresenter implements SearchTask.Model, SearchTask.View {

    private SearchModel model;
    private SearchTask.Presenter presenter;

    public SearchPresenter(SearchTask.Presenter presenter) {
        this.presenter = presenter;
        this.model = new SearchModel(this);
    }

    @Override
    public void onSearchSuccess(ArrayList<Offer> offers) { presenter.onSearchSuccess(offers);}

    @Override
    public void onSearchFailed() { presenter.onSearchFailed();}

    @Override
    public void onSuggetionsSuccess(String[] suggestions) { presenter.onSuggetionsSuccess(suggestions); }

    @Override
    public void onSuggestionFailed() { presenter.onSuggestionFailed(); }

    @Override
    public void makeSearch(String search, String city) {

        if(search.isEmpty() || city.isEmpty())
            presenter.emptySearch();
        else
            model.searchFirebase(search.toLowerCase(), city);

    }

    @Override
    public void getSuggetions(String city) {
        if(!city.isEmpty())
            model.getSugestions(city);
    }
}
