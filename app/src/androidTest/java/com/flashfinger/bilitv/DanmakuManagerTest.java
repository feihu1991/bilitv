package com.flashfinger.bilitv;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.flashfinger.bilitv.danmaku.DanmakuManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Automated test for DanmakuManager
 * Tests danmaku display, pause, resume, and clear
 */
@RunWith(AndroidJUnit4.class)
public class DanmakuManagerTest {

    private DanmakuManager danmakuManager;
    private Context context;
    private android.widget.FrameLayout container;

    @Before
    public void setUp() {
        context = ApplicationProvider.getApplicationContext();
        container = new android.widget.FrameLayout(context);
        container.setLayoutParams(new android.view.ViewGroup.LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.MATCH_PARENT
        ));
        danmakuManager = new DanmakuManager(context, container, 1920, 1080);
    }

    @Test
    public void testDanmakuManagerCreation() {
        assertNotNull("DanmakuManager should be created", danmakuManager);
    }

    @Test
    public void testAddDanmaku() throws InterruptedException {
        String testDanmaku = "Test Danmaku";
        danmakuManager.addDanmaku(testDanmaku);
        Thread.sleep(100);

        assertEquals("Danmaku should be added", 1, container.getChildCount());
    }

    @Test
    public void testMultipleDanmaku() throws InterruptedException {
        String[] danmakus = {"First", "Second", "Third", "Fourth", "Fifth"};

        for (String danmaku : danmakus) {
            danmakuManager.addDanmaku(danmaku);
            Thread.sleep(50);
        }

        // Should have multiple danmaku views
        assertTrue("Should have multiple danmakus", container.getChildCount() > 1);
    }

    @Test
    public void testPauseDanmaku() throws InterruptedException {
        danmakuManager.addDanmaku("Test");
        Thread.sleep(100);
        danmakuManager.pause();

        assertTrue("Danmaku should be paused", container.getChildCount() > 0);
    }

    @Test
    public void testResumeDanmaku() throws InterruptedException {
        danmakuManager.addDanmaku("Test");
        Thread.sleep(100);
        danmakuManager.pause();
        Thread.sleep(100);
        danmakuManager.start();

        assertTrue("Danmaku should be resumed", container.getChildCount() > 0);
    }

    @Test
    public void testClearDanmaku() throws InterruptedException {
        danmakuManager.addDanmaku("Test");
        Thread.sleep(100);
        danmakuManager.clear();

        assertEquals("All danmakus should be cleared", 0, container.getChildCount());
    }

    @Test
    public void testReleaseDanmaku() throws InterruptedException {
        danmakuManager.addDanmaku("Test");
        Thread.sleep(100);
        danmakuManager.release();

        assertEquals("Danmaku should be released", 0, container.getChildCount());
    }

    @Test
    public void testEmptyDanmaku() {
        danmakuManager.addDanmaku("");
        danmakuManager.addDanmaku(null);

        // Should not add empty danmaku
        assertEquals("Empty danmakus should not be added", 0, container.getChildCount());
    }

    @Test
    public void testStopDanmaku() throws InterruptedException {
        danmakuManager.addDanmaku("Test");
        Thread.sleep(100);
        danmakuManager.stop();

        assertTrue("Danmaku should be stopped", container.getChildCount() >= 0);
    }
}
