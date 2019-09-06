package com.hackathon.philips.dare2complete.philips.Objects;

public class Doctor {
    private String name;
    private String image;
    private int rating;
    private String hospital;
    private String degree;
    private String specialisation;

    public Doctor() {
    }

    public Doctor(String name, String image, int rating, String hospital, String degree, String specialisation) {
        this.name = name;
        this.image = image;
        this.rating = rating;
        this.hospital = hospital;
        this.degree = degree;
        this.specialisation = specialisation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getSpecialisation() {
        return specialisation;
    }

    public void setSpecialisation(String specialisation) {
        this.specialisation = specialisation;
    }
}
