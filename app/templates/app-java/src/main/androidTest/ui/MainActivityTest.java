package <%= appPackage %>.ui;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import <%= appPackage %>.application.App;
import <%= appPackage %>.di.MockApplicationComponent;
import <%= appPackage %>.ui.main.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.registerIdlingResources;
import static android.support.test.espresso.Espresso.unregisterIdlingResources;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by deividi on 23/03/16.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public IntentsTestRule<MainActivity> activityRule = new IntentsTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        App app = (App) instrumentation.getTargetContext().getApplicationContext();
        MockApplicationComponent component = (MockApplicationComponent) app.getComponent();
        component.inject(this);
    }

    @After
    public void unregisterIdlingResource() {
        unregisterIdlingResources(activityRule.getActivity().getCountingIdlingResource());
    }

    @Before
    public void registerIdlingResource() {
        registerIdlingResources(activityRule.getActivity().getCountingIdlingResource());
    }

    @Test
    public void created() {
        onView(withId(android.R.id.content)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

}
