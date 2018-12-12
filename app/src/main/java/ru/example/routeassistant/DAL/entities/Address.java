package ru.kenguru.driverassistant.DAL.entities;

public class Address {
    private String stringAddress;
    private double latitude;
    private double longitude;

    public Address(String stringAddress, double latitude, double longitude) {
        this.stringAddress = stringAddress;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getStringAddress() {
        return stringAddress;
    }

    public void setStringAddress(String stringAddress) {
        this.stringAddress = stringAddress;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
