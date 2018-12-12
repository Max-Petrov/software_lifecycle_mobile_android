package ru.kenguru.driverassistant.presentation.contract;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;


public interface IMainContract {
    interface IView {
        void setBtnEventEnabled(boolean enable);
        void setBtnEventCaption(String caption);
        void showVehicleNumberInputDialog();
        void showOdometerInputDialog();
        void showFragment(Fragment fragment);
    }

    interface IPresenter {
        void attach(IView view, IMainNavigator navigator);
        void detach();
        void onDrawerItemSelected(int itemId);
        void onBtnEventClick();
        void onVehicleNumberInputDialogResult(String vehicleNumber);
        void onOdometerInputDialogResult(String odometerInfo);
    }

    interface IMainNavigator {
        void attach(AppCompatActivity activity);
        void detach();
        void showStartScreen();
        void reloadScreen();
        void navigateByMenuItem(int itemId);
        void navigateToNoRouteScreen();
    }
}
