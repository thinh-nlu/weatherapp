package com.expressweather.accurate.live.weather.forecast.view;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;

import com.expressweather.accurate.live.weather.forecast.R;
import com.expressweather.accurate.live.weather.forecast.base.BaseActivity;
import com.expressweather.accurate.live.weather.forecast.databinding.ActivityPolicyBinding;

public class PolicyActivity extends BaseActivity<ActivityPolicyBinding> {

    @Override
    protected ActivityPolicyBinding setViewBinding() {
        return ActivityPolicyBinding.inflate(LayoutInflater.from(this));
    }

    @Override
    protected void initView() {
        binding.icBack.setOnClickListener(view -> onBackPressed());
        binding.tvPolicy.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://madyglobal.netlify.app/policy"));
            startActivity(intent);
        });

        binding.tvVersion.setText(getString(R.string.version) + " 1.0.0");
    }

    @Override
    protected void viewListener() {

    }
}