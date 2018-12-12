package ru.kenguru.driverassistant.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import ru.kenguru.driverassistant.DAL.entities.Location;
import ru.kenguru.driverassistant.DAL.entities.Waypoint;
import ru.kenguru.driverassistant.DriverAssistantApp;
import ru.kenguru.driverassistant.MainActivity;
import ru.kenguru.driverassistant.R;
import ru.kenguru.driverassistant.controllers.IRouteController;
import ru.kenguru.driverassistant.controllers.LocationController;
import ru.kenguru.driverassistant.others.DateController;
import ru.kenguru.driverassistant.others.PreferencesEditor;

public class RouteService extends Service {

    private final long MIN_DISTANCE = 0;
    private final double ACCURACY = 0.0001;

    private Location lastLocation = null;

    private boolean nextFinish = false;

    private double nextWaypointLatitude;
    private double nextWaypointLongitude;

    private long interval;

    private LocationManager locationManager = null;

    private DateController dateController;

    private IRouteController routeController;

    public RouteService() {
    }

    private void showNotification(String title, String content) {
        Intent resultIntent = new Intent(this, MainActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(content)
                        .setAutoCancel(true)
                        .setPriority(2)
                        .setContentIntent(resultPendingIntent);

        Notification notification = builder.build();

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }

    private void loadNextPointCoords() {
        Waypoint w = routeController.getCurrentWaypoint();
        if (w != null) {
            nextWaypointLatitude = w.getPointLatitude();
            nextWaypointLongitude = w.getPointLongitude();
        }
        else {
            nextWaypointLatitude = routeController.getCurrentRoute().getRoute().getFinishPointLatitude();
            nextWaypointLongitude = routeController.getCurrentRoute().getRoute().getFinishPointLongitude();
            nextFinish = true;
        }
    }

    private boolean checkStopRadius(Location location) {
        if (Math.abs(location.getLatitude() - nextWaypointLatitude) <= ACCURACY
                && Math.abs(location.getLongitude() - nextWaypointLongitude) <= ACCURACY) {
            if (lastLocation != null && Math.abs(location.getLatitude() - lastLocation.getLatitude()) <= ACCURACY
                    && Math.abs(location.getLongitude() - lastLocation.getLongitude()) <= ACCURACY) {
                return true;
            }
        }
        return false;
    }

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(android.location.Location location) {
            Location newLocation = new Location(1, location.getLatitude(), location.getLongitude(),
                    dateController.format(Calendar.getInstance().getTime()));

            Waypoint w = routeController.getCurrentWaypoint();

            if (w.getArriveTimeFact() == null) {
                if (checkStopRadius(newLocation)) {
                    String title;
                    String content;
                    // Напомнить о прибытии в точку (промежуточную или конечную)
                    if (nextFinish) {
                        title = getApplicationContext().getResources().getString(R.string.finish_route_notification_title);
                        content = getApplicationContext().getResources().getString(R.string.finish_route_notification_content);
                    }
                    else {
                        title = getApplicationContext().getResources().getString(R.string.arrive_notification_title);
                        content = getApplicationContext().getResources().getString(R.string.arrive_notification_content);
                    }
                    showNotification(title, content);
                }
            }
            else if (w.getLeaveTimeFact() == null) {
                if (!checkStopRadius(newLocation)) {
                    // Напомнить о выбытии с точки
                    String title = getApplicationContext().getResources().getString(R.string.leave_notification_title);
                    String content = getApplicationContext().getResources().getString(R.string.leave_notification_content);
                    showNotification(title, content);
                }
            }

            // Отправить местоположение
            LocationController.getInstance().sendLocation(newLocation);
            lastLocation = newLocation;
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    private void initializeLocationManager() {
        if (locationManager == null) {
            locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        routeController = DriverAssistantApp.getRouteController();
        loadNextPointCoords();
        dateController = new DateController();
        interval = PreferencesEditor.getInstance().readLocationTimePeriod();
        initializeLocationManager();
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    interval, MIN_DISTANCE, locationListener);
        } catch (java.lang.SecurityException ex) {
            Toast.makeText(getApplicationContext(),"Отсутствуют необходимые разрешения", Toast.LENGTH_SHORT).show();
        } catch (IllegalArgumentException ex) {
            Toast.makeText(getApplicationContext(),"Отсутствуют необходимые разрешения", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if (locationManager != null) {
            try {
                locationManager.removeUpdates(locationListener);
            } catch (Exception ex) {
                Toast.makeText(getApplicationContext(),"Не удалось удалить слушатель", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


}
