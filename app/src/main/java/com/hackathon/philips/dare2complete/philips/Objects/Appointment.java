package com.hackathon.philips.dare2complete.philips.Objects;

public class Appointment {
    private String hospital;
    private String date;

    public Appointment() {
    }

    public Appointment(String hospital, String date) {
        this.hospital = hospital;
        this.date = date;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
