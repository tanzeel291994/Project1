package com.example.tanzeel.project1;


public class Movie_model
{
    public Movie_model(String thumbnail_path,String title,String date, String synopsis,String ratings )
    {
        this.thumbnail_path=thumbnail_path;
        this.title=title;
        this.date=date;
        this.ratings=ratings;
        this.synopsis=synopsis;
    }

    public String thumbnail_path;
    public String title;
    public String date;
    public String synopsis;
    public String ratings;

}
