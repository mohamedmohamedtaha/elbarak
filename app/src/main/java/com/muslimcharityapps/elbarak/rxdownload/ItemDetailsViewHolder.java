package com.muslimcharityapps.elbarak.rxdownload;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.muslimcharityapps.elbarak.R;
import com.muslimcharityapps.elbarak.utils.Values;


public class ItemDetailsViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener, Values {

    private TextView imageName;
    private SongDownloaderIconView imageDownloadIcon;
    private ImageView itemCoverIcon;
    private DownloadableItem downloadableItem;
    private Context context;
    private ItemDownloadCallback callback;
    private LinearLayout llPlay;

    public ItemDetailsViewHolder(View itemView, Context context, ItemDownloadCallback callback) {
        super(itemView);

        if (itemView == null) {
            return;
        }

        imageName = (TextView) itemView.findViewById(R.id.item_name);
        imageDownloadIcon = (SongDownloaderIconView) itemView.findViewById(R.id.icon_image_download);
//        itemCoverIcon = (ImageView) itemView.findViewById(R.id.icon_item_cover);
        llPlay = (LinearLayout) itemView.findViewById(R.id.llPlay);
        imageDownloadIcon.init();
//        imageDownloadIcon.setOnClickListener(this);
        this.context = context;
        this.callback = callback;
    }

    public void updateImageDetails(DownloadableItem downloadableItem) {
        this.downloadableItem = downloadableItem;
        imageName.setText(downloadableItem.getItemTitle());
//        itemCoverIcon.setImageResource(downloadableItem.getItemCoverId());
        imageDownloadIcon.setItemId(downloadableItem.getId());
        imageDownloadIcon.updateDownloadingStatus(downloadableItem.getDownloadingStatus());

        if (downloadableItem.getDownloadingStatus() == DownloadingStatus.DOWNLOADED) {
            setImageToCompletedState(downloadableItem.getId());
        } else if (downloadableItem.getDownloadingStatus() == DownloadingStatus.IN_PROGRESS &&
                downloadableItem.getItemDownloadPercent()
                        == DOWNLOAD_COMPLETE_PERCENT) {
            setImageToCompletedState(downloadableItem.getId());
            callback.onDownloadComplete();
        } else if (downloadableItem.getDownloadingStatus() == DownloadingStatus.IN_PROGRESS) {
            setImageInProgressState(downloadableItem.getItemDownloadPercent(), downloadableItem.getId());
        }
    }

    @Override
    public void onClick(View v) {
        DownloadingStatus downloadingStatus = imageDownloadIcon.getDownloadingStatus();
        //Only when the icon is in not downloaded state, then do the following.
        if (downloadingStatus == DownloadingStatus.NOT_DOWNLOADED) {
            setImageToWaitingState(downloadableItem.getId());
            callback.onDownloadEnqueued(downloadableItem);
        }
    }

    public void setImageToWaitingState(String itemId) {
        if (!downloadableItem.getId().equalsIgnoreCase(itemId)) {
            return;
        }
        imageDownloadIcon.updateDownloadingStatus(DownloadingStatus.WAITING);
    }

    public void setImageToCompletedState(String itemId) {
        if (!downloadableItem.getId().equalsIgnoreCase(itemId)) {
            return;
        }
        imageDownloadIcon.updateDownloadingStatus(DownloadingStatus.DOWNLOADED);
    }

    public void setImageInProgressState(int progress, String itemId) {
        if (!downloadableItem.getId().equalsIgnoreCase(itemId)) {
            return;
        }
        imageDownloadIcon.updateProgress(context, progress);
        imageDownloadIcon.updateDownloadingStatus(DownloadingStatus.IN_PROGRESS);
    }

    public SongDownloaderIconView getImageDownloadIcon() {
        return imageDownloadIcon;
    }

    public ItemDownloadCallback getCallback() {
        return callback;
    }

    public LinearLayout getLlPlay() {
        return llPlay;
    }
}
