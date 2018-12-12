package ru.kenguru.driverassistant.others;

import android.content.Context;
import android.content.SharedPreferences;

import ru.kenguru.driverassistant.R;

public class PreferencesEditor {
    private Context context;
    private SharedPreferences preferences;

    private static PreferencesEditor instance = null;

    private PreferencesEditor(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(context.getResources()
                .getString(R.string.preferences_file_name), Context.MODE_PRIVATE);
    }

    public static void initInstance(Context context) {
        if (instance == null) {
            instance = new PreferencesEditor(context);
        }
    }

    public static PreferencesEditor getInstance() {
        return instance;
    }

    public void setDeviceId(long id) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(context.getResources().getString(R.string.device_id), id);
        editor.apply();
    }

    public long getDeviceId() {
        return preferences.getLong(context.getResources().getString(R.string.device_id), -1);
    }

    private void writeDbPopulate() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(context.getResources().getString(R.string.db_is_populate), false);
        editor.apply();
    }

    public boolean dbIsPopulate() {
        boolean first = preferences.getBoolean(context.getResources().getString(R.string.db_is_populate), true);
        if (first) {
            writeDbPopulate();
        }
        return first;
    }

    public void writeLocationTimePeriod(long period) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(context.getResources().getString(R.string.location_time_period), period);
        editor.apply();
    }

    public long readLocationTimePeriod() {
        return preferences.getLong(context.getResources().getString(R.string.location_time_period), 30000);
    }
}
