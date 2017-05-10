package org.catroid.catrobat.newui.data;

import android.graphics.Bitmap;

public class ProjectItem {

    private static final transient int THUMBNAIL_SIZE = 200;
    private String infoText;
    private Bitmap thumbnail;

    public ProjectItem(Bitmap thumbnail, String infoText) {
        this.thumbnail = thumbnail;
        this.infoText = infoText;
    }

    public String getInfoText() {
        return infoText;
    }

    public void setInfoText(String infoText) {
        this.infoText = infoText;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }

}
