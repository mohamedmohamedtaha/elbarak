package com.muslimcharityapps.elbarak.communicator;
import com.muslimcharityapps.elbarak.model.DownloadProgress;

/**
 * Created by ssb on 21/3/17.
 */

public interface DownloadCommunicator {
    void updateProgress(int position, DownloadProgress downloadProgress);
}
