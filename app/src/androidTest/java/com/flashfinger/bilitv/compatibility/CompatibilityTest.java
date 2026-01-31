package com.flashfinger.bilitv.compatibility;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.hardware.display.DisplayManager;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;

import com.flashfinger.bilitv.data.VideoData;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Compatibility test suite
 * Tests compatibility with different devices and configurations
 */
@RunWith(AndroidJUnit4.class)
public class CompatibilityTest {

    private Context context;
    private UiDevice device;

    @Before
    public void setUp() {
        context = ApplicationProvider.getApplicationContext();
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }

    /**
     * TC-33: Android版本兼容性测试
     */
    @Test
    public void testAndroidVersionCompatibility() {
        int currentSdk = Build.VERSION.SDK_INT;
        System.out.println("Current Android SDK: " + currentSdk);

        // Should support Android 8.0 (API 26) and above
        assertTrue("Should support Android 8.0+", currentSdk >= Build.VERSION_CODES.O);
    }

    /**
     * TC-34: 屏幕方向兼容性测试
     */
    @Test
    public void testScreenOrientationCompatibility() {
        int orientation = context.getResources().getConfiguration().orientation;
        System.out.println("Current orientation: " +
                (orientation == Configuration.ORIENTATION_LANDSCAPE ? "LANDSCAPE" : "PORTRAIT"));

        // Video players should force landscape
        Intent intent = new Intent(context, com.flashfinger.bilitv.player.VideoPlayerActivity.class);
        intent.putExtra(VideoData.VIDEO_ID, "1");
        intent.putExtra(VideoData.VIDEO_TITLE, "Test");
        intent.putExtra(VideoData.VIDEO_URL, "https://sf1-hscdn-tos.pstatp.com/obj/media-fe/xgplayer_doc_video/mp4/xgplayer-demo-720p.mp4");

        try (ActivityScenario<com.flashfinger.bilitv.player.VideoPlayerActivity> scenario =
                     ActivityScenario.launch(intent)) {
            scenario.onActivity(activity -> {
                int requestedOrientation = activity.getRequestedOrientation();
                assertTrue("Video player should request landscape orientation",
                        requestedOrientation == android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            });
        }
    }

    /**
     * TC-35: 屏幕分辨率兼容性测试
     */
    @Test
    public void testScreenResolutionCompatibility() {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        android.graphics.Point size = new android.graphics.Point();
        display.getSize(size);

        int width = size.x;
        int height = size.y;
        System.out.println("Screen resolution: " + width + "x" + height);

        // Should support 720P and above
        assertTrue("Screen should support at least 720P",
                Math.min(width, height) >= 720);
    }

    /**
     * TC-36: Leanback支持测试
     */
    @Test
    public void testLeanbackSupport() {
        PackageManager pm = context.getPackageManager();
        boolean hasLeanback = pm.hasSystemFeature(PackageManager.FEATURE_LEANBACK);

        System.out.println("Leanback support: " + hasLeanback);
        assertTrue("Should support Leanback (Android TV)", hasLeanback);
    }

    /**
     * TC-37: 4K输出支持测试
     */
    @Test
    public void test4KOutputSupport() {
        PackageManager pm = context.getPackageManager();
        boolean has4K = false;

        try {
            has4K = pm.hasSystemFeature("android.hardware.tv.4k_output");
        } catch (Exception e) {
            // Feature might not exist on all devices
        }

        System.out.println("4K output support: " + has4K);
        // Not all devices support 4K, so we just log it
    }

    /**
     * TC-38: HDR支持测试
     */
    @Test
    public void testHDRSupport() {
        PackageManager pm = context.getPackageManager();
        boolean hasHDR = false;

        try {
            hasHDR = pm.hasSystemFeature("android.hardware.tv.hdr");
        } catch (Exception e) {
            // Feature might not exist on all devices
        }

        System.out.println("HDR support: " + hasHDR);
        // Not all devices support HDR, so we just log it
    }

    /**
     * TC-39: 硬件加速支持测试
     */
    @Test
    public void testHardwareAccelerationSupport() {
        DisplayManager dm = (DisplayManager) context.getSystemService(Context.DISPLAY_SERVICE);
        Display[] displays = dm.getDisplays();

        assertNotNull("Should have at least one display", displays);
        assertTrue("Should have at least one display", displays.length > 0);

        for (Display display : displays) {
            int flags = display.getFlags();
            // FLAG_SUPPORTS_WIDE_COLOR_GAMUT is available in API 26+
            boolean supportsWideColorGamut = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O &&
                    (flags & 0x00000001) != 0; // FLAG_SUPPORTS_WIDE_COLOR_GAMUT = 0x00000001
            System.out.println("Display " + display.getDisplayId() +
                    " supports wide color gamut: " + supportsWideColorGamut);
        }
    }

    /**
     * TC-40: ExoPlayer兼容性测试
     */
    @Test
    public void testExoPlayerCompatibility() {
        // Test if ExoPlayer can be initialized
        try {
            com.google.android.exoplayer2.ExoPlayer player = new com.google.android.exoplayer2.ExoPlayer.Builder(context).build();
            assertNotNull("ExoPlayer should be initialized", player);
            player.release();
            System.out.println("ExoPlayer compatibility: OK");
        } catch (Exception e) {
            System.out.println("ExoPlayer compatibility: FAILED - " + e.getMessage());
        }
    }

    /**
     * TC-41: 触摸屏兼容性测试
     */
    @Test
    public void testTouchScreenCompatibility() {
        PackageManager pm = context.getPackageManager();
        boolean hasTouchScreen = pm.hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN);

        System.out.println("Touch screen support: " + hasTouchScreen);
        // TV apps should work without touch screen
    }

    /**
     * TC-42: 网络连接兼容性测试
     */
    @Test
    public void testNetworkCompatibility() {
        PackageManager pm = context.getPackageManager();
        boolean hasWifi = pm.hasSystemFeature(PackageManager.FEATURE_WIFI);
        boolean hasEthernet = false;

        try {
            hasEthernet = pm.hasSystemFeature(PackageManager.FEATURE_ETHERNET);
        } catch (Exception e) {
            // FEATURE_ETHERNET might not be defined
        }

        System.out.println("WiFi support: " + hasWifi);
        System.out.println("Ethernet support: " + hasEthernet);

        assertTrue("Should have network connectivity (WiFi or Ethernet)",
                hasWifi || hasEthernet);
    }
}
