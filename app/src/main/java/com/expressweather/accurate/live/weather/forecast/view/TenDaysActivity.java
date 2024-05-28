package com.expressweather.accurate.live.weather.forecast.view.ten_days_forecast;

import android.view.LayoutInflater;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.expressweather.accurate.live.weather.forecast.base.BaseActivity;
import com.expressweather.accurate.live.weather.forecast.databinding.ActivityTenDaysBinding;
import com.expressweather.accurate.live.weather.forecast.model.network_model.RootModel;
import com.expressweather.accurate.live.weather.forecast.model.network_model.Weather;
import com.expressweather.accurate.live.weather.forecast.utils.SharePrefUtils;

import java.util.ArrayList;

public class TenDaysActivity extends BaseActivity<ActivityTenDaysBinding> {
    @Override
    protected ActivityTenDaysBinding setViewBinding() {
        return ActivityTenDaysBinding.inflate(LayoutInflater.from(this));
    }

    @Override
    protected void initView() {
        RootModel dataWeather = (RootModel) getIntent().getSerializableExtra(SharePrefUtils.WEATHER_LOCATION_SELECTED);
        if (dataWeather != null) {
            setDataWeather(dataWeather);
        }
    }

    @Override
    protected void viewListener() {
        binding.imgBack.setOnClickListener(view -> onBackPressed());
    }

    private void setDataWeather(RootModel dataWeather) {
        ArrayList<Weather> weatherList = dataWeather.weather;
        PrecipitationAdapter adapter = new PrecipitationAdapter(weatherList, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rvTenPrecipitation.setLayoutManager(layoutManager);
        binding.rvTenPrecipitation.setAdapter(adapter);
    }
}