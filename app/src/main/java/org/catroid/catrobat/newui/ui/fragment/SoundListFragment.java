package org.catroid.catrobat.newui.ui.fragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.copypaste.Clipboard;
import org.catroid.catrobat.newui.data.SoundInfo;
import org.catroid.catrobat.newui.db.brigde.SceneBridge;
import org.catroid.catrobat.newui.db.brigde.SoundBridge;
import org.catroid.catrobat.newui.io.PathInfoFile;
import org.catroid.catrobat.newui.io.StorageHandler;
import org.catroid.catrobat.newui.ui.activity.SceneActivity;
import org.catroid.catrobat.newui.ui.activity.SpriteDetailActivity;
import org.catroid.catrobat.newui.ui.adapter.SceneAdapter;
import org.catroid.catrobat.newui.ui.recyclerview.adapter.RecyclerViewAdapter;
import org.catroid.catrobat.newui.ui.adapter.SoundAdapter;
import org.catroid.catrobat.newui.utils.Utils;

import java.util.ArrayList;


public class SoundListFragment extends TabableFragment<SoundInfo> {

    public static final String TAG = SoundListFragment.class.getSimpleName();
    private static final String ARG_SECTION_NUMBER = "section_number_sound_list";
    private SpriteDetailActivity mSpriteDetailActivity;

    public static SoundListFragment newInstance(int sectionNumber) {
        SoundListFragment fragment = new SoundListFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        mSpriteDetailActivity = (SpriteDetailActivity) getActivity();

        setBaseRecyclerListFragmentDelegate(mSpriteDetailActivity);

        return view;
    }

    @Override
    public int getTabNameResource() {
        return R.string.tab_name_sounds;
    }

    @Override
    public RecyclerViewAdapter createAdapter() {
        SoundBridge bridge = new SoundBridge(mSpriteDetailActivity);
        SoundAdapter adapter = new SoundAdapter(mSpriteDetailActivity);

        adapter.startLoading(bridge, mSpriteDetailActivity.getSpriteId());

        return adapter;
		}

    @Override
    protected void addToList(SoundInfo item) {
        mRecyclerViewAdapter.insertItem(item);
    }

    @Override
    protected String getItemName(SoundInfo item) {
        return item.getName();
    }

    @Override
    protected Clipboard.ItemType getItemType() {
        return Clipboard.ItemType.SOUND;
    }

    @Override
    protected SoundInfo copyItem(SoundInfo item) throws Exception {
        String name = Utils.getUniqueSoundName(item.getName(), mRecyclerViewAdapter.getItems());
        PathInfoFile pathInfo = null;
        if (item.getPathInfo() != null) {
            pathInfo = StorageHandler.copyFile(item.getPathInfo());
        }

        return new SoundInfo(name, pathInfo);
    }

    @Override
    protected void cleanupItem(SoundInfo item) throws Exception {
        // Currently nothing to do here!
    }

    @Override
    protected SoundInfo createNewItem(String itemName) {
        String uniqueSoundName = Utils.getUniqueSoundName(itemName,
                mRecyclerViewAdapter.getItems());

        SoundInfo soundInfo = new SoundInfo(uniqueSoundName, null);

        soundInfo.setSpriteId(mSpriteDetailActivity.getSpriteId());

        return soundInfo;
    }

    @Override
    protected void renameItem(SoundInfo item, String itemName) {
        item.setName(itemName);
    }

    @Override
    protected SoundInfo createNewItem(String itemName, PathInfoFile pathInfoFile) {

        String uniqueSoundName = Utils.getUniqueSoundName(itemName,
                mRecyclerViewAdapter.getItems());

        return new SoundInfo(uniqueSoundName, pathInfoFile);
    }
}
