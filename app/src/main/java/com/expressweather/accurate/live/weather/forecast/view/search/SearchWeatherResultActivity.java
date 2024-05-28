package com.expressweather.accurate.live.weather.forecast.view.search;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.expressweather.accurate.live.weather.forecast.R;
import com.expressweather.accurate.live.weather.forecast.base.BaseActivity;
import com.expressweather.accurate.live.weather.forecast.databinding.ActivitySearchWeatherResultBinding;
import com.expressweather.accurate.live.weather.forecast.helper.UnitHelper;
import com.expressweather.accurate.live.weather.forecast.helper.WeatherHelper;
import com.expressweather.accurate.live.weather.forecast.helper.WeatherState;
import com.expressweather.accurate.live.weather.forecast.model.network_model.AreaName;
import com.expressweather.accurate.live.weather.forecast.model.network_model.Astronomy;
import com.expressweather.accurate.live.weather.forecast.model.network_model.CurrentCondition;
import com.expressweather.accurate.live.weather.forecast.model.network_model.Hourly;
import com.expressweather.accurate.live.weather.forecast.model.network_model.NearestArea;
import com.expressweather.accurate.live.weather.forecast.model.network_model.RootModel;
import com.expressweather.accurate.live.weather.forecast.model.network_model.Weather;
import com.expressweather.accurate.live.weather.forecast.utils.Constants;
import com.expressweather.accurate.live.weather.forecast.utils.SharePrefUtils;
import com.expressweather.accurate.live.weather.forecast.view.MainActivity;
import com.expressweather.accurate.live.weather.forecast.view.home.TimeWeatherAdapter;
import com.expressweather.accurate.live.weather.forecast.view.ten_days_forecast.TenDaysActivity;
import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SearchWeatherResultActivity extends BaseActivity<ActivitySearchWeatherResultBinding> {

    private RootModel rootModel;
    private String location;
    private boolean isToday = true;

    //for sunrise progress
    int widthUV = 0;
    int widthPointUV = 0;
    @Override
    protected ActivitySearchWeatherResultBinding setViewBinding() {
        return ActivitySearchWeatherResultBinding.inflate(LayoutInflater.from(this));
    }

    @Override
    protected void initView() {
        rootModel = (RootModel) getIntent().getSerializableExtra(SharePrefUtils.WEATHER_SEARCH_RESULT);
        if (getIntent().hasExtra(SharePrefUtils.LOCATION_SEARCH)) {
            location = getIntent().getStringExtra(SharePrefUtils.LOCATION_SEARCH);
        }
        boolean added = getIntent().getBooleanExtra(Constants.LOCATION_ADDED, false);

        if (added) {
            binding.tvAdd.setVisibility(View.INVISIBLE);
        }
        setDataWeather(rootModel);
    }

    @Override
    protected void viewListener() {
        binding.layoutInfo.tvNext10days.setOnClickListener(view -> {
            Intent intent = new Intent(this, TenDaysActivity.class);
            intent.putExtra(SharePrefUtils.WEATHER_LOCATION_SELECTED, rootModel);
            startActivity(intent);}
        );

        binding.imgClose.setOnClickListener(view -> onBackPressed());

        binding.tvAdd.setOnClickListener(view -> {
            updateListSearchHistory();

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(Constants.SEARCH_WEATHER_HISTORY, rootModel);
            startActivity(intent);
            finishAffinity();
        });

        binding.layoutInfo.tvToday.setOnClickListener(view -> {
            if (!isToday) {
                isToday = true;
                setDataWeather(rootModel);
            }
        });

        binding.layoutInfo.tvTomorrow.setOnClickListener(view -> {
            if (isToday) {
                isToday = false;
                setDataWeather(rootModel);
            }
        });

        binding.appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    binding.layoutLocationCollapse.setVisibility(View.VISIBLE);
                    binding.llWeatherCollapse.setVisibility(View.VISIBLE);
                    binding.viewTransparent.setVisibility(View.VISIBLE);

                    binding.layoutWeatherExtend.setVisibility(View.INVISIBLE);
                    Animation fadeIn = new AlphaAnimation(0, 1);
                    fadeIn.setDuration(500);
                    binding.layoutLocationCollapse.setAnimation(fadeIn);
                    binding.llWeatherCollapse.setAnimation(fadeIn);

                    binding.layoutActionCollapse.setVisibility(View.INVISIBLE);
                    Animation fadeOut = new AlphaAnimation(1, 0);
                    binding.layoutActionCollapse.setAnimation(fadeOut);
                }

                if (verticalOffset == 0) {
                    binding.layoutLocationCollapse.setVisibility(View.INVISIBLE);
                    binding.llWeatherCollapse.setVisibility(View.INVISIBLE);
                    binding.viewTransparent.setVisibility(View.INVISIBLE);

                    binding.layoutActionCollapse.setVisibility(View.VISIBLE);
                    binding.layoutWeatherExtend.setVisibility(View.VISIBLE);
                    Animation fadeIn = new AlphaAnimation(0, 1);
                    fadeIn.setDuration(500);
                    binding.layoutWeatherExtend.setAnimation(fadeIn);
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void setDataWeather(RootModel dataWeather) {
        boolean isDegreeC = SharePrefUtils.getBoolean(SharePrefUtils.TEMP_UNIT, true);
        String temperature;
        String temperatureFeel;
        String weatherString;
        String uvIndex;
        Astronomy astronomy;
        String windSpeedKmph;
        String pressureValue;
        Weather weather;
        ArrayList<Hourly> hourlyWeatherList;
        String precipitationMM;
        String humidity;
        String visibility;
        int tempC, temFeelC;

        //data for today
        CurrentCondition currentCondition = dataWeather.current_condition.get(0);
        List<NearestArea> nearestAreaList = dataWeather.nearest_area;
        List<AreaName> areaNameList = nearestAreaList.get(0).areaName;

        //current location based on city
        binding.tvPosition.setText(areaNameList.get(0).value);

        //set data weather collapse today
        binding.tvLocationCollapse.setText(areaNameList.get(0).value);

        if (isToday) {
            weather = dataWeather.weather.get(0);

            temperature = isDegreeC ? currentCondition.temp_C : currentCondition.temp_F;
            temperatureFeel = isDegreeC ? currentCondition.feelsLikeC + "°" : currentCondition.feelsLikeF + "°";
            weatherString = currentCondition.weatherDesc.get(0).value;
            uvIndex = currentCondition.uvIndex;
            astronomy = weather.astronomy.get(0);
            windSpeedKmph = currentCondition.windspeedKmph;
            pressureValue = currentCondition.pressure;
            precipitationMM = currentCondition.precipMM;
            humidity = currentCondition.humidity + "%";
            visibility = currentCondition.visibility;
            tempC = Integer.parseInt(currentCondition.temp_C);
            temFeelC = Integer.parseInt(currentCondition.feelsLikeC);
        } else {
            //data for tomorrow
            weather = dataWeather.weather.get(1);
            Hourly weatherByHourly = getTemperature(weather.hourly);

            temperature = isDegreeC ? weatherByHourly.tempC : weatherByHourly.tempF;
            temperatureFeel = isDegreeC ? weatherByHourly.feelsLikeC + "°" : weatherByHourly.feelsLikeF + "°";
            weatherString = weatherByHourly.weatherDesc.get(0).value;
            uvIndex = weatherByHourly.uvIndex;
            astronomy = weather.astronomy.get(0);
            windSpeedKmph = weatherByHourly.windspeedKmph;
            pressureValue = weatherByHourly.pressure;
            precipitationMM = weatherByHourly.precipMM;
            humidity = weatherByHourly.humidity + "%";
            visibility = weatherByHourly.visibility;
            tempC = Integer.parseInt(weatherByHourly.tempC);
            temFeelC = Integer.parseInt(weatherByHourly.feelsLikeC);
        }

        hourlyWeatherList = weather.hourly;

        //temperature
        binding.tvTemperature.setText(temperature);

        //weather type and weather image
        binding.tvWeather.setText(weatherString);
        binding.imgIconWeather.setImageResource(WeatherState.getImageWeather(weatherString));
        binding.bgWeather.setImageResource(WeatherState.gifWeather);

        //Uv index
        binding.layoutInfo.tvUvIndexValue.setText(uvIndex);
        setUVIndexUI(Integer.parseInt(uvIndex));
        binding.layoutInfo.tvUVDescription.setText(WeatherHelper.getUVDescription(this, Integer.parseInt(uvIndex)));

        //sun set, sun rise
        binding.layoutInfo.tvSunRiseTime.setText(astronomy.sunrise);
        binding.layoutInfo.tvSunSetTime.setText(getString(R.string.sunset) + " " + astronomy.sunset);
        setProcessSunriseUI(astronomy);

        //wind speed
        String windSpeed = UnitHelper.getWindSpeed(Integer.parseInt(windSpeedKmph));
        binding.layoutInfo.tvWindValue.setText(windSpeed);
        binding.layoutInfo.tvUnitWS.setText(UnitHelper.windSpeedUnit);

        //feels like
        binding.layoutInfo.tvFLTempValue.setText(temperatureFeel);
        binding.layoutInfo.tvFLDescription.setText(WeatherHelper.getFeelLikeDescription(this, tempC, temFeelC));

        //pressure
        double pressure = (double) Integer.parseInt(pressureValue) / 1000;
        binding.layoutInfo.tvPressureValue.setText(UnitHelper.getPressure(pressure));
        binding.layoutInfo.tvUnitPressure.setText(UnitHelper.pressureUnit);
        setProcessPressureUI(pressure);

        //temperature highest and lowest
        binding.tvHighest.setText("H:" + (isDegreeC ? weather.maxtempC : weather.maxtempF) + "°");
        binding.tvLowest.setText("L:" + (isDegreeC ? weather.mintempC : weather.mintempF) + "°");

        setTimeWeatherList(hourlyWeatherList);

        // rain fall
        binding.layoutInfo.tvRainFallValue.setText(UnitHelper.getPrecipitation(Double.parseDouble(precipitationMM)));
        binding.layoutInfo.tvRainFallValue.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
        binding.layoutInfo.tvUnitRainFall.setText(UnitHelper.precipitationUnit);

        //humidity
        binding.layoutInfo.tvHumidityPercent.setText(humidity);

        Hourly weatherHourlyNow = getTemperature(weather.hourly);

        String dewPoint = getString(R.string.the_dew_point_is)
                + (isDegreeC ? weatherHourlyNow.dewPointC : weatherHourlyNow.dewPointF)
                + getString(R.string.right_now);
        binding.layoutInfo.tvHumidityDescription.setText(dewPoint);

        //visibility
        String visibilityValue = UnitHelper.getVisibility(Integer.parseInt(visibility));
        binding.layoutInfo.tvVisibilityValue.setText(visibilityValue);
        binding.layoutInfo.tvUnitVisibility.setText(UnitHelper.visibilityUnit);

        //set data weather collapse
        binding.tvTemperatureCollapse.setText(temperature + "°");
        binding.imgWeatherCollapse.setImageResource(WeatherState.getImageWeather(weatherString));
        binding.tvWeatherCollapse.setText(weatherString);
    }

    private Hourly getTemperature(ArrayList<Hourly> hourlies) {
        Hourly weatherByTime;
        Calendar calendar = Calendar.getInstance();
        int hNow = calendar.get(Calendar.HOUR_OF_DAY);
        int hourlyFirst = Integer.parseInt(hourlies.get(0).time);
        if (hourlyFirst > 0) {
            hourlyFirst = hourlyFirst / 100;
        }
        weatherByTime = hourlies.get(0);

        int difference = Math.abs(hNow - hourlyFirst);
        for (Hourly h : hourlies) {
            int time = Integer.parseInt(h.time);
            if (time > 0) {
                time = time / 100;
            }

            if (difference > Math.abs(hNow - time)) {
                difference = Math.abs(hNow - time);
                weatherByTime = h;
                Log.d("HomeFragment==", "getTemperature called with time " + h.time);
            }
        }
        return weatherByTime;
    }

    private void setUVIndexUI(int uvIndex) {
        ViewTreeObserver vto = binding.layoutInfo.imgUvSlider.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                binding.layoutInfo.imgUvSlider.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                widthUV = binding.layoutInfo.imgUvSlider.getMeasuredWidth();

                int unit = widthUV / 11;
                int marginLeft;
                if (uvIndex == 0) {
                    marginLeft = 0;
                } else if (uvIndex == 11) {
                    marginLeft = widthUV - widthPointUV;
                } else {
                    marginLeft = unit * uvIndex;
                }
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) binding.layoutInfo.imgPointUVIndex.getLayoutParams();
                params.setMargins(marginLeft, 0, 0, 0);
                binding.layoutInfo.imgPointUVIndex.setLayoutParams(params);
            }
        });

        ViewTreeObserver vtoPoint = binding.layoutInfo.imgPointUVIndex.getViewTreeObserver();
        vtoPoint.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                binding.layoutInfo.imgPointUVIndex.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                widthPointUV = binding.layoutInfo.imgPointUVIndex.getMeasuredWidth();

                int unit = widthUV / 11;
                int marginLeft;
                if (uvIndex == 0) {
                    marginLeft = 0;
                } else if (uvIndex == 11) {
                    marginLeft = widthUV - widthPointUV;
                } else {
                    marginLeft = unit * uvIndex;
                }
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) binding.layoutInfo.imgPointUVIndex.getLayoutParams();
                params.setMargins(marginLeft, 0, 0, 0);
                binding.layoutInfo.imgPointUVIndex.setLayoutParams(params);
            }
        });
    }

    private void setProcessSunriseUI(Astronomy astronomy) {
        //get hour and minute off time sunrise
        String sunrise = astronomy.sunrise;
        int hSunrise = Integer.parseInt(sunrise.substring(0, 2));
        int mSunrise = Integer.parseInt(sunrise.substring(3, 5));

        //get hour and minute off time sunset
        String sunset = astronomy.sunset;
        int hSunset = Integer.parseInt(sunset.substring(0, 2)) + 12;
        int mSunset = Integer.parseInt(sunset.substring(3, 5));

        //get time now and calculate time based on total time sunrise
        Calendar calendar = Calendar.getInstance();
        int mPresent = calendar.get(Calendar.MINUTE);
        int hPresent = calendar.get(Calendar.HOUR_OF_DAY);
        int minPresent = (hPresent - hSunrise) * 60 + mPresent - mSunrise;

        //total time sunrise
        int totalMinSunrise = (hSunset - hSunrise) * 60 + (mSunset - mSunrise);

        //calculate progress
        if (minPresent > 0) {
            binding.layoutInfo.progressSunrise.setProgress(minPresent);
        }

        binding.layoutInfo.progressSunrise.setProgressMax(totalMinSunrise * 2);

        float toDegree = ((float) minPresent / (totalMinSunrise * 2)) * 360;
        RotateAnimation animRotate = new RotateAnimation(0, toDegree, Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0.6f);

        animRotate.setDuration(0);
        animRotate.setStartOffset(0);
        animRotate.setFillAfter(true);
        animRotate.setFillBefore(true);

        binding.layoutInfo.layoutPointSunrise.startAnimation(animRotate);
    }

    private void setProcessPressureUI(double pressure) {
        binding.layoutInfo.progressPressure.setProgress((float) pressure);
        binding.layoutInfo.progressPressure.setProgressMax(2);

        float toDegree = (float) (pressure - 1) * 180;

        RotateAnimation animRotate = new RotateAnimation(0, toDegree, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1f);

        animRotate.setDuration(0);
        animRotate.setStartOffset(0);
        animRotate.setFillAfter(true);
        animRotate.setFillBefore(true);

        binding.layoutInfo.layoutPointPressure.startAnimation(animRotate);
    }

    private void setTimeWeatherList(ArrayList<Hourly> hourlyWeatherList) {
        TimeWeatherAdapter adapter = new TimeWeatherAdapter(hourlyWeatherList);
        binding.layoutInfo.rvTimeWeather.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        binding.layoutInfo.rvTimeWeather.setAdapter(adapter);
    }

    private void updateListSearchHistory() {
        SharePrefUtils.putString(SharePrefUtils.HISTORY_SEARCH_LOCATION_FOURTH,
                SharePrefUtils.getString(SharePrefUtils.HISTORY_SEARCH_LOCATION_THIRD, ""));

        SharePrefUtils.putString(SharePrefUtils.HISTORY_SEARCH_LOCATION_THIRD,
                SharePrefUtils.getString(SharePrefUtils.HISTORY_SEARCH_LOCATION_SECOND, ""));

        SharePrefUtils.putString(SharePrefUtils.HISTORY_SEARCH_LOCATION_SECOND,
                SharePrefUtils.getString(SharePrefUtils.HISTORY_SEARCH_LOCATION_FIRST, ""));

        SharePrefUtils.putString(SharePrefUtils.HISTORY_SEARCH_LOCATION_FIRST, location);
    }
}