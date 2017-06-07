package org.catroid.catrobat.newui.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.ui.adapter.SpriteViewPagerAdapter;

public class SpriteDetailActivity extends AppCompatActivity {

    public static final String TAG = SpriteDetailActivity.class.getSimpleName();
    public static final String SPRITE_ID_KEY = "scene_id";
    public static final String SPRITE_NAME_KEY = "scene_name";

    SpriteViewPagerAdapter mSpriteViewPagerAdapter;
    ViewPager mViewPager;
    private long mSpriteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recycler_view);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSpriteViewPagerAdapter = new SpriteViewPagerAdapter(this);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSpriteViewPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mSpriteViewPagerAdapter.onPageSelected(position);
            }
        });

        setupFromIntent();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddButtonClicked();
            }
        });

        ActivityCompat.requestPermissions(SpriteDetailActivity.this, new String[]
                {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
    }

    private void setupFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            mSpriteId = intent.getLongExtra(SPRITE_ID_KEY, -1);

            Log.d(TAG, "Setting sprite id: " + mSpriteId);

            if (mSpriteId == -1) {
                throw new UnsupportedOperationException();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.main_menu, menu);

        return true;
    }

    private void onAddButtonClicked() {
        mSpriteViewPagerAdapter.onAddButtonClicked();
    }

    public long getSpriteId() {
        return mSpriteId;
    }
}
