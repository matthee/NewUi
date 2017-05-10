package org.catroid.catrobat.newui.data;


import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class Scene {
    public String name;

    public List<Sprite> mSpriteList = new ArrayList<>();

    public Bitmap getThumbnail() {
        Bitmap thumbnail = null;

        if (mSpriteList.size() > 0) {
            thumbnail = mSpriteList.get(0).getThumbnail();
        }

        return thumbnail;
    }

    public void setSpriteList(List<Sprite> spriteList) {
        if (spriteList != null) {
            this.mSpriteList = spriteList;
        }
    }

    public List<Sprite> getSpriteList() {
        return mSpriteList;
    }
}
