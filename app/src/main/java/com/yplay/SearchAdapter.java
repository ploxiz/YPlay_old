package com.yplay;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<SearchObject> objects;

    private class ViewHolder {
        TextView title;
        TextView duration;
    }

    public SearchAdapter(Context context, ArrayList<SearchObject> objects) {
        inflater = LayoutInflater.from(context);
        this.objects = objects;
    }

    public int getCount() {
        return objects.size();
    }

    public SearchObject getItem(int position) {
        return objects.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.search_listview_layout, null);
            holder.title = (TextView) convertView.findViewById(R.id.title_textView);
            holder.duration = (TextView) convertView.findViewById(R.id.duration_textView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.title.setText(objects.get(position).getTitle());
        holder.duration.setText(objects.get(position).getDuration());
        return convertView;
    }

}