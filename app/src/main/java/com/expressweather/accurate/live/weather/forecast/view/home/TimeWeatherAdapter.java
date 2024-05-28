package com.expressweather.accurate.live.weather.forecast.view.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.expressweather.accurate.live.weather.forecast.R;
import com.expressweather.accurate.live.weather.forecast.helper.WeatherState;
import com.expressweather.accurate.live.weather.forecast.model.network_model.Hourly;
import com.expressweather.accurate.live.weather.forecast.utils.SharePrefUtils;

import java.util.List;

public class TimeWeatherAdapter extends RecyclerView.Adapter<TimeWeatherAdapter.TimeWeatherViewHolder> {
    List<Hourly> listTimeWeather;
    boolean isDegreeC;

    public TimeWeatherAdapter(@NonNull List<Hourly> listTimeWeather) {
        this.listTimeWeather = listTimeWeather;
        isDegreeC = SharePrefUtils.getBoolean(SharePrefUtils.TEMP_UNIT, true);
    }

    @NonNull
    @Override
    public TimeWeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_weather_time, parent, false);
        return new TimeWeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeWeatherViewHolder holder, int position) {
        Hourly hourlyWeather = listTimeWeather.get(position);
        holder.tvTemp.setText((isDegreeC ? hourlyWeather.tempC : hourlyWeather.tempF) + "°");

        int time = Integer.parseInt(hourlyWeather.time);
        if (time > 0) {
            time = time / 100;
        }

        if (time < 10) {
            holder.tvTime.setText("0" + time + ":00");
        } else {
            holder.tvTime.setText(time + ":00");
        }

        holder.imgWeather.setImageResource(WeatherState.getImageWeather(hourlyWeather.weatherDesc.get(0).value));

        String weatherDes = hourlyWeather.weatherDesc.get(0).value;
        boolean isSunnyWeather = weatherDes.equals("Clear") || weatherDes.equals("Sunny");

        if (isSunnyWeather) {
            if (time >= 18) {
                holder.imgWeather.setImageResource(R.drawable.ic_moon);
            } else {
                holder.imgWeather.setImageResource(R.drawable.ic_sunshine);
            }
        }

    }

    @Override
    public int getItemCount() {
        return listTimeWeather.size();
    }

    static class TimeWeatherViewHolder extends RecyclerView.ViewHolder {
        TextView tvTime;
        TextView tvTemp;
        ImageView imgWeather;

        public TimeWeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvTemp = itemView.findViewById(R.id.tvDegree);
            imgWeather = itemView.findViewById(R.id.imgWeather);
        }
    }
}
