package com.svoemestodev.fdmcostcalculator;

import java.util.Locale;

public class Utils {

    public static String convertSecondsToHHMM(int seconds) {
        String result = "";
        int hours = seconds / (60*60);
        int minutes = (seconds - hours*60*60) / 60;
        result = String.format(Locale.getDefault(), "%01d:%02d", hours, minutes);
        return result;
    }

}
