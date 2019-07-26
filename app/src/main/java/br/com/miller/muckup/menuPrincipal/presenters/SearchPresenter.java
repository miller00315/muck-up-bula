package br.com.miller.muckup.menuPrincipal.presenters;

import java.util.ArrayList;

import br.com.miller.muckup.domain.Departament;
import br.com.miller.muckup.menuPrincipal.models.SearchModel;
import br.com.miller.muckup.menuPrincipal.tasks.SearchTask;
import br.com.miller.muckup.domain.Offer;
import br.com.miller.muckup.utils.StringUtils;

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
    public void onDepartamentsSuccess(ArrayList<Departament> departaments) { presenter.onDepartamentsSuccess(departaments);}

    @Override
    public void onDepartamentsFailed() { presenter.onDepartamentsFailed(); }

    @Override
    public void makeSearch(String search, String city, Object o) {

        if(search.isEmpty())
            presenter.emptySearch();
        else if(o instanceof Departament){

            model.searchFirebase(StringUtils.normalizer(search), city, ((Departament) o).getId() );

        }

    }

    @Override
    public void getSuggetions(String city) {
        if(!city.isEmpty())
            model.getSugestions(city);
    }
}
