package com.yplay.modules.search;

public class SearchObject {

    private String title;
    private String id;
    private String duration;

    public SearchObject(String title, String id, String duration) {
        this.title = title;
        this.id = id;
        this.duration = duration;
    }

    public String getTitle() {
        return this.title;
    }

    public String getId() {
        return this.id;
    }

    public String getDuration(){
        return this.duration;
    }
}