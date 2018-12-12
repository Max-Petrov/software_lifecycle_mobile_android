package ru.kenguru.driverassistant.DAL.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.kenguru.driverassistant.others.DateController;

public class RouteStatus {
    private long id;
    private String name;

    public RouteStatus(long id, String name) {
        this.name = name;
        this.id = id;
    }

    public static RouteStatus getRouteStatusById(long id) {
        if (id < 1 || id > 4) return null;
        return getStatuses().get((int)(id - 1));
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

    public static List<RouteStatus> getStatuses() {
        List<RouteStatus> list = new ArrayList<>();
        list.add(new RouteStatus(1, "Назначен"));
        list.add(new RouteStatus(2, "Выполняется"));
        list.add(new RouteStatus(3, "Выполнен"));
        list.add(new RouteStatus(4, "Выполнен с опозданием"));
        list.add(new RouteStatus(5, "Не выполнен"));
        return list;
    }
}
