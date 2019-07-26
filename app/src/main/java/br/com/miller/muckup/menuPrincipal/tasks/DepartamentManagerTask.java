package br.com.miller.muckup.menuPrincipal.tasks;

import android.os.Bundle;

import java.util.ArrayList;

import br.com.miller.muckup.domain.Departament;

public interface DepartamentManagerTask {

    interface Presenter{
        void onDepartamentsSuccess(ArrayList<Departament> departaments);
        void onDepartmentsFailed();
        void onDepartamentsStoreSuccess(ArrayList<Departament>departaments);
        void onDepartamentsStoreFailed();
        void onSingleDepartamenteFailed();
        void onSingleDepartmentSuccess(Departament departament);

    }

    interface Model{
        void onSingleDepartamenteFailed();
        void onSingleDepartmentSuccess(Departament departament);
        void onDepartamentsStoreSuccess(ArrayList<Departament>departaments);
        void onDepartamentsStoreFailed();
        void onDepartamentsSuccess(ArrayList<Departament> departaments);
        void onDepartmentsFailed();
    }

    interface View{
        void getDepartamentsStore(String city, final String storeId);
        void getDepartaments(String city);
        void getSingleDepartament(String city, String storeId, String DeparatmentId);
        void getDepartamentCheck(Bundle bundle);
    }
}
