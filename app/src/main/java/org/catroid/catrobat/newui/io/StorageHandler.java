package org.catroid.catrobat.newui.io;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import org.catroid.catrobat.newui.utils.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public final class StorageHandler {

    public static final String TAG = StorageHandler.class.getSimpleName();

    public static final String ROOT = Environment.getExternalStorageDirectory().toString()
            + File.separator + "NewUi";
    public static final String IMAGE_FOLDER = "images";
    public static final String SOUND_FOLDER = "sounds";
    public static final String TMP_FOLDER = "tmp";

    public static final PathInfoDirectory ROOT_DIRECTORY = new PathInfoDirectory(ROOT);

    private static final String FILE_NAME_APPENDIX = "_#";


    public static void exportBitmapToFile(Bitmap bitmap, File file) throws IOException {
        FileOutputStream os = new FileOutputStream(file);
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, os);
        os.flush();
        os.close();
    }

    public static PathInfoFile copyFile(PathInfo srcPathInfo) throws Exception {
        String srcPath = srcPathInfo.getAbsolutePath();
        File dstFile = copyFile(srcPath);

        return new PathInfoFile(srcPathInfo.getParent(), dstFile.getName());
    }

    public static PathInfoFile copyFile(PathInfoFile srcPathInfo,
                                        PathInfoDirectory dstDirectoryInfo)
            throws Exception {
        String srcPath = srcPathInfo.getAbsolutePath();
        String dstPath = dstDirectoryInfo.getAbsolutePath();
        File dstFile = copyFile(srcPath, dstPath);

        return new PathInfoFile(dstDirectoryInfo, dstFile.getName());
    }

    public static File copyFile(PathInfoFile sourceFileInfo, PathInfoFile dstFileInfo) throws Exception {
        File srcFile = new File(sourceFileInfo.getAbsolutePath());
        File dstFile = new File(dstFileInfo.getAbsolutePath());

        if (!srcFile.exists()) {
            throw new Exception("Source file not found: " + sourceFileInfo.getAbsolutePath());
        }

        if (dstFile.exists()) {
            throw new Exception("Destination file already exists: " + dstFileInfo.getAbsolutePath());
        }


        copyFile(srcFile, dstFile);

        return dstFile;
    }

    private static File copyFile(String srcPath) throws Exception {
        String dstPath = new File(srcPath).getParent();
        return copyFile(srcPath, dstPath);
    }

    private static File copyFile(String srcPath, String dstPath) throws Exception {
        File srcFile = new File(srcPath);
        if (!srcFile.exists()) {
            throw new FileNotFoundException("File: " + srcPath + "does not exist.");
        }

        File dstFile = getUniqueFile(srcFile.getName(), dstPath);
        copyFile(srcFile, dstFile);

        return dstFile;
    }

    public static synchronized File getUniqueFile(String originalName, String dstDirectory)
            throws Exception {
        int extensionStartIndex = originalName.lastIndexOf(".");

        int appendixStartIndex = originalName.lastIndexOf(FILE_NAME_APPENDIX);
        if (appendixStartIndex == -1) {
            appendixStartIndex = extensionStartIndex;
        }

        String extension = originalName.substring(extensionStartIndex);
        String fileName = originalName.substring(0, appendixStartIndex);

        int appendix = 0;

        while (appendix < Integer.MAX_VALUE) {
            String dstFileName = fileName + FILE_NAME_APPENDIX + String.valueOf(appendix) +
                    extension;
            File dstFile = new File(dstDirectory, dstFileName);
            if (!dstFile.exists()) {
                return dstFile;
            }
            appendix++;
        }

        throw new Exception("Could not find a unique file name in " + dstDirectory + ".");
    }

    private static void copyFile(File srcFile, File dstFile) throws IOException {
        FileChannel ic = new FileInputStream(srcFile).getChannel();
        FileChannel oc = new FileOutputStream(dstFile).getChannel();

        try {
            ic.transferTo(0, ic.size(), oc);
        } finally {
            if (ic != null) {
                ic.close();
            }
            if (oc != null) {
                oc.close();
            }
        }
    }

    public static void deleteFile(PathInfo srcPathInfo) throws IOException {
        deleteFile(srcPathInfo.getAbsolutePath());
    }

    private static void deleteFile(String srcPath) throws IOException {
        File file = new File(srcPath);
        if (!file.exists()) {
            throw new FileNotFoundException("File: " + srcPath + " does not exist.");
        }
        if (!file.delete()) {
            throw new IOException("File: " + srcPath + "could not be deleted");
        }
    }

    public static boolean fileExists(String path) {
        File file = new File(path);
        return file.exists();
    }

    public static void setupDirectoryStructure() {
        mkDir(ROOT);
        mkDir(ROOT, IMAGE_FOLDER);
        mkDir(ROOT, SOUND_FOLDER);
        mkDir(ROOT, TMP_FOLDER);
    }

    private static void mkDir(String name) {
        File directory = new File(name);
        if (!directory.exists()) {
            if (!directory.mkdir()) {
                Log.e(TAG, "Directory NOT created! " + directory.getAbsolutePath());
            }
        }
    }

    private static void mkDir(String parentDirectory, String name) {
        File directory = new File(parentDirectory, name);

        if (!directory.exists()) {
            if (!directory.mkdir()) {
                Log.e(TAG, "Directory NOT created! " + directory.getAbsolutePath());
            }
        }
    }

    public static PathInfoFile createImage(Bitmap bitmap, String itemName) {
        if(bitmap == null) {
            return null;
        }

        StorageHandler.setupDirectoryStructure();

        String dir = Utils.getImageDirectory().getAbsolutePath();
        File file;
        try {
            file = StorageHandler.getUniqueFile(itemName + ".png", dir);

        } catch (Exception e) {
            Log.d(TAG, "Image file not created");
            e.printStackTrace();
            return null;
        }

        try {
            StorageHandler.exportBitmapToFile(bitmap, file);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return new PathInfoFile(Utils.getImageDirectory(), file.getName());
    }

    public static PathInfoFile createSound(Uri uri, String itemName) {
        if(uri == null) { return null; }

        String uri_ = uri.getPath();
        return new PathInfoFile(Utils.getSoundDirectory(), uri.getEncodedPath());
    }
}
