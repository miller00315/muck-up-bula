package br.com.miller.muckup.menuPrincipal.presenters;


import java.util.ArrayList;

import br.com.miller.muckup.menuPrincipal.models.DepartamentModel;
import br.com.miller.muckup.menuPrincipal.tasks.DepartamentTask;
import br.com.miller.muckup.domain.Departament;

public class DepartamentPresenter implements DepartamentTask.View, DepartamentTask.Model {

    private DepartamentModel model;
    private DepartamentTask.Presenter presenter;

    public DepartamentPresenter(DepartamentTask.Presenter presenter) {
        this.presenter = presenter;
        this.model = new DepartamentModel(this);
    }

    @Override
    public void onDepartmentSuccess(Departament departament) {
        if(departament != null)
            presenter.onDepartmentSuccess(departament);
    }

    @Override
    public void onDepartamentByStoreSuccess(ArrayList<Departament> departaments) {
        if(departaments != null)
            presenter.onDepartamentByStoreSuccess(departaments);
    }

    @Override
    public void onDepartamentsSuccess(ArrayList<Departament> departaments) {
        if(departaments != null)
            presenter.onDepartamentsSuccess(departaments);
    }

    @Override
    public void onDepartamentByStoreFailed() { presenter.onDepartamentFailed();}

    @Override
    public void onDepartmentsFailed() { presenter.onDepartmentsFailed(); }

    @Override
    public void onDepartamentFailed() { presenter.onDepartamentFailed(); }

    @Override
    public void getDepartamentByStore(String city, String storeId) {
        if(!city.isEmpty() && !storeId.isEmpty())
            model.getDepartmentByStoreId(city, storeId);
    }

    @Override
    public void getDepartaments(String city) {
        if(!city.isEmpty())
            model.getDepartaments(city);
    }

    @Override
    public void getDepartament() { }
}
