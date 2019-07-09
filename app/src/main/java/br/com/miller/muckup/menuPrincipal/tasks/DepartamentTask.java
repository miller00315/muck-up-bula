package br.com.miller.muckup.menuPrincipal.tasks;

import java.util.ArrayList;

import br.com.miller.muckup.models.Departament;

public interface DepartamentTask {

    interface Presenter{
        void onDepartmentSuccess(Departament departament);
        void onDepartamentsSuccess(ArrayList<Departament> departaments);
        void onDepartamentByStoreSuccess(ArrayList<Departament>departaments);
        void onDepartamentByStoreFailed();
        void onDepartmentsFailed();
        void onDepartamentFailed();
    }

    interface Model{
        void onDepartmentSuccess(Departament departament);
        void onDepartamentByStoreSuccess(ArrayList<Departament>departaments);
        void onDepartamentsSuccess(ArrayList<Departament> departaments);
        void onDepartamentByStoreFailed();
        void onDepartmentsFailed();
        void onDepartamentFailed();
    }

    interface View{
        void getDepartamentByStore(String city, final String storeId);
        void getDepartaments(String city);
        void getDepartament();
    }
}
