package com.muslimcharityapps.elbarak.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.muslimcharityapps.elbarak.ItemListAdapter;
import com.muslimcharityapps.elbarak.R;
import com.muslimcharityapps.elbarak.adapter.CustomAdapter;
import com.muslimcharityapps.elbarak.adapter.DownlaodAdapter;
import com.muslimcharityapps.elbarak.model.SongBean;
import com.muslimcharityapps.elbarak.rxdownload.DownloadableItem;
import com.muslimcharityapps.elbarak.utils.Values;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;



public class PlayListDownloaded extends AppCompatActivity implements SearchView.OnQueryTextListener, Values {
    // Songs list
    private ArrayList<SongBean> songsList = new ArrayList<>();
    private ListView listView;
    TextView titre;
    //  private CustomAdapter simpleAdapter;
    private NiftyDialogBuilder dialogBuilder;
    private Context context;
    private LinearLayout llDelete;
    private ArrayList<HashMap<String, String>> songsListData;
    private DownlaodAdapter itemListAdapter;

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
        MobileAds.initialize(this, initializationStatus -> {});

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

        /*
        INITIALIZING
         */

        context = PlayListDownloaded.this;

        /*
        FETCHING DATA
         */

        songsListData = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra(INTENT_DATA);

        /*
        MULTIPLE DELETE
         */
        llDelete = findViewById(R.id.llDelete);
        llDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuilder = NiftyDialogBuilder.getInstance(PlayListDownloaded.this);
                dialogBuilder
                        .isCancelableOnTouchOutside(false)
                        .withMessage(getString(R.string.msg_delete_song))
                        .withEffect(Effectstype.Fliph)
                        .withButton1Text(getString(R.string.lbl_yes))
                        .withButton2Text(getString(R.string.lbl_no))
                        .setButton1Click(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                for (int i = 0; i < songsList.size(); i++) {
                                    if (songsList.get(i).isToDelete()) {
                                        deleteSong(i, true);
                                    }
                                }
                                itemListAdapter.notifyDataSetChanged();
                                if (songsList.size() <= 0) {
                                    llDelete.setVisibility(View.GONE);
                                } else {
                                    llDelete.setVisibility(View.VISIBLE);
                                }
                                dialogBuilder.dismiss();
                                dialogBuilder.cancel();
                            }
                        }).setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogBuilder.dismiss();
                    }
                }).show();
            }
        });

        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }



        getSupportActionBar().setTitle(getString(R.string.title_playlist_downloaded));

        titre = findViewById(R.id.titre_content);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = findViewById(R.id.searchView);


        /*SearchManager searchManager = getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = findViewById(R.id.searchView);*/

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);
        // Font path
        titre.setText(getString(R.string.title_playlist_downloaded));
        String fontPath = "fonts/DROIDKUFI-BOLD.TTF";

        // Loading Font Face
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        // Applying font
        titre.setTypeface(tf);

        listView = findViewById(R.id.listview);
        listView.setVisibility(View.VISIBLE);


        listView = findViewById(R.id.listview);
        listView.setVisibility(View.VISIBLE);


        listView.setTextFilterEnabled(true);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < songsList.size(); i++) {
                    SongBean songBean = songsList.get(i);
                    songBean.setMultiDeleteEnabled(true);
                    if (i == position) {
                        songBean.setChecked(true);
                    }
                    songsList.set(i, songBean);
                }
                llDelete.setVisibility(View.VISIBLE);
                itemListAdapter.notifyDataSetChanged();
                return false;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    File home = new File(MEDIA_PATH);
                    final File file_xStore = new File(home,songsList.get(position).getSongTitle() + ".mp3");
                    Log.d("TAG", "file_xStore " + file_xStore);
                    int songIndex = 0;
                    //Integer.parseInt(songsList.get(position).getPosition());

                    if (songsList.get(position).getSongIndex().contains("_")) {
                        songsList.get(position).getPosition();
                        songIndex = Integer.parseInt(songsList.get(position).getSongIndex().split("_")[1]) - 1;
                        songsList.get(position).getSongPath();
                        Log.d("TAG", "songIndex " +songIndex);
                        Log.d("TAG", "getSongPath " +songsList.get(position).getSongPath());
                        Log.d("TAG", "songsList.get(position).getPosition() " +songsList.get(position).getPosition());
                    } else {
                     //   songIndex = Integer.parseInt(songsList.get(position).getSongIndex());
                        songsList.get(position).getSongPath();
                        songsList.get(position).getSongTitle();
                        Log.d("TAG", "songIndex two " +songIndex);
                        Log.d("TAG", "position two " +songsList.get(position).getPosition());

                        Log.d("TAG", "getSongPath two " +songsList.get(position).getSongPath());
                    }
                    Intent in = new Intent(getApplicationContext(), Dashboard.class);
                   // in.putExtra(SP_SONG_INDEX, songIndex);
                   in.putExtra(KEY_SONG_PATH,songsList.get(position).getSongPath());
                   // in.putExtra(KEY_SONG_POSITION, songsList.get(position).toString());
                    in.putExtra(KEY_SONG_TITLE,songsList.get(position).getSongTitle());
                    setResult(100, in);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("TAG", e.getMessage());
                }
            }
        });
        /*
        GETTING SONGS FROM DEVICE
         */
        provideSongListOberservable().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(provideSongListObserver());
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

    private void deleteSong(int position, boolean isMultipleDelete) {
        File dir = new File(Environment.getExternalStorageDirectory() + "/AndroiSaJa/AlhussariwarchSaJa/");
        File file = new File(Environment.getExternalStorageDirectory() + "/AndroiSaJa/AlhussariwarchSaJa/" + songsList.get(position).getSongIndex() + ".mp3");
        boolean deleted = file.delete();

        if (deleted) {
            songsList.remove(position);
            if (!isMultipleDelete) {
                itemListAdapter.notifyDataSetChanged();
                Toast.makeText(context, getString(R.string.msg_song_deleted), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, getString(R.string.msg_delete_failed), Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public boolean onQueryTextChange(String newText) {
        // this is your adapter that will be filtered
        if (!TextUtils.isEmpty(newText)) {
            ArrayList<SongBean> stringList = new ArrayList<>();
            for (SongBean item : songsList) {
                if (item.getSongTitle().contains(newText)) {
                    stringList.add(item);
                    itemListAdapter = new DownlaodAdapter(stringList, context);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                    //listView.setLa(linearLayoutManager);
                    //listView.setVisibility(View.VISIBLE);
                    listView.setAdapter(itemListAdapter);

                    Log.d("TAG", stringList.get(0).getSongIndex());
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
    /*
    RX FOR STORAGE SONG LIST
     */
    private Observable<ArrayList<SongBean>> provideSongListOberservable() {
        return new Observable<ArrayList<SongBean>>() {
            @Override
            protected void subscribeActual(Observer<? super ArrayList<SongBean>> observer) {
                File home = new File(MEDIA_PATH);
                String[] values = getResources().getStringArray(R.array.array_songs);
                ArrayList<SongBean> songsList = new ArrayList<>();
                if (home.listFiles(new FileExtensionFilter()).length > 0) {
                    for (File file : home.listFiles(new FileExtensionFilter())) {
                        try {
                            SongBean song = new SongBean();
                            int songIndex = Integer.parseInt(file.getName().substring(0, (file.getName().length() - 4)));

                            song.setSongIndex(String.valueOf(songIndex));
                         //   song.setSongTitle(songsListData.get(songIndex).get(KEY_SONG_TITLE));
                            song.setPosition(String.valueOf(songIndex));
                            song.setSongPath(file.getPath());
                            // Adding each song to SongList
                            songsList.add(song);
                        } catch (Exception e) {
                            try {
                                SongBean song = new SongBean();
//                                song.setSongTitle(values[Integer.parseInt(file.getName().split("_")[1].substring(0,
//                                        (file.getName().split("_")[1].length() - 4))) - 1]);
                                song.setSongTitle(file.getName().substring(0,(file.getName().length() - 8)));
                                String getNumberSong = file.getName().substring(0,(file.getName().length() - 4));
                                song.setSongIndex(getNumberSong.substring(getNumberSong.length() - 3));
                                song.setPosition(getNumberSong.substring(getNumberSong.length() - 3));

                                //song.setPosition(file.getName().get(songIndex).get(KEY_SONG_POSITION));

                                // song.setSongIndex(file.getName().substring(0,(file.getName().length() - 4)));
                                //song.setPosition(Integer.parseInt(song.getSongIndex()));

                                song.setSongPath(file.getPath());
                             //   song.setPosition(song.getSongIndex());
                                Log.d("TAG","e : " + e.getMessage());
                                Log.d("TAG","getSongTitle one: " +  song.getSongTitle());
                                Log.d("TAG", "getSongIndex one: " + song.getSongIndex());
                                Log.d("TAG", "getSongPath one: " + song.getSongPath());
                                Log.d("TAG", "getPosition one: " + song.getPosition());
                                // Adding each song to SongList
                                songsList.add(song);
                            } catch (Exception e1) {

                            }
                        }
                    }
                }
                observer.onNext(songsList);
            }
        };
    }

    private DisposableObserver<ArrayList<SongBean>> provideSongListObserver() {
        return new DisposableObserver<ArrayList<SongBean>>() {
            @Override
            public void onNext(ArrayList<SongBean> value) {
                songsList = value;
                itemListAdapter = new DownlaodAdapter(songsList, context);
                listView.setAdapter(itemListAdapter);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    class FileExtensionFilter implements FilenameFilter {
        public boolean accept(File dir, String name) {
            return (name.endsWith(".mp3") || name.endsWith(".MP3"));
        }
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
