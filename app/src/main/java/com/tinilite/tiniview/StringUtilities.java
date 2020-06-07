package com.tinilite.tiniview;

import android.util.Log;

public class StringUtilities {
    public static String padRight(String s, String pad, int n) {

        String returnValue = String.format("%-" + n + "s", s);
        if (!pad.equals(" ")) {
            returnValue = returnValue.replace(" ", pad);
        }

        if (returnValue.length() > n) {
            returnValue = returnValue.substring(0, n);
        }

        return returnValue;
    }
    public static String join(String delim, String[] arr) {

        StringBuilder returnValueBld = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            returnValueBld.append(arr[i]);
            if (i < arr.length - 1) {
                returnValueBld.append(delim);
            }
        }

        return returnValueBld.toString();
    }
}
