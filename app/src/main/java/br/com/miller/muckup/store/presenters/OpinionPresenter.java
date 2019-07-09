package br.com.miller.muckup.store.presenters;

import android.util.Log;

import java.util.ArrayList;

import br.com.miller.muckup.models.Evaluate;
import br.com.miller.muckup.store.models.OpinionsModel;
import br.com.miller.muckup.store.tasks.OpinionTasks;

public class OpinionPresenter implements OpinionTasks.Model, OpinionTasks.View {

    private OpinionsModel opinionsModel;
    private OpinionTasks.Presenter presenter;

    public OpinionPresenter(OpinionTasks.Presenter presenter) {
        this.presenter = presenter;
        opinionsModel = new OpinionsModel(this);
    }

    @Override
    public void onOpinionsSuccess(ArrayList<Evaluate> evaluates) { presenter.onOpinionsSuccess(evaluates); }

    @Override
    public void onOpinionsFiled() { presenter.onOpinionsFiled(); }

    @Override
    public void getOpinions(String storeId, String city) { opinionsModel.getOpnions(storeId, city); }
}
