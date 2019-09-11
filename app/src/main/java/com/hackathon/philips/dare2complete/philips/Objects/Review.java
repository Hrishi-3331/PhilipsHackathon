package com.hackathon.philips.dare2complete.philips.Objects;

public class Review {

    private String content;
    private String name;
    private Double rating;

    public Review() {
    }

    public Review(String content, String name, Double rating) {
        this.content = content;
        this.name = name;
        this.rating = rating;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
