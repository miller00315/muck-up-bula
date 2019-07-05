package br.com.miller.muckup.passwordRecovery.tasks;

public interface PasswordRecoveryTask {

    interface PasswordRecoveryModel{

        void recoveryOk();

        void recoveryFailed();

    }

    interface PasswordRecoveryPresenter{

        void recoveryOk();

        void recoveryFailed();

        void stringEmpty();

    }

    interface PasswordRecoveryView{

    }
}
