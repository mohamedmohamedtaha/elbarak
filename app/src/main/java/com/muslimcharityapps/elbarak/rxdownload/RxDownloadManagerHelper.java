package com.muslimcharityapps.elbarak.rxdownload;

import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.muslimcharityapps.elbarak.utils.Utilities;
import com.muslimcharityapps.elbarak.utils.Values;

import java.io.File;

import io.reactivex.ObservableEmitter;

public class RxDownloadManagerHelper implements Values {

    private final static int DOWNLOAD_QUERY_DELAY_PARAM = 200;
    private final static int DOWNLOAD_COMPLETE_PERCENT = 100;
    private final static int PERCENT_MULTIPLIER = 100;
    private final static int MIN_DOWNLOAD_PERCENT_DIFF = 3;
    private final static int INVALID_DOWNLOAD_ID = -1;
    private final static String TAG = RxDownloadManagerHelper.class.getSimpleName();

    /**
     * @param downloadManager - Android's Download Manager.
     * @param downloadUrl     - The url of the item to be downloaded.
     * @return - the download id of the download.
     */
    public static long enqueueDownload(Context context,DownloadManager downloadManager,
                                       String downloadUrl, String title) {
        if (downloadManager == null || downloadUrl == null || downloadUrl.equals("")) {
            return INVALID_DOWNLOAD_ID;
        }
        Uri uri = Uri.parse(downloadUrl);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        String fileName = downloadUrl.replace(DOWNLOAD_FILE_PREFIX, "");
        //File file = new Utilities().getOutputMediaFile(fileName);
        request.setDestinationInExternalPublicDir(IMAGE_DIRECTORY_NAME, title + "_" + fileName);
        try {
            return downloadManager.enqueue(request);
        }catch (IllegalArgumentException e ){
            Toast.makeText(context,"Unable to download...",Toast.LENGTH_SHORT).show();
            return 0;
        }
    }

    /**
     * This method will be called upon every 'x' milliseconds to know the percentage of a download.
     * This method will only emit the percent download, if the current percent download is 5%
     * greater than the previous percent download.
     *
     * @param downloadManager
     * @param downloadableItem
     * @param percentFlowableEmiitter
     */
    public static void queryDownloadPercents(final DownloadManager downloadManager,
                                             final DownloadableItem downloadableItem,
                                             final ObservableEmitter percentFlowableEmiitter) {

        //If the emitter has been disposed, then return.
        if (downloadManager == null || downloadableItem == null || percentFlowableEmiitter == null
                || percentFlowableEmiitter.isDisposed()) {
            return;
        }

        long lastEmittedDownloadPercent = downloadableItem.getLastEmittedDownloadPercent();


        DownloadableResult downloadableResult = getDownloadResult(downloadManager, downloadableItem
                .getDownloadId());

        downloadableItem.setItemTotalSize(downloadableResult.getTotalSize());

        if (downloadableResult == null) {
            return;
        }

        //Get the current DownloadPercent and download status
        int currentDownloadPercent = downloadableResult.getPercent();
        int downloadStatus = downloadableResult.getDownloadStatus();
        downloadableItem.setItemDownloadPercent(currentDownloadPercent);
        if ((currentDownloadPercent - lastEmittedDownloadPercent >= MIN_DOWNLOAD_PERCENT_DIFF) ||
                currentDownloadPercent == DOWNLOAD_COMPLETE_PERCENT) {
            percentFlowableEmiitter.onNext(downloadableItem);
            downloadableItem.setLastEmittedDownloadPercent(currentDownloadPercent);
        }
        Log.d(TAG,
                " Querying the DB: DownloadStatus is " + downloadStatus + " and downloadPercent is " +
                        "" + currentDownloadPercent);
        switch (downloadStatus) {
            case DownloadManager.STATUS_FAILED:
                break;

            case DownloadManager.STATUS_SUCCESSFUL:
                break;

            case DownloadManager.STATUS_PENDING:
            case DownloadManager.STATUS_RUNNING:
            case DownloadManager.STATUS_PAUSED:
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        queryDownloadPercents(downloadManager, downloadableItem, percentFlowableEmiitter);
                    }
                }, DOWNLOAD_QUERY_DELAY_PARAM);
                break;

            case DownloadManager.ERROR_FILE_ERROR:
                break;
        }
    }

    /**
     * @param downloadManager - Android's DownloadManager
     * @param downloadId      - The downloadId for which the progress has to be fetched from the db.
     * @return
     */
    private static DownloadableResult getDownloadResult(DownloadManager downloadManager,
                                                        long downloadId) {

        //Create a query with downloadId as the filter.
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(downloadId);

        //Create an instance of downloadable result
        DownloadableResult downloadableResult = new DownloadableResult();

        Cursor cursor = null;
        try {
            cursor = downloadManager.query(query);
            if (cursor == null || !cursor.moveToFirst()) {
                return downloadableResult;
            }
            //Get the download percent
            float bytesDownloaded =
                    cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
            float bytesTotal =
                    cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
            downloadableResult.setTotalSize(bytesTotal);
            int downloadPercent = (int) ((bytesDownloaded / bytesTotal) * PERCENT_MULTIPLIER);
            if (downloadPercent <= DOWNLOAD_COMPLETE_PERCENT) {
                downloadableResult.setPercent(downloadPercent);
            }
            //Get the download status
            int columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
            int downloadStatus = cursor.getInt(columnIndex);
            downloadableResult.setDownloadStatus(downloadStatus);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return downloadableResult;
    }
}
