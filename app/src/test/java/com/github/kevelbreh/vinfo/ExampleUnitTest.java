package com.github.kevelbreh.vinfo;

import com.github.kevelbreh.vinfo.contract.ScanResultContract;
import com.github.kevelbreh.vinfo.data.VehicleInfo;
import com.github.kevelbreh.vinfo.data.VehicleService;
import com.github.kevelbreh.vinfo.presenter.ScanResultPresenter;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import io.reactivex.Single;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;


public class ExampleUnitTest {

    @ClassRule
    public static final RxImmediateSchedulerRule rxImmediateSchedulerRule =
            new RxImmediateSchedulerRule();

    @Mock private ScanResultContract.View view;
    @Mock private VehicleService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test_success() {
        when(service.getVehicle(anyString())).thenReturn(Single.just(new VehicleInfo()));

        ScanResultContract.Presenter presenter = new ScanResultPresenter(service, "mock");
        presenter.onAttach(view);

        InOrder inOrder = inOrder(view);
        inOrder.verify(view, atLeastOnce()).onLoading();
        inOrder.verify(view, atLeastOnce()).onVehicle(any());
        inOrder.verify(view, never()).onVehicleError(any());
    }

    @Test
    public void test_failure() {
        when(service.getVehicle(anyString())).thenReturn(Single.error(new IOException()));

        ScanResultContract.Presenter presenter = new ScanResultPresenter(service, "mock");
        presenter.onAttach(view);

        InOrder inOrder = inOrder(view);
        inOrder.verify(view, atLeastOnce()).onLoading();
        inOrder.verify(view, atLeastOnce()).onVehicleError(any());
        inOrder.verify(view, never()).onVehicle(any());
    }
}