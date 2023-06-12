package al.fshn.notsocleanearth.ui.dashboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import al.fshn.notsocleanearth.MyApplication;
import al.fshn.notsocleanearth.R;
import al.fshn.notsocleanearth.data.APIClient;
import al.fshn.notsocleanearth.data.APIInterface;
import al.fshn.notsocleanearth.data.model.AirPollutionForecast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardFragment extends Fragment {

    APIInterface apiInterface;
    Location currentLocation = null;
    Location gpsLocation = null;
    Location networkLocation = null;
    Location locationByGps = null;
    Location locationByNetwork = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (isLocationPermissionGranted()) {
            LocationManager locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);
            boolean hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (hasGps) {
                /*locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        5000,
                        0F,
                        gpsLocationListener
                );*/
                locationManager.requestSingleUpdate(
                        LocationManager.GPS_PROVIDER,
                        gpsLocationListener,
                        null
                );
            }

        }
    }

    private Boolean isLocationPermissionGranted() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                    requireActivity(),
                    new String[]{
                            android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION
                    },
                    1001
            );
            return false;
        } else {
            return true;
        }
    }

    LocationListener gpsLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            locationByGps = location;

            Call<AirPollutionForecast> call =
                    apiInterface.getFuturePollution(
                            location.getLatitude(),
                            location.getLongitude(),
                            MyApplication.OPEN_WEATHER_API_KEY
                    );
            call.enqueue(new Callback<AirPollutionForecast>() {
                @Override
                public void onResponse(Call<AirPollutionForecast> call, Response<AirPollutionForecast> response) {
                    Log.d("TAG", response.code() + "");

//                Dogs resource = response.body();

//                dogRecycler.setAdapter(new DogAdapter(response.body().dogPicsUrl));
                }

                @Override
                public void onFailure(Call<AirPollutionForecast> call, Throwable t) {
                    call.cancel();
                    Log.d("TAG", "cancelled");
                }
            });
        }
    };

}