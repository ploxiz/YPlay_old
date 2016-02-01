package com.yplay.modules.search;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.yplay.R;
import com.yplay.modules.playlist.PlaylistActivity;

import java.util.ArrayList;

public class SearchAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<SearchObject> objects;

    private class ViewHolder {
        TextView title;
        TextView duration;
    }

    public SearchAdapter(Context context, ArrayList<SearchObject> objects) {
        this.context = context;
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
        LayoutInflater inflater = LayoutInflater.from(context);
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

        Button addToPlaylistButton = (Button) convertView.findViewById(R.id.add_to_playlist_button);
        addToPlaylistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, PlaylistActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });

        return convertView;
    }

}