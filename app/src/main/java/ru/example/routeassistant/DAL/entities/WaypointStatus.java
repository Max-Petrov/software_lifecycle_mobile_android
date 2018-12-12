package ru.kenguru.driverassistant.DAL.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.kenguru.driverassistant.others.DateController;

public class WaypointStatus {
    private long id;
    private String name;

    public WaypointStatus(long id, String name) {
        this.name = name;
        this.id = id;
    }

    public static WaypointStatus getWaypointStatusById(long id) {
        if (id < 2 || id > 5) return null;
        return getStatuses().get((int)(id) - 2);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<WaypointStatus> getStatuses() {
        List<WaypointStatus> list = new ArrayList<>();
        list.add(new WaypointStatus(2, "Назначено"));
        list.add(new WaypointStatus(3, "Выполнено"));
        list.add(new WaypointStatus(4, "Выполнено с опозданием"));
        list.add(new WaypointStatus(5, "Не выполнено"));
        return list;
    }
}
