package com.muslimcharityapps.elbarak.rxdownload;

public class DownloadableResult {

    private int percent;
    private int downloadStatus;
    private float totalSize;

    public float getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(float totalSize) {
        this.totalSize = totalSize;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public int getDownloadStatus() {
        return downloadStatus;
    }

    public void setDownloadStatus(int downloadStatus) {
        this.downloadStatus = downloadStatus;
    }

}
