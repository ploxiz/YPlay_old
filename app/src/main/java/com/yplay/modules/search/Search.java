package com.yplay.modules.search;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Search {

    private static List<SearchObject> results = new ArrayList<>();

    public Search(String keyword) {
        query(keyword);
    }

    private static void query(String keyword) {
        String url = "https://www.youtube.com/results?lclk=video&filters=video&q=" + keyword;

        try {
            Document source = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36").get();
            Elements videos = source.select("h3.yt-lockup-title ");

            for (Element video : videos) {
                String title = video.select("a").attr("title");
                String duration = video.select("a").attr("href").split("=")[1];
                String id = video.select("span").text().split(" ")[2];

                results.add(new SearchObject(title, duration, id.substring(0, id.length() - 1)));
            }
        } catch (IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<SearchObject> getResults() {
        return results;
    }

    public static void main(String[] args) {
        query("asd");
        int i = 0;
        for (SearchObject result : results) {
            System.out.println(++i + " | " + result.getTitle() + " | " + result.getDuration() + " | " + result.getId());
        }
    }

}