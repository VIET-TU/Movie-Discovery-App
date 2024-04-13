package com.example.telegram.models;

import java.util.ArrayList;
import java.util.List;

public class SliderItems {

    private String poster,title;
    private int id;
    private List<String> genres;

    public SliderItems(String poster, String title, int id, List<String> genres) {
        this.poster = poster;
        this.title = title;
        this.id = id;
        this.genres = genres;
    }

    public String getPoster() {
        return poster;
    }
    public void setPoster(String poster) {
        this.poster = poster;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }
}
