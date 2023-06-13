package al.fshn.notsocleanearth.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import al.fshn.notsocleanearth.R;
import al.fshn.notsocleanearth.data.model.City;

public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.ViewHolder> {
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView cityName;
        private final TextView countryIn;

        public ViewHolder(View view) {
            super(view);

            cityName = (TextView) view.findViewById(R.id.cityName);
            countryIn = (TextView) view.findViewById(R.id.countryIn);
        }

        public TextView getCityName() {
            return cityName;
        }

        public TextView getCountryIn() {
            return countryIn;
        }
    }

    List<City> cities;
    OnCityClickInterface onCityClickInterface;

    CitiesAdapter(List<City> cities, OnCityClickInterface onCityClickInterface) {
        this.cities = cities;
        this.onCityClickInterface = onCityClickInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_city, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getCityName().setText(cities.get(position).getName());
        holder.getCountryIn().setText(cities.get(position).getCountry());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCityClickInterface.onCLick(cities.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }
}

