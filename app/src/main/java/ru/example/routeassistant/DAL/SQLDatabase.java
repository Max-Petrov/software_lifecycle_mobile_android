package ru.kenguru.driverassistant.DAL;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import ru.kenguru.driverassistant.DAL.entities.EventType;
import ru.kenguru.driverassistant.DAL.entities.Location;
import ru.kenguru.driverassistant.DAL.entities.Route;
import ru.kenguru.driverassistant.DAL.entities.RouteEvent;
import ru.kenguru.driverassistant.DAL.entities.RouteStatus;
import ru.kenguru.driverassistant.DAL.entities.Waypoint;
import ru.kenguru.driverassistant.DAL.entities.WaypointStatus;
import ru.kenguru.driverassistant.DAL.entities.WaypointType;

@Database(entities = {Location.class, Route.class,
        RouteEvent.class, Waypoint.class}, version = 1)
public abstract class SQLDatabase extends RoomDatabase implements IDatabase {
}