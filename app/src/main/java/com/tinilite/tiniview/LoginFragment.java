package com.tinilite.tiniview;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tinilite.tiniview.databinding.FragmentLoginBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    private static final String TAG = "LoginFragment";
    private FragmentLoginBinding binding;
    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance(){
        return new LoginFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);

        // get the rooot view
        final View rootView = binding.getRoot();

        binding.butLoginCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayWelcomeFragment();;
            }
        });

        return rootView;
    }


    public void displayWelcomeFragment() {
        Log.d(TAG, "displayWelcomeFragment: beginning.......");

        closeLoginFragment();

        WelcomeFragment welcomeFragment = WelcomeFragment.newInstance();

        //get FragmentManager and start a transaction
        //FragmentManager fragmentManager =  getSupportFragmentManager();
        FragmentManager fragmentManager = getFragmentManager();
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
        FragmentManager fragmentManager = getFragmentManager() ;
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

        LoginFragment loginFragment = LoginFragment.newInstance();

        //get FragmentManager and start a transaction
        //FragmentManager fragmentManager =  getSupportFragmentManager();
        FragmentManager fragmentManager = getFragmentManager();
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
        FragmentManager fragmentManager = getFragmentManager() ;
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






}
