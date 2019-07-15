package br.com.miller.muckup.menuPrincipal.presenters;

import java.util.ArrayList;

import br.com.miller.muckup.menuPrincipal.models.AdvModel;
import br.com.miller.muckup.menuPrincipal.tasks.AdvTasks;
import br.com.miller.muckup.domain.Adv;

public class AdvPresenter implements AdvTasks.View, AdvTasks.Model{

    private AdvTasks.Presenter presenter;
    private AdvModel advModel;

    public AdvPresenter(AdvTasks.Presenter presenter) {
        this.presenter = presenter;
        advModel = new AdvModel(this);
    }

    @Override
    public void getAdvs(String city) { advModel.getAdv(city);}

    @Override
    public void onAdvsSuccess(ArrayList<Adv> advs) { presenter.onAdvsSuccess(advs);}

    @Override
    public void onAdvFialed() { presenter.onAdvFialed();}
}
