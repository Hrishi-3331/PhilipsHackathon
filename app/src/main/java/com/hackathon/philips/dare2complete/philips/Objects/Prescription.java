package com.hackathon.philips.dare2complete.philips.Objects;

public class Prescription {
    private String doctor;
    private String hospital;
    private String remark;
    private String date_start;
    private String date_end;

    public Prescription() {
    }

    public Prescription(String doctor, String hospital, String remark, String date_start, String date_end) {
        this.doctor = doctor;
        this.hospital = hospital;
        this.remark = remark;
        this.date_start = date_start;
        this.date_end = date_end;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDate_start() {
        return date_start;
    }

    public void setDate_start(String date_start) {
        this.date_start = date_start;
    }

    public String getDate_end() {
        return date_end;
    }

    public void setDate_end(String date_end) {
        this.date_end = date_end;
    }
}
