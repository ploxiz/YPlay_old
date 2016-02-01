package com.yplay.modules.playlist;

import java.util.ArrayList;
import java.util.List;

public class Playlist {

    private String name;

    private List<String[]> audio = new ArrayList<>();

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void addAudio(String title, String id) {
        String[] pair = {title, id};
        audio.add(audio.size(), pair);
    }

    public List<String[]> getAudio() {
        return this.audio;
    }

}
