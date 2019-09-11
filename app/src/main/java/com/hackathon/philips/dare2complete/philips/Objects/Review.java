package com.hackathon.philips.dare2complete.philips.Objects;

public class Review {

    private String Content;
    private String rater_id;
    private Double rating;

    public Review() {
    }

    public Review(String content, String rater_id, Double rating) {
        Content = content;
        this.rater_id = rater_id;
        this.rating = rating;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getRater_id() {
        return rater_id;
    }

    public void setRater_id(String rater_id) {
        this.rater_id = rater_id;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
