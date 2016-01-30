package com.yplay;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class PlaylistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playlist_activity);

        populate(); // populate the acitivty with the current playlists (found in xml/playlists.xml)
    }

    void populate() {

    }
}
