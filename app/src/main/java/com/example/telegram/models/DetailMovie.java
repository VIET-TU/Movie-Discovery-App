package com.example.telegram.models;

import java.util.List;

public class DetailMovie {
    private int id,vote_count;

    private String title,release_date,poster,image_backdrop,overview;

    private List<String> genres;

    public DetailMovie(int id, int vote_count, String title, String release_date, String poster, String image_backdrop, String overview, List<String> genres, Double vote_average) {
        this.id = id;
        this.vote_count = vote_count;
        this.title = title;
        this.release_date = release_date;
        this.poster = poster;
        this.image_backdrop = image_backdrop;
        this.overview = overview;
        this.genres = genres;
        this.vote_average = vote_average;
    }

    private  Double vote_average;

    public int getId() {
        return id;
    }



    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Double getVote_average() {
        return vote_average;
    }

    public void setVote_average(Double vote_average) {
        this.vote_average = vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

}
