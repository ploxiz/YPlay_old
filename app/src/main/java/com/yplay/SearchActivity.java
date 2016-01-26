package com.yplay;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        SearchView searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                new SearchDataTask().execute(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
    }

    private class SearchDataTask extends AsyncTask<String, Void, ArrayList<SearchObject>> {

        @Override
        protected ArrayList<SearchObject> doInBackground(String... params) {
            Search search = new Search(params[0]);
            return (ArrayList<SearchObject>) search.getResults();
        }

        protected void onPreExecute() {
            // TODO: add loading indicator
        }

        @Override
        protected void onPostExecute(final ArrayList<SearchObject> results) {
            SearchAdapter searchAdapter = new SearchAdapter(getApplicationContext(), results);
            ListView listView = (ListView)  findViewById(R.id.search_listView);
            listView.setAdapter(searchAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String videoId = results.get(position).getId();

                    new RetrieveAudio().execute(videoId);
                }
            });
        }
    }

    private class RetrieveAudio extends AsyncTask<String, Void, Void> {

        final String ANDROID_ID = Settings.Secure.getString(getApplicationContext()
                .getContentResolver(), Settings.Secure.ANDROID_ID);

        static final int RECEIVE_AUDIO_URL = 1;  // The request code

        @Override
        protected Void doInBackground(String... params) {
            String videoId = params[0];

            try {
                URL url = new URL("http://82.196.0.94/do.php?video_id=" + videoId + "&android_id=" + ANDROID_ID);
                InputStream inputStream = url.openStream();

                Intent data = new Intent().putExtra("audio_url", "http://82.196.0.94/audio/" + ANDROID_ID + "/" + videoId + ".mp3");

                inputStream.close();

                if (getParent() == null) {
                    setResult(Activity.RESULT_OK, data);
                } else {
                    getParent().setResult(Activity.RESULT_OK, data);
                }
                finish();
            } catch (MalformedURLException e) {
                System.err.println("Something went wrong: " + e.getMessage());
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

}