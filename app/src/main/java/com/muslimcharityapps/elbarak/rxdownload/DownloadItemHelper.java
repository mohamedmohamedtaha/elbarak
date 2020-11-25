package com.muslimcharityapps.elbarak.rxdownload;

import android.content.Context;
import android.content.SharedPreferences;

import com.muslimcharityapps.elbarak.utils.Values;

public class DownloadItemHelper implements Values {

    public static DownloadableItem getItem(Context context, DownloadableItem
            downloadableItem) {
        if (context == null || downloadableItem == null) {
            return downloadableItem;
        }
        /*String downloadingStatus = DownloadItemHelper.getDownloadStatus(context, downloadableItem.getId());
        int downloadPercent = DownloadItemHelper.getDownloadPercent(context, downloadableItem.getId());
        downloadableItem.setDownloadingStatus(DownloadingStatus.getValue(downloadingStatus));
        downloadableItem.setItemDownloadPercent(downloadPercent);*/
        return downloadableItem;
    }

    public static String getDownloadStatus(Context context, String itemId) {
        SharedPreferences preferences =
                context.getSharedPreferences(SHARED_PREFERENCES, Context
                        .MODE_PRIVATE);
        return preferences.getString(DOWNLOAD_PREFIX + itemId,
                DownloadingStatus.NOT_DOWNLOADED.getDownloadStatus());
    }

    public static void persistItemState(Context context, DownloadableItem downloadableItem) {
        DownloadItemHelper.setDownloadPercent(context, downloadableItem.getId(),
                downloadableItem.getItemDownloadPercent());
        DownloadItemHelper.setDownloadStatus(context, downloadableItem.getId(),
                downloadableItem.getDownloadingStatus());
    }

    public static void setDownloadStatus(Context context, String itemId, DownloadingStatus
            downloadingStatus) {
        /*SharedPreferences preferences =
                context.getSharedPreferences(SHARED_PREFERENCES, Context
                        .MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(DOWNLOAD_PREFIX + itemId, downloadingStatus.getDownloadStatus());
        editor.commit();*/
    }

    public static void setDownloadPercent(Context context, String itemId, int percent) {
        /*SharedPreferences preferences =
                context.getSharedPreferences(SHARED_PREFERENCES, Context
                        .MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(PERCENT_PREFIX + itemId, percent);
        editor.commit();*/
    }

    public static int getDownloadPercent(Context context, String itemId) {
        SharedPreferences preferences =
                context.getSharedPreferences(SHARED_PREFERENCES, Context
                        .MODE_PRIVATE);
        return preferences.getInt(PERCENT_PREFIX + itemId, 0);
    }
}
