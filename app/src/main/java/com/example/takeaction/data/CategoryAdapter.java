package com.example.takeaction.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.takeaction.R;
import com.example.takeaction.model.Category;

import java.util.List;

public class CategoryAdapter extends ArrayAdapter<Category> {
    private final LayoutInflater mInflater;
    private final Context mContext;
    private final List<Category> items;
    private final int mResource;

    public CategoryAdapter(@NonNull Context context, int resource, @NonNull List<Category> objects) {
        super(context, resource, objects);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mResource = resource;
        items = objects;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    private View createItemView(int position, View convertView, ViewGroup parent) {
        final View view = mInflater.inflate(mResource, parent, false);
        ImageView listIcon = (ImageView) view.findViewById(R.id.category_icon);
        TextView listItem = (TextView) view.findViewById(R.id.category_name);

        Category categoryData = items.get(position);

        listIcon.setId(categoryData.getIcon());
        listItem.setText(categoryData.getName());
                listIcon.setImageDrawable(listIcon.getResources().getDrawable(categoryData.getIcon()));

        return view;
    }

    @Override
    public int getCount() {
        return items.size();
    }
}
