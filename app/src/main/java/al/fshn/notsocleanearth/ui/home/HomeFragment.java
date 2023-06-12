package al.fshn.notsocleanearth.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import al.fshn.notsocleanearth.MainActivity;
import al.fshn.notsocleanearth.R;
import al.fshn.notsocleanearth.data.APIClient;
import al.fshn.notsocleanearth.data.APIInterface;
import al.fshn.notsocleanearth.data.model.City;

public class HomeFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
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
        citiesRecycler.setAdapter(new CitiesAdapter(cities));
    }
}