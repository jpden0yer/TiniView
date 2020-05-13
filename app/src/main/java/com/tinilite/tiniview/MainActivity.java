package com.tinilite.tiniview;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.tinilite.tiniview.WelcomeFragment.OnWelcomeFragmentListener;

public class MainActivity extends AppCompatActivity
  implements LoginFragment.OnLoginFragmentListener,
        WelcomeFragment.OnWelcomeFragmentListener {

    private static final String TAG = "MainActivity";

    private String mServer;
    private String mUsername;
    private String mPassword;
    private boolean mLoggedon = false;

    private boolean loaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: beginning.......");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }


            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, WelcomeFragment.newInstance()).commit();
        }
        Log.d(TAG, "onCreate: finished");
    }

    public void displayFragment(Fragment fragment) {
        Log.d(TAG, "displayFragment: beginning.......");

        //get FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();
        //start a transaction
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment,
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        // add the transaction to the back stack so the user can navigate back
        fragmentTransaction.addToBackStack(null);
        // Commit the transaction
        fragmentTransaction.commit();

        Log.d(TAG, "displayFragment: finished");
    }


    public void displayDisplayFragment() {
        Log.d(TAG, "displayDisplayFragment: beginning.......");
        displayFragment(DisplayFragment.newInstance());
        Log.d(TAG, "displayDisplayFragment: finished");
    }

    public void displayWelcomeFragment() {
        Log.d(TAG, "displayWelcomeFragment: beginning.......");
        displayFragment(WelcomeFragment.newInstance());
        Log.d(TAG, "displayWelcomeFragment: finished");
    }


    public  void displayLoginFragment() {
        Log.d(TAG, "displayLoginFragment: beginning.......");
        displayFragment(LoginFragment.newInstance());
        Log.d(TAG, "displayLoginFragment: finished");
    }


    @Override
    public void OnLoginSetCredentials(String server, String Username, String Password, boolean loggedon) {
                 mServer = server;
                 mUsername = Username;
                 mPassword = Password;
                 mLoggedon = loggedon;

                 if (loggedon)
                     displayDisplayFragment();
                 else
                     displayWelcomeFragment();
    }

    @Override
    public void OnLogingDisplayWelcomeFragment() {
        displayWelcomeFragment();
    }

    @Override
    public String[] OnLoginGetCredentials() {
        String[] credentials;
        if (mLoggedon)
            credentials = new String[]{mServer, mUsername, mPassword,"true"};
        else
            credentials = new String[]{mServer, mUsername,mPassword,"false"};

        return credentials;
    }

    @Override
    public void OnWelcomeLoggon() {
        displayLoginFragment();
    }
}


/*
*02- 051020 04:03P JPCM  review
*03- 051220 09:27P JP single framelayout and replace fragment
hefdafadsfadsf
fdsafsadfasdfgi
*/

