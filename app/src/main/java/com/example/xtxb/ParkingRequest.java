package com.example.xtxb;

public class ParkingRequest {
    private String applicantName;
    private String licensePlate;
    private String requestTime;

    public ParkingRequest(String applicantName, String licensePlate, String requestTime) {
        this.applicantName = applicantName;
        this.licensePlate = licensePlate;
        this.requestTime = requestTime;
    }

    // Getters and setters
    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }
}
