package ru.kenguru.driverassistant.DAL.localDAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import ru.kenguru.driverassistant.DAL.entities.RouteEvent;

@Dao
public interface IRouteEventLocalDao {
    @Query("SELECT * FROM route_events")
    List<RouteEvent> getEvents();
    @Insert
    long insertEvent(RouteEvent routeEvent);
}
