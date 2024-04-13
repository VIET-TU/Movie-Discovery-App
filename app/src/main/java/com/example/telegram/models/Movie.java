package com.example.telegram.models;



public class Movie {

    private int id;
    private String title , poster , overview;
    private Double rating;

    private String release_date;

    public int getId() {
        return id;
    }

    public String release_date() {
        return release_date;
    }

    public void setYear_publish(String release_date) {
        this.release_date = release_date;
    }

    public Movie(int id, String title, String poster, String overview, Double rating, String release_date) {
        this.id = id;
        this.title = title;
        this.poster = poster;
        this.overview = overview;
        this.rating = rating;
        this.release_date = release_date;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getTitle() {
        return title;
    }

    public String getPoster() {
        return poster;
    }

    public String getOverview() {
        return overview;
    }

    public Double getRating() {
        return rating;
    }
}