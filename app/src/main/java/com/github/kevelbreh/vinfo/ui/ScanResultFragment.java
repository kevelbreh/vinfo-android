package com.github.kevelbreh.vinfo.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.kevelbreh.vinfo.R;
import com.github.kevelbreh.vinfo.VinfoApplication;
import com.github.kevelbreh.vinfo.contract.ScanResultContract;
import com.github.kevelbreh.vinfo.data.VehicleInfo;
import com.github.kevelbreh.vinfo.presenter.ScanResultPresenter;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class ScanResultFragment extends Fragment implements ScanResultContract.View {

    public static ScanResultFragment create(String vin) {
        Bundle bundle = new Bundle();
        bundle.putString("vin", vin);
        ScanResultFragment fragment = new ScanResultFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @BindView(R.id.error_container) View errorContainer;
    @BindView(R.id.error_text) TextView errorText;
    @BindView(R.id.loading_container) View loadingContainer;
    @BindView(R.id.loading_text) TextView loadingText;
    @BindView(R.id.vehicle_container) View vehicleContainer;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.cover_image) ImageView coverImage;
    @BindView(R.id.make) TextView makeText;
    @BindView(R.id.model) TextView modelText;
    @BindView(R.id.year) TextView yearText;
    @BindView(R.id.gears) TextView gearsText;
    @BindView(R.id.fuel) TextView fuelText;
    @BindView(R.id.shape) TextView shapeText;
    @BindView(R.id.km) TextView kmText;

    private ScanResultPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        VinfoApplication app = (VinfoApplication) getActivity().getApplication();
        presenter = new ScanResultPresenter(app.getVehicleService(),
                getArguments().getString("vin"));
    }

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_vehicle_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        presenter.onAttach(this);

        toolbar.setNavigationIcon(R.drawable.ic_launcher_background);
        toolbar.setNavigationOnClickListener(v -> {
            getFragmentManager().popBackStack();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDetach(this);
    }

    @Override
    public void onLoading() {
        loadingContainer.setVisibility(View.VISIBLE);
        errorContainer.setVisibility(View.GONE);
        vehicleContainer.setVisibility(View.GONE);
        loadingText.setText(getString(R.string.vehicle_info_loading,
                getArguments().getString("vin")));
    }

    @Override
    public void onVehicleError(Throwable e) {
        loadingContainer.setVisibility(View.GONE);
        errorContainer.setVisibility(View.VISIBLE);
        vehicleContainer.setVisibility(View.GONE);
        errorText.setText("Something bad happened while fetching the vehicle :(");
    }

    @Override
    public void onVehicle(VehicleInfo info) {
        loadingContainer.setVisibility(View.GONE);
        errorContainer.setVisibility(View.GONE);
        vehicleContainer.setVisibility(View.VISIBLE);

        Picasso.get().load(info.image).fit().into(coverImage);

        makeText.setText(getString(R.string.vehicle_make, info.make));
        modelText.setText(getString(R.string.vehicle_model, info.model));
        yearText.setText(getString(R.string.vehicle_year, String.valueOf(info.year)));
        gearsText.setText(getString(R.string.vehicle_gears, info.gears));
        fuelText.setText(getString(R.string.vehicle_fuel, info.fuel));
        shapeText.setText(getString(R.string.vehicle_shape, info.shape));
        kmText.setText(getString(R.string.vehicle_km, String.valueOf(info.km)));
    }
}
