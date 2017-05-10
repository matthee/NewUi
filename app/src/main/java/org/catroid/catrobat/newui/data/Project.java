package org.catroid.catrobat.newui.data;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class Project {

    private static final transient int THUMBNAIL_SIZE = 200;
    private int mId;
    private String mInfoText;
    private Bitmap mThumbnail;
    private List<Scene> mSceneList = new ArrayList<>();;

    public Project(int id, Bitmap thumbnail, String infoText) {
        this.mThumbnail = thumbnail;
        this.mInfoText = infoText;
        this.mId = id;
    }

    public String getInfoText() {
        return mInfoText;
    }

    public void setInfoText(String infoText) {
        this.mInfoText = infoText;
    }

    public Bitmap getThumbnail() {
        return mThumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.mThumbnail = thumbnail;
    }

    public int getId() {
        return mId;
    }

    public void setSceneList(List<Scene> sceneList) {
        if (sceneList != null) {
            this.mSceneList = sceneList;
        }
    }

    public List<Scene> getSceneList() {
        return this.mSceneList;
    }
}
