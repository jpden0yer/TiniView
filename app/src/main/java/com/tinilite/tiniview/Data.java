package com.tinilite.tiniview;

import static com.tinilite.tiniview.StringUtilities.join;
import static com.tinilite.tiniview.StringUtilities.padRight;
import static com.tinilite.tiniview.Timer.tmr_init;
import static com.tinilite.tiniview.Timer.tmr_poll;
import static com.tinilite.tiniview.spi.displayLine;
public class Data {
    static String mRawData, mFixedData, blankline,mCurrentLine;
    static private int selected_line = 0, mlineLength, mlineCount;

    //these only used on android
    static String mPassedFile = "";
    static boolean mDataChanged = false;

    //this 2 methods only on android
    public static void passFileData(String pPassedFile){
        if (! mPassedFile.equals(pPassedFile))
        {
            mPassedFile = pPassedFile;
            mDataChanged = true;
        }
    }
    public static String getmCurrentLine(){
        return mCurrentLine;
    }


    //These  3 methods have same signature but different
    // implementation on pi and android
    static  boolean dataChanged(){

        return mDataChanged;
    }
    static  String loadFile(){
          return mPassedFile;
    }
    static  void displayLine(String pCurrnetLine){
        mCurrentLine = pCurrnetLine;
        spi.displayLine(mCurrentLine);
        //Spi.displayLine(mCurrentLine);
        //on the pi more occurs here.
    }

    //below methods expect to be implimented the same on android and pi
    static void formatText() {

        String[] splitData;
        String[] fixedsplitdata = new String[mlineCount];
        splitData = mRawData.split("\n");
        for (int j = 0; j < splitData.length; j++) {
            if (splitData[j].length() > mlineLength) {
                splitData[j] = splitData[j].substring(0, mlineLength);
            } else if (splitData[j].length() < mlineLength) {
                splitData[j] = padRight(splitData[j], " ", mlineLength);
            }
        }

        for (int j = 0; j < mlineCount; j++) {
            if (j < splitData.length)
                fixedsplitdata[j] = splitData[j];
            else
                fixedsplitdata[j] = blankline;
        }

        mFixedData = join("\n", fixedsplitdata);
        mFixedData = mFixedData.toUpperCase();
    }
    static void reinit() {
        //on pi we would read file here
        mRawData = loadFile();
        formatText();
    }
   public static void init(long interval, int pLinelength, int pLinecount   ) {
        reinit();
        mlineLength = pLinelength;
        mlineCount = pLinecount;
        tmr_init(interval);
        blankline = "";
        for (int i = 0; i < mlineLength; i++)
            blankline = blankline + " ";
    }

   public static void data_poll(){
        if (dataChanged() )
             reinit();

        //look timer.c to see tmr_poll function
        if (tmr_poll() == true )  {

            selected_line  = selected_line + 1;
            if (selected_line >= mlineCount )
                selected_line = 0;
            displayLine(mFixedData.substring((mlineLength + 1)*selected_line, (mlineLength + 1)*(selected_line+ 1) - 1));
        }
    }
}
