package com.tinilite.tiniview;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity
  implements LoginFragment.OnLoginFragmentListener,
        WelcomeFragment.OnWelcomeFragmentListener,
        DisplayFragment.OnDisplayFragmentListener {

    private static final String TAG = "MainActivity";
    private String mServer;
    private String mPort;
    private String mUsername;
    private String mPassword;
    private boolean mLoggedon = false;


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

    public void showFragment(Fragment fragment) {
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


    @Override
    public void OnLoginSetCredentials(String server, String port,  String Username, String Password, boolean loggedon) {
                 mServer = server;

                 mUsername = Username;
                 mPort = port;
                 mPassword = Password;
                 mLoggedon = loggedon;

                 if (loggedon)
                     //displayDisplayFragment();
                     showFragment(DisplayFragment.newInstance());
                 else
                     showFragment(WelcomeFragment.newInstance());
    }


    @Override
    public String[] OnLoginGetCredentials() {
        String[] credentials;
        if (mLoggedon)
            credentials = new String[]{mServer, mPort, mUsername, mPassword,"true"};
        else
            credentials = new String[]{mServer, mPort, mUsername,mPassword,"false"};

        return credentials;
    }

    @Override
    public void OnWelcomeLoggon() {
        showFragment(LoginFragment.newInstance());
    }

    @Override
    public String[] OnDisplayGetCredentials() {
        String[] credentials;
        if (mLoggedon)
            credentials = new String[]{mServer, mPort, mUsername, mPassword,"true"};
        else
            credentials = new String[]{mServer, mPort, mUsername,mPassword,"false"};

        return credentials;
    }
}

/*
*02- 051020 04:03P JPCM  review
*03- 051220 09:27P JP single framelayout and replace fragment
hefdafadsfadsf
fdsafsadfasdfgi
*/

