package com.github.kevelbreh.vinfo.presenter;

import com.github.kevelbreh.vinfo.contract.ScanResultContract;
import com.github.kevelbreh.vinfo.data.VehicleService;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ScanResultPresenter implements ScanResultContract.Presenter {

    private final VehicleService service;
    private final String vin;
    private Disposable disposable;

    public ScanResultPresenter(VehicleService service, String vin) {
        this.service = service;
        this.vin = vin;
    }

    @Override
    public void onAttach(ScanResultContract.View view) {
        view.onLoading();
        disposable = service.getVehicle(vin)
                .delay(3, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(view::onVehicle, view::onVehicleError);
    }

    @Override
    public void onDetach(ScanResultContract.View view) {
        disposable.dispose();
    }
}
