package ru.kenguru.driverassistant.DAL.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

public class EventType {
    private long id;
    private String name;

    public EventType(long id, String name) {
        this.name = name;
        this.id = id;
    }

    public EventType getEventTypeById(long id) {
        if (id < 1 || id > 6) return null;
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

    public static List<EventType> getTypes() {
       List<EventType> list = new ArrayList<>();
       list.add(new EventType(1, "Начало маршрута"));
       list.add(new EventType(2, "Завершение маршрута"));
       list.add(new EventType(3, "Прибытие в точку назначения"));
       list.add(new EventType(4, "Убытие из точки назначения"));
       list.add(new EventType(5, "Отключение устройства"));
       list.add(new EventType(6, "Включение устройства"));
       list.add(new EventType(7, "Завершение незаконченного маршрута"));
       return list;
    }
}
