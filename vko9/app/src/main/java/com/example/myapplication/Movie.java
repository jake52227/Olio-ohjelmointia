package com.example.myapplication;

public class Movie {
    private String showDate;
    private String name;
    private String theatre;
    private String showStart;
    private String showEnd;

    public Movie(String showDate, String name, String theatre, String showStart, String showEnd) {
        this.showDate = showDate;
        this.name = name;
        this.theatre = theatre;
        this.showStart = showStart;
        this.showEnd = showEnd;
    }

    public String getShowStart() {
        return this.showStart;
    }

    public String getShowEnd() {
        return this.showEnd;
    }

    public String getTheatreID() {
        return this.theatre;
    }

    public String getName() {
        return this.name;
    }

    public String getShowDate() {
        return this.showDate;
    }
}
