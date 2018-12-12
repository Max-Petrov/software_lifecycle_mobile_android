package ru.kenguru.driverassistant.presentation.navigator;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import ru.kenguru.driverassistant.R;
import ru.kenguru.driverassistant.fragments.MapFragment;
import ru.kenguru.driverassistant.fragments.NoRouteFragment;
import ru.kenguru.driverassistant.fragments.RouteFragment;
import ru.kenguru.driverassistant.fragments.SettingsFragment;
import ru.kenguru.driverassistant.fragments.WaypointsFragment;
import ru.kenguru.driverassistant.presentation.contract.IMainContract;

public class MainNavigator implements IMainContract.IMainNavigator {

    private AppCompatActivity activity;
    private Fragment currentFragment;

    public MainNavigator() {
        activity = null;
        currentFragment = null;
    }

    public MainNavigator(AppCompatActivity activity) {
        this.activity = activity;
        currentFragment = null;
    }

    @Override
    public void attach(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void detach() {
        currentFragment = null;
        activity = null;
    }

    @Override
    public void showStartScreen() {
        currentFragment = new RouteFragment();
        showFragment(currentFragment, true);
    }

    @Override
    public void reloadScreen() {
        if (activity != null && currentFragment != null) {
            FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
            transaction.detach(currentFragment);
            transaction.attach(currentFragment);
            transaction.commit();
        }
    }

    @Override
    public void navigateByMenuItem(int itemId) {
        if (activity != null) {
            String title = "";
            switch (itemId) {
                case R.id.nav_route:
                    currentFragment = new RouteFragment();
                    title = activity.getResources().getString(R.string.action_route);
                    break;
                case R.id.nav_waypoints:
                    currentFragment = new WaypointsFragment();
                    title = activity.getResources().getString(R.string.action_waypoints);
                    break;
                case R.id.nav_map:
                    currentFragment = new MapFragment();
                    title = activity.getResources().getString(R.string.action_map);
                    break;
                case R.id.nav_settings:
                    currentFragment = new SettingsFragment();
                    title = activity.getResources().getString(R.string.action_settings);
                    break;
                default:
                    return;
            }
            showFragment(currentFragment, false);
            activity.setTitle(title);
        }
    }

    @Override
    public void navigateToNoRouteScreen() {
        currentFragment = new NoRouteFragment();
        showFragment(currentFragment, true);
    }

    private void showFragment(Fragment fragment, boolean add) {
        if (activity != null && fragment != null) {
            FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
            if (add)
                transaction.add(R.id.container, fragment);
            else
                transaction.replace(R.id.container, fragment);
            transaction.replace(R.id.container, fragment);
            transaction.commit();
        }
    }
}
