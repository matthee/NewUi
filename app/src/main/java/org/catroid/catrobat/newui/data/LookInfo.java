package org.catroid.catrobat.newui.data;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;

import org.catroid.catrobat.newui.copypaste.CopyPasteable;
import org.catroid.catrobat.newui.db.brigde.PersistableRecord;
import org.catroid.catrobat.newui.io.PathInfoDirectory;
import org.catroid.catrobat.newui.io.PathInfoFile;
import org.catroid.catrobat.newui.io.StorageHandler;
import org.catroid.catrobat.newui.utils.Utils;

import java.io.Serializable;

public class LookInfo extends ItemInfo implements Serializable, CopyPasteable, PersistableRecord {

    private static final transient int THUMBNAIL_WIDTH = 80;
    private static final transient int THUMBNAIL_HEIGHT = 80;

    //TODO: uncomment after XStream integration
    //@XStreamAsAttribute
    private transient PathInfoFile mPathInfo;
    private transient int width;
    private transient int height;
    private long mId;
    private String mFilename;
    private long mSpriteId;

    public LookInfo(String name, PathInfoFile pathInfo) {
        super(name);
        this.mPathInfo = pathInfo;
        //TODO what if the pathInfo's relative path is not the filename alone?
        mFilename = pathInfo.getRelativePath();


        createThumbnail();
    }

    public LookInfo() {
    }

    public void initializeAfterDeserialize(PathInfoDirectory parent) {
        mPathInfo = new PathInfoFile(parent, mFilename);
    }

    public PathInfoFile getPathInfo() {
        return mPathInfo;
    }

    public void setPathInfo(PathInfoFile pathInfo) {
        mPathInfo = pathInfo;

        createThumbnail();
    }

    public void setAndCopyToPathInfo(PathInfoFile pathInfo) throws Exception {
        StorageHandler.copyFile(mPathInfo, pathInfo);
        setPathInfo(pathInfo);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void cleanup() throws Exception {
        if (StorageHandler.fileExists(mPathInfo.getAbsolutePath())) {
            StorageHandler.deleteFile(mPathInfo);
        }
    }

    @Override
    public Bitmap getBitmap() {
        String imagePath = mPathInfo.getAbsolutePath();

        if (!StorageHandler.fileExists(imagePath)) {
            return null;
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

        return BitmapFactory.decodeFile(imagePath, options);
    }

    @Override
    public LookInfo clone() throws CloneNotSupportedException {
        LookInfo clonedLookInfo = (LookInfo) super.clone();
        setThumbnailDrawable((RoundedBitmapDrawable) clonedLookInfo.getThumbnailDrawable().mutate());
        return clonedLookInfo;
    }

    @Override
    public void prepareForClipboard() throws Exception {
        setAndCopyToPathInfo(PathInfoFile.getUniqueTmpFilePath(mPathInfo));

        createThumbnail();
    }

    @Override
    public void cleanupFromClipboard() throws Exception {
        cleanup();
    }

    @Override
    public void setId(long id) {
        mId = id;
    }

    @Override
    public long getId() {
        return mId;
    }

    @Override
    public void beforeDestroy() {

    }

    @Override
    public void afterDestroy() {

    }

    public void setFilename(String filename) {
        mFilename = filename;
        mPathInfo = new PathInfoFile(Utils.getImageDirectory(), filename);

        createThumbnail();
    }

    public void loadThumbnail() {
        createThumbnail();
    }

    public String getFilename() {
        return mFilename;
    }

    public void setSpriteId(long spriteId) {
        mSpriteId = spriteId;
    }

    public long getSpriteId() {
        return mSpriteId;
    }
}
