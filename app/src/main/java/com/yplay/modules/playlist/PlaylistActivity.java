package com.yplay.modules.playlist;

import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

import com.yplay.R;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class PlaylistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playlist_activity);

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));

        final ListView playlistsListView = (ListView) findViewById(R.id.playlists_listView);

        try {
            populate(playlistsListView); // populate the activity with the current playlists (found in xml/playlists.xml)
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    void populate(ListView listView) throws IOException, XmlPullParserException {
        List<Playlist> playlists = new ArrayList<>();

        XmlResourceParser xmlResourceParser = getResources().getXml(R.xml.playlists);
        AttributeSet as;

        int eventType = xmlResourceParser.getEventType();
        while (eventType != XmlResourceParser.END_DOCUMENT) {
            if (eventType == XmlResourceParser.START_TAG) {
                if (xmlResourceParser.getName().equals("playlist")) {
                    playlists.add(new Playlist());

                    as = Xml.asAttributeSet(xmlResourceParser);
                    playlists.get(playlists.size() - 1).setName(as.getAttributeValue(0)); // sets the name for each playlist
                }
                else if (xmlResourceParser.getName().equals("audio")) {
                    as = Xml.asAttributeSet(xmlResourceParser);
                    playlists.get(playlists.size() - 1).addAudio(as.getAttributeValue(0), as.getAttributeValue(1));
                }
            }
            eventType = xmlResourceParser.next();
        }

        xmlResourceParser.close();

        // starting the population of the parsed ListView
        // TODO:

    }

}
