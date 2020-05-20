package com.tinilite.tiniview;

//import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.tinilite.tiniview.databinding.FragmentLoginBinding;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.IOException;
import java.io.InputStream;


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
    private String mServer, mPort, mUsername, mPassword;
    private boolean mLoggedon = false;

    public LoginFragment() {
        // Required empty public constructor
    }


    OnLoginFragmentListener mListener;

    interface OnLoginFragmentListener {
        void OnLoginSetCredentials(
                String server,
                String port,
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
        mPort = loginCredentails[1];
        binding.etPort.setText(mPort);

        mUsername = loginCredentails[2];
        binding.etUsername.setText(loginCredentails[2]);
        mPassword = loginCredentails[3];
        binding.etPassword.setText(loginCredentails[3]);

        mLoggedon = loginCredentails[4].equals("true");

        binding.butLoginCancel.setOnClickListener(new View.OnClickListener() {
                                                      @Override
                                                      public void onClick(View view) {
                                                          //String holder = "" ;
                                                          mListener.OnLoginSetCredentials(mServer, mPort, mUsername, mPassword, mLoggedon);
                                                      }
                                                  }

        );

        binding.butLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String server = binding.etServer.getText().toString();
                    String port = binding.etPort.getText().toString();
                    String username = binding.etUsername.getText().toString();
                    String password = binding.etPassword.getText().toString();

                    String[] params = {           //params
                            server,               //0
                            port,                 //1
                            username,             //2
                            password,             //3
                            "dat/signlist.txt"    //4
                    };
                    new LoginTask().execute(params);

                    /*mListener.OnLoginSetCredentials(
                            server,
                            port,
                            username,
                            password,
                            true
                    );
    */
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

    public class LoginTask extends AsyncTask<String, Void, String[]> {

        private static final String TAG = "GetSignListTask";

        @Override
        protected void onPreExecute() {
            Log.d(TAG, "onPreExecute: begin and end");
        }

        @Override
        protected String[] doInBackground(String... params) {
            Log.d(TAG, "doInBackground: beginning.......");
            String server = params[0];
            int port;

            String user = params[2];
            String pass = params[3];
            String fileName = params[4];
            StringBuilder fileContentBld = new StringBuilder();
            FTPClient ftpClient = new FTPClient();

            try {

                port = Integer.parseInt(params[1]);
            }
            catch (Exception ex){
                return new String[]{server, params[1], user, pass, "false" };

            }

            boolean ok;
            try {
                Log.d(TAG, "doInBackground: Starting try block........");
                ftpClient.connect(server,port );

                ok = ftpClient.isConnected();
               if (ok)
                 ok =  ftpClient.login(user, pass);
               if (ok) {
                   ftpClient.enterLocalPassiveMode();
                   ok = ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
               }


               if (ok) {
                   InputStream inputStream = ftpClient.retrieveFileStream(fileName);
                   if (inputStream == null)
                       ok = false;

               }

                Log.d(TAG, "doInBackground: finish try");
            } catch (Exception ex) {
                Log.e(TAG, "doInBackground: catched - login fale");

                return new String[]{server, params[1], user, pass, "false" };
            }
            Log.d(TAG, "doInBackground: finished");
            if (ok)
               return new String[]{server, params[1], user, pass, "true" };
            else return new String[]{server, params[1], user, pass, "false" };
        }

        @Override
        protected void onPostExecute(String[] passedData) {
            Log.d(TAG, "onPostExecute: beginning");
            String server = passedData[0];
            String port = passedData[1];
            String user = passedData[2];
            String pass = passedData[3];
            String loggeon = passedData[4];

            if (loggeon.equals("true"))
                mListener.OnLoginSetCredentials(server,port,user,pass, true);
            else
                Toast.makeText(getContext(),"login failed", Toast.LENGTH_LONG).show();


        }
    }
}
