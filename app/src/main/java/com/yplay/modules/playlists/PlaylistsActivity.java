package com.yplay.modules.playlists;

import android.content.Context;
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
public class PlaylistsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlists);

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));

        final ListView playlistsListView = (ListView) findViewById(R.id.playlists_listView);

        try {
            populate(playlistsListView, this); // populate the activity with the current playlists (found in xml/playlists.xml)
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    void populate(ListView listView, Context context) throws IOException, XmlPullParserException {
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
        /*for (Playlist playlist : playlists) {
            System.out.println(playlist.getName());
            System.out.println("============================");
            for (int i = 0; i < playlist.getAudio().size(); i++) {
                System.out.println(playlist.getAudio().get(i).get("title"));
                System.out.println(playlist.getAudio().get(i).get("id"));
                System.out.println(" ");
            }
        }*/
        PlaylistAdapter playlistAdapter = new PlaylistAdapter(context, (ArrayList<Playlist>)playlists);
        listView.setAdapter(playlistAdapter);
        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String[] videoInfo = {
                        results.get(position).getId(), // the id of the video
                        results.get(position).getTitle(), // the title of the video
                        null // for future storing of the ANDROID_ID
                };

                new RetrieveAudio().execute(videoInfo);
            }
        });*/

    }

}
