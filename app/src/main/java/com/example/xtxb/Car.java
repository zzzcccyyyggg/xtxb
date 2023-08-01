package com.example.xtxb;

public class Car {
    private String licensePlate;
    private String owner;
    private String parkingTime;
    private String state;

    public Car(String licensePlate, String owner, String parkingTime,String state) {
        this.licensePlate = licensePlate;
        this.owner = owner;
        this.parkingTime = parkingTime;
        this.state = state;
    }

    // Getter methods
    public String getLicensePlate() {
        return licensePlate;
    }

    public String getOwner() {
        return owner;
    }

    public String getParkingTime() {
        return parkingTime;
    }
    public String getState() {
        return state;
    }


    // Setter methods (optional, if you need to modify the values later)
    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setParkingTime(String parkingTime) {
        this.parkingTime = parkingTime;
    }
    public void setState(String state) {
        this.state = state;
    }
}
