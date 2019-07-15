package br.com.miller.muckup;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.miller.muckup.login.views.Login;
import br.com.miller.muckup.menuPrincipal.views.activities.MenuPrincipal;
import br.com.miller.muckup.passwordRecovery.view.PasswordRecovery;
import br.com.miller.muckup.register.view.Register;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<Login> activityLoginRule = new ActivityTestRule<>(Login.class, true, true);

    @Test
    public void verifyViews(){

        onView(withId(R.id.login_layout)).check(matches(isDisplayed()));//verifico se as views foram criadas
        onView(withId(R.id.register_button)).check(matches(isDisplayed()));
        onView(withId(R.id.login_button)).check(matches(isDisplayed()));
        onView(withId(R.id.password)).check(matches(isDisplayed()));
        onView(withId(R.id.login)).check(matches(isDisplayed()));

        onView(withId(R.id.login_button)).perform(click());
    }

    @Test
    public void verifyRegister(){

        Intents.init();

        onView(withId(R.id.register_button)).perform(click());

        Matcher<Intent> matcher = hasComponent(Register.class.getName());
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, null);

        intending(matcher).respondWith(result);

        Intents.release();
    }

    @Test
    public void verifyLogin(){

        Intents.init();

        onView(withId(R.id.login)).perform(typeText("bruno@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("qwerty"), closeSoftKeyboard());

        onView(withId(R.id.register_button)).perform(click());

        Matcher<Intent> matcher = hasComponent(MenuPrincipal.class.getName());
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, null);

        intending(matcher).respondWith(result);

        Intents.release();
    }

    @Test
    public void verificarRecuperarSenha(){

        Intents.init();

        onView(withId(R.id.login)).perform(typeText("bruno@gmail.com"), closeSoftKeyboard());

        onView(withId(R.id.recovery)).perform(click());

        Matcher<Intent> matcher = hasComponent(PasswordRecovery.class.getName());
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, null);

        intending(matcher).respondWith(result);

        Intents.release();

    }
}
