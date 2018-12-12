package ru.kenguru.driverassistant.DAL;

import ru.kenguru.driverassistant.DAL.localDAO.IEventTypeLocalDao;
import ru.kenguru.driverassistant.DAL.localDAO.ILocationLocalDao;
import ru.kenguru.driverassistant.DAL.localDAO.IRouteEventLocalDao;
import ru.kenguru.driverassistant.DAL.localDAO.IRouteLocalDao;
import ru.kenguru.driverassistant.DAL.localDAO.IRouteStatusLocalDao;
import ru.kenguru.driverassistant.DAL.localDAO.IWaypointLocalDao;
import ru.kenguru.driverassistant.DAL.localDAO.IWaypointStatusLocalDao;
import ru.kenguru.driverassistant.DAL.localDAO.IWaypointTypeLocalDao;

public interface IDatabase {
    ILocationLocalDao getLocationLocalDao();
    IRouteEventLocalDao getEventLocalDao();
    IRouteLocalDao getRouteLocalDao();
    IWaypointLocalDao getWaypointLocalDao();

//    IEventTypeLocalDao getEventTypeLocalDao();
//    IRouteStatusLocalDao getRouteStatusLocalDao();
//    IWaypointStatusLocalDao getWaypointStatusLocalDao();
//    IWaypointTypeLocalDao getWaypointTypeLocalDao();
}
