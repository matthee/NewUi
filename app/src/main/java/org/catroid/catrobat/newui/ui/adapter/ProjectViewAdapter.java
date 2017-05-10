package org.catroid.catrobat.newui.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.data.Constants;
import org.catroid.catrobat.newui.data.ProjectItem;

import java.util.ArrayList;

public class ProjectViewAdapter extends ArrayAdapter {

    private ArrayList<ProjectItem> objects;

    public ProjectViewAdapter(Context context,
                              int textViewResourceId,
                              ArrayList<ProjectItem> objects) {
        super(context, textViewResourceId, objects);

        this.objects = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            v = inflater.inflate(R.layout.project_item, null);
        }

        ProjectItem tmp = objects.get(position);

        if (tmp != null) {
            ImageView imgView = (ImageView) v.findViewById(R.id.project_image_view);
            TextView txtView = (TextView) v.findViewById(R.id.project_title_view);

            if (imgView != null) {
                imgView.getLayoutParams().height = Constants.PROJECT_IMAGE_SIZE;
                imgView.getLayoutParams().width = Constants.PROJECT_IMAGE_SIZE;
                imgView.setImageBitmap(tmp.getThumbnail());
            }

            if (txtView != null) {
                txtView.setWidth(Constants.PROJECT_IMAGE_SIZE);
                txtView.setBackgroundColor(Color.BLACK);
                txtView.getBackground().setAlpha(123);
                txtView.setText(tmp.getInfoText());
            }
        }

        return v;
    }
}
