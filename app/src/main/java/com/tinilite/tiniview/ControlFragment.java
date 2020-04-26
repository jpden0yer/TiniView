package com.tinilite.tiniview;

import android.os.Bundle;


import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tinilite.tiniview.databinding.FragmentControlBinding;
import com.tinilite.tiniview.databinding.FragmentDisplayBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class ControlFragment extends Fragment {
    private static final String TAG = "ControlFragment";

    private FragmentControlBinding binding;
    public ControlFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentControlBinding.inflate(inflater, container, false);

        // get the rooot view
        final View rootView = binding.getRoot();

        return rootView;
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState){

        displayWelcomeFragment();
        super.onActivityCreated(savedInstanceState);
    }

    public void displayWelcomeFragment() {
        Log.d(TAG, "displayWelcomeFragment: beginning.......");
        WelcomeFragment simpleFragment = WelcomeFragment.newInstance();

        //get FragmentManager and start a transaction
        //FragmentManager fragmentManager =  getSupportFragmentManager();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //Add the simple fragment
        fragmentTransaction.add(R.id.welcome_fragment_container,
                simpleFragment).addToBackStack(null).commit();


        Log.d(TAG, "displayWelcomeFragment: finished");
    }
}
