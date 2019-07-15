package br.com.miller.muckup.menuPrincipal.tasks;

import java.util.ArrayList;

import br.com.miller.muckup.domain.Adv;

public interface AdvTasks {

    interface Model{
        void onAdvsSuccess(ArrayList<Adv> advs);
        void onAdvFialed();
    }

    interface View{
        void getAdvs(String city);
    }

    interface Presenter{
        void onAdvsSuccess(ArrayList<Adv> advs);
        void onAdvFialed();
    }

}
