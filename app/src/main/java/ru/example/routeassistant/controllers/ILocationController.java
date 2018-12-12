package ru.kenguru.driverassistant.controllers;

import android.content.Context;

import ru.kenguru.driverassistant.DAL.entities.Location;

public interface ILocationController {
    void changePeriodSettings(long period);
    long getPeriodSettings();
    Location getCurrentLocation();
    void sendLocation(Location location);
}
