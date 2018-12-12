package ru.kenguru.driverassistant.DAL;

import android.arch.persistence.room.Room;
import android.content.Context;

import ru.kenguru.driverassistant.DAL.entities.Location;
import ru.kenguru.driverassistant.DAL.entities.Route;
import ru.kenguru.driverassistant.DAL.entities.RouteEvent;
import ru.kenguru.driverassistant.DAL.entities.Waypoint;
import ru.kenguru.driverassistant.others.PreferencesEditor;

public class SQLRepository implements ILocalRepository {
    private static SQLRepository instance = null;
    private static SQLDatabase database = null;

    private SQLRepository(Context context) {
        database = Room.databaseBuilder(context, SQLDatabase.class, "driver_assistant_db")
                .allowMainThreadQueries() // Временно
                .build();
        prepareDb();
    }

    public static void initInstance(Context context) {
        if (instance == null) {
            instance = new SQLRepository(context);
        }
    }

    public static SQLRepository getInstance() {
        return instance;
    }

    public SQLDatabase getDatabase() {
        return database;
    }

    private void prepareDb() {
        PreferencesEditor editor = PreferencesEditor.getInstance();
        if (editor.dbIsPopulate()) {
            //database.getRouteLocalDao().insertRoute(Route.getRoutes().get(0));
            //database.getWaypointLocalDao().insertWaypoints(Waypoint.getWaypoints());
            //database.getLocationLocalDao().insertLocation(Location.getTest().get(0));
            //database.getEventLocalDao().insertEvent(RouteEvent.getEvents().get(0));
        }
    }
}
