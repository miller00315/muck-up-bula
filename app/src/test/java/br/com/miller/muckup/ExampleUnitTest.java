package br.com.miller.muckup;

import com.google.firebase.auth.FirebaseUser;

import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

import br.com.miller.muckup.models.User;
import br.com.miller.muckup.register.presenters.RegisterPresenter;
import br.com.miller.muckup.register.tasks.Task;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest implements Task.Presenter {

    @Test
    public void Setupuser() {

        RegisterPresenter registerPresenter = new RegisterPresenter(this);

            assertNotNull(registerPresenter.setupUser("Miller", "Oliveirs", "miller@gmail.com", "Lavras", "09876544",
                    "oitavo", "09/02/1990").getBirth_date());

    }

    @Override
    public void successRegister(User user) {

    }

    @Override
    public void failedRegister() {

    }

    @Override
    public void incompleteRegister(int code) {

    }

    @Override
    public void tempraryUserDeleted() {

    }

    @Override
    public void uploadImageListener(boolean state, FirebaseUser firebaseUser) {

    }

    @Override
    public void errorLogin() {

    }

    @Override
    public void firstUserConfigured() {

    }

    @Override
    public void uploaImageError() {

    }

    //@Test
    //public void addition_isCorrect() {
    //    assertEquals(4, 2 + 2);
   // }
}