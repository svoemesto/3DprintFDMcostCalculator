package com.svoemestodev.fdmcostcalculator;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.util.Locale;

public class Utils {

    public static String convertSecondsToHHMM(int seconds) {
        String result = "";
        int hours = seconds / (60*60);
        int minutes = (seconds - hours*60*60) / 60;
        result = String.format(Locale.getDefault(), "%01d:%02d", hours, minutes);
        return result;
    }

    public static String convertFloatToStringFormatter2digit(float number) {
        String pattern = "###,##0.00";
        DecimalFormat myFormatter = new DecimalFormat(pattern);
        return myFormatter.format(number);
    }

    public static String convertFloatToStringFormatter3digit(float number) {
        String pattern = "###,##0.000";
        DecimalFormat myFormatter = new DecimalFormat(pattern);
        return myFormatter.format(number);
    }

    public static String convertIntToStringFormatter(int number) {
        String pattern = "###,##0";
        DecimalFormat myFormatter = new DecimalFormat(pattern);
        return myFormatter.format(number);
    }


}
