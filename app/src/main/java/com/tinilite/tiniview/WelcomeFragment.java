package com.tinilite.tiniview;

import android.content.Context;
import android.os.Bundle;

import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class WelcomeFragment extends Fragment {
    //comment
    private static final String TAG = "WelcomeFragment";

    Button mButWelcomeLogin;

    OnWelcomeFragmentListener mListener;
    interface OnWelcomeFragmentListener{

        void OnWelcomeLoggon() ;

    }

    public WelcomeFragment() {
        // Required empty public constructor
    }

    public static WelcomeFragment newInstance(){
        return new WelcomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_welcome, container, false);

        mButWelcomeLogin = view.findViewById(R.id.butwelcomeLogin);

        mButWelcomeLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.OnWelcomeLoggon();
            }
        });
       return view;
    }


    @Override
    public void onAttach(Context context ){
        super.onAttach(context);
        if (context instanceof LoginFragment.OnLoginFragmentListener){
            mListener = (WelcomeFragment.OnWelcomeFragmentListener) context;

        }
        else {
            throw new ClassCastException(context.toString()
                    + "must implement OnLoginFragmentListener");
        }

    }




}
