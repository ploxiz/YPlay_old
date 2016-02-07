package com.yplay.modules.playlists;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Playlist {

    private String name;

    private List<HashMap<String, String>> audio = new ArrayList<>();

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void addAudio(String title, String id) {
        HashMap<String, String> map = new HashMap<>();
        map.put("title", title);
        map.put("id", id);

        audio.add(map);
    }

    public List<HashMap<String, String>> getAudio() {
        return this.audio;
    }

}
