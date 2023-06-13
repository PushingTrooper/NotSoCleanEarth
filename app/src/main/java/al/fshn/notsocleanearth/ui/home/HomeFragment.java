package al.fshn.notsocleanearth.ui.home;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import al.fshn.notsocleanearth.MainActivity;
import al.fshn.notsocleanearth.MyApplication;
import al.fshn.notsocleanearth.R;
import al.fshn.notsocleanearth.data.APIClient;
import al.fshn.notsocleanearth.data.APIInterface;
import al.fshn.notsocleanearth.data.model.AirPollutionForecast;
import al.fshn.notsocleanearth.data.model.City;
import al.fshn.notsocleanearth.ui.dashboard.ForecastAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    APIInterface apiInterface;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity activity = (MainActivity) requireActivity();
        activity.showBottomNavigation();

        List<City> cities = new ArrayList<>();
        cities.add(new City("Shanghai", 31.22222, 121.45806, "China"));
        cities.add(new City("Beijing", 39.9075, 116.39723, "China"));
        cities.add(new City("Shenzhen", 22.54554, 114.0683, "China"));
        cities.add(new City("Guangzhou", 23.11667, 113.25, "China"));
        cities.add(new City("Lagos", 6.45407, 3.39467, "Nigeria"));
        cities.add(new City("Istanbul", 41.01384, 28.94966, "Turkey"));
        cities.add(new City("Chengdu", 30.66667, 104.06667, "China"));
        cities.add(new City("Mumbai", 19.07283, 72.88261, "India"));

        RecyclerView citiesRecycler = view.findViewById(R.id.citiesRecycler);
        citiesRecycler.setAdapter(new CitiesAdapter(cities, new OnCityClickInterface() {
            @Override
            public void onCLick(City city) {
                Call<AirPollutionForecast> call =
                        apiInterface.getPollutionForCity(
                                city.getLatitude(),
                                city.getLongitude(),
                                MyApplication.OPEN_WEATHER_API_KEY
                        );
                call.enqueue(new Callback<AirPollutionForecast>() {
                    @Override
                    public void onResponse(Call<AirPollutionForecast> call, Response<AirPollutionForecast> response) {
                        Log.d("TAG", response.code() + "");

                        AirPollutionForecast resource = response.body();
                        Dialog dialogFragment = new Dialog(requireContext());
                        dialogFragment.setContentView(R.layout.dialog_city_pollution);
                        dialogFragment.show();

                        TextView aqi = (TextView) dialogFragment.findViewById(R.id.aqiText);
                        TextView co = (TextView) dialogFragment.findViewById(R.id.co);
                        TextView no = (TextView) dialogFragment.findViewById(R.id.no);
                        TextView no2 = (TextView) dialogFragment.findViewById(R.id.no2);
                        TextView o3 = (TextView) dialogFragment.findViewById(R.id.o3);
                        TextView so2 = (TextView) dialogFragment.findViewById(R.id.so2);
                        TextView pm25 = (TextView) dialogFragment.findViewById(R.id.pm25);
                        TextView pm10 = (TextView) dialogFragment.findViewById(R.id.pm10);
                        TextView nh3 = (TextView) dialogFragment.findViewById(R.id.nh3);


                        aqi.setText("AQI: "+String.valueOf(resource.list.get(0).main.aqi));
                        co.setText("CO: "+String.valueOf(resource.list.get(0).components.co));
                        no.setText("NO: "+String.valueOf(resource.list.get(0).components.no));
                        no2.setText("NO2: "+String.valueOf(resource.list.get(0).components.no2));
                        o3.setText("O3: "+String.valueOf(resource.list.get(0).components.o3));
                        so2.setText("SO2: "+String.valueOf(resource.list.get(0).components.so2));
                        pm25.setText("PM2.5: "+String.valueOf(resource.list.get(0).components.pm2_5));
                        pm10.setText("PM10: "+String.valueOf(resource.list.get(0).components.pm10));
                        nh3.setText("NH3: "+String.valueOf(resource.list.get(0).components.nh3));
                    }

                    @Override
                    public void onFailure(Call<AirPollutionForecast> call, Throwable t) {
                        call.cancel();
                        Log.d("TAG", "cancelled");
                    }
                });
            }
        }));
    }
}