package com.tinilite.tiniview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    /*controls*/
    private EditText mTextData;


    private String blankline;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextData = findViewById(R.id.etData);
        StringBuilder bld = new StringBuilder();
        for (int j = 0; j < constants.lineLength; j++ )
            bld.append( " " );

        blankline = bld.toString();
    }

   /*send data support functions*/
   protected static String join( String delim, String [] arr){

       String returnValue = "";
       for (int i = 0; i < arr.length; i ++){
           returnValue = returnValue + arr[i];
           if (i < arr.length - 1 ) {
               returnValue = returnValue + delim;
           }

       }


       return returnValue;
   }

    protected static String padRight(String s, String pad, int n) {
        String returnValue;

        returnValue =  String.format("%-" + n + "s", s);

        if (pad != " ") {
            returnValue = returnValue.replace(" ", pad);
        }

        if (returnValue.length() > n ) {
            returnValue = returnValue.substring(0, n);
        }
        return returnValue;
    }
    protected static String padLeft(String s, String pad, int n) {

        String returnValue;
        returnValue =  String.format("%" + n + "s", s);

        if (pad != " ") {
            returnValue = returnValue.replace(" ", pad);
        }

        if (returnValue.length() > n ) {
            returnValue = returnValue.substring(returnValue.length() - n);
        }
        return returnValue;
    }

    void formatText(){
       String mlines = mTextData.getText().toString()  ;
       String [] splitData;
       String [] fixedsplitdata = new String[constants.lineCount];

       splitData = mlines.split("\n") ;

       for (int j = 0; j < splitData.length; j ++ ){
           if (splitData [j].length() > constants.lineLength)  {
               splitData [j] = splitData [j].substring(0, constants.lineLength);
           }
           else if (splitData [j].length() < constants.lineLength) {
               splitData [j] = padRight (splitData[j], " ", constants.lineLength);
           }

       }

        for (int j = 0; j < constants.lineCount; j ++ )
        {
            if (j < splitData.length)
                fixedsplitdata[j] = splitData[j];
            else
                fixedsplitdata[j] = blankline;

        }

       mlines = join( "\n", fixedsplitdata) ;
       mlines = mlines.toUpperCase();

       mTextData.setText(mlines);
   }

    private String generateFileContents(){

        formatText();  //this function call makes lines uppercase and correct length. changes data in textbox
        String retval = mTextData.getText().toString().replace("\n", "\r\n").toUpperCase() ;/*+
                "\r\n[trick coding version 2.2]\r\n" +
                "020105010001FF050100" +
                padLeft(Integer.toHexString(constants.lineCount), "0", 2 ) +
                padLeft(Integer.toHexString(constants.speed * 10), "0", 2 ) ;
*/

        return retval;

    }

    public void Send(View view) {


        String fileContent = generateFileContents();

        String [] params = {            //params
                constants.server,                 //0
                "" + constants.port,              //1
                constants.user,                   //2
                constants.pass,                   //3
                constants.fileName,               //4
                fileContent             //5

        };
        new SendDataTask().execute(params);

    }


    public class SendDataTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String[] doInBackground(String... params) {

            Log.e("FTP-jp0", "begin doInBackground");


            String server = params[0];
            int port =   Integer.parseInt(params[1]);
            String user = params[2];
            String pass = params[3];
            String fileName = params[4];
            String fileContents = params[5];


            FTPClient ftpClient = new FTPClient();


            try {

                ftpClient.connect(server, port);

                ftpClient.login(user, pass);


                ftpClient.enterLocalPassiveMode();

                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

                OutputStream outputStream = ftpClient.storeFileStream(fileName);

                byte[] bytesIn = fileContents.getBytes() ;

                outputStream.write(bytesIn, 0, fileContents.length());

                outputStream.close();

                boolean completed = ftpClient.completePendingCommand();


                Log.e("FTP-jp0", fileContents);
                //"end doInBackground " + completed  );
            } catch (IOException ex) {



                Log.e("FTP-jp0","catch block after location " );
                System.out.println("Error: " + ex.getMessage());
                ex.printStackTrace();


            }



            return null;

        }

        @Override
        protected void onPostExecute(String[] passedData) {
        }

    }

    public void Get(View view) {

        String [] params = {            //params
                constants.server,                 //0
                "" + constants.port,              //1
                constants.user,                   //2
                constants.pass,                   //3
                constants.fileName                //4


        };
        new GetDataTask().execute(params);

    }

    public class GetDataTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String[] doInBackground(String... params) {

            Log.e("FTP-jp0", "begin doInBackground");


            String server = params[0];
            int port =   Integer.parseInt(params[1]);
            String user = params[2];
            String pass = params[3];
            String fileName = params[4];
            String fileContents = "";

            int bytesRead;

            FTPClient ftpClient = new FTPClient();


            try {

                ftpClient.connect(server, port);

                ftpClient.login(user, pass);


                ftpClient.enterLocalPassiveMode();

                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

                InputStream inputStream  = ftpClient.retrieveFileStream(fileName);



                byte[] bytesIn = new byte[2048];

                while ((bytesRead = inputStream.read(bytesIn)) > 0) {

                    fileContents = fileContents + new String( bytesIn) ;
                }

            } catch (IOException ex) {



                Log.e("FTP-jp0","catch block after location " );
                System.out.println("Error: " + ex.getMessage());
                ex.printStackTrace();


            }



            return new String[] { fileContents};

        }

        @Override
        protected void onPostExecute(String[] passedData) {
            if (passedData == null)  return;

            String fileContents = passedData[0];

            String textdata = "";

            String [] splitData;
            splitData = fileContents.split("\r\n") ;

            int speed;
            int i;

            for (i=0;
                 //030720 JP-CF check # lines actually read from file as well as expected #
                 i < constants.lineCount && i < splitData.length;
                 i++){

                /*030720 JP-CF this looked for 'junk' at end of old file format
                if (splitData[i].equals("[TRICK CODING VERSION 2.2]" )) break;
                */


                if (! textdata.equals("") ) {

                    textdata = textdata + "\n" ;

                    /*030720 JP-CF this changes linelength bassed on filecontents
                         the way written  uses length of last line
                    constants.lineLength = splitData[i].length();

                     */
                }


                textdata = textdata + splitData[i];

            }
            for (; i<constants.lineCount ; i++)
            {
                textdata = textdata + blankline;
            }

            mTextData.setText(textdata);
            formatText();

        }

    }

}
