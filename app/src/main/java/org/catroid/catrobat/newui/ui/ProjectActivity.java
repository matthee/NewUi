package org.catroid.catrobat.newui.ui;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.GridView;
import android.widget.Toast;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.data.Constants;
import org.catroid.catrobat.newui.data.ProjectItem;
import org.catroid.catrobat.newui.ui.adapter.ProjectViewAdapter;
import org.catroid.catrobat.newui.ui.adapter.WebViewManager;

import java.util.ArrayList;

public class ProjectActivity extends AppCompatActivity {

    private WebView mWebView;
    private GridView mGridView;
    private ProjectViewAdapter mProjectViewAdapter;
    private ArrayList<ProjectItem> mProjectItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.project_activity_view);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActivityCompat.requestPermissions(ProjectActivity.this, new String[]
                {Manifest.permission.INTERNET}, 1);
        ActivityCompat.requestPermissions(ProjectActivity.this, new String[]
                {Manifest.permission.ACCESS_WIFI_STATE}, 1);
        ActivityCompat.requestPermissions(ProjectActivity.this, new String[]
                {Manifest.permission.ACCESS_NETWORK_STATE}, 1);

        mWebView = (WebView) findViewById(R.id.webview);
        try {
            if (!(WebViewManager.loadFromURL(mWebView, Constants.PROJECT_NEWS_URL, this))) {
                Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show();
            } else {
                DisplayMetrics mDisplayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);

                mWebView.getLayoutParams().height = mDisplayMetrics.heightPixels / 4;


            }
        } catch (Exception exception) {
            mWebView.setVisibility(View.GONE);
            mWebView.getLayoutParams().height = 0;
            Toast.makeText(this, "NOT connected !!!", Toast.LENGTH_LONG).show();
        }


        Constants.PROJECT_IMAGE_SIZE = getSizeForGridViewImages();

        mProjectViewAdapter = new ProjectViewAdapter(this, R.layout.project_item, mProjectItems);

        mGridView = (GridView) findViewById(R.id.project_gridview);
        mGridView.setAdapter(mProjectViewAdapter);

        // Fill in Test-Data
        for (int i = 0; i < 12; i++) {
            String text = "Item " + i;
            if(addNewProjectItem(R.drawable.blue_test_pic, text))
            {
                mProjectViewAdapter.notifyDataSetChanged();
            }
            else
            {
                Toast.makeText(this, "Could not add File: " + text, Toast.LENGTH_LONG).show();
            }
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(), "Replace with Action",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    private int getSizeForGridViewImages()
    {
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);

        float density = getApplicationContext().getResources().getDisplayMetrics().density;
        float dp = (mDisplayMetrics.widthPixels * 1.5f) / density;

        return (int) dp;
    }

    private Boolean addNewProjectItem(int res_id, String project_info) {

        try {
            Bitmap image = BitmapFactory.decodeResource(this.getResources(),
                    res_id);
            Bitmap.createScaledBitmap(image, Constants.PROJECT_IMAGE_SIZE,
                    Constants.PROJECT_IMAGE_SIZE, false);

            mProjectItems.add(mProjectItems.size(), new ProjectItem(image, project_info));
        }
        catch (Exception ex)
        {
            Log.wtf("ADD NEW PROJECT ", ex.getMessage());
            return false;
        }

        return true;
    }

}
