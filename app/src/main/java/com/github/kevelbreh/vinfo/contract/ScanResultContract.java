package com.github.kevelbreh.vinfo.contract;

import com.github.kevelbreh.vinfo.data.VehicleInfo;
import com.github.kevelbreh.vinfo.presenter.BasePresenter;
import com.github.kevelbreh.vinfo.ui.BaseView;

public class ScanResultContract {

    public interface View extends BaseView {
        void onLoading();
        void onVehicleError(Throwable e);
        void onVehicle(VehicleInfo info);
    }

    public interface Presenter extends BasePresenter<View> {
    }
}
