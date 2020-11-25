package com.muslimcharityapps.elbarak.rxdownload;

import java.util.HashMap;

public class DownloadableItem {

  private String id;
  private long downloadId;
  private String itemTitle;
  private int itemCoverId;
  private DownloadingStatus downloadingStatus;
  private String itemDownloadUrl;
  private int itemDownloadPercent;
  private String position;
  private String key;

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getPosition() {
    return position;
  }

  public void setPosition(String position) {
    this.position = position;
  }

  private long lastEmittedDownloadPercent = -1;
  private double itemTotalSize;

  public HashMap<String, String> getSong() {
    return song;
  }

  public void setSong(HashMap<String, String> song) {
    this.song = song;
  }

  private HashMap<String, String> song = new HashMap<>();
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getItemTitle() {
    return itemTitle;
  }

  public void setItemTitle(String itemTitle) {
    this.itemTitle = itemTitle;
  }

  public int getItemCoverId() {
    return itemCoverId;
  }

  public void setItemCoverId(int itemCoverId) {
    this.itemCoverId = itemCoverId;
  }

  public DownloadingStatus getDownloadingStatus() {
    return downloadingStatus;
  }

  public void setDownloadingStatus(DownloadingStatus downloadingStatus) {
    this.downloadingStatus = downloadingStatus;
  }

  public String getItemDownloadUrl() {
    return itemDownloadUrl;
  }

  public void setItemDownloadUrl(String itemDownloadUrl) {
    this.itemDownloadUrl = itemDownloadUrl;
  }

  public int getItemDownloadPercent() {
    return itemDownloadPercent;
  }

  public void setItemDownloadPercent(int itemDownloadPercent) {
    this.itemDownloadPercent = itemDownloadPercent;
  }



  public long getLastEmittedDownloadPercent() {
    return lastEmittedDownloadPercent;
  }

  public void setLastEmittedDownloadPercent(long lastEmittedDownloadPercent) {
    this.lastEmittedDownloadPercent = lastEmittedDownloadPercent;
  }

  public long getDownloadId() {
    return downloadId;
  }

  public void setDownloadId(long downloadId) {
    this.downloadId = downloadId;
  }

  public double getItemTotalSize() {
    return itemTotalSize;
  }

  public void setItemTotalSize(double itemTotalSize) {
    this.itemTotalSize = itemTotalSize;
  }

  public void setSong(String songTitle, String s) {
  }
}
