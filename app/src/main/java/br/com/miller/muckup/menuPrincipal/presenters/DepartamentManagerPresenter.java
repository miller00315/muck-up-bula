package br.com.miller.muckup.menuPrincipal.presenters;


import android.os.Bundle;

import com.google.android.gms.common.internal.Objects;

import java.util.ArrayList;

import br.com.miller.muckup.menuPrincipal.models.DepartamentModel;
import br.com.miller.muckup.menuPrincipal.tasks.DepartamentManagerTask;
import br.com.miller.muckup.domain.Departament;
import br.com.miller.muckup.menuPrincipal.views.fragments.DepartamentsFragment;
import br.com.miller.muckup.store.views.fragments.DepartamentStoreFragment;

public class DepartamentManagerPresenter implements DepartamentManagerTask.View, DepartamentManagerTask.Model {

    private DepartamentModel model;
    private DepartamentManagerTask.Presenter presenter;

    public DepartamentManagerPresenter(DepartamentManagerTask.Presenter presenter) {
        this.presenter = presenter;
        this.model = new DepartamentModel(this);
    }

    @Override
    public void onSingleDepartamenteFailed() { presenter.onSingleDepartamenteFailed(); }

    @Override
    public void onSingleDepartmentSuccess(Departament departament) { presenter.onSingleDepartmentSuccess(departament);}

    @Override
    public void onDepartamentsStoreSuccess(ArrayList<Departament> departaments) {
        if(departaments != null)
            presenter.onDepartamentsStoreSuccess(departaments);
    }

    @Override
    public void onDepartamentsStoreFailed() { presenter.onDepartamentsStoreFailed(); }

    @Override
    public void onDepartamentsSuccess(ArrayList<Departament> departaments) {
        if(departaments != null)
            presenter.onDepartamentsSuccess(departaments);
    }

    @Override
    public void onDepartmentsFailed() { presenter.onDepartmentsFailed(); }

    @Override
    public void getDepartamentsStore(String city, String storeId) {
        if(!city.isEmpty() && !storeId.isEmpty())
            model.getDepartmentStore(city, storeId);
    }

    @Override
    public void getDepartaments(String city) {
        if(!city.isEmpty())
            model.getDepartaments(city);
    }

    @Override
    public void getSingleDepartament(String city, String storeId, String departamentId) {
            model.getSingleDepartament(city, storeId, departamentId);
    }

    @Override
    public void getDepartamentCheck(Bundle bundle) {

        if(Objects.equal(DepartamentStoreFragment.ID, bundle.getString("code"))){
            model.getSingleDepartament(bundle.getString("city"), bundle.getString("id_store"), bundle.getString("id_departament"));
        }else if(Objects.equal(DepartamentsFragment.ID, bundle.getString("code"))){
            model.getDepartamentCity(bundle.getString("city"), bundle.getString("id_departament"));
        }

    }

}
