package br.com.miller.muckup.passwordRecovery.presenter;

import br.com.miller.muckup.passwordRecovery.model.PasswordRecoveryModel;
import br.com.miller.muckup.passwordRecovery.tasks.PasswordRecoveryTask;

public class PasswordRecoveryPresenter implements PasswordRecoveryTask.PasswordRecoveryModel {

    private PasswordRecoveryTask.PasswordRecoveryPresenter passwordRecoveryPresenter;
    private PasswordRecoveryModel passwordRecoveryModel;

    public PasswordRecoveryPresenter(PasswordRecoveryTask.PasswordRecoveryPresenter passwordRecoveryTask) {

        this.passwordRecoveryPresenter = passwordRecoveryTask;

        this.passwordRecoveryModel = new PasswordRecoveryModel(this);

    }

    public void checkString(String email){

        if(email.isEmpty()){
            passwordRecoveryPresenter.stringEmpty();
        }else{
            passwordRecoveryModel.reset(email);
        }
    }

    @Override
    public void recoveryOk() {
        passwordRecoveryPresenter.recoveryOk();
    }

    @Override
    public void recoveryFailed() {
        passwordRecoveryPresenter.recoveryFailed();
    }

}
