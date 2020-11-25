package com.muslimcharityapps.elbarak.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by ssb on 11/3/17.
 */

public interface Values {

    /*
    RETRO TIMEOUTS
     */
    int CONNECTION_TIMEOUT = 20;
    int READ_TIMEOUT = 20;
    int WRITE_TIMEOUT = 20;

    /*
    RETRO URL
     */

    String WS_SERVER_URL = "http://api.openweathermap.org/data/2.5/";

    /*
    WEATHER API
     */

    /*String KEY_WEATHER_API = "";
    String URL_WEATHER_IMAGE = "http://openweathermap.org/img/w/";
    String UNIT_WEATHER_TEMPERATURE = "imperial";
    String SYMBOL_WEATHER_TEMPERATURE = (char) 0x00B0 + "F";*/

    /*
    SHARED PREFERENCE
     */

    String SP_SONG_STATE = "songstate";
    String SP_LANGUAGE = "language";
    String SP_SONG_PROGRESS = "songprogress";
    String SP_SONG_INDEX = "songindex";

    String SP_FAVORITE_SONGS = "favorite_songs";
    //String SP_IS_NOTIFICATION_SCHEDULED = "is_notification_scheduled";
    //String SP_IS_NOTIFICATION_ENABLED = "is_notification_enabled";

    /*
    BROADCAST RECEIVER
     */
    String RECEIVER_EVENTS_FROM_NOTIFICATION = "com.muslimcharityapps.elbarak.PROCESS_RESPONSE";

    /*
    NOTIFICATION FLAGS
     */

    /*String ACTION_NEXT = "action_next";
    String ACTION_PREVIOUS = "action_previous";
    String ACTION_PLAY_PAUSE = "action_play_pause";*/

    /*
    INTENT
     */

    String INTENT_ACTION = "intent_action";
    String INTENT_DATA = "intent_data";

    /*
    DATA
     */

    String TAG_EMAIL = "email";

    /*
    FILE STORAGE
     */

    String IMAGE_DIRECTORY_NAME = "/AndroiSaJa/ElbarakSaJa/";
    String DOWNLOAD_FILE_PREFIX = "https://server13.mp3quran.net/braak/";
    String MEDIA_PATH = Environment.getExternalStorageDirectory().getPath() + "/AndroiSaJa/ElbarakSaJa/";

    /*
    REQUEST
     */

    int REQUEST_CHANGE_LANGUAGE = 99;
    int PERMISSION_REQUEST_CODE = 9;

    /*
    RX DOWNLOAD
     */

    int DOWNLOAD_COMPLETE_PERCENT = 100;
    int MAX_COUNT_OF_SIMULTANEOUS_DOWNLOADS = 5;
    int INVLALID_ID = -1;
    String SHARED_PREFERENCES = "shared_preferences";
    String DOWNLOAD_PREFIX = "download";
    String PERCENT_PREFIX = "percent";

    /*
    ALARM
     */

    //long ALARM_DELAY = AlarmManager.INTERVAL_DAY * 5;

    /*
    MAP KEYS
     */

    String KEY_SONG_TITLE = "songTitle";
    String KEY_SONG_PATH = "songPath";
    String KEY_SONG_POSITION = "songPosition";

    /*
    RX MESSAGE
     */

    String MESSAGE_CONNECTIVITY = "message_connectivity";
    //String MESSAGE_SONG_DATA = "message_song_data";
}
