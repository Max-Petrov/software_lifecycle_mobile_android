package ru.kenguru.driverassistant.DAL.entities;

import java.util.ArrayList;
import java.util.List;

public class WaypointType {
    private long id;
    private String name;

    public WaypointType(long id, String name) {
        this.name = name;
        this.id = id;
    }

    public static WaypointType getWaypointTypeById(long id) {
        if (id < 1 || id > 3) return null;
        return getTypes().get((int)(id - 1));
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

    public static List<WaypointType> getTypes() {
        List<WaypointType> list = new ArrayList<>();
        list.add(new WaypointType(1, "Погрузка"));
        list.add(new WaypointType(2, "Отгрузка"));
        list.add(new WaypointType(3, "Возвращение на базу"));
        return list;
    }
}
