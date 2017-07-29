package com.example.astatic.newsapp.utilities;

/**
 * Created by static on 6/29/2017.
 */

public class newsItem {



    private String Author;
    private String title;
    private String Description;
    private String URL;
    private String URLtoImge;
    private String PublishedAt;


    public newsItem(String Author, String title, String Description, String URL, String URLtoImge, String PublishedAt){

        this.title = title;
        this.Description = Description;
        this.URL = URL;
        this.Author=Author;
        this.PublishedAt = PublishedAt;
        this.URLtoImge=URLtoImge;

    }

    public String getAuthor() {

        return Author;
    }

    public void setAuthor(String author)
    {
        Author = author;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription()
    {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getURLtoImge() {
        return URLtoImge;
    }

    public void setURLtoImge(String URLtoImge) {
        this.URLtoImge = URLtoImge;
    }

    public String getPublishedAt() {
        return PublishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        PublishedAt = publishedAt;
    }
}