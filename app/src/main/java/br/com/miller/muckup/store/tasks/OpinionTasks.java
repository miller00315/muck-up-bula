package br.com.miller.muckup.store.tasks;

import java.util.ArrayList;

import br.com.miller.muckup.models.Evaluate;

public interface OpinionTasks {

    interface Presenter{

        void onOpinionsSuccess(ArrayList<Evaluate> evaluates);
        void onOpinionsFiled();
    }

    interface View{

        void getOpinions(String storeId, String city);

    }

    interface Model{

        void onOpinionsSuccess(ArrayList<Evaluate> evaluates);
        void onOpinionsFiled();
    }
}
