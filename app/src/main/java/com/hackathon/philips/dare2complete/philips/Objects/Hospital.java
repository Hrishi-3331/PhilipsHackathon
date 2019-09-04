package com.hackathon.philips.dare2complete.philips.Objects;

public class Hospital {

    private String id;
    private String Address;
    private String Department;
    private String Name;
    private int Rating;
    private String Contact;
    private String Description;
    private String image;

    public Hospital() {
    }

    public Hospital(String id, String address, String department, String name, int rating, String contact, String Description, String image) {
        this.id = id;
        this.Address = address;
        this.Department = department;
        this.Name = name;
        this.Rating = rating;
        this.Contact = contact;
        this.Description = Description;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getRating() {
        return Rating;
    }

    public void setRating(int rating) {
        Rating = rating;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
