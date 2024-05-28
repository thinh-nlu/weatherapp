package com.expressweather.accurate.live.weather.forecast.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;

import com.expressweather.accurate.live.weather.forecast.base.BaseActivity;
import com.expressweather.accurate.live.weather.forecast.databinding.ActivitySplashBinding;
import com.expressweather.accurate.live.weather.forecast.utils.Constants;
import com.expressweather.accurate.live.weather.forecast.utils.SharePrefUtils;
import com.expressweather.accurate.live.weather.forecast.view.intro.IntroActivity;
import com.expressweather.accurate.live.weather.forecast.view.language.LanguageActivity;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends BaseActivity<ActivitySplashBinding> {
    private boolean openingApp = false;

    @Override
    protected ActivitySplashBinding setViewBinding() {
        return ActivitySplashBinding.inflate(LayoutInflater.from(this));
    }

    @Override
    protected void initView() {
        new Handler().postDelayed(this::openNextScreen, 3000);
    }

    @Override
    protected void viewListener() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        openingApp = true;
    }

    public void openNextScreen() {
        if (openingApp) {
            Log.d("openNextScreen", "called");
            if (SharePrefUtils.getCountOpenFirstLang(this) == 0) {
                Intent intent = new Intent(this, LanguageActivity.class);
                intent.putExtra(Constants.LANGUAGE_START, true);
                startActivity(intent);
            } else {
                startActivity(new Intent(SplashActivity.this, IntroActivity.class));
            }
            finish();
        }
    }

    @Override
    protected void onStop() {
        openingApp = false;
        super.onStop();
    }
}