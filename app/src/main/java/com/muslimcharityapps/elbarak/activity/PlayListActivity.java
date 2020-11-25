package com.muslimcharityapps.elbarak.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.muslimcharityapps.elbarak.ItemListAdapter;
import com.muslimcharityapps.elbarak.R;
import com.muslimcharityapps.elbarak.SSBApp;
import com.muslimcharityapps.elbarak.communicator.DownloadCommunicator;
import com.muslimcharityapps.elbarak.model.DownloadProgress;
import com.muslimcharityapps.elbarak.rxdownload.DownloadableItem;
import com.muslimcharityapps.elbarak.rxdownload.DownloadingStatus;
import com.muslimcharityapps.elbarak.utils.Utilities;
import com.muslimcharityapps.elbarak.utils.Values;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import javax.inject.Inject;

//import android.support.v7.app.ActionBarActivity;

public class PlayListActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, Values, DownloadCommunicator {// Songs list

    @Inject
    Utilities utils;

    @Inject
    SharedPreferences sharedpreferences;

    @Inject
    SharedPreferences.Editor editor;

    public ArrayList<HashMap<String, String>> songsList = new ArrayList<>();
    private final String TAG = PlayListActivity.class.getSimpleName();
    //  private ListView txt;
    private TextView titre;
  //  private ListView lv;
    private InterstitialAd mInterstitialAd;
    //  private final String MyPREFERENCES = "MyPrefs";
    private final String ADS = "ads";
    private ArrayList<HashMap<String, String>> songsListData;
    private Context context;
    private ItemListAdapter itemListAdapter;
    private ArrayList<DownloadableItem> downloadableItemList;
    private RecyclerView itemsListView;

    String ad;
    // This is an ad unit ID for a test ad. Replace with your own banner ad unit ID.
    private static final String AD_UNIT_ID = "ca-app-pub-3940256099942544/6300978111";
    private FrameLayout adContainerView;
    private AdView adView;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playlist);

        /*// Initialize the Mobile Ads SDK.
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) { }
        });
        adContainerView = findViewById(R.id.ad_view_container);
        // Step 1 - Create an AdView and set the ad unit ID on it.
        adView = new AdView(this);
        adView.setAdUnitId("ca-app-pub-9034075681938673/9264291013");
        adContainerView.addView(adView);
        loadBanner();*/

        // Initialize the Mobile Ads SDK.
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
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
        adContainerView.post(this::loadBanner);

        /*
        INITIALIZING
         */

        context = PlayListActivity.this;

        /*
        INJECTING DAGGER
         */

        SSBApp.getApp().getRetroComponent().inject(this);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }
        getSupportActionBar().setTitle(getString(R.string.title_playlist));


        Intent in = getIntent();
        songsListData = (ArrayList<HashMap<String, String>>) in.getSerializableExtra(INTENT_DATA);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        AdRequest adRequest2 = new AdRequest.Builder().build();

        mInterstitialAd.loadAd(adRequest2);

//        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);


        ad = sharedpreferences.getString(ADS, "0");


        // ArrayList<String> conString3= in.getStringArrayListExtra(TAG3);

        titre = (TextView) findViewById(R.id.titre_content);
        String fontPath = "fonts/DROIDKUFI-BOLD.TTF";
        // Loading Font Face
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) findViewById(R.id.searchView);

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        // Applying font
        titre.setTypeface(tf);
        titre.setText(getString(R.string.title_content));
        readyToLoadSongs();
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


    @Override
    public boolean onQueryTextChange(String newText) {
        // this is your adapter that will be filtered
        if (!TextUtils.isEmpty(newText)) {
            ArrayList<DownloadableItem> stringList = new ArrayList<>();
            for (DownloadableItem item : downloadableItemList) {
                if (item.getItemTitle().contains(newText)) {
                    stringList.add(item);
                    itemListAdapter = new ItemListAdapter(this, stringList, itemsListView);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                    itemsListView.setLayoutManager(linearLayoutManager);
                    itemsListView.setAdapter(itemListAdapter);
                 //   Log.d("TAG", stringList.get(0).getPosition());
                }
            }
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void readyToLoadSongs() {
        for (int i = 0; i < songsList.size(); i++) {
            HashMap<String, String> song = songsList.get(i);
            songsListData.add(song);
        }
        itemsListView = findViewById(R.id.download_items_list);
        downloadableItemList = getItems();
        itemListAdapter = new ItemListAdapter(this, downloadableItemList, itemsListView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        itemsListView.setLayoutManager(linearLayoutManager);
        itemsListView.setAdapter(itemListAdapter);
    }

    @Override
    public void updateProgress(int position, DownloadProgress downloadProgress) {

    }

    private ArrayList<DownloadableItem> getItems() {
        ArrayList<DownloadableItem> downloadableItems = new ArrayList<>();

        for (int i = 0; i < songsListData.size(); i++) {
            DownloadableItem downloadableItem = new DownloadableItem();
            String itemId = String.valueOf(i);
            downloadableItem.setId(itemId);
            String downloadingStatus = getDownloadStatus(context, itemId);
            downloadableItem.setDownloadingStatus(DownloadingStatus.getValue(downloadingStatus));
            downloadableItem.setItemTitle(songsListData.get(i).get(KEY_SONG_TITLE));
            downloadableItem.setPosition(songsListData.get(i).get(KEY_SONG_POSITION));


            downloadableItem.setItemCoverId(0);
            downloadableItem.setItemDownloadUrl(songsListData.get(i).get(KEY_SONG_PATH));
            String fileName = songsListData.get(i).get(KEY_SONG_PATH).replace(DOWNLOAD_FILE_PREFIX, "");
            /*
            CHECKING IS FILE ALREADY DOWNLOADED
             */
            if (isFileExists(i)) {
                downloadableItem.setItemDownloadPercent(100);
                downloadableItem.setDownloadingStatus(DownloadingStatus.DOWNLOADED);
            } else {
                downloadableItem.setItemDownloadPercent(0);
                downloadableItem.setDownloadingStatus(DownloadingStatus.NOT_DOWNLOADED);
            }
            /*if (utils.getOutputMediaFile(fileName).exists()) {
                downloadableItem.setItemDownloadPercent(100);
                downloadableItem.setDownloadingStatus(DownloadingStatus.DOWNLOADED);
            } else {
                downloadableItem.setItemDownloadPercent(0);
                downloadableItem.setDownloadingStatus(DownloadingStatus.NOT_DOWNLOADED);
            }*/
            downloadableItems.add(downloadableItem);
        }
        return downloadableItems;
    }

    public static String getDownloadStatus(Context context, String itemId) {
        SharedPreferences preferences =
                context.getSharedPreferences(SHARED_PREFERENCES, Context
                        .MODE_PRIVATE);
        return preferences.getString(DOWNLOAD_PREFIX + itemId,
                DownloadingStatus.NOT_DOWNLOADED.getDownloadStatus());
    }

    /*
    FOR RECYCLER ITEM CLICK
     */

//    public interface ClickListener {
//        public void onClick(View view, int position);
//
//        public void onLongClick(View view, int position);
//    }

//    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
//
//        private ClickListener clicklistener;
//        private GestureDetector gestureDetector;
//
//        public RecyclerTouchListener(Context context, final RecyclerView recycleView, final ClickListener clicklistener) {
//
//            this.clicklistener = clicklistener;
//            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
//                @Override
//                public boolean onSingleTapUp(MotionEvent e) {
//                    return true;
//                }
//
//                @Override
//                public void onLongPress(MotionEvent e) {
//                    View child = recycleView.findChildViewUnder(e.getX(), e.getY());
//                    if (child != null && clicklistener != null) {
//                        clicklistener.onLongClick(child, recycleView.getChildAdapterPosition(child));
//                    }
//                }
//            });
//        }
//
//        @Override
//        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
//            View child = rv.findChildViewUnder(e.getX(), e.getY());
//            if (child != null && clicklistener != null && gestureDetector.onTouchEvent(e)) {
//                clicklistener.onClick(child, rv.getChildAdapterPosition(child));
//            }
//
//            return false;
//        }
//
//        @Override
//        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
//
//        }
//
//        @Override
//        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
//
//        }
//    }

    public String getAd() {
        return ad;
    }

    public InterstitialAd getInterstitial() {
        return mInterstitialAd;
    }

    public SharedPreferences.Editor getEditor() {
        return editor;
    }

    public ArrayList<HashMap<String, String>> getSongsListData() {
        return songsListData;
    }

    private boolean isFileExists(int index) {
        boolean isFileExists = false;
        try {
            /*
            OLD FILE NAME
             */
            File oldFile = new File(Environment.getExternalStorageDirectory()
                    .getPath() + "/AndroiSaJa/ElbarakSaJa/" + index + ".mp3");

            /*
            FILE NAME
             */
            String fileName = songsListData.get(index).get("songPath").replace(DOWNLOAD_FILE_PREFIX, "");
            String compareName = Environment.getExternalStorageDirectory()
                    .getPath() + "/AndroiSaJa/ElbarakSaJa/" + songsListData.get(index).get("songTitle") + "_" + fileName;
            if (fileName.contains("/AndroiSaJa/ElbarakSaJa/")) {
                compareName = fileName;
            }
            if (new File(compareName).exists() || oldFile.exists()) {
                isFileExists = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isFileExists;
    }

    /** Called when leaving the activity */
    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    /** Called when returning to the activity */
    @Override
    public void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }

    /** Called before the activity is destroyed */
    @Override
    public void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }

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
