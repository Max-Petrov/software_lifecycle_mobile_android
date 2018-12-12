package ru.kenguru.driverassistant.DAL.localDAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import ru.kenguru.driverassistant.DAL.entities.Location;

@Dao
public interface ILocationLocalDao {
    @Query("SELECT * FROM locations")
    List<Location> getLocations();
    @Insert
    long insertLocation(Location location);
}
