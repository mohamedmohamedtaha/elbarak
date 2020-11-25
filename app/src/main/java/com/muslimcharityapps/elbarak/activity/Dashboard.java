package com.muslimcharityapps.elbarak.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RemoteViews;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.google.ads.consent.ConsentForm;
import com.google.ads.consent.ConsentFormListener;
import com.google.ads.consent.ConsentInfoUpdateListener;
import com.google.ads.consent.ConsentInformation;
import com.google.ads.consent.ConsentStatus;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.muslimcharityapps.elbarak.HelperClass;
import com.muslimcharityapps.elbarak.R;
import com.muslimcharityapps.elbarak.SSBApp;
import com.muslimcharityapps.elbarak.checkConnection.NetworkConnection;
import com.muslimcharityapps.elbarak.checkConnection.NoInternetConnection;
import com.muslimcharityapps.elbarak.receiver.ConnectivityReceiver;
import com.muslimcharityapps.elbarak.receiver.DownloadReceiver;
import com.muslimcharityapps.elbarak.receiver.NoInternetReceiver;
import com.muslimcharityapps.elbarak.retro.RetroWS;
import com.muslimcharityapps.elbarak.utils.ConnectionDetector;
import com.muslimcharityapps.elbarak.utils.RxBus;
import com.muslimcharityapps.elbarak.utils.Utilities;
import com.muslimcharityapps.elbarak.utils.Values;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import okhttp3.ResponseBody;
import retrofit2.Response;

//import com.crashlytics.android.Crashlytics;
//import io.fabric.sdk.android.Fabric;

/**
 * Created by ssb on 19/3/17.
 */

public class Dashboard extends AppCompatActivity implements Values,
        AudioManager.OnAudioFocusChangeListener, MediaPlayer.OnCompletionListener,
        SeekBar.OnSeekBarChangeListener, MediaPlayer.OnErrorListener,
        NavigationView.OnNavigationItemSelectedListener {

    /*
    INJECTING OBJECTS
     */

    @Inject
    RetroWS retroWS;

    @Inject
    SharedPreferences appStateSharedPreference;

    @Inject
    SharedPreferences.Editor appStateEditor;

    @Inject
    ConnectionDetector connectionDetector;

    @Inject
    MediaPlayer mediaPlayer;

    @Inject
    AudioManager audioManager;

    @Inject
    Utilities utils;

    /*
    BINDING VIEWS
     */

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.songTitle)
    TextView songTitleLabel;

    @BindView(R.id.btnPlay)
    ImageButton btnPlay;

    @BindView(R.id.songTotalDurationLabel)
    TextView songTotalDurationLabel;

    @BindView(R.id.songCurrentDurationLabel)
    TextView songCurrentDurationLabel;

    @BindView(R.id.songProgressBar)
    SeekBar songProgressBar;

    @BindView(R.id.progressBarSpinner)
    ProgressBar spinner;

    /*@BindView(R.id.adView)
    AdView adView;*/

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.seekBarVolume)
    SeekBar volumeSeekbar;

    @BindView(R.id.btnPrevious)
    ImageButton btnPrevious;

    @BindView(R.id.btnRepeat)
    ImageButton btnRepeat;

    @BindView(R.id.btnShuffle)
    ImageButton btnShuffle;

    @BindView(R.id.btnNext)
    ImageButton btnNext;

    @BindView(R.id.ivWeatherCondition)
    ImageView ivWeatherCondition;

    @BindView(R.id.tvWeatherTemperature)
    TextView tvWeatherTemperature;

    @BindView(R.id.tvWeatherLocation)
    TextView tvWeatherLocation;

    @BindView(R.id.tvTime)
    TextView tvTime;

    @BindView(R.id.tvAmPm)
    TextView tvAmPm;

    @BindView(R.id.tvDate)
    TextView tvDate;

    @BindView(R.id.btnWeatherByZipCode)
    Button btnWeatherByZipCode;

    @BindView(R.id.btnFavorite)
    ImageButton btnFavorite;

    /*
    DECLARING VARIABLES
     */

    private FirebaseAnalytics mFirebaseAnalytics;
    public static final String BROADCAST_NOT_CONNECTION = "com.muslimcharityapps.elbarak.activity.no.connection";
    public static final String BROADCAST_NOT_INTERNET = "com.muslimcharityapps.elbarak.activity.no.internet";

    private ConnectivityReceiver connectivityReceiver = null;
    private NoInternetReceiver noInternetReceiver = null;
    private DownloadReceiver downloadReceiver = null;

    private long downloadReference = 0;

    //private MyWebRequestReceiver receiver;
    private RemoteViews remoteViews;
    private Notification notification;
    private NotificationManager notificationManager;
    private int currentSongIndex = 0;
    private int index;
    private String fileUrl;
    private String songTitle;
    private int Volume = 40;
    private ArrayList<HashMap<String, String>> songsList;
  //  private ArrayList<HashMap<String, String>> allSongsList;
    private DisposableSubscriber<Long> songProgressObserver;
    //private DisposableObserver<WeatherBean> weatherObserver;
    private DisposableSubscriber<Long> timeAndDateSubscriber;
    private boolean isShuffle;
    private boolean isRepeat;
    private String TAG = Dashboard.class.getSimpleName();
    private Context context;
    private NiftyDialogBuilder dialogBuilder;
    //private LocationRequest locationRequest;
//    private GoogleApiClient googleApiClient;
    //  private String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    private String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private Set<String> favoriteSongSet;
    private int progress_bar_type = 0;
    private ProgressDialog mProgressDialog;
    private final int DIALOG_DOWNLOAD_PROGRESS = 0;
    private boolean isToShowInternetDialog = true;
    private Boolean exitApp = false;
    Uri music_uri;
    private long Music_DownloadId;
    private DownloadManager downloadManager;
    private File media_path = null;
    private File mediaStorageDir;
    private String title_for_download_list;
    private String path = null;

    ConsentForm form;
    // This is an ad unit ID for a test ad. Replace with your own banner ad unit ID.
    private static final String AD_UNIT_ID = "ca-app-pub-3940256099942544/6300978111";
    private FrameLayout adContainerView;
    private AdView adView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Button crashButton = new Button(this);
        crashButton.setText("Crash!");
        crashButton.setOnClickListener(view -> {
            throw new RuntimeException("Test Crash"); // Force a crash
        });

        addContentView(crashButton, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));*/

        /*MobileAds.initialize(this, initializationStatus -> {
        });
        adContainerView = findViewById(R.id.ad_view_container);
        // Step 1 - Create an AdView and set the ad unit ID on it.
        adView = new AdView(this);
        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
        adContainerView.addView(adView);
        loadBanner();*/

        // Initialize the Mobile Ads SDK.
        MobileAds.initialize(this, initializationStatus -> {
        });

        // Set your test devices. Check your logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("ABCDEF012345"))
        // to get test ads on this device."
        MobileAds.setRequestConfiguration(
                new RequestConfiguration.Builder().build());

        adContainerView = findViewById(R.id.ad_view_container);

        // Since we're loading the banner based on the adContainerView size, we need to wait until this
        // view is laid out before we can get the width.
        adContainerView.post(new Runnable() {
            @Override
            public void run() {
                loadBanner();
            }
        });

        checkForConsent();
        connectivityReceiver = new ConnectivityReceiver();
        noInternetReceiver = new NoInternetReceiver();
        /*
        INITIALIZING
         */

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);


        context = Dashboard.this;
        RxBus.get().register(this);

        /*
        INJECTING BUTTER KNIFE
         */

        ButterKnife.bind(this);

        /*
        INJECTING DAGGER
         */

        SSBApp.getApp().getRetroComponent().inject(this);

        /*
        CHECK FOR PERMISSION
        */

        //checkForPermission();

        /*
        ENABLING DEFAULT NOTIFICATION FOR FIRST TIME
         */

        //setNotificationEnableByDefault();

        /*
        INITIALIZING APPLICATION
         */

        initializeApplication();

        /*
        SCHEDULE NOTIFICATION
         */
        /*if (!appStateSharedPreference.getBoolean(SP_IS_NOTIFICATION_SCHEDULED, false))
            scheduleNotification();*/

        /*
        RETRIEVING FAVORITE SONGS
         */

        favoriteSongSet = appStateSharedPreference.getStringSet(SP_FAVORITE_SONGS, new HashSet<String>());

        /*
        STARTING TIME AND DATE UPDATES
         */

        timeAndDateSubscriber = getTimeAndDateObserver();
        getTimeAndDateObservable().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(timeAndDateSubscriber);

        /*///////////////////////GDPR Badr////////////////////////////
        ConsentInformation consentInformation = ConsentInformation.getInstance(Dashboard.this);
        ConsentInformation.getInstance(this).addTestDevice("89A80718462A5501C7F3F2EA31202B82");
        ConsentInformation.getInstance(Dashboard.this).setDebugGeography(DebugGeography.DEBUG_GEOGRAPHY_EEA);
        String[] publisherIds = {"pub-9034075681938673"};
        consentInformation.requestConsentInfoUpdate(publisherIds, new ConsentInfoUpdateListener() {
            @Override
            public void onConsentInfoUpdated(ConsentStatus consentStatus) {
// User's consent status successfully updated.
                Log.d(TAG,"onConsentInfoUpdated");
                switch (consentStatus){
                    case PERSONALIZED:
                        Log.d(TAG,"PERSONALIZED");
                        ConsentInformation.getInstance(Dashboard.this)
                                .setConsentStatus(ConsentStatus.PERSONALIZED);
                        break;
                    case NON_PERSONALIZED:
                        Log.d(TAG,"NON_PERSONALIZED");
                        ConsentInformation.getInstance(Dashboard.this)
                                .setConsentStatus(ConsentStatus.NON_PERSONALIZED);
                        break;

                    case UNKNOWN:
                        Log.d(TAG,"UNKNOWN");
                        if
                        (ConsentInformation.getInstance(Dashboard.this).isRequestLocationInEeaOrUnknown
                                ()){
                            URL privacyUrl = null;
                            try {

                                privacyUrl = new URL("https://www.muslimcharityapps.ml/p/privacy-policy.html");
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
// Handle error.
                            }
                            form = new ConsentForm.Builder(Dashboard.this,privacyUrl)
                                    .withListener(new ConsentFormListener() {
                                        @Override
                                        public void onConsentFormLoaded() {
// Consent form loaded successfully.
                                            Log.d(TAG,"onConsentFormLoaded");
                                            showform();
                                        }
                                        @Override
                                        public void onConsentFormOpened() {
// Consent form was displayed.
                                            Log.d(TAG,"onConsentFormOpened");
                                        }
                                        @Override
                                        public void onConsentFormClosed(
                                                ConsentStatus consentStatus, Boolean
                                                userPrefersAdFree) {
// Consent form was closed.
                                            Log.d(TAG,"onConsentFormClosed");
                                        }
                                        @Override
                                        public void onConsentFormError(String
                                                                               errorDescription) {
// Consent form error.
                                            Log.d(TAG,"onConsentFormError");
                                            Log.d(TAG,errorDescription);
                                        }
                                    })
                                    .withPersonalizedAdsOption()
                                    .withNonPersonalizedAdsOption()
                                    .build();
                            form.load();
                        } else {
                            Log.d(TAG,"PERSONALIZED else");
                            ConsentInformation.getInstance(Dashboard.this)
                                    .setConsentStatus(ConsentStatus.PERSONALIZED);
                        }
                        break;

                    default:
                        break;
                }
            }
            @Override
            public void onFailedToUpdateConsentInfo(String errorDescription) {
// User's consent status failed to update.
                Log.d(TAG,"onFailedToUpdateConsentInfo");
                Log.d(TAG,errorDescription);
            }
            private void showform(){
                if (form!=null){
                    Log.d(TAG,"show ok");
                    form.show();
                }
            }
        });

        //////////////////////GDPR Badr/////////////////////////////////////*/


    }
    //gdpr

    private void checkForConsent() {
        ConsentInformation consentInformation = ConsentInformation.getInstance(Dashboard.this);
        //ConsentInformation.getInstance(this).addTestDevice("89A80718462A5501C7F3F2EA31202B82");
        //ConsentInformation.getInstance(Dashboard.this).setDebugGeography(DebugGeography.DEBUG_GEOGRAPHY_EEA);
        String[] publisherIds = {"pub-9034075681938673"};
        //pub-9034075681938673
        consentInformation.requestConsentInfoUpdate(publisherIds, new ConsentInfoUpdateListener() {
            @Override
            public void onConsentInfoUpdated(ConsentStatus consentStatus) {
                // User's consent status successfully updated.
                switch (consentStatus) {
                    case PERSONALIZED:
                        Log.d(TAG, "PERSONALIZED");
                        ConsentInformation.getInstance(Dashboard.this)
                                .setConsentStatus(ConsentStatus.PERSONALIZED);
                        break;
                    case NON_PERSONALIZED:
                        Log.d(TAG, "NON_PERSONALIZED");
                        ConsentInformation.getInstance(Dashboard.this)
                                .setConsentStatus(ConsentStatus.NON_PERSONALIZED);
                        break;
                    case UNKNOWN:
                        Log.d(TAG, "UNKNOWN");
                        if (ConsentInformation.getInstance(getBaseContext())
                                .isRequestLocationInEeaOrUnknown()) {
                            requestConsent();
                        } else {
                            //showPersonalizedAds();
                            ConsentInformation.getInstance(Dashboard.this)
                                    .setConsentStatus(ConsentStatus.PERSONALIZED);
                        }
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailedToUpdateConsentInfo(String errorDescription) {
                // User's consent status failed to update.
            }
        });
    }

    private void requestConsent() {
        URL privacyUrl = null;
        try {
            // TODO: Replace with your app's privacy policy URL.
            privacyUrl = new URL("https://www.muslimcharityapps.ml/p/privacy-policy.html");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            // Handle error.
        }
        form = new ConsentForm.Builder(Dashboard.this, privacyUrl)
                .withListener(new ConsentFormListener() {
                    @Override
                    public void onConsentFormLoaded() {
                        // Consent form loaded successfully.
                        Log.d(TAG, "Requesting Consent: onConsentFormLoaded");
                        showForm();
                    }

                    @Override
                    public void onConsentFormOpened() {
                        // Consent form was displayed.
                        Log.d(TAG, "Requesting Consent: onConsentFormOpened");
                    }

                    @Override
                    public void onConsentFormClosed(
                            ConsentStatus consentStatus, Boolean userPrefersAdFree) {
                        Log.d(TAG, "Requesting Consent: onConsentFormClosed");
                        if (userPrefersAdFree) {
                            // Buy or Subscribe
                            Log.d(TAG, "Requesting Consent: User prefers AdFree");
                        } else {
                            Log.d(TAG, "Requesting Consent: Requesting consent again");
                            switch (consentStatus) {
                                case PERSONALIZED:
                                    Log.d(TAG, "PERSONALIZED");
                                    ConsentInformation.getInstance(Dashboard.this)
                                            .setConsentStatus(ConsentStatus.PERSONALIZED);
                                    break;
                                case NON_PERSONALIZED:
                                    Log.d(TAG, "NON_PERSONALIZED");
                                    ConsentInformation.getInstance(Dashboard.this)
                                            .setConsentStatus(ConsentStatus.NON_PERSONALIZED);
                                    break;
                                case UNKNOWN:
                                    Log.d(TAG, "UNKNOWN");
                            }

                        }
                        // Consent form was closed.
                    }

                    @Override
                    public void onConsentFormError(String errorDescription) {
                        Log.d(TAG, "Requesting Consent: onConsentFormError. Error - " + errorDescription);
                        // Consent form error.
                    }
                })
                .withPersonalizedAdsOption()
                .withNonPersonalizedAdsOption()
                .build();
        form.load();
    }


    private void showForm() {
        if (form == null) {
            Log.d(TAG, "Consent form is null");
        }
        if (form != null) {
            Log.d(TAG, "Showing consent form");
            form.show();
        } else {
            Log.d(TAG, "Not Showing consent form");
        }
    }
    //==================END ADS GDPR================

    @OnClick(R.id.btnFavorite)
    void setFavorite() {
        try {
            if (songsList != null && songsList.size() > 0) {
                if (favoriteSongSet.contains(songsList.get(currentSongIndex).get(KEY_SONG_PATH))) {
                    favoriteSongSet.remove(songsList.get(currentSongIndex).get(KEY_SONG_PATH));
                    appStateEditor.putStringSet(SP_FAVORITE_SONGS, favoriteSongSet);
                    appStateEditor.commit();
                    btnFavorite.setImageResource(R.drawable.unfavorite);
                } else {
                    favoriteSongSet.add(songsList.get(currentSongIndex).get(KEY_SONG_PATH));
                    appStateEditor.putStringSet(SP_FAVORITE_SONGS, favoriteSongSet);
                    appStateEditor.commit();
                    btnFavorite.setImageResource(R.drawable.favorite);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkPermistion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermission();
            } else {
                downloadSound();
            }
        } else {
            downloadSound();
        }
    }

    public void requestPermission() {
        ActivityCompat.requestPermissions((Activity) context, permissions, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean isAllPermissionsGranted = true;
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            isAllPermissionsGranted = false;
                        } else {
                            downloadSound();
                        }
                    }

                    if (isAllPermissionsGranted) {

                    } else {
                        boolean showStorageRational = false;
                        boolean showSmsRational = false;

                        if ((ActivityCompat.shouldShowRequestPermissionRationale(Dashboard.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) || ActivityCompat.shouldShowRequestPermissionRationale(Dashboard.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            showStorageRational = true;
                        }

                        if ((ActivityCompat.shouldShowRequestPermissionRationale(Dashboard.this, Manifest.permission.READ_SMS)) || (ActivityCompat.shouldShowRequestPermissionRationale(Dashboard.this, Manifest.permission.RECEIVE_SMS))) {
                            showSmsRational = true;
                        }

                        /*
                        SHOWING STORAGE RATIONAL
                         */

                        if (showStorageRational) {
                            String message = getResources().getString(R.string.app_name) + " " + "requires storage permission to store profile photo";
                            getAlertDialog()
                                    .setMessage(message)
                                    .setPositiveButton("GRANT", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            requestPermission();
                                        }
                                    })
                                    .setNegativeButton("DENY", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    }).create().show();
                        }

                        /*
                        SHOWING SMS RATIONAL
                         */

                        if (showSmsRational) {
                            String message = getResources().getString(R.string.app_name) + " " + "requires sms permission to read EEC number verification code";
                            final boolean finalShowStorageRational = showStorageRational;
                            getAlertDialog()
                                    .setMessage(message)
                                    .setPositiveButton("GRANT", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            requestPermission();
                                        }
                                    })
                                    .setNegativeButton("DENY", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    }).create().show();
                        }
                    }

                }
        }
    }

    private void downloadSound() {
//        Toast.makeText(getApplicationContext(),
//                getString(R.string.downnloadin),
//                Toast.LENGTH_LONG).show();
        new DownloadMusicfromInternet().execute(songsList.get(currentSongIndex).get("songPath"),
                songsList.get(currentSongIndex).get(KEY_SONG_TITLE));

//        downloadSora(songsList.get(currentSongIndex).get(KEY_SONG_PATH),
//                songsList.get(currentSongIndex).get(KEY_SONG_TITLE));

    }

    private void downloadSora(String path, String title) {
        //check Internet
        NoInternetConnection noInternetConnection = new NoInternetConnection();
        noInternetConnection.execute("http://clients3.google.com/generate_204");
        boolean isConnected = NetworkConnection.networkConnectivity(getApplicationContext());
        new Handler().postDelayed(new Runnable() {
                                      @Override
                                      public void run() {
                                          if (!isConnected) {
                                              //send BroadcastReceiver to the Service -> Not Connection
                                              Intent broadcastIntent = new Intent(BROADCAST_NOT_CONNECTION);
                                              sendBroadcast(broadcastIntent);
                                          } else {
                                              //   if (!isInternet()) {
                                              //send BroadcastReceiver to the Service -> Not Internet
                                              // } else {
                                              music_uri = Uri.parse(path);
                                              Music_DownloadId = DownloadData(music_uri, title);
                                              //}
                                          }
                                      }
                                  }
                , 1000);

    }

    private long DownloadData(Uri uri, String name_sora) {
        downloadReference = 0;
        OutputStream output = null;
        String fileName = uri.toString().replace(DOWNLOAD_FILE_PREFIX, "");
        utils.getOutputMediaFile(name_sora + "_" + fileName);
        try {
            output = new FileOutputStream(Environment
                    .getExternalStorageDirectory().getPath()
                    + "/AndroiSaJa/ElbarakSaJa/" + name_sora + "_" + fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        //Create Requect for android download manager
        downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        //Setting title of request
        request.setTitle(output + name_sora);
        //Setting description of request
        request.setDescription(getString(R.string.setDescriptionRequet));
        //Setting Show Notification After downloaded
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        //Set the local destination for the download file to a path within the application's external files directory
        media_path = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS + output);

        //check download folder for the App private
        media_path = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS + output);
        //check download folder Global
        mediaStorageDir = new File(media_path, name_sora + getString(R.string.mp3));
        if (mediaStorageDir != null && mediaStorageDir.exists()) {
            HelperClass.customToast(this, getString(R.string.send_problem_string));
        } else {
            request.setVisibleInDownloadsUi(true);
            request.setDestinationInExternalFilesDir(Dashboard.this, Environment.DIRECTORY_DOWNLOADS + output
                    , name_sora + getString(R.string.mp3));
            //Enqueue download and save the referenceId
            downloadReference = downloadManager.enqueue(request);
            HelperClass.customToast(this, getString(R.string.download_sound));
            //Listen for Download Sound
            downloadReceiver = new DownloadReceiver(downloadReference, this);
            registerDownloadSound();

        }
        return downloadReference;
    }

    private void registerNoConnection() {
        //Register no internet receiver
        IntentFilter filter = new IntentFilter(BROADCAST_NOT_CONNECTION);
        registerReceiver(connectivityReceiver, filter);
    }

    private void registerNoInternet() {
        //Register no internet receiver
        IntentFilter filter = new IntentFilter(BROADCAST_NOT_INTERNET);
        registerReceiver(noInternetReceiver, filter);
    }

    private void registerDownloadSound() {
        //Register no internet receiver
        IntentFilter intentFilter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        registerReceiver(downloadReceiver, intentFilter);
    }

    public AlertDialog.Builder getAlertDialog() {

        return new AlertDialog.Builder(context)
                .setIcon(context.getResources().getDrawable(R.mipmap.ic_launcher))
                .setTitle(context.getResources().getString(R.string.app_name));
    }

    private void initializeApplication() {
        /*
        SETTING UP TOOLBAR
         */

        setSupportActionBar(toolbar);

        /*
        CHECKING FOR APPLICATION LANGUAGE
         */

        checkForAppLanguage();

        /*
        SETTING UP VOLUME CONTROL STREAM
         */

        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        /*
        REQUESTING AUDIO FOCUS
         */

        audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

        /*
        CREATING FOLDER
         */

        mkFolder("AndroiSaJa/");
        mkFolder("AndroiSaJa/ElbarakSaJa/");

        /*
        DISPLAYING ADD
         */
        //AdRequest adRequest = new AdRequest.Builder().build();
        //adView.loadAd(adRequest);

        /*
        FETCH SONG LIST
         */

        getSongListObservable().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getSongListObserver());

        /*
        SETTING UP DRAWER
         */

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();
        toolbar.setNavigationIcon(android.R.drawable.stat_sys_download);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    // Downloaded Music File path in SD Card


                    String fileName = songsList.get(index).get(KEY_SONG_PATH).replace(DOWNLOAD_FILE_PREFIX, "");
                    String compareName = Environment.getExternalStorageDirectory()
                            .getPath() + "/AndroiSaJa/ElbarakSaJa/" + songsList.get(index).get(KEY_SONG_TITLE) + "_" + fileName;
                    File file = new File(Environment.getExternalStorageDirectory()
                            .getPath() + "/AndroiSaJa/ElbarakSaJa/" + index + ".mp3");
                    File newName = new File(compareName);
                    // Check if the Music file already exists
                    if (file.exists() || newName.exists()) {
                        Toast.makeText(
                                getApplicationContext(),
                                getString(R.string.already_download),
                                Toast.LENGTH_LONG).show();

                        // If the Music File doesn't exist in SD card (Not yet
                        // downloaded)
                    } else {
                        checkPermistion();
//                        Toast.makeText(getApplicationContext(),
//                                getString(R.string.downnloadin),
//                                Toast.LENGTH_LONG).show();
//
//                        new DownloadMusicfromInternet().execute(songsList.get(currentSongIndex).get("songPath"), songsList.get(currentSongIndex).get(KEY_SONG_TITLE));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        /*
        SETTING LISTENERS
         */
        songProgressBar.setOnSeekBarChangeListener(this); // Important
        mediaPlayer.setOnCompletionListener(this);
        navigationView.setNavigationItemSelectedListener(this);
        /*
        SETTING FONTS
         */
        String fontPath = "fonts/DROIDKUFI-BOLD.TTF";
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        songTitleLabel.setTypeface(tf);
        /*
        SETTING VOLUME SEEK BAR
         */
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        volumeSeekbar.setMax(maxVolume);
        volumeSeekbar.setProgress(curVolume);
        volumeSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
                Volume = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        /*
        PLAY SONG
         */
        btnPlay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // check for already playing
                if (mediaPlayer.isPlaying()) {
                    if (mediaPlayer != null) {
                        mediaPlayer.pause();
                        /*new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                createNotification();
                            }
                        }, 200);*/
                        // Changing button image to play button
                        btnPlay.setImageResource(R.drawable.play);
                    }
                } else {
                    // Resume song
                    if (mediaPlayer != null) {
                        mediaPlayer.start();

                        /*new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                createNotification();
                            }
                        }, 200);*/
                        // Changing button image to pause button
                        btnPlay.setImageResource(R.drawable.pause);
                    }
                }

            }
        });
        /*
        NEXT SONG
         */
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (currentSongIndex < (songsList.size() - 1)) {

                    playSong(currentSongIndex + 1);
                    currentSongIndex = currentSongIndex + 1;
                } else {
                    // play first song

                    playSong(0);
                    currentSongIndex = 0;
                }

                appStateEditor.putInt(SP_SONG_PROGRESS, 0).commit();

            }

        });
        /**
         * Back button click event Plays previous song by currentSongIndex - 1
         * */
        btnPrevious.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (currentSongIndex > 0) {
                    playSong(currentSongIndex - 1);
                    currentSongIndex = currentSongIndex - 1;
                } else {
                    // play last song
                    playSong(songsList.size() - 1);
                    currentSongIndex = songsList.size() - 1;
                }

                appStateEditor.putInt(SP_SONG_PROGRESS, 0).commit();

            }
        });
        /**
         * Button Click event for Repeat button Enables repeat flag to true
         * */
        btnRepeat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (isRepeat) {
                    isRepeat = false;
                    Toast.makeText(getApplicationContext(),
                            getString(R.string.stp_repeat), Toast.LENGTH_SHORT)
                            .show();
                    btnRepeat.setImageResource(R.drawable.repeat_dark);
                } else {
                    // make repeat to true
                    isRepeat = true;
                    Toast.makeText(getApplicationContext(),
                            getString(R.string.repeat_active), Toast.LENGTH_SHORT)
                            .show();
                    // make shuffle to false
                    isShuffle = false;
                    btnRepeat.setImageResource(R.drawable.repeat);
                    btnShuffle.setImageResource(R.drawable.shuffle_light);
                }
            }
        });
        /**
         * Button Click event for Shuffle button Enables shuffle flag to true
         * */
        btnShuffle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (isShuffle) {
                    isShuffle = false;
                    Toast.makeText(getApplicationContext(),
                            getString(R.string.stp_shuflle),
                            Toast.LENGTH_SHORT).show();
                    btnShuffle.setImageResource(R.drawable.shuffle_light);
                } else {
                    // make repeat to true
                    isShuffle = true;
                    Toast.makeText(getApplicationContext(),
                            getString(R.string.shuflle_active),
                            Toast.LENGTH_SHORT).show();
                    // make shuffle to false
                    isRepeat = false;
                    btnShuffle.setImageResource(R.drawable.shuffle);
                    btnRepeat.setImageResource(R.drawable.repeat_dark);
                }
            }
        });
    }

    private void checkForAppLanguage() {
        if (appStateSharedPreference.getString(SP_LANGUAGE, "").length() > 0) {
            setApplicationLocale(appStateSharedPreference.getString(SP_LANGUAGE, ""));
        }
    }

    private void setApplicationLocale(String locale) {
        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = new Locale(locale.toLowerCase());
        resources.updateConfiguration(configuration, displayMetrics);
    }

    public int mkFolder(String folderName) { // make a folder
        String state = Environment.getExternalStorageState();
        if (!Environment.MEDIA_MOUNTED.equals(state)) {
            Log.d("myAppName", "Error: external storage is unavailable");
            return 0;
        }
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            Log.d("myAppName", "Error: external storage is read only.");
            return 0;
        }
        Log.d("myAppName", "External storage is not read only or unavailable");

        if (ContextCompat.checkSelfPermission(this, // request permission when it is not granted.
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Log.d("myAppName", "permission:WRITE_EXTERNAL_STORAGE: NOT granted!");

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1);
            }
        }
        File folder = new File(Environment.getExternalStorageDirectory(), folderName);
        int result = 0;
        if (folder.exists()) {
            Log.d("myAppName", "folder exist:" + folder.toString());
            result = 2; // folder exist
        } else {
            try {
                if (folder.mkdir()) {
                    Log.d("myAppName", "folder created:" + folder.toString());
                    result = 1; // folder created
                } else {
                    Log.d("myAppName", "create folder fails:" + folder.toString());
                    result = 0; // create folder fails
                }
            } catch (Exception ecp) {
                ecp.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        try {
            if (focusChange <= 0) {
                mediaPlayer.pause();
            } else {
                mediaPlayer.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void playSong(int songIndex) {
        disposeSongProgressObserver();
        try {
            index = songIndex;
            if (index < 0) {
                index = 0;
            }
            /*OLD FILE NAME*/
            File oldFile = new File(MEDIA_PATH + index + ".mp3");

            String MEDIA_PATH = Environment.getExternalStorageDirectory().getPath() + "/AndroiSaJa/ElbarakSaJa/";

            try {
                if (songsList.size() > 0) {
                    //  final File file_xStore = new File(home,songsList.get(0).get(KEY_SONG_TITLE) + ".mp3");

                    String fileName = songsList.get(index).get(KEY_SONG_PATH).replace(DOWNLOAD_FILE_PREFIX, "");
                    String DOWNLOAD_FILE_PREFIX = "https://server13.mp3quran.net/braak/";


                    String compareName = MEDIA_PATH + songsList.get(index).get(KEY_SONG_TITLE) + "_" + fileName;
                    if (fileName.contains("/AndroiSaJa/ElbarakSaJa/")) {
                        compareName = fileName;
                    }
                    try {
                        if (!(new File(compareName).exists() || oldFile.exists()) && isOnline()) {
                            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                            mediaPlayer.reset();
                            mediaPlayer.setDataSource(songsList.get(songIndex).get(KEY_SONG_PATH));
                        } else if (new File(compareName).exists() || oldFile.exists()) {
                            mediaPlayer.reset();
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                mediaPlayer.setAudioAttributes(new AudioAttributes.Builder()
                                        .setUsage(AudioAttributes.USAGE_MEDIA)
                                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                        .build());
                            } else {
                                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                            }

                            // mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                            Uri uri = Uri.parse(path);
                            mediaPlayer.reset();
                            mediaPlayer.setDataSource(getApplicationContext(), uri);
                        } else if (!(new File(compareName).exists() || oldFile.exists()) && !isOnline()) {
                            mediaPlayer.stop();
                        } else {
                            mediaPlayer.reset();
                            mediaPlayer.setDataSource(compareName);
                        }
                    } catch (Exception e) {
                        Log.e("TAG", "Media error... " + e.getMessage());
                    }
                    //fileUrl = songsList.get(index).get(KEY_SONG_PATH);
                    mediaPlayer.prepareAsync();
                    showLoading();
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer player) {
                    /*
                    UPDATING SONG STATE
                     */
                            if (currentSongIndex < 0) {
                                currentSongIndex = 0;
                            }
                            appStateEditor.putInt(SP_SONG_INDEX, currentSongIndex);

                    /*
                    SETTING FAVORITE AND UN FAVORITE
                     */

                            try {
                                if (!favoriteSongSet.isEmpty() && favoriteSongSet.contains(songsList.get(currentSongIndex).get(KEY_SONG_PATH))) {
                                    btnFavorite.setImageResource(R.drawable.favorite);
                                } else {
                                    btnFavorite.setImageResource(R.drawable.unfavorite);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                    /*
                    GETTING SONG STATE
                     */
                            int progress = appStateSharedPreference.getInt(SP_SONG_PROGRESS, 0);
                            player.seekTo(progress);
                            player.start();
                            // Changing Button Image to pause image
                            btnPlay.setImageResource(R.drawable.pause);
                            // set Progress bar values
                            songProgressBar.setProgress(0);
                            songProgressBar.setMax(100);

                            // Updating progress bar
                            updateProgressBar();
                            hideLoading();

                            //createNotification();
                        }

                    });

                    // Displaying Song title
                    songTitle = songsList.get(songIndex).get(KEY_SONG_TITLE);
                    songTitleLabel.setText(songTitle);
                } else {
                    Toast.makeText(Dashboard.this, "No song to play", Toast.LENGTH_SHORT).show();
                }

            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();

            }

        } catch (IllegalArgumentException e) {
            Log.e("Error", e.getMessage(), e);
        } catch (IllegalStateException e) {
            Log.e("Error", e.getMessage(), e);
        } catch (Exception e) {
            Log.e("Error", e.getMessage(), e);
        }


    }

    private void playSongForTwoTest(int songIndex) {
        disposeSongProgressObserver();
        try {
            index = songIndex;
            if (index < 0) {
                index = 0;
            }

            try {
                if (songsList.size() > 0) {
                    try {
                        if (path == null && isOnline()) {
                            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                            mediaPlayer.reset();
                            mediaPlayer.setDataSource(songsList.get(songIndex).get(KEY_SONG_PATH));
                        } else if (path != null) {
                            mediaPlayer.reset();
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                mediaPlayer.setAudioAttributes(new AudioAttributes.Builder()
                                        .setUsage(AudioAttributes.USAGE_MEDIA)
                                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                        .build());
                            } else {
                                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                            }
                            Uri uri = Uri.parse(path);
                            mediaPlayer.reset();
                            mediaPlayer.setDataSource(getApplicationContext(), uri);
                        } else if (path == null && !isOnline()) {
                            mediaPlayer.stop();
                        } else {
                        }
                    } catch (Exception e) {
                        Log.e("TAG", "Media error... " + e.getMessage());
                    }
                    mediaPlayer.prepareAsync();
                    showLoading();
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer player) {
                    /*
                    UPDATING SONG STATE
                     */
                            if (currentSongIndex < 0) {
                                currentSongIndex = 0;
                            }
                            appStateEditor.putInt(SP_SONG_INDEX, currentSongIndex);

                    /*
                    SETTING FAVORITE AND UN FAVORITE
                     */

                            try {
                                if (!favoriteSongSet.isEmpty() && favoriteSongSet.contains(songsList.get(currentSongIndex).get(KEY_SONG_PATH))) {
                                    btnFavorite.setImageResource(R.drawable.favorite);
                                } else {
                                    btnFavorite.setImageResource(R.drawable.unfavorite);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                    /*
                    GETTING SONG STATE
                     */
                            int progress = appStateSharedPreference.getInt(SP_SONG_PROGRESS, 0);
                            player.seekTo(progress);
                            player.start();
                            // Changing Button Image to pause image
                            btnPlay.setImageResource(R.drawable.pause);
                            // set Progress bar values
                            songProgressBar.setProgress(0);
                            songProgressBar.setMax(100);
                            // Updating progress bar
                            updateProgressBar();
                            hideLoading();
                            //createNotification();
                        }
                    });
                    // Displaying Song title
                    if (title_for_download_list != null) {
                        songTitleLabel.setText(title_for_download_list);
                    } else {
                        songTitle = songsList.get(songIndex).get(KEY_SONG_TITLE);
                        songTitleLabel.setText(songTitle);
                    }


                } else {
                    Toast.makeText(Dashboard.this, "No song to play", Toast.LENGTH_SHORT).show();
                }

            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();

            }

        } catch (IllegalArgumentException e) {
            Log.e("Error", e.getMessage(), e);
        } catch (IllegalStateException e) {
            Log.e("Error", e.getMessage(), e);
        } catch (Exception e) {
            Log.e("Error", e.getMessage(), e);
        }


    }

    private void updateProgressBar() {
        songProgressObserver = getSongProgressObserver();
        getSongProgressObservable().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(songProgressObserver);
    }

    private void showLoading() {
        spinner.setVisibility(View.VISIBLE);
        btnPlay.setClickable(false);
    }

    private void hideLoading() {
        spinner.setVisibility(View.GONE);
        btnPlay.setClickable(true);
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void updateSongProgress() {

        /*
        UPDATING SONG STATE
         */
        try {
            appStateEditor.putInt(SP_SONG_INDEX, currentSongIndex);
            appStateEditor.putInt(SP_SONG_PROGRESS, mediaPlayer.getCurrentPosition());
            appStateEditor.commit();


            long totalDuration = mediaPlayer.getDuration();
            long currentDuration = mediaPlayer.getCurrentPosition();

            songTotalDurationLabel.setText(String.valueOf(utils.milliSecondsToTimer(totalDuration)));
            songCurrentDurationLabel.setText(String.valueOf(utils.milliSecondsToTimer(currentDuration)));

            // Updating progress bar
            int progress = (utils.getProgressPercentage(currentDuration,
                    totalDuration));
            // Log.d("Progress", ""+progress);
            songProgressBar.setProgress(progress);

            /*if (remoteViews != null) {
                remoteViews.setProgressBar(R.id.pbSongProgress, 100, progress, false);
                if (notificationManager == null) {
                    notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                }
                notificationManager.notify(0, notification);
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public JSONObject parseJSONData() {
        String JSONString = null;
        JSONObject JSONObject = null;
        try {

            //open the inputStream to the file
            InputStream inputStream = getAssets().open("mahmoud.json");

            int sizeOfJSONFile = inputStream.available();

            //array that will store all the data
            byte[] bytes = new byte[sizeOfJSONFile];

            //reading data into the array from the file
            inputStream.read(bytes);

            //close the input stream
            inputStream.close();

            JSONString = new String(bytes, "UTF-8");
            JSONObject = new JSONObject(JSONString);

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        } catch (JSONException x) {
            x.printStackTrace();
            return null;
        }
        return JSONObject;
    }

    private void confirmExit() {
        dialogBuilder = NiftyDialogBuilder.getInstance(this);
        dialogBuilder
                .isCancelableOnTouchOutside(false).withDuration(800)
                .withTitle(getString(R.string.title_exit))
                .withMessage(getString(R.string.msg_exit))
                .withEffect(Effectstype.Fliph)
                .withButton1Text(getString(R.string.lbl_yes))
                .withButton2Text(getString(R.string.lbl_no))
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*
                        SAVING STATE
                         */
                        appStateEditor.putInt(SP_SONG_INDEX, currentSongIndex);
                        appStateEditor.putInt(SP_SONG_PROGRESS, mediaPlayer.getCurrentPosition());
                        appStateEditor.commit();
                        /*
                        RELEASING
                         */
                        disposeSongProgressObserver();
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                        mediaPlayer.release();
                        audioManager.abandonAudioFocus(Dashboard.this);

                        /*
                        REMOVING NOTIFICATION
                         */
                        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                        notificationManager.cancelAll();
                        System.exit(0);

                    }
                }).setButton2Click(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder.dismiss();
            }
        }).show();

    }

    private void confirmExitTest() {
        /*
                        SAVING STATE
                         */
        appStateEditor.putInt(SP_SONG_INDEX, currentSongIndex);
        appStateEditor.putInt(SP_SONG_PROGRESS, mediaPlayer.getCurrentPosition());
        appStateEditor.commit();
                        /*
                        RELEASING
                         */
        disposeSongProgressObserver();
        mediaPlayer.stop();
        mediaPlayer.reset();
        mediaPlayer.release();
        audioManager.abandonAudioFocus(Dashboard.this);
                        /*
                        REMOVING NOTIFICATION
                         */
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
        System.exit(0);
    }

    private void releaseState() {
        /*
        SAVING STATE
        */
        appStateEditor.putInt(SP_SONG_INDEX, currentSongIndex);
        appStateEditor.putInt(SP_SONG_PROGRESS, mediaPlayer.getCurrentPosition());
        appStateEditor.commit();

        /*
        RELEASING
        */
        disposeSongProgressObserver();
        mediaPlayer.stop();

        /*
        REMOVING NOTIFICATION
        */
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();

        /*
        UN REGISTER RECEIVER
         */

        /*try {
            unregisterReceiver(receiver);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }


    private void setCurrentDateAndTime() {
        String[] dateChunks = getCurrentDateAndTime();
        tvTime.setText(dateChunks[3]);
        tvAmPm.setText(dateChunks[4]);
        String dateText = dateChunks[2] + ". " + dateChunks[0] + " " + dateChunks[1];
        tvDate.setText(dateText);
    }

    private String[] getCurrentDateAndTime() {
        SimpleDateFormat outputFormatAmPm = new SimpleDateFormat("MMM'-'dd'-'EEE'-'KK:mm-a");
        try {
            Date date = new Date();
            return outputFormatAmPm.format(date).split("-");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (isChangingConfigurations()) {
            releaseState();
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCompletion(MediaPlayer arg0) {

        appStateEditor.putInt(SP_SONG_PROGRESS, 0).commit();
        try {
            // check for repeat is ON or OFF
            if (isRepeat) {
                // repeat is on play same song again
                playSong(currentSongIndex);
            } else if (isShuffle) {
                // shuffle is on - play a random song
                Random rand = new Random();
                currentSongIndex = rand.nextInt((songsList.size() - 1) - 1);
                playSong(currentSongIndex);
            } else {
                // no repeat or shuffle ON - play next song
                if (currentSongIndex < (songsList.size() - 1)) {
                    playSong(currentSongIndex + 1);
                    currentSongIndex = currentSongIndex + 1;
                } else {
                    // play first song
                    playSong(0);
                    currentSongIndex = 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress,
                                  boolean fromTouch) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // remove message Handler from updating progress bar
        disposeSongProgressObserver();
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        disposeSongProgressObserver();
        int totalDuration = mediaPlayer.getDuration();
        int currentPosition = utils.progressToTimer(seekBar.getProgress(),
                totalDuration);
        // forward or backward to certain seconds
        mediaPlayer.seekTo(currentPosition);

        // update timer progress again
        updateProgressBar();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        drawer.closeDrawer(Gravity.RIGHT);
        int id = item.getItemId();

        if (id == R.id.nav_playlist) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
                    Intent i = new Intent(getApplicationContext(),
                            PlayListActivity.class);
                    i.putExtra(INTENT_DATA, songsList);
                    //  i.putExtra(INTENT_DATA, allSongsList);
                    startActivityForResult(i, 100);
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);

                }
            }, 200);
        } else if (id == R.id.nav_favoriteplaylist) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
                    Intent i = new Intent(getApplicationContext(),
                            PlayListFavorite.class);
                    i.putExtra(INTENT_DATA, songsList);
//                    i.putExtra(INTENT_DATA, allSongsList);

                    startActivityForResult(i, 100);
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);

                }
            }, 200);
        } else if (id == R.id.nav_downloadedplaylist) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    File file = new File(Environment.getExternalStorageDirectory()
                            .getPath() + "/AndroiSaJa/ElbarakSaJa/");
                    try {
                        if (file.list().length == 0) {
                            Toast.makeText(
                                    getApplicationContext(),
                                    getString(R.string.msg_no_downloads),
                                    Toast.LENGTH_LONG).show();
                        } else {
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
                            Intent i = new Intent(getApplicationContext(), PlayListDownloaded.class);
                            i.putExtra(INTENT_DATA, songsList);
                            //i.putExtra(INTENT_DATA, allSongsList);
                            startActivityForResult(i, 100);
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                        }
                    } catch (NullPointerException e) {
                        Toast.makeText(
                                getApplicationContext(),
                                getString(R.string.msg_no_downloads),
                                Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }

                }

            }, 200);
        } else if (id == R.id.nav_about) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(getApplicationContext(), About.class);
                    startActivity(i);
                }
            }, 200);
        } else if (id == R.id.nav_shareapp) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        Intent i = new Intent(Intent.ACTION_SEND);
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(
                                Intent.EXTRA_TEXT,
                                getString(R.string.msg_share)
                                        + "https://play.google.com/store/apps/details?id=com.muslimcharityapps.elbarak");
                        startActivity(Intent.createChooser(intent, "Share"));
                    } catch (Exception e) { //e.toString();
                    }

                }
            }, 200);

        } else if (id == R.id.nav_rateapp) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(
                            "android.intent.action.VIEW",
                            Uri.parse("https://play.google.com/store/apps/details?id=com.muslimcharityapps.elbarak")));
                }
            }, 200);
        } else if (id == R.id.nav_privacy) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(getApplicationContext(), Privacy.class);
                    startActivity(i);
                }
            }, 200);
        } else if (id == R.id.nav_language) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(getApplicationContext(), DefaultLanguage.class);
                    startActivityForResult(i, REQUEST_CHANGE_LANGUAGE);
                }
            }, 200);
        } else if (id == R.id.settings) {
            if (ConsentInformation.getInstance(getBaseContext())
                    .isRequestLocationInEeaOrUnknown()) {
                requestConsent();


            } else {
                Toast.makeText(this, "This option is available only for European Users", Toast.LENGTH_LONG).show();
            }
            return true;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(Gravity.RIGHT)) {
            drawer.closeDrawer(Gravity.RIGHT);
        } else {
            if (exitApp) {
                confirmExitTest();
                //   confirmExit();
                return;
            }
            exitApp = true;
            HelperClass.customToast(this, getString(R.string.exit_app));
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exitApp = false;
                }
            }, 2000);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_download:
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 105) {
            appStateEditor.putInt(SP_SONG_PROGRESS, 0).commit();
            songsList = (ArrayList<HashMap<String, String>>) data.getSerializableExtra(INTENT_DATA);
            String title = data.getExtras().getString(SP_SONG_INDEX);
            for (int i = 0; i < songsList.size(); i++) {
                if (title.equals(songsList.get(i).get(KEY_SONG_TITLE))) {
                    currentSongIndex = i;
                    break;
                }
            }
            if (songsList == null) {
                releaseState();
                songsList = (ArrayList<HashMap<String, String>>) data.getSerializableExtra(INTENT_DATA);
            } else
                playSong(currentSongIndex);
        }

        if (resultCode == 100) {
            appStateEditor.putInt(SP_SONG_PROGRESS, 0).commit();
            currentSongIndex = data.getExtras().getInt(SP_SONG_INDEX);
            path = data.getExtras().getString(KEY_SONG_PATH);
            title_for_download_list = data.getExtras().getString(KEY_SONG_TITLE);

            Log.e("TAG", "path: " + path);
            if (songsList == null) {
                releaseState();
                songsList = (ArrayList<HashMap<String, String>>) data.getSerializableExtra(INTENT_DATA);
            } else {
                playSongForTwoTest(currentSongIndex);

            }
        }

        if (requestCode == REQUEST_CHANGE_LANGUAGE && resultCode == REQUEST_CHANGE_LANGUAGE) {
            disposeSongProgressObserver();
            if (mediaPlayer.isPlaying())
                mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            audioManager.abandonAudioFocus(Dashboard.this);
            //unregisterReceiver(receiver);

            NotificationManager notifManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            notifManager.cancelAll();
            finish();

            Intent restartApplication = getBaseContext().getPackageManager()
                    .getLaunchIntentForPackage(getBaseContext().getPackageName());
            restartApplication.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(restartApplication);

            System.exit(0);
        }
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_DOWNLOAD_PROGRESS:
                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setMessage(getString(R.string.down_progress));
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                mProgressDialog.setCancelable(false);
                mProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.msg_cncl), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        File file = new File(Environment.getExternalStorageDirectory() + "/AndroiSaJa/ElbarakSaJa/" + index + ".mp3");
                        if (file.exists()) {
                            boolean deleted = file.delete();
                        }
                        mProgressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), getString(R.string.down_cancel), Toast.LENGTH_LONG).show();
                    }
                });
                mProgressDialog.show();
                return mProgressDialog;
            default:
                return null;
        }
    }

    /**
     * Called when leaving the activity
     */
    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    //todojamal
    @Override
    protected void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
        registerNoConnection();
        //Listen for not Internet
        registerNoInternet();
        boolean isInternetPresent = connectionDetector.isConnectingToInternet();
        if (!isInternetPresent && isToShowInternetDialog) {
            isToShowInternetDialog = false;
            dialogBuilder = NiftyDialogBuilder.getInstance(this);
            dialogBuilder
                    .isCancelableOnTouchOutside(false).withDuration(800)
                    .withTitle(getString(R.string.no_net))
                    .withMessage(getString(R.string.no_net_msg))
                    .withEffect(Effectstype.Fliph)
                    //.withButton1Text(getString(R.string.lbl_yes))
                    .withButton2Text(getString(R.string.lbl_ok))
                    .setButton2Click(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialogBuilder.dismiss();

                            //GETTING SONGS FROM DEVICE


                            provideSongListOberservable()
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(provideSongListObserver());
                        }
                    }).show();
        }
    }

    @Override
    protected void onDestroy() {
        RxBus.get().unregister(this);
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
        if (connectivityReceiver != null) {
            unregisterReceiver(connectivityReceiver);
        }
        if (noInternetReceiver != null) {
            unregisterReceiver(noInternetReceiver);
        }
        if (downloadReceiver != null) {
            unregisterReceiver(downloadReceiver);
        }
    }

    private void disposeSongProgressObserver() {
        if (songProgressObserver != null && !songProgressObserver.isDisposed()) {
            songProgressObserver.dispose();
        }
    }

    /*
    RX FOR TIME AND DATE
     */
    private Flowable getSongProgressObservable() {
        return Flowable.interval(0, 1, TimeUnit.SECONDS);
    }

    private DisposableSubscriber<Long> getSongProgressObserver() {
        return new DisposableSubscriber<Long>() {
            @Override
            public void onNext(Long aLong) {
                updateSongProgress();
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {
            }
        };
    }

    /*
    RX FOR SONG LIST
     */

    private Observable<ArrayList<HashMap<String, String>>> getSongListObservable() {
        return new Observable<ArrayList<HashMap<String, String>>>() {
            @Override
            protected void subscribeActual(Observer<? super ArrayList<HashMap<String, String>>> observer) {
                ArrayList<HashMap<String, String>> songLocationList = new ArrayList<>();
                JSONObject parent = parseJSONData();
                try {
                    /*
                    FETCHING PATH
                     */
                    ArrayList<String> songList = new ArrayList<>();
                    JSONArray contacts = parent.getJSONArray("contacts");
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);
                        String email = c.getString(TAG_EMAIL);
                        songList.add(email);
                    }

                    /*
                    FETCHING TITLE
                     */

                    String[] values = getResources().getStringArray(R.array.array_songs);
                    ArrayList<String> titles = new ArrayList<>();
                    //   ArrayList<Integer> position = new ArrayList<>();

                    for (int i = 0; i < values.length; i++) {
                        titles.add(values[i]);
                        //    songList.add(String.valueOf(i));
                        // songList.toString().add(i);
                    }
                    /*
                    COMBINING TITLE AND PATH
                     */
                    for (int i = 0; i < songList.size(); i++) {
                        HashMap<String, String> song = new HashMap<>();
                        song.put(KEY_SONG_PATH, songList.get(i));
                        song.put(KEY_SONG_TITLE, titles.get(i));
                        song.put(KEY_SONG_POSITION, String.valueOf(i));
                        //  song.put
                        Log.i("TAG", "songList " + String.valueOf(i));
                        songLocationList.add(song);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                observer.onNext(songLocationList);
            }
        };
    }

    private DisposableObserver<ArrayList<HashMap<String, String>>> getSongListObserver() {
        return new DisposableObserver<ArrayList<HashMap<String, String>>>() {
            @Override
            public void onNext(ArrayList<HashMap<String, String>> value) {
                songsList = value;
                if (isOnline()) {
                    //    currentSongIndex = appStateSharedPreference.getInt(SP_SONG_INDEX, 0);
                    if (currentSongIndex < 0) {
                        currentSongIndex = 0;
                    }
                    playSong(currentSongIndex);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    /*
    RX FOR FILE DOWNLOAD
     */

    private DisposableObserver<Response<ResponseBody>> provideFileObserver() {
        return new DisposableObserver<Response<ResponseBody>>() {
            @Override
            public void onNext(Response<ResponseBody> value) {
                String fileName = value.raw().request().url().toString().replace(DOWNLOAD_FILE_PREFIX, "");
                provideWriteFileObservable(value.body().byteStream(), fileName, value)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(provideWriteFileObserver());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    private Observable<File> provideWriteFileObservable(final InputStream is, final String fileName, final Response<ResponseBody> responseBody) {
        return new Observable<File>() {
            @Override
            protected void subscribeActual(Observer<? super File> observer) {
                int totalFileSize;
                InputStream inputStream = is;
                File file = utils.getOutputMediaFile(fileName);
                OutputStream output = null;
                try {
                    output = new FileOutputStream(file);
                    byte[] buffer = new byte[1024];
                    int read;
                    long fileSize = responseBody.body().contentLength();
                    long total = 0;
                    while ((read = inputStream.read(buffer)) != -1) {
                        total += read;
                        totalFileSize = (int) (fileSize / (Math.pow(1024, 2)));
                        double current = Math.round(total / (Math.pow(1024, 2)));
                        int progress = (int) ((total * 100) / fileSize);
                        Log.d(TAG, "subscribeActual: " + progress);
                        output.write(buffer, 0, read);
                    }
                    output.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (output != null) {
                            output.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                observer.onNext(file);
            }
        };
    }

    private DisposableObserver<File> provideWriteFileObserver() {
        return new DisposableObserver<File>() {
            @Override
            public void onNext(File value) {
                Toast.makeText(context, "File Stored At" + value.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    /*
    RX FOR TIME AND DATE
     */
    private Flowable getTimeAndDateObservable() {
        return Flowable.interval(0, 1, TimeUnit.MINUTES);
    }

    private DisposableSubscriber<Long> getTimeAndDateObserver() {
        return new DisposableSubscriber<Long>() {
            @Override
            public void onNext(Long aLong) {
                setCurrentDateAndTime();
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {
            }
        };
    }

    /*
    RX FOR STORAGE SONG LIST
     */

    private Observable<ArrayList<HashMap<String, String>>> provideSongListOberservable() {
        return new Observable<ArrayList<HashMap<String, String>>>() {
            @Override
            protected void subscribeActual(Observer<? super ArrayList<HashMap<String, String>>> observer) {

                File home = new File(MEDIA_PATH);
                String[] values = getResources().getStringArray(R.array.array_songs);
                ArrayList<HashMap<String, String>> songsList = new ArrayList<>();
                if (home.listFiles(new FileExtensionFilter()) != null && home.listFiles(new FileExtensionFilter()).length > 0) {
                    for (File file : home.listFiles(new FileExtensionFilter())) {
                        try {
                            HashMap<String, String> song = new HashMap<>();
                            song.put(KEY_SONG_TITLE, values[Integer.parseInt(file.getName().split("_")[1].substring(0,
                                    (file.getName().split("_")[1].length() - 4))) - 1]);
                            song.put(KEY_SONG_PATH, file.getPath());
                            // Adding each song to SongList
                            songsList.add(song);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                observer.onNext(songsList);
            }
        };
    }

    private DisposableObserver<ArrayList<HashMap<String, String>>> provideSongListObserver() {
        return new DisposableObserver<ArrayList<HashMap<String, String>>>() {
            @Override
            public void onNext(ArrayList<HashMap<String, String>> value) {
                songsList = value;
                if (songsList != null && songsList.size() > 0) {
                    playSong(0);
                } else {
                    Toast.makeText(context, getString(R.string.msg_no_downloads), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    static class FileExtensionFilter implements FilenameFilter {
        public boolean accept(File dir, String name) {
            return (name.endsWith(".mp3") || name.endsWith(".MP3"));
        }
    }

    /*
    SINGLE FILE DOWNLOAD
     */

    class DownloadMusicfromInternet extends AsyncTask<String, String, String> {
        // Show Progress bar before downloading Music
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Shows Progress Bar Dialog and then call doInBackground method
            showDialog(progress_bar_type);
            mProgressDialog.setProgress(Integer.parseInt("0"));
        }

        // Download Music File from Internet
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                String title = f_url[1];
                URLConnection conection = url.openConnection();
                conection.connect();
                // Get Music file length
                int lenghtOfFile = conection.getContentLength();
                // input stream to read file - with 8k buffer
                InputStream input = new BufferedInputStream(url.openStream(),
                        10 * 1024);
                // Output stream to write file in SD card
                String fileName = url.toString().replace(DOWNLOAD_FILE_PREFIX, "");
                utils.getOutputMediaFile(title + "_" + fileName);
                OutputStream output = new FileOutputStream(Environment
                        .getExternalStorageDirectory().getPath()
                        + "/AndroiSaJa/ElbarakSaJa/" + title + "_" + fileName);
                Log.e("TAG", "" + url + title + fileName);

                byte data[] = new byte[1024];
                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    // Publish the progress which triggers onProgressUpdate
                    // method
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));
                    // Write data to file
                    output.write(data, 0, count);
                }
                // Flush output
                output.flush();
                // Close streams
                output.close();
                input.close();
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
            return null;
        }

        // While Downloading Music File
        protected void onProgressUpdate(String... progress) {
            // Set progress percentage
            mProgressDialog.setProgress(Integer.parseInt(progress[0]));
        }

        // Once Music File is downloaded
        @Override
        protected void onPostExecute(String file_url) {
            // Dismiss the dialog after the Music file was downloaded
            try {
                try {
                    removeDialog(progress_bar_type);
                } catch (Exception e) {
                    e.printStackTrace();
                    dismissDialog(progress_bar_type);
                }
                Toast.makeText(getApplicationContext(), getString(R.string.down_ok),
                        Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Play the music
        }
    }

    /*
    RX BUS
     */

    @Subscribe(tags = {@Tag(MESSAGE_CONNECTIVITY)})
    public void processConnectivity(Boolean isConnected) {
        if (isConnected) {
            /*
            FETCH SONG LIST
             */

            getSongListObservable().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(getSongListObserver());
        } else {
            /*
            GETTING SONGS FROM DEVICE
            */
            provideSongListOberservable()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(provideSongListObserver());
        }
    }

    /*private void loadBanner() {
        // Create an ad request. Check your logcat output for the hashed device ID
        // to get test ads on a physical device, e.g.,
        // "Use AdRequest.Builder.addTestDevice("ABCDE0123") to get test ads on this
        // device."
        AdRequest adRequest =
                new AdRequest.Builder().build();

        AdSize adSize = getAdSize();
        // Step 4 - Set the adaptive ad size on the ad view.
        adView.setAdSize(adSize);

        // Step 5 - Start loading the ad in the background.
        adView.loadAd(adRequest);
    }

    private AdSize getAdSize() {
        // Step 2 - Determine the screen width (less decorations) to use for the ad width.
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;

        int adWidth = (int) (widthPixels / density);

        // Step 3 - Get adaptive ad size and return for setting on the ad view.
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth);
    }*/
    private void loadBanner() {
        // Create an ad request.
        adView = new AdView(this);
        adView.setAdUnitId(AD_UNIT_ID);
        adContainerView.removeAllViews();
        adContainerView.addView(adView);

        AdSize adSize = getAdSize();
        adView.setAdSize(adSize);

        AdRequest adRequest = new AdRequest.Builder().build();

        // Start loading the ad in the background.
        adView.loadAd(adRequest);
    }

    private AdSize getAdSize() {
        // Determine the screen width (less decorations) to use for the ad width.
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float density = outMetrics.density;

        float adWidthPixels = adContainerView.getWidth();

        // If the ad hasn't been laid out, default to the full screen width.
        if (adWidthPixels == 0) {
            adWidthPixels = outMetrics.widthPixels;
        }

        int adWidth = (int) (adWidthPixels / density);

        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth);
    }

}
