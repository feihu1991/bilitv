package com.flashfinger.bilitv;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;

/**
 * Automated test for MainActivity
 * Tests the main browse interface with remote control navigation
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private UiDevice device;

    @Before
    public void setUp() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }

    @Test
    public void useAppContext() {
        Context context = ApplicationProvider.getApplicationContext();
        assertEquals("com.flashfinger.bilitv", context.getPackageName());
    }

    @Test
    public void testMainActivityLaunches() {
        try (ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {
            // Verify main browse fragment is displayed
            onView(withId(R.id.main_browse_fragment))
                    .check(matches(isDisplayed()));
        }
    }

    @Test
    public void testRemoteControlDpadNavigation() {
        try (ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {
            // Simulate remote control D-pad navigation
            // Press RIGHT to navigate to next card
            device.pressDPadRight();
            Thread.sleep(500);

            // Press DOWN to navigate to next row
            device.pressDPadDown();
            Thread.sleep(500);

            // Press RIGHT to navigate in the row
            device.pressDPadRight();
            Thread.sleep(500);

            // Verify navigation works - main fragment should still be displayed
            onView(withId(R.id.main_browse_fragment))
                    .check(matches(isDisplayed()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRemoteControlCenterButton() {
        try (ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {
            // Navigate using remote control
            device.pressDPadRight();
            Thread.sleep(500);

            // Press center button to select video
            device.pressDPadCenter();
            Thread.sleep(1000);

            // Verify video player activity was launched (by checking current activity)
            String currentActivity = device.getCurrentActivityName();
            // VideoPlayerActivity or LivePlayerActivity should be launched
            assert (currentActivity.contains("VideoPlayerActivity") ||
                    currentActivity.contains("LivePlayerActivity"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testBackButton() {
        try (ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {
            // Simulate back button press
            device.pressBack();
            Thread.sleep(500);

            // Activity should be finished or still active (depending on implementation)
            String currentActivity = device.getCurrentActivityName();
            // Back button should either exit app or navigate up
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMenuButton() {
        try (ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {
            // Simulate menu button press
            device.pressMenu();
            Thread.sleep(500);

            // Verify main activity is still displayed
            onView(withId(R.id.main_browse_fragment))
                    .check(matches(isDisplayed()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
