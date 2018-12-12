package ru.kenguru.driverassistant.presentation.presenter.main;

import android.app.Activity;

import ru.kenguru.driverassistant.presentation.contract.IMainContract;
import ru.kenguru.driverassistant.presentation.contract.IMainContract.IMainNavigator;

public class MainPresenter implements IMainContract.IPresenter {

    private IMainContract.IView view;
    private IMainNavigator navigator;

    public MainPresenter(IMainContract.IView view, IMainNavigator navigator) {
        this.view = view;
        this.navigator = navigator;
    }

    @Override
    public void attach(IMainContract.IView view, IMainNavigator navigator) {
        this.view = view;
        this.navigator = navigator;
    }

    @Override
    public void detach() {
        this.view = null;
        this.navigator.detach();
        this.navigator = null;
    }

    @Override
    public void onDrawerItemSelected(int itemId) {
        navigator.navigateByMenuItem(itemId);
    }

    @Override
    public void onBtnEventClick() {

    }

    @Override
    public void onVehicleNumberInputDialogResult(String vehicleNumber) {

    }

    @Override
    public void onOdometerInputDialogResult(String odometerInfo) {

    }
}
