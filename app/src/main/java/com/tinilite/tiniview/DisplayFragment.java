package com.tinilite.tiniview;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.tinilite.tiniview.databinding.FragmentDisplayBinding;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * A simple {@link Fragment} subclass.
 */
public class DisplayFragment extends Fragment {
    private static final String TAG = "DisplayFragment";
    private FragmentDisplayBinding binding;
    private String blankline;

    private String mServer;
    private String mPort;
    private String mUsername;
    private String mPassword;
    private boolean mLoggedon = false;

    //these are stored in a local variable to facilitate future changes.
    //they may not always be a fixed contant.
    private int mLineLength ;
    private int mLineCount ;


    OnDisplayFragmentListener mListener;

    interface OnDisplayFragmentListener {

        String[] OnDisplayGetCredentials();
    }

    public static DisplayFragment newInstance(){
        return new DisplayFragment();
    }

    public DisplayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //inflate the binding
        binding = FragmentDisplayBinding.inflate(inflater, container, false);

        // get the root view to return
        final View rootView = binding.getRoot();

        binding.butSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  Send(view);
            }
        });

        binding.butGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Get(view);
            }
        });

        mLineLength = getResources().getInteger(R.integer.lineLength)  ;
        mLineCount = getResources().getInteger(R.integer.lineCount) ;

        //get the login credentials
        String[] loginCredentails = mListener.OnDisplayGetCredentials();
        mServer = loginCredentails[0];
        mPort = loginCredentails[1];
        mUsername = loginCredentails[2];
        mPassword = loginCredentails[3];
        mLoggedon = loginCredentails[4].equals("true");

        //create a blank line in case user types less then 10
        StringBuilder bld = new StringBuilder();
        for (int j = 0; j < mLineLength; j++ )
            bld.append( " " );
        blankline = bld.toString();

        //get the list of signs
        String [] params = {            //params
                /*Constants.server*/ mServer,     //0
               /* "" + Constants.port*/ mPort,    //1
                /* Constants.user*/ mUsername,    //2
                /*Constants.pass*/ mPassword,     //3
                "dat/signlist.txt"                //4
        };
        new GetSignListTask().execute(params);

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDisplayFragmentListener) {
            mListener = (OnDisplayFragmentListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + "must implement OnDisplayFragmentListener");
        }
    }
    /*send data support functions*/

    protected static String[] splitLines(String p_str){
        Log.d(TAG, "splitLines: beginning........");
        int linecnt = 0;
        int i;
        for (i = 1; i < p_str.length() && p_str.charAt(i ) != '\0' ; i++){
            if ( p_str.charAt(i ) == '\n' || p_str.charAt(i ) == '\r' ){
                linecnt++;
                while (p_str.charAt(i ) == '\r' || p_str.charAt(i ) == '\n')
                    i++;
            }
        }
        i--;
        if ((p_str.charAt(i)  != '\r' &&
                p_str.charAt(i) != '\n' &&
                p_str.charAt(i) != '\0') ||
                (p_str.charAt(i)  == '\0' &&
                        (p_str.charAt(i - 1)  != '\r' &&
                                p_str.charAt(i -1 ) != '\n')
                )
        )
            linecnt++;

        String[] returnvalue = new String[linecnt];
        StringBuilder this_line_bld = new StringBuilder();
        int retn_index = 0;
        for ( i = 0; i < p_str.length() && p_str.charAt(i ) != '\0' ; i++) {
            if (p_str.charAt(i ) == '\r' || p_str.charAt(i ) == '\n') {
                returnvalue[retn_index]  = this_line_bld.toString();
                retn_index ++;
                this_line_bld = new StringBuilder();
                while ( i < p_str.length() && (p_str.charAt(i ) == '\r' || p_str.charAt(i ) == '\n') )
                    i++;
            }
            if (i < p_str.length() )
                this_line_bld.append( p_str.charAt(i ));
        }
        i--;
        if ((p_str.charAt(i)  != '\r' &&
                p_str.charAt(i) != '\n'&&
                p_str.charAt(i) != '\0') ||
                (p_str.charAt(i)  == '\0' &&
                        (p_str.charAt(i - 1)  != '\r' &&
                                p_str.charAt(i -1 ) != '\n')
                )
        )
            returnvalue[linecnt - 1] = this_line_bld.toString();
        Log.d(TAG, "splitLines: finished");
        return returnvalue;
    }

    protected static String join( String delim, String [] arr){
        Log.d(TAG, "join: beginning......");
        StringBuilder returnValueBld = new StringBuilder();
        for (int i = 0; i < arr.length; i ++){
            returnValueBld.append( arr[i]);
            if (i < arr.length - 1 ) {
                returnValueBld.append( delim);
            }
        }
        Log.d(TAG, "join: finished");
        return returnValueBld.toString();
    }

    protected static String padRight(String s, String pad, int n) {
        Log.d(TAG, "padRight: beginning.........");
        String returnValue =  String.format("%-" + n + "s", s);
        if (! pad.equals(" ")) {
            returnValue = returnValue.replace(" ", pad);
        }

        if (returnValue.length() > n ) {
            returnValue = returnValue.substring(0, n);
        }
        Log.d(TAG, "padRight: finished");
        return returnValue;
    }

    void formatText(){
        Log.d(TAG, "formatText: beginning......");
        String mlines =  binding.etData.getText().toString()  ;
        String [] splitData;
        String [] fixedsplitdata = new String[mLineCount];
        splitData = mlines.split("\n") ;
        for (int j = 0; j < splitData.length; j ++ ){
            if (splitData [j].length() > mLineLength)  {
                splitData [j] = splitData [j].substring(0, mLineLength);
            }
            else if (splitData [j].length() < mLineLength) {
                splitData [j] = padRight (splitData[j], " ", mLineLength);
            }
        }

        for (int j = 0; j < mLineCount; j ++ )
        {
            if (j < splitData.length)
                fixedsplitdata[j] = splitData[j];
            else
                fixedsplitdata[j] = blankline;
        }

        mlines = join( "\n", fixedsplitdata) ;
        mlines = mlines.toUpperCase();
        binding.etData.setText(mlines);
        Log.d(TAG, "formatText: finished");
    }

    private String generateFileContents(){
        Log.d(TAG, "generateFileContents: beginning");
        formatText();  //this function call makes lines uppercase and correct length. changes data in textbox
        String retval = binding.etData.getText().toString().replace("\n", "\r\n").toUpperCase() ;
        Log.d(TAG, "generateFileContents: finished");
        return retval;
    }

    public void Send(View view) {
        Log.d(TAG, "Send: beginning");
        String filename = "dat/" + binding.spinSignList.getSelectedItem().toString() + ".data";
        String fileContent = generateFileContents();
        String [] params = {            //params
                /*Constants.server*/ mServer,     //0
                /* "" + Constants.port*/ mPort,   //1
                /* Constants.user*/ mUsername,    //2
                /*Constants.pass*/ mPassword,     //3
                filename,                         //4
                fileContent                       //5
        };
        new SendDataTask().execute(params);
        Log.d(TAG, "Send: finished");
    }


    public class SendDataTask extends AsyncTask<String, Void, String[]> {
        private static final String TAG = "SendDataTask";

        @Override
        protected void onPreExecute() {
            Log.d(TAG, "onPreExecute: begin and end");
        }

        @Override
        protected String[] doInBackground(String... params) {
            Log.d(TAG, "doInBackground: beginning");
            String server = params[0];
            int port =   Integer.parseInt(params[1]);
            String user = params[2];
            String pass = params[3];
            String fileName = params[4];
            String fileContents = params[5];
            FTPClient ftpClient = new FTPClient();

            try {
                Log.d(TAG, "doInBackground:Starting try block.......... ");
                ftpClient.connect(server, port);
                ftpClient.login(user, pass);
                ftpClient.enterLocalPassiveMode();
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                OutputStream outputStream = ftpClient.storeFileStream(fileName);
                byte[] bytesIn = fileContents.getBytes() ;
                outputStream.write(bytesIn, 0, fileContents.length());
                outputStream.close();
                ftpClient.completePendingCommand();
                Log.d(TAG, "doInBackground: finish try block");
            } catch (IOException ex) {
                Log.e(TAG, "doInBackground: ERROR catched" );
                System.out.println("Error: " + ex.getMessage());
                ex.printStackTrace();
            }
            Log.d(TAG, "doInBackground: finished");
            return null;
        }

        @Override
        protected void onPostExecute(String[] passedData) {
            Log.d(TAG, "onPostExecute: begin and end");
        }
    }

    public void Get(View view) {
        Log.d(TAG, "Get: beginning........");
        String filename = "dat/" + binding.spinSignList.getSelectedItem().toString() + ".data";
        String [] params = {            //params
                /*Constants.server*/ mServer,     //0
                /*"" + Constants.port*/ mPort,              //1
                /* Constants.user*/ mUsername,    //2
                /*Constants.pass*/ mPassword,     //3
                filename                //4
        };
        new GetDataTask().execute(params);
        Log.d(TAG, "Get: finished");
    }

    public class GetDataTask extends AsyncTask<String, Void, String[]> {
        private static final String TAG = "GetDataTask";
        @Override
        protected void onPreExecute() {
            Log.d(TAG, "onPreExecute: begin and end");
        }

        @Override
        protected String[] doInBackground(String... params) {
            Log.d(TAG, "doInBackground: beginning.......");
            String server = params[0];
            int port =   Integer.parseInt(params[1]);
            String user = params[2];
            String pass = params[3];
            String fileName = params[4];


            StringBuilder fileContentBld = new StringBuilder();
            FTPClient ftpClient = new FTPClient();
            try {
                Log.d(TAG, "doInBackground: Starting try block.......");
                ftpClient.connect(server, port);
                ftpClient.login(user, pass);
                ftpClient.enterLocalPassiveMode();
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                InputStream inputStream  = ftpClient.retrieveFileStream(fileName);
                byte[] bytesIn = new byte[2048];
                while (( inputStream.read(bytesIn)) > 0) {
                    fileContentBld.append(new String( bytesIn)) ;
                }
                Log.d(TAG, "doInBackground: finish try block");
            } catch (IOException ex) {
                Log.e(TAG, "doInBackground: ERROR catched");
                System.out.println("Error: " + ex.getMessage());
                ex.printStackTrace();
            }
            Log.d(TAG, "doInBackground: finished");
            return new String[] { fileContentBld.toString() };
        }

        @Override
        protected void onPostExecute(String[] passedData) {
            Log.d(TAG, "onPostExecute: beginning........");
            if (passedData == null)  return;
            String fileContents = passedData[0];
            StringBuilder textDataBld = new StringBuilder();
            String [] splitData;
            splitData = fileContents.split("\r\n") ;

            int i;
            for (i=0;
                //030720 JP-CF check # lines actually read from file as well as expected #
                 i < mLineCount && i < splitData.length;
                 i++){

                if (! (textDataBld.length() == 0) ) {
                    textDataBld.append( "\n") ;
                }
                textDataBld.append(splitData[i]);
            }
            for (; i< mLineCount ; i++)
            {
                textDataBld.append(blankline);
            }
            binding.etData.setText(textDataBld.toString());
            formatText();
            Log.d(TAG, "onPostExecute: finished");
        }
    }

    public class GetSignListTask extends AsyncTask<String, Void, String[]> {

        private static final String TAG = "GetSignListTask";

        @Override
        protected void onPreExecute() {
            Log.d(TAG, "onPreExecute: begin and end");
        }

        @Override
        protected String[] doInBackground(String... params) {
            Log.d(TAG, "doInBackground: beginning.......");
            String server = params[0];
            int port =   Integer.parseInt(params[1]);
            String user = params[2];
            String pass = params[3];
            String fileName = params[4];
            StringBuilder fileContentBld = new StringBuilder();
            FTPClient ftpClient = new FTPClient();
            try {
                Log.d(TAG, "doInBackground: Starting try block........");
                ftpClient.connect(server, port);
                ftpClient.login(user, pass);
                ftpClient.enterLocalPassiveMode();
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                InputStream inputStream  = ftpClient.retrieveFileStream(fileName);
                byte[] bytesIn = new byte[2048];
                while (( inputStream.read(bytesIn)) > 0) {
                    fileContentBld.append( new String( bytesIn)) ;
                }
                Log.d(TAG, "doInBackground: finish try");
            } catch (IOException ex) {
                Log.e(TAG, "doInBackground: ERROR catched" );
                System.out.println("Error: " + ex.getMessage());
                ex.printStackTrace();
            }
            Log.d(TAG, "doInBackground: finished");
            return new String[] { fileContentBld.toString()};
        }

        @Override
        protected void onPostExecute(String[] passedData) {
            Log.d(TAG, "onPostExecute: beginning");
            if (passedData == null)  return;
            String fileContents = passedData[0];
            String [] SignList;
            SignList = splitLines( fileContents);
            ArrayAdapter<String> adapter = new ArrayAdapter<String> (getActivity(),
                    android.R.layout.simple_spinner_item, SignList);
            binding.spinSignList.setAdapter(adapter);
            Log.d(TAG, "onPostExecute: finished");
        }
    }
}
