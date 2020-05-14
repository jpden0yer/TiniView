package com.tinilite.tiniview;

import android.content.Context;
import android.os.Bundle;

import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tinilite.tiniview.databinding.FragmentLoginBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    private static final String TAG = "LoginFragment";
    //jp042620 warning generatated because binding currently only used
    // in onCreate. intelesense suggest could be local variable. However
    //this is inherantly likely to be used in other fuctions
    private FragmentLoginBinding binding;
    //050620 create interface to sendLogininfo to MainActivity
    private String mServer;
    private String mUsername;
    private String mPassword;
    private boolean mLoggedon = false;

    public LoginFragment() {
        // Required empty public constructor
    }


    OnLoginFragmentListener mListener;

    interface OnLoginFragmentListener {
        void OnLoginSetCredentials(
                String server,
                String Username,
                String Password,
                boolean loggedon);

        String[] OnLoginGetCredentials();
    }


    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);

        // get the rooot view
        final View rootView = binding.getRoot();


        String[] loginCredentails = mListener.OnLoginGetCredentials();
        mServer = loginCredentails[0];
        binding.etServer.setText(loginCredentails[0]);
        mUsername = loginCredentails[1];
        binding.etUsername.setText(loginCredentails[1]);
        mPassword = loginCredentails[2];
        binding.etPassword.setText(loginCredentails[2]);

        mLoggedon = loginCredentails[3].equals("true");

        binding.butLoginCancel.setOnClickListener(new View.OnClickListener() {
                                                      @Override
                                                      public void onClick(View view) {
                                                          //String holder = "" ;
                                                          mListener.OnLoginSetCredentials(mServer, mUsername, mPassword, mLoggedon);
                                                      }
                                                  }

        );

        binding.butLogin.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    String server = binding.etServer.getText().toString();
                                                    String username = binding.etUsername.getText().toString();
                                                    String password = binding.etPassword.getText().toString();
                                                    mListener.OnLoginSetCredentials(
                                                            server,
                                                            username,
                                                            password,
                                                            true
                                                    );

                                                    //mListener.OnLogingDisplayWelcomeFragment();
                                                }
                                            }

        );
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnLoginFragmentListener) {
            mListener = (OnLoginFragmentListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + "must implement OnLoginFragmentListener");
        }
    }

}
