package com.tinilite.tiniview;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity
        implements LoginFragment.OnLoginFragmentListener,
        WelcomeFragment.OnWelcomeFragmentListener,
        DisplayFragment.OnDisplayFragmentListener {

    private static final String TAG = "MainActivity";
    private String mServer, mPort, mUsername, mPassword;
    private boolean mLoggedon = false;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: beginning.......");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or els
            //
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }
            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, WelcomeFragment.newInstance()).commit();


        }

        sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        //get persisted log in values or set to default values
        mServer = sharedPref.getString(getString(R.string.saved_server_key), "");
        mPort = sharedPref.getString(getString(R.string.saved_port_key), "");
        mUsername = sharedPref.getString(getString(R.string.saved_user_key), "");
        mPassword = sharedPref.getString(getString(R.string.saved_password_key), "");
        mLoggedon = sharedPref.getBoolean(getString(R.string.saved_loggedon_key), false);

        if (mLoggedon)
            showFragment(DisplayFragment.newInstance());
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
    public void OnWelcomeLoggon() {
        showFragment(LoginFragment.newInstance());
    }



    @Override
    public String[] OnGetCredentials() {
        String[] credentials;

        //get vredentials from shared preferences. default to current values if nothing
        //persisted. beginning of first run the current values will be ""
        mServer = sharedPref.getString(getString(R.string.saved_server_key), mServer);
        mPort = sharedPref.getString(getString(R.string.saved_port_key), mPort);
        mUsername = sharedPref.getString(getString(R.string.saved_user_key), mUsername);
        mPassword = sharedPref.getString(getString(R.string.saved_password_key), mPassword);
        mLoggedon = sharedPref.getBoolean(getString(R.string.saved_loggedon_key), mLoggedon);


        if (mLoggedon)
            credentials = new String[]{mServer, mPort, mUsername, mPassword, "true"};
        else
            credentials = new String[]{mServer, mPort, mUsername, mPassword, "false"};

        return credentials;
    }

    @Override
    public void OnSetCredentials(String server, String port, String Username, String Password, boolean loggedon) {
        mServer = server;
        mUsername = Username;
        mPort = port;
        mPassword = Password;
        mLoggedon = loggedon;
        //if not logged in forget password
        if (mLoggedon == false)
            mPassword = "";

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.saved_server_key), server);
        editor.putString(getString(R.string.saved_port_key), port);
        editor.putString(getString(R.string.saved_user_key), Username);
        editor.putString(getString(R.string.saved_password_key), mPassword);
        editor.putBoolean(getString(R.string.saved_loggedon_key), loggedon);
        editor.commit();


        if (loggedon)
            //displayDisplayFragment();
            showFragment(DisplayFragment.newInstance());
        else
            showFragment(WelcomeFragment.newInstance());
    }
}

/*
*02- 051020 04:03P JPCM  review
*03- 051220 09:27P JP single framelayout and replace fragment
hefdafadsfadsf
fdsafsadfasdfgi
*/

/*051720 this class written out of program
  retained because login credential needed
package com.tinilite.tiniview;

class Constants {
    */
/*FTP parameters*//*


 */
/*    Server constant*//*

    public static String server = "107.180.55.10";

*/
/*    User constants*//*

    public static int port = 21;
    public static String user = "Sign1@tiniliteworld.com";
    public static String pass = "Sign1";

             user = "User1@tiniliteworld.com";
             pwdUser1
             user = "User1@tiniliteworld.com";
             pwdUser2



    public static String fileName = "dat/Sign1.data";

*/
/*    Sign constants*//*

    public static int speed = 2;
    public static int lineCount = 10;
    public static int lineLength = 20;
}
*/
