package ru.kenguru.driverassistant.others;

import ru.kenguru.driverassistant.DAL.entities.Location;
import ru.kenguru.driverassistant.DAL.entities.RouteWithWaypoints;

public interface IMapGenerator {
    String getMapWebViewPage(RouteWithWaypoints route, Location location);
}
