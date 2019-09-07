package com.hackathon.philips.dare2complete.philips.Objects;

public class Hospital {
    private String name;
    private String address;
    private String contact;
    private double rating;
    private String image;

    public Hospital() {
    }

    public Hospital(String name, String address, String contact, double rating, String image) {
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.rating = rating;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
