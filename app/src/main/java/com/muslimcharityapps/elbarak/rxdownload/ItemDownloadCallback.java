package com.muslimcharityapps.elbarak.rxdownload;

public interface ItemDownloadCallback {

  void onDownloadEnqueued(DownloadableItem downloadableItem);

  void onDownloadStarted(DownloadableItem downloadableItem);

  void onDownloadComplete();
}
