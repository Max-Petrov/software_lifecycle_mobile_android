package ru.kenguru.driverassistant.controllers;

public interface IRouteEventController {
    void sendEvent(long eventTypeId, long waypointId);
}
