package com.tinilite.tiniview;

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

        if (savedInstanceState == null)
           displayWelcomeFragment();
        Log.d(TAG, "onCreate: finished");
    }

    @Override
    protected void onStart()
    {
        super.onStart();
/*
        if (!loaded) {
            displayWelcomeFragment();
            loaded = true;
        }*/
        //Toast.makeText(getApplicationContext(),"Now onStart() calls", Toast.LENGTH_LONG).show(); //onStart Called
    }



    public void displayDisplayFragment() {
        Log.d(TAG, "displayDisplayFragment: beginning.......");

        closeLoginFragment();
        closeWelcomeFragment();

        DisplayFragment displayFragmentFragment = DisplayFragment.newInstance();

        //get FragmentManager and start a transaction
        //FragmentManager fragmentManager =  getSupportFragmentManager();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //Add the simple fragment
        fragmentTransaction.add(R.id.display_fragment_container,
                displayFragmentFragment).addToBackStack(null).commit();

        Log.d(TAG, "displayDisplayFragment: finished");
    }

    public void closeDisplayFragment(){
        Log.d(TAG, "closeWelcomeFragment: beginning........");
        //get the fragment manager
        //FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentManager fragmentManager = getSupportFragmentManager() ;
        //check to see if the fragment is alredy showing
        DisplayFragment displayFragment = (DisplayFragment) fragmentManager
                .findFragmentById(R.id.display_fragment_container);
        if(displayFragment != null){
            //create and commit the transaction to remove the fragment
            FragmentTransaction fragmentTransaction =
                    fragmentManager.beginTransaction();
            fragmentTransaction.remove(displayFragment).commit();
        }

        Log.d(TAG, "closeWelcomeFragment: finished");
    }


    public void displayWelcomeFragment() {
        Log.d(TAG, "displayWelcomeFragment: beginning.......");

        closeLoginFragment();
        closeDisplayFragment();

        WelcomeFragment welcomeFragment = WelcomeFragment.newInstance();

        //get FragmentManager and start a transaction
        //FragmentManager fragmentManager =  getSupportFragmentManager();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //Add the simple fragment
        fragmentTransaction.add(R.id.Welcome_fragment_container,
                welcomeFragment).addToBackStack(null).commit();

        Log.d(TAG, "displayWelcomeFragment: finished");
    }

    public void closeWelcomeFragment(){
        Log.d(TAG, "closeWelcomeFragment: beginning........");
        //get the fragment manager
        //FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentManager fragmentManager = getSupportFragmentManager() ;
        //check to see if the fragment is alredy showing
        WelcomeFragment welcomeFragment = (WelcomeFragment) fragmentManager
                .findFragmentById(R.id.Welcome_fragment_container);
        if(welcomeFragment != null){
            //create and commit the transaction to remove the fragment
            FragmentTransaction fragmentTransaction =
                    fragmentManager.beginTransaction();
            fragmentTransaction.remove(welcomeFragment).commit();
        }

        Log.d(TAG, "closeWelcomeFragment: finished");
    }



    public  void displayLoginFragment() {
        Log.d(TAG, "displayLoginFragment: beginning.......");

        closeWelcomeFragment();
        closeDisplayFragment();

        LoginFragment loginFragment = LoginFragment.newInstance();

        //get FragmentManager and start a transaction
        //FragmentManager fragmentManager =  getSupportFragmentManager();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //Add the simple fragment
        fragmentTransaction.add(R.id.login_fragment_container,
                loginFragment).addToBackStack(null).commit();

        Log.d(TAG, "displayLoginFragment: finished");
    }

    public void closeLoginFragment(){
        Log.d(TAG, "closeWelcomeFragment: beginning........");
        //get the fragment manager
        //FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentManager fragmentManager = getSupportFragmentManager() ;
        //check to see if the fragment is alredy showing
        LoginFragment loginFragment = (LoginFragment) fragmentManager
                .findFragmentById(R.id.login_fragment_container);
        if(loginFragment != null){
            //create and commit the transaction to remove the fragment
            FragmentTransaction fragmentTransaction =
                    fragmentManager.beginTransaction();
            fragmentTransaction.remove(loginFragment).commit();
        }

        Log.d(TAG, "closeWelcomeFragment: finished");
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

