package com.example.telegram.models;

public class Film {
    private int id,vote_count;

    private String title,release_date,poster;
    private  Double vote_average;

    public int getId() {
        return id;
    }

    public Film(int id, String title, String release_date, String poster, Double vote_average, int vote_count) {
        this.id = id;
        this.title = title;
        this.release_date = release_date;
        this.poster = poster;
        this.vote_average = vote_average;
        this.vote_count = vote_count;

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
