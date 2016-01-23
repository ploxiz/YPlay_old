package com.yplay;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

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
        protected void onPostExecute(ArrayList<SearchObject> results) {
            SearchAdapter searchAdapter = new SearchAdapter(getApplicationContext(), results);
            ListView listView = (ListView)  findViewById(R.id.search_listView);
            listView.setAdapter(searchAdapter);
        }
    }

}