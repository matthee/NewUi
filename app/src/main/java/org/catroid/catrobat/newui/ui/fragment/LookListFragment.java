package org.catroid.catrobat.newui.ui.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.data.LookInfo;
import org.catroid.catrobat.newui.dialog.NewItemDialog;
import org.catroid.catrobat.newui.io.PathInfoFile;
import org.catroid.catrobat.newui.io.StorageHandler;
import org.catroid.catrobat.newui.ui.adapter.LookAdapter;
import org.catroid.catrobat.newui.ui.adapter.RecyclerViewAdapter;
import org.catroid.catrobat.newui.ui.featureDiscovery.SpriteViewFeatureDiscoveryFactory;
import org.catroid.catrobat.newui.ui.featureDiscovery.SpriteViewFeatureDiscoveryManager;
import org.catroid.catrobat.newui.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class LookListFragment extends BaseRecyclerListFragment<LookInfo>
        implements NewItemDialog.NewItemInterface {
    public static final String TAG = LookListFragment.class.getSimpleName();
    private static final String ARG_SECTION_NUMBER = "section_number_look_list";

    public static BaseRecyclerListFragment newInstance(int sectionNumber) {
        BaseRecyclerListFragment fragment = new LookListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (shouldPresentFeatureDiscovery()) {
            SpriteViewFeatureDiscoveryManager.create(this).start();
        }
    }

    @Override
    public int getTabNameResource() {
        return R.string.tab_name_looks;
    }

    @Override
    public RecyclerViewAdapter<LookInfo> createAdapter() {
        //TODO change again
        List<LookInfo> lookInfoList = new ArrayList<LookInfo>();
        for(int i = 0; i < 3; i++) {
            lookInfoList.add( new LookInfo("Item " + i, createImage()));
        }

        return new LookAdapter(lookInfoList, R.layout.list_item);

    }

    @Override
    protected LookInfo copyItem(LookInfo item) throws Exception {
        String name = Utils.getUniqueLookName(item.getName(), mRecyclerViewAdapter.getItems());
        PathInfoFile pathInfo = StorageHandler.copyFile(item.getPathInfo());

        return new LookInfo(name, pathInfo);
    }

    @Override
    protected void cleanupItem(LookInfo item) throws Exception {
        item.cleanup();
    }

    @Override
    protected void renameItem(LookInfo item, String itemName) {
        item.setName(itemName);
    }

    @Override
    protected LookInfo createNewItem(String itemName) {
        String uniqueLookName = Utils.getUniqueLookName(itemName, mRecyclerViewAdapter.getItems());

        PathInfoFile pathInfo = createImage();
        return new LookInfo(uniqueLookName, pathInfo);
    }


    private PathInfoFile createImage() {
        StorageHandler.setupDirectoryStructure();

        String dir = Utils.getImageDirectory().getAbsolutePath();
        File file;
        try {
            file = StorageHandler.getUniqueFile("bg_260.png", dir);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        try {
            file.createNewFile();
        } catch (IOException e) {
            Log.d(TAG, "File not created: " + file.getAbsolutePath());
            e.printStackTrace();
            return null;
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

        InputStream is = getResources().openRawResource(R.raw.bg_260);
        Bitmap bmp = BitmapFactory.decodeStream(is);

        try {
            StorageHandler.exportBitmapToFile(bmp, file);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return new PathInfoFile(Utils.getImageDirectory(), file.getName());
    }

    private boolean shouldPresentFeatureDiscovery() {
        return true;
    }
}
