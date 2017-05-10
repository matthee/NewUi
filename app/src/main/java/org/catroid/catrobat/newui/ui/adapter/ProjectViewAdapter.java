package org.catroid.catrobat.newui.ui.adapter;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.data.Project;

import java.util.ArrayList;
import java.util.List;

public class ProjectViewAdapter extends BaseAdapter {

    private List<Project> mProjectItems = new ArrayList<>();

    public ProjectViewAdapter() {
    }

    public Boolean addItem(int id, Bitmap image, String infoText) {
        try {
            mProjectItems.add(new Project(id, image, infoText));
            return true;
        } catch (Exception exception) {
            Log.wtf("EXCEPTION ", exception.getMessage());
            return false;
        }
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View gridView;

        if (convertView == null) {

            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.project_item, null);

            // set image based on selected text
            ImageView imageView = (ImageView) gridView
                    .findViewById(R.id.project_image_view);

            imageView.setImageResource(mProjectItems.get(position).getId());

        } else {
            gridView = convertView;
        }

        return gridView;
    }
}
