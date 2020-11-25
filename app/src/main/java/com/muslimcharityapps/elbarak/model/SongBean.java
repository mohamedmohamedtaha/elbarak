package com.muslimcharityapps.elbarak.model;

/**
 * Created by ssb on 22/3/17.
 */

public class SongBean {
    private boolean isToDelete;
    private String songTitle;
    private String songPath;
    private String songIndex;
    private boolean isMultiDeleteEnabled;
    private boolean isChecked;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    private String position;

    @Override
    public String toString() {
        return "SongBean{" +
                "isToDelete=" + isToDelete +
                ", songTitle='" + songTitle + '\'' +
                ", songPath='" + songPath + '\'' +
                ", songIndex='" + songIndex + '\'' +
                ", isMultiDeleteEnabled=" + isMultiDeleteEnabled +
                ", isChecked=" + isChecked +
                '}';
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean isMultiDeleteEnabled() {
        return isMultiDeleteEnabled;
    }

    public void setMultiDeleteEnabled(boolean multiDeleteEnabled) {
        isMultiDeleteEnabled = multiDeleteEnabled;
    }

    public String getSongIndex() {
        return songIndex;
    }

    public void setSongIndex(String songIndex) {
        this.songIndex = songIndex;
    }

    public boolean isToDelete() {
        return isToDelete;
    }

    public void setToDelete(boolean toDelete) {
        isToDelete = toDelete;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public String getSongPath() {
        return songPath;
    }

    public void setSongPath(String songPath) {
        this.songPath = songPath;
    }
}
