package br.com.miller.muckup.menuPrincipal.tasks;

import java.util.ArrayList;

import br.com.miller.muckup.domain.Departament;
import br.com.miller.muckup.domain.Offer;

public interface SearchTask {

    interface Presenter{

        void onSearchSuccess(ArrayList<Offer> offers);

        void onSearchFailed();

        void onSuggetionsSuccess(String[] suggestions);

        void onSuggestionFailed();

        void emptySearch();

        void onDepartamentsSuccess(ArrayList<Departament> departaments);

        void onDepartamentsFailed();

    }

    interface Model{

        void onSearchSuccess(ArrayList<Offer> offers);

        void onSearchFailed();

        void onSuggetionsSuccess(String[] suggestions);

        void onSuggestionFailed();

        void onDepartamentsSuccess(ArrayList<Departament> departaments);

        void onDepartamentsFailed();
    }

    interface View{

        void makeSearch(String search, String city, Object o);

        void getSuggetions(String city);
    }
}
