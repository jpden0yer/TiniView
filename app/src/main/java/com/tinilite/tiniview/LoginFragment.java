package com.tinilite.tiniview;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.tinilite.tiniview.databinding.FragmentLoginBinding;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

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
        void OnSetCredentials(
                String server,
                String port,
                String Username,
                String Password,
                boolean loggedon);

        String[] OnGetCredentials();
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


        String[] loginCredentails = mListener.OnGetCredentials();
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
                                                          mListener.OnSetCredentials(mServer, mPort, mUsername, mPassword, mLoggedon);
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
            int failLocation = 0;
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
                Log.d(TAG, "doInBackground: ERROR port not integer");
                return new String[]{server, params[1], user, pass, "false","port not integer" };

            }

            failLocation = 1;
            boolean ok;
            try {
                failLocation = 2;
                Log.d(TAG, "doInBackground: Starting try block........");
                failLocation = 3;
                ftpClient.connect(server,port );
                failLocation = 4;
                ok = ftpClient.isConnected();
                failLocation = 5;
               if (ok) {
                   ok = ftpClient.login(user, pass);
                   failLocation = 6;
               }
               if (ok) {
                   ftpClient.enterLocalPassiveMode();
                   failLocation = 7;
                   ok = ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                   failLocation = 8;
               }


               if (ok) {
                   failLocation = 9;
                   InputStream inputStream = ftpClient.retrieveFileStream(fileName);
                   failLocation = 10;
                   if (inputStream == null)
                       ok = false;

                   failLocation = 11;
               }

                Log.d(TAG, "doInBackground: finish try");
            } catch (Exception ex) {
                Log.e(TAG, "doInBackground: catched - login fail location " + failLocation);

                return new String[]{server, params[1], user, pass, "false", "catchloc " + failLocation };
            }
            Log.d(TAG, "doInBackground: finished");
            if (ok)
               return new String[]{server, params[1], user, pass, "true", "" };
            else return new String[]{server, params[1], user, pass, "false", "catchloc " + failLocation };
        }

        @Override
        protected void onPostExecute(String[] passedData) {
            Log.d(TAG, "onPostExecute: beginning");
            String server = passedData[0];
            String port = passedData[1];
            String user = passedData[2];
            String pass = passedData[3];
            String loggeon = passedData[4];
            String failmessage = passedData[5];

            if (loggeon.equals("true")) {
                Toast.makeText(getContext(), "Login Succeed........", Toast.LENGTH_SHORT).show();
                mListener.OnSetCredentials(server, port, user, pass, true);

            }
            else
                Toast.makeText(getContext(), "Login Failed: " + failmessage , Toast.LENGTH_LONG).show();


        }
    }
}
