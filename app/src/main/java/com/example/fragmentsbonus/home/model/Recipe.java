package com.example.fragmentsbonus.home.model;

public class Recipe {
    private String title;
    private String description;
    private String time;
    private float rating;
    private double price;
    private int imageResource;

    public Recipe(String title, String description, String time, float rating, double price, int imageResource) {
        this.title = title;
        this.description = description;
        this.time = time;
        this.rating = rating;
        this.price = price;
        this.imageResource = imageResource;
    }

    public String getTitle() {
        return title;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}