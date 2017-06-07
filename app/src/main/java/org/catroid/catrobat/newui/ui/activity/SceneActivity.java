package org.catroid.catrobat.newui.ui.activity;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.data.Scene;
import org.catroid.catrobat.newui.ui.fragment.BaseRecyclerListFragment;
import org.catroid.catrobat.newui.ui.fragment.BaseRecyclerListFragmentDelegate;
import org.catroid.catrobat.newui.ui.fragment.SceneListFragment;

public class SceneActivity extends AppCompatActivity implements BaseRecyclerListFragmentDelegate<Scene> {
    public static final String PROJECT_ID_KEY = "scene_id";
    public static final String PROJECT_NAME_KEY = "scene_name";
    private static final String TAG = SceneActivity.class.getSimpleName();
    private long mProjectId;
    private SceneListFragment mSceneFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_scene);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupFromIntent();

        setupFAB();
        setupRecyclerListFragment();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            long projectId = intent.getLongExtra(PROJECT_ID_KEY, -1);

            if (projectId != -1) {
                mProjectId = projectId;
            }

            Log.d(TAG, "Project Id: " + mProjectId);
        }
    }

    private void setupRecyclerListFragment() {
        mSceneFragment = (SceneListFragment) getSupportFragmentManager().findFragmentById(R.id.scene_fragment);
        mSceneFragment.setBaseRecyclerListFragmentDelegate(this);
    }

    private void setupFAB() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addButtonClicked();
            }
        });
    }

    private void addButtonClicked() {
        mSceneFragment.onAddButtonClicked();
    }

    public long getProjectId() {
        return mProjectId;
    }

    @Override
    public void onItemClicked(BaseRecyclerListFragment<Scene> fragment, Scene scene) {
        Intent scenesActivityIntent = new Intent(this, SpriteActivity.class);

        scenesActivityIntent.putExtra(SpriteActivity.SCENE_ID_KEY, scene.getId());
        scenesActivityIntent.putExtra(SpriteActivity.SCENE_NAME_KEY, scene.getName());

        startActivity(scenesActivityIntent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
