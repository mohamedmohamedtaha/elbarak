package com.muslimcharityapps.elbarak;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.muslimcharityapps.elbarak.activity.Dashboard;
import com.muslimcharityapps.elbarak.activity.PlayListActivity;
import com.muslimcharityapps.elbarak.rxdownload.DownloadItemHelper;
import com.muslimcharityapps.elbarak.rxdownload.DownloadRequestsSubscriber;
import com.muslimcharityapps.elbarak.rxdownload.DownloadableItem;
import com.muslimcharityapps.elbarak.rxdownload.DownloadingStatus;
import com.muslimcharityapps.elbarak.rxdownload.ItemDetailsViewHolder;
import com.muslimcharityapps.elbarak.rxdownload.ItemDownloadCallback;
import com.muslimcharityapps.elbarak.rxdownload.ItemDownloadPercentObserver;
import com.muslimcharityapps.elbarak.rxdownload.ItemPercentCallback;
import com.muslimcharityapps.elbarak.rxdownload.RxDownloadManagerHelper;
import com.muslimcharityapps.elbarak.utils.Values;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

//import androidx.recyclerview.widget.RecyclerView;


public class ItemListAdapter extends RecyclerView.Adapter implements
        ItemDownloadCallback, ItemPercentCallback, Values {

    private ArrayList<DownloadableItem> itemsList;

    private int currentDownloadsCount = 0;
    private final DownloadManager downloadManager;
    private static final String TAG = ItemListAdapter.class.getSimpleName();
    private final ItemDownloadPercentObserver mItemDownloadPercentObserver;
    private final DownloadRequestsSubscriber mDownloadRequestsSubscriber;
    private final WeakReference<Context> contextWeakReference;
    private final RecyclerView recyclerView;
    private Context context;
    private final String ADS = "ads";
    private String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public ItemListAdapter(Context context, ArrayList<DownloadableItem> downloadableItemList,
                           RecyclerView recyclerView) {
        this.context = context;
        this.itemsList = downloadableItemList;
        this.downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        this.contextWeakReference = new WeakReference(context);
        this.recyclerView = recyclerView;

        //Observable for percent of individual downloads.
        mItemDownloadPercentObserver = new ItemDownloadPercentObserver(this);
        //Observable for download request
        mDownloadRequestsSubscriber = new DownloadRequestsSubscriber(this);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_image_details, parent, false);
        ItemDetailsViewHolder itemDetailsViewHolder = new ItemDetailsViewHolder(view, contextWeakReference.get(), this);
        return itemDetailsViewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (!(holder instanceof ItemDetailsViewHolder)) {
            return;
        }

        /*
        SETTING UP DOWNLOAD CLICK LISTENER
         */
        ((ItemDetailsViewHolder) holder).getImageDownloadIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkForPermission(position, holder);
            }
        });

        final DownloadableItem downloadableItem = DownloadItemHelper.getItem(contextWeakReference
                .get(), itemsList.get(position));
        ((ItemDetailsViewHolder) holder).updateImageDetails(downloadableItem);

        /*
        SETTING UP PLAY CLICK LISTENER
         */

        ((ItemDetailsViewHolder) holder).getLlPlay().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isToPlaySong = true;
                if (!isOnline() && !downloadableItem.getDownloadingStatus().equals(DownloadingStatus.DOWNLOADED)) {
                    isToPlaySong = false;
                }

                if (isToPlaySong) {
                    int songIndex  = 0;
                    try {

                        songIndex = Integer.valueOf(itemsList.get(position).getPosition());
                    }catch (Exception e){
                        Log.e("TAG","Error Item List Adapter : " + e.getMessage() + " : " + e.getStackTrace());
                    }
                    if (((PlayListActivity) context).getAd().equalsIgnoreCase("0")) {
                        if (((PlayListActivity) context).getInterstitial().isLoaded()) {
                            ((PlayListActivity) context).getInterstitial().show();
                            ((PlayListActivity) context).getEditor().putString(ADS, "1");
                            ((PlayListActivity) context).getEditor().commit();
                        }
                    }
                    Intent in = new Intent(context, Dashboard.class);
                    in.putExtra(SP_SONG_INDEX, songIndex);
                    in.putExtra(INTENT_DATA, ((PlayListActivity) context).getSongsListData());
                    ((PlayListActivity) context).setResult(100, in);
                    ((PlayListActivity) context).finish();

                } else {
                    Toast.makeText(context, context.getString(R.string.error_play_song), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * This callback is called when the user clicks on any item for download.
     *
     * @param downloadableItem - Item to be downloaded.
     */
    public void onDownloadEnqueued(DownloadableItem downloadableItem) {
        mDownloadRequestsSubscriber.emitNextItem(downloadableItem);
    }

    @Override
    public int getItemCount() {
        if (itemsList == null) {
            return 0;
        }
        return itemsList.size();
    }

    /**
     * This callback is called when the item starts getting downloaded.
     *
     * @param downloadableItem - Item to be downloaded.
     */
    @Override
    public void onDownloadStarted(DownloadableItem downloadableItem) {
        //Increment the current number of downloads by 1
        currentDownloadsCount++;
        String downloadUrl = downloadableItem.getItemDownloadUrl();
        long downloadId =
                RxDownloadManagerHelper.enqueueDownload(context, downloadManager, downloadUrl, downloadableItem.getItemTitle());
        Log.d("TAG",downloadUrl + " : " + downloadableItem.getItemTitle());

        if (downloadId == INVLALID_ID) {
            return;
        }
        downloadableItem.setDownloadId(downloadId);
        downloadableItem.setDownloadingStatus(DownloadingStatus.IN_PROGRESS);
        updateDownloadableItem(downloadableItem);
        RxDownloadManagerHelper.queryDownloadPercents(downloadManager, downloadableItem,
                mItemDownloadPercentObserver.getPercentageObservableEmitter());
    }

    @Override
    public void onDownloadComplete() {
        //Decrement the current number of downloads by 1
        currentDownloadsCount--;
        mDownloadRequestsSubscriber.requestSongs(MAX_COUNT_OF_SIMULTANEOUS_DOWNLOADS -
                currentDownloadsCount);
    }


    public void performCleanUp() {
        Log.d(TAG, "performing clean up of the resources");
        mItemDownloadPercentObserver.performCleanUp();
        mDownloadRequestsSubscriber.performCleanUp();
    }

    @Override
    public void updateDownloadableItem(DownloadableItem downloadableItem) {
        /*if (downloadableItem == null || contextWeakReference.get() == null) {
            return;
        }

        DownloadItemHelper.persistItemState(contextWeakReference.get(), downloadableItem);

        int position = Integer.parseInt(downloadableItem.getId()) - 1;
        ItemDetailsViewHolder itemDetailsViewHolder = (ItemDetailsViewHolder)
                recyclerView.findViewHolderForLayoutPosition(position);*/

        //It means that the viewholder is not currently displayed.
        /*if (itemDetailsViewHolder == null) {
            if (downloadableItem.getItemDownloadPercent() == DOWNLOAD_COMPLETE_PERCENT) {
                downloadableItem.setDownloadingStatus(DownloadingStatus.DOWNLOADED);
                DownloadItemHelper.persistItemState(contextWeakReference.get(), downloadableItem);
                onDownloadComplete();
            }
            return;
        }
        itemDetailsViewHolder.updateImageDetails(downloadableItem);*/
    }

    private void checkForPermission(int position, RecyclerView.ViewHolder holder) {
        if (
                (
                        (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                                &&
                                (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)

                )) {
            downloadSong(position, holder);
        } else {
            requestPermission();
        }
    }

    public void requestPermission() {
        ActivityCompat.requestPermissions((Activity) context, permissions, PERMISSION_REQUEST_CODE);
    }

    private void downloadSong(int position, RecyclerView.ViewHolder holder) {
       /* DownloadableItem downloadableItem = DownloadItemHelper.getItem(contextWeakReference
                .get(), itemsList.get(position));*/
        DownloadableItem downloadableItem = itemsList.get(position);
        /*DownloadingStatus downloadingStatus = ((ItemDetailsViewHolder) holder).getImageDownloadIcon().getDownloadingStatus();*/
        DownloadingStatus downloadingStatus = downloadableItem.getDownloadingStatus();
        //Only when the icon is in not downloaded state, then do the following.
        if (downloadingStatus == DownloadingStatus.NOT_DOWNLOADED) {
            downloadableItem.setDownloadingStatus(DownloadingStatus.WAITING);
            ((ItemDetailsViewHolder) holder).getCallback().onDownloadEnqueued(downloadableItem);
            itemsList.set(position, downloadableItem);
            notifyDataSetChanged();
        }
    }

    public void refreshData(ArrayList<DownloadableItem> data) {
        itemsList = data;
        notifyDataSetChanged();
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
