package al.fshn.notsocleanearth.ui.dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import al.fshn.notsocleanearth.R;
import al.fshn.notsocleanearth.data.model.AirPollutionForecast;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView aqi;
        private final TextView co;
        private final TextView no;
        private final TextView no2;
        private final TextView o3;
        private final TextView so2;
        private final TextView pm25;
        private final TextView pm10;
        private final TextView nh3;
        private final TextView timeOfForecast;

        public ViewHolder(View view) {
            super(view);

            aqi = (TextView) view.findViewById(R.id.aqiText);
            co = (TextView) view.findViewById(R.id.co);
            no = (TextView) view.findViewById(R.id.no);
            no2 = (TextView) view.findViewById(R.id.no2);
            o3 = (TextView) view.findViewById(R.id.o3);
            so2 = (TextView) view.findViewById(R.id.so2);
            pm25 = (TextView) view.findViewById(R.id.pm25);
            pm10 = (TextView) view.findViewById(R.id.pm10);
            nh3 = (TextView) view.findViewById(R.id.nh3);
            timeOfForecast = (TextView) view.findViewById(R.id.timeOfForecast);
        }

        public TextView getCo() {
            return co;
        }

        public TextView getNo() {
            return no;
        }

        public TextView getNo2() {
            return no2;
        }

        public TextView getO3() {
            return o3;
        }

        public TextView getSo2() {
            return so2;
        }

        public TextView getPm25() {
            return pm25;
        }

        public TextView getPm10() {
            return pm10;
        }

        public TextView getNh3() {
            return nh3;
        }

        public TextView getAqi() {
            return aqi;
        }

        public TextView getTimeOfForecast() {
            return timeOfForecast;
        }
    }

    List<AirPollutionForecast.Forecast> forecasts;

    ForecastAdapter(List<AirPollutionForecast.Forecast> forecasts) {
        this.forecasts = forecasts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_forecast, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getAqi().setText("AQI: " + String.valueOf(forecasts.get(position).main.aqi));

        holder.getCo().setText("CO: " + String.valueOf(forecasts.get(position).components.co));
        holder.getNo().setText("NO: " + String.valueOf(forecasts.get(position).components.no));
        holder.getNo2().setText("NO2: " + String.valueOf(forecasts.get(position).components.no2));
        holder.getO3().setText("O3: " + String.valueOf(forecasts.get(position).components.o3));
        holder.getSo2().setText("SO2: " + String.valueOf(forecasts.get(position).components.so2));
        holder.getPm25().setText("PM2.5: " + String.valueOf(forecasts.get(position).components.pm2_5));
        holder.getPm10().setText("PM10: " + String.valueOf(forecasts.get(position).components.pm10));
        holder.getNh3().setText("NH3: " + String.valueOf(forecasts.get(position).components.nh3));

        String date = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new java.util.Date (forecasts.get(position).dt*1000));
        holder.getTimeOfForecast().setText(date);
    }

    @Override
    public int getItemCount() {
        return forecasts.size();
    }
}

