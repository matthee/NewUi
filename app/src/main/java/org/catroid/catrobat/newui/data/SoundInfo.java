package org.catroid.catrobat.newui.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.copypaste.CopyPasteable;
import org.catroid.catrobat.newui.db.brigde.PersistableRecord;
import org.catroid.catrobat.newui.io.PathInfoDirectory;
import org.catroid.catrobat.newui.io.PathInfoFile;
import org.catroid.catrobat.newui.io.StorageHandler;

import java.io.Serializable;

public class SoundInfo extends ItemInfo implements Serializable, CopyPasteable, PersistableRecord {

    //TODO: uncomment after XStream integration
    //@XStreamAsAttribute
    private long mId;
    private String name;
    private String mFilename;
    private transient PathInfoFile mPathInfo;
    private String duration;
    private long mSpriteId;


    public SoundInfo(String name, PathInfoFile pathInfo) {
        super(name);
        this.mPathInfo = pathInfo;

        //TODO what if the mPathInfo's relative path is not the filename alone?
        if (pathInfo != null) {
						//TODO differentiate between external and local sounds (means: between relative and absolute)

            mFilename = pathInfo.getRelativePath();
            getDurationFromFile();
        }
    }

    public void setPathInfoBitmap(PathInfoFile pathInfoBitmap) {
        this.mPathInfoBitmap = pathInfoBitmap;
        createThumbnail();
    }

    public SoundInfo(SoundInfo srcSoundInfo) throws Exception {
        setName(srcSoundInfo.getName());
        duration = srcSoundInfo.getDuration();
        mPathInfo = StorageHandler.copyFile(srcSoundInfo.getPathInfo());
        //TODO what if the mPathInfo's relative path is not the filename alone?
        mFilename = mPathInfo.getAbsolutePath();
    }

    public SoundInfo() {

    }

    public void initializeAfterDeserialize(PathInfoDirectory parent) {
        mPathInfo = new PathInfoFile(parent, mFilename);
    }

    public PathInfoFile getPathInfo() {
        return mPathInfo;
    }

    public void setPathInfo(PathInfoFile pathInfo) {
        this.mPathInfo = pathInfo;
    }

    public void setAndCopyToPathInfo(PathInfoFile pathInfo) throws Exception {
        StorageHandler.copyFile(mPathInfo, pathInfo);
        setPathInfo(mPathInfo);
    }

    public String getDuration() {
        Long ms = Long.parseLong(duration);
        int min = (int) (((ms / 1000) / 60) % 60);
        int sec = (int) ((ms / 1000)  % 60);
        return String.format("%02d:%02d", min, sec);
    }

    public void deleteFile() throws Exception {
        StorageHandler.deleteFile(mPathInfo);
    }

    private void getDurationFromFile() {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(fileName);
        duration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
    }


    @Override
    public SoundInfo clone() throws CloneNotSupportedException {
        SoundInfo clonedSoundInfo = (SoundInfo) super.clone();

        return clonedSoundInfo;
    }

    @Override
    public void prepareForClipboard() throws Exception {
        if (mPathInfo != null) {
            setAndCopyToPathInfo(PathInfoFile.getUniqueTmpFilePath(mPathInfo));
        }
    }

    @Override
    public void cleanupFromClipboard() throws Exception {
        StorageHandler.deleteFile(mPathInfo);
    }

    @Override
    public Bitmap getBitmap() {
        String imagePath = mPathInfoBitmap.getAbsolutePath();

        if (!StorageHandler.fileExists(imagePath)) {
            return null;
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

        return BitmapFactory.decodeFile(imagePath, options);
		}

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

    public String getFilename() {
        return mFilename;
    }

    public void setFilename(String filename) {
        mFilename = filename;
        mPathInfo = new PathInfoFile(StorageHandler.ROOT_DIRECTORY, filename);
    }

    public void setSpriteId(long spriteId) {
        mSpriteId = spriteId;
    }

    public long getSpriteId() {
        return mSpriteId;
    }
}
