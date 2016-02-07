package com.yplay.modules.playlists;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.yplay.R;

import java.util.ArrayList;

public class PlaylistAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Playlist> objects;

    private class ViewHolder {
        TextView name;
        // TextView totalDuration;
        // TextView audioCount;
    }

    public PlaylistAdapter(Context context, ArrayList<Playlist> objects) {
        this.context = context;
        this.objects = objects;
    }

    public int getCount() {
        return objects.size();
    }

    public Playlist getItem(int position) {
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
            convertView = inflater.inflate(R.layout.playlists_listview_layout, null);
            holder.name = (TextView) convertView.findViewById(R.id.playlists_name_textview);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText(objects.get(position).getName());

        /*Button addToPlaylistButton = (Button) convertView.findViewById(R.id.search_add_to_playlist_button);
        addToPlaylistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, PlaylistsActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });*/

        return convertView;
    }

}
