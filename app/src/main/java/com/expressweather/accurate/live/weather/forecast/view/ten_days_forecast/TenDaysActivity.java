package com.expressweather.accurate.live.weather.forecast.view.ten_days_forecast;

import android.view.LayoutInflater;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.expressweather.accurate.live.weather.forecast.base.BaseActivity;
import com.expressweather.accurate.live.weather.forecast.databinding.ActivityTenDaysBinding;
import com.expressweather.accurate.live.weather.forecast.model.network_model.RootModel;
import com.expressweather.accurate.live.weather.forecast.model.network_model.Weather;
import com.expressweather.accurate.live.weather.forecast.utils.SharePrefUtils;

import java.util.ArrayList;

/**
 * Activity for displaying 10-day forecast
 */
public class TenDaysActivity extends BaseActivity<ActivityTenDaysBinding> {

    /**
     * This method is called to set the binding for the layout
     *
     * @return Binding object inflated from the layout
     */
    @Override
    protected ActivityTenDaysBinding setViewBinding() {
        return ActivityTenDaysBinding.inflate(LayoutInflater.from(this));
    }

    /**
     * This method is called to initialize the view
     */
    @Override
    protected void initView() {
        // Get weather data from Intent
        RootModel dataWeather = (RootModel) getIntent().getSerializableExtra(SharePrefUtils.WEATHER_LOCATION_SELECTED);
        if (dataWeather != null) {
            // Display weather data
            setDataWeather(dataWeather);
        }
    }

    /**
     * This method is called to set listeners for views
     */
    @Override
    protected void viewListener() {
        // Set listener for the back button
        binding.imgBack.setOnClickListener(view -> onBackPressed());
    }

    /**
     * This method is used to set weather data for the RecyclerView
     *
     * @param dataWeather Weather data passed from Intent
     */
    private void setDataWeather(RootModel dataWeather) {
        // Get weather list from weather data
        ArrayList<Weather> weatherList = dataWeather.weather;
        // Create adapter and set it for RecyclerView
        PrecipitationAdapter adapter = new PrecipitationAdapter(weatherList, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rvTenPrecipitation.setLayoutManager(layoutManager);
        binding.rvTenPrecipitation.setAdapter(adapter);
    }
}
