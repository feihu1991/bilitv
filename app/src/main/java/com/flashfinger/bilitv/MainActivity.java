package com.flashfinger.bilitv;

import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;

/**
 * Main Activity for BiliTV Android TV Application
 * Supports Xiaomi TV and other Android TV systems
 */
public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_browse_fragment, new MainBrowseFragment())
                    .commit();
        }
    }
}
