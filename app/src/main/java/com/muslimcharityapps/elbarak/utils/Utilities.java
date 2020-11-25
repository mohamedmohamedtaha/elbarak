package com.muslimcharityapps.elbarak.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utilities implements Values {

    private String TAG = Utilities.class.getSimpleName();

    /**
     * Function to convert milliseconds time to Timer Format
     * Hours:Minutes:Seconds
     */
    public String milliSecondsToTimer(long milliseconds) {
        String finalTimerString = "";
        String secondsString = "";

        // Convert total duration into time
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        // Add hours if there
        if (hours > 0) {
            finalTimerString = hours + ":";
        }

        // Prepending 0 to seconds if it is one digit
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }

        finalTimerString = finalTimerString + minutes + ":" + secondsString;

        // return timer string
        return finalTimerString;
    }

    /**
     * Function to get Progress percentage
     *
     * @param currentDuration
     * @param totalDuration
     */
    public int getProgressPercentage(long currentDuration, long totalDuration) {
        Double percentage = (double) 0;

        long currentSeconds = (int) (currentDuration / 1000);
        long totalSeconds = (int) (totalDuration / 1000);

        // calculating percentage
        percentage = (((double) currentSeconds) / totalSeconds) * 100;

        // return percentage
        return percentage.intValue();
    }

    /**
     * Function to change progress to timer
     *
     * @param progress      -
     * @param totalDuration returns current duration in milliseconds
     */
    public int progressToTimer(int progress, int totalDuration) {
        int currentDuration = 0;
        totalDuration = (int) (totalDuration / 1000);
        currentDuration = (int) ((((double) progress) / 100) * totalDuration);

        // return current duration in milliseconds
        return currentDuration * 1000;
    }

    public File getOutputMediaFile(String fileName) {

        /*
        GETTING EXTERNAL SDCARD LOCATION
         */

        File mediaStorageDir = new File(
                Environment.getExternalStorageDirectory().getPath(),
                IMAGE_DIRECTORY_NAME);
        /*
        CREATE THE STORAGE DIRECTORY IF IT DOES NOT EXIST
          */

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(TAG, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        /*
        CREATING A MEDIA FILE NAME
          */

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());

        File mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                fileName);

        return mediaFile;
    }

    public boolean isFileExists(String fileName/*, int fileLength*/) {
        boolean isFileExists = false;

        /*
        GETTING EXTERNAL SDCARD LOCATION
         */
        File file = new File(fileName);

        /*
        CHECKING IF FILE EXISTS OR NOT
          */

        if (file.exists()) {
            isFileExists = true;
            /*if (file.length() >= fileLength) {
                isFileExists = true;
            }*/
        }

        return isFileExists;
    }
}
