package io.ichi_go.ichigo;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;


public class SampleActivityBase extends FragmentActivity {

    public static final String TAG = "SampleActivityBase";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected  void onStart() {
        super.onStart();
        //initializeLogging();
    }

    /** Set up targets to receive log data */
//    public void initializeLogging() {
//        // Using Log, front-end to the logging chain, emulates android.util.log method signatures.
//        // Wraps Android's native log framework
//        LogWrapper logWrapper = new LogWrapper();
//        Log.setLogNode(logWrapper);
//
//        Log.i(TAG, "Ready");
//    }
}
