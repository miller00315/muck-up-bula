package br.com.miller.muckup.menuPrincipal.tasks;

import java.util.ArrayList;

import br.com.miller.muckup.domain.Offer;

public interface SearchTask {

    interface Presenter{

        void onSearchSuccess(ArrayList<Offer> offers);

        void onSearchFailed();

        void onSuggetionsSuccess(String[] suggestions);

        void onSuggestionFailed();

        void emptySearch();

    }

    interface Model{

        void onSearchSuccess(ArrayList<Offer> offers);

        void onSearchFailed();

        void onSuggetionsSuccess(String[] suggestions);

        void onSuggestionFailed();
    }

    interface View{

        void makeSearch(String search, String city);

        void getSuggetions(String city);
    }
}
