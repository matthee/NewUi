package org.catroid.catrobat.newui.data;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;

import org.catroid.catrobat.newui.io.PathInfoDirectory;
import org.catroid.catrobat.newui.io.PathInfoFile;
import org.catroid.catrobat.newui.io.StorageHandler;

import java.io.Serializable;

public class LookInfo implements Serializable {

    private static final transient int THUMBNAIL_WIDTH = 80;
    private static final transient int THUMBNAIL_HEIGHT = 80;

    //TODO: uncomment after XStream integration
    //@XStreamAsAttribute
    private String name;
    private String fileName;
    private transient PathInfoFile pathInfo;
    private transient int width;
    private transient int height;
    private transient Bitmap mThumbnail;
    private RoundedBitmapDrawable mThumbnailDrawable;

    public LookInfo(String name, PathInfoFile pathInfo) {
        this.name = name;
        this.pathInfo = pathInfo;
        //TODO what if the pathInfo's relative path is not the filename alone?
        fileName = pathInfo.getRelativePath();

        createThumbnail();
    }

    public void initializeAfterDeserialize(PathInfoDirectory parent) {
        pathInfo = new PathInfoFile(parent, fileName);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PathInfoFile getPathInfo() {
        return pathInfo;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Bitmap getThumbnail() {
        return mThumbnail;
    }

    public void cleanup() throws Exception {
        StorageHandler.deleteFile(pathInfo);
    }

    public Bitmap getBitmap() {
        String imagePath = pathInfo.getAbsolutePath();

        if (!StorageHandler.fileExists(imagePath)) {
            return null;
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

        return BitmapFactory.decodeFile(imagePath, options);
    }

    public Drawable getRoundedDrawable() {
        if (mThumbnailDrawable == null) {
            Bitmap thumbnail = getThumbnail();

            mThumbnailDrawable = RoundedBitmapDrawableFactory.create(Resources.getSystem(), thumbnail);
            mThumbnailDrawable.setCircular(true);
        }

        return mThumbnailDrawable;
    }


    private void createThumbnail() {
        Bitmap bigImage = getBitmap();
        mThumbnail = ThumbnailUtils.extractThumbnail(bigImage, THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT);
    }
}
