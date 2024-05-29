package com.expressweather.accurate.live.weather.forecast.view.config;

import android.view.LayoutInflater;
import android.widget.TextView;

import com.expressweather.accurate.live.weather.forecast.R;
import com.expressweather.accurate.live.weather.forecast.base.BaseActivity;
import com.expressweather.accurate.live.weather.forecast.databinding.ActivityConfigBinding;
import com.expressweather.accurate.live.weather.forecast.utils.Constants;
import com.expressweather.accurate.live.weather.forecast.utils.SharePrefUtils;
import com.expressweather.accurate.live.weather.forecast.view.home.HomeFragment;

import java.util.ArrayList;
import java.util.List;

public class ConfigActivity extends BaseActivity<ActivityConfigBinding> {


    @Override
    protected ActivityConfigBinding setViewBinding() {
        return ActivityConfigBinding.inflate(LayoutInflater.from(this));
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void viewListener() {
        handleWindSpeed();
        handleTemperatureItem();
        handlePressure();
        handlePrecipitation();
        handleDisplay();

        binding.imgBack.setOnClickListener(view -> onBackPressed());

        // Đặt giá trị true từ TEMP_UNIT là độ C, false là độ F
        boolean isDegreeC = SharePrefUtils.getBoolean(SharePrefUtils.TEMP_UNIT, true);
        if (isDegreeC) {
            binding.tvDegreeC.performClick();
        } else {
            binding.tvDegreeF.performClick();
        }

        // Đặt phương thức getInt lấy giá trị của WIND_SPEED_UNIT chuyển đổi đơn vị tốc độ gió
        int windSpeedUnit = SharePrefUtils.getInt(SharePrefUtils.WIND_SPEED_UNIT, Constants.WIND_SPEED_KM_PER_H);
        switch (windSpeedUnit) {
            case Constants.WIND_SPEED_KM_PER_H:
                binding.tvKmH.performClick();
                break;
            case Constants.WIND_SPEED_M_PER_S:
                binding.tvMS.performClick();
                break;
            case Constants.WIND_SPEED_KN:
                binding.tvKn.performClick();
                break;
            case Constants.WIND_SPEED_MPH:
                binding.tvMph.performClick();
                break;
            case Constants.WIND_SPEED_FT_PER_S:
                binding.tvFtS.performClick();
                break;
        }

        // Đặt phương thức getInt lấy giá trị của PRESSURE_UNIT chuyển đổi đơn vị áp suất
        int pressureUnit = SharePrefUtils.getInt(SharePrefUtils.PRESSURE_UNIT, Constants.PRESSURE_HPA);
        switch (pressureUnit) {
            case Constants.PRESSURE_HPA:
                binding.tvHPa.performClick();
                break;
            case Constants.PRESSURE_MM_HG:
                binding.tvMMHg.performClick();
                break;
            case Constants.PRESSURE_IN_HG:
                binding.tvInHg.performClick();
                break;
        }

        // Đặt phương thức getInt lấy giá trị của PRECIPITATION_UNIT chuyển đổi đơn vị lượng mưa
        int precipitationUnit = SharePrefUtils.getInt(SharePrefUtils.PRECIPITATION_UNIT, Constants.PRECIPITATION_MM);
        switch (precipitationUnit) {
            case Constants.PRECIPITATION_MM:
                binding.tvMm.performClick();
                break;
            case Constants.PRECIPITATION_INCH:
                binding.tvInch.performClick();
                break;
            case Constants.PRECIPITATION_L_PER_M2:
                binding.tvLM2.performClick();
                break;
        }

        int displayUnit = SharePrefUtils.getInt(SharePrefUtils.VISIBILITY_UNIT, Constants.VISIBILITY_KM);
        switch (displayUnit) {
            case Constants.VISIBILITY_KM:
                binding.tvKm.performClick();
                break;
            case Constants.VISIBILITY_M:
                binding.tvM.performClick();
                break;
            case Constants.VISIBILITY_FT:
                binding.tvFt.performClick();
                break;
        }
    }
    // Tạo sự kiện khi người dùng nhấn vào nút thay đổi nhiệt độ (C/F)
    private void handleTemperatureItem() {
        binding.tvDegreeC.setOnClickListener(view -> {
            // Nếu chọn độ C, thay đổi nền TextView độ C, xoá nền TextView độ F
            binding.tvDegreeC.setBackgroundResource(R.drawable.bg_item_config_selected);
            binding.tvDegreeF.setBackground(null);
            // Lưu trạng thái lựa chọn đơn vị độ C (true) vào SharePrefUtils
            SharePrefUtils.putBoolean(SharePrefUtils.TEMP_UNIT, true);
        });

        binding.tvDegreeF.setOnClickListener(view -> {
            // Nếu chọn độ F, thay đổi nền TextView độ F, xoá nền TextView độ C
            binding.tvDegreeF.setBackgroundResource(R.drawable.bg_item_config_selected);
            binding.tvDegreeC.setBackground(null);
            // Lưu trạng thái lựa chọn đơn vị độ F (false) vào SharePrefUtils
            SharePrefUtils.putBoolean(SharePrefUtils.TEMP_UNIT, false);
        });
    }

    // Tạo sự kiện khi người dùng nhấn vào nút thay đổi tốc độ gió (KmH/MS/Kn/Mph/FtS)
    private void handleWindSpeed() {
        // Tạo listItem lưu các lựa chọn đơn vị đo tốc độ gió
        List<TextView> listItem = new ArrayList<>();
        listItem.add(binding.tvKmH);
        listItem.add(binding.tvMS);
        listItem.add(binding.tvKn);
        listItem.add(binding.tvMph);
        listItem.add(binding.tvFtS);

        //Đơn vị km/h
        binding.tvKmH.setOnClickListener(view -> {
            setBgItemNonSelect(listItem); // Bỏ nền của các TextView trong listItem
            binding.tvKmH.setBackgroundResource(R.drawable.bg_item_config_selected); // Đặt nền cho TextView KmH
            SharePrefUtils.putInt(SharePrefUtils.WIND_SPEED_UNIT, Constants.WIND_SPEED_KM_PER_H); // Lưu đơn vị tốc độ gió là km/h vào WIND_SPEED_UNIT
        });
        // Đơn vị m/s
        binding.tvMS.setOnClickListener(view -> {
            setBgItemNonSelect(listItem);
            binding.tvMS.setBackgroundResource(R.drawable.bg_item_config_selected);
            SharePrefUtils.putInt(SharePrefUtils.WIND_SPEED_UNIT, Constants.WIND_SPEED_M_PER_S);
        });
        // Đơn vị knot
        binding.tvKn.setOnClickListener(view -> {
            setBgItemNonSelect(listItem);
            binding.tvKn.setBackgroundResource(R.drawable.bg_item_config_selected);
            SharePrefUtils.putInt(SharePrefUtils.WIND_SPEED_UNIT, Constants.WIND_SPEED_KN);
        });
        // Đơn vị mph
        binding.tvMph.setOnClickListener(view -> {
            setBgItemNonSelect(listItem);
            binding.tvMph.setBackgroundResource(R.drawable.bg_item_config_selected);
            SharePrefUtils.putInt(SharePrefUtils.WIND_SPEED_UNIT, Constants.WIND_SPEED_MPH);
        });
        // Đơn vị ft/s
        binding.tvFtS.setOnClickListener(view -> {
            setBgItemNonSelect(listItem);
            binding.tvMph.setBackground(null);
            binding.tvFtS.setBackgroundResource(R.drawable.bg_item_config_selected);
            SharePrefUtils.putInt(SharePrefUtils.WIND_SPEED_UNIT, Constants.WIND_SPEED_FT_PER_S);
        });
    }

    private void handlePressure() {
        List<TextView> listItem = new ArrayList<>();
        listItem.add(binding.tvHPa);
        listItem.add(binding.tvMMHg);
        listItem.add(binding.tvInHg);

        binding.tvHPa.setOnClickListener(view -> {
            setBgItemNonSelect(listItem);
            binding.tvHPa.setBackgroundResource(R.drawable.bg_item_config_selected);
            SharePrefUtils.putInt(SharePrefUtils.PRESSURE_UNIT, Constants.PRESSURE_HPA);
        });

        binding.tvMMHg.setOnClickListener(view -> {
            setBgItemNonSelect(listItem);
            binding.tvMMHg.setBackgroundResource(R.drawable.bg_item_config_selected);
            SharePrefUtils.putInt(SharePrefUtils.PRESSURE_UNIT, Constants.PRESSURE_MM_HG);
        });

        binding.tvInHg.setOnClickListener(view -> {
            setBgItemNonSelect(listItem);
            binding.tvMMHg.setBackground(null);
            binding.tvInHg.setBackgroundResource(R.drawable.bg_item_config_selected);
            SharePrefUtils.putInt(SharePrefUtils.PRESSURE_UNIT, Constants.PRESSURE_IN_HG);
        });
    }

    private void handlePrecipitation() {
        List<TextView> listItem = new ArrayList<>();
        listItem.add(binding.tvMm);
        listItem.add(binding.tvInch);
        listItem.add(binding.tvLM2);

        binding.tvMm.setOnClickListener(view -> {
            setBgItemNonSelect(listItem);
            binding.tvMm.setBackgroundResource(R.drawable.bg_item_config_selected);
            SharePrefUtils.putInt(SharePrefUtils.PRECIPITATION_UNIT, Constants.PRECIPITATION_MM);
        });

        binding.tvInch.setOnClickListener(view -> {
            setBgItemNonSelect(listItem);
            binding.tvInch.setBackgroundResource(R.drawable.bg_item_config_selected);
            SharePrefUtils.putInt(SharePrefUtils.PRECIPITATION_UNIT, Constants.PRECIPITATION_INCH);
        });

        binding.tvLM2.setOnClickListener(view -> {
            setBgItemNonSelect(listItem);
            binding.tvInch.setBackground(null);
            binding.tvLM2.setBackgroundResource(R.drawable.bg_item_config_selected);
            SharePrefUtils.putInt(SharePrefUtils.PRECIPITATION_UNIT, Constants.PRECIPITATION_L_PER_M2);
        });
    }

    private void handleDisplay() {
        List<TextView> listItem = new ArrayList<>();
        listItem.add(binding.tvKm);
        listItem.add(binding.tvM);
        listItem.add(binding.tvFt);

        binding.tvKm.setOnClickListener(view -> {
            setBgItemNonSelect(listItem);
            binding.tvKm.setBackgroundResource(R.drawable.bg_item_config_selected);
            SharePrefUtils.putInt(SharePrefUtils.VISIBILITY_UNIT, Constants.VISIBILITY_KM);
        });

        binding.tvM.setOnClickListener(view -> {
            setBgItemNonSelect(listItem);
            binding.tvM.setBackgroundResource(R.drawable.bg_item_config_selected);
            SharePrefUtils.putInt(SharePrefUtils.VISIBILITY_UNIT, Constants.VISIBILITY_M);
        });

        binding.tvFt.setOnClickListener(view -> {
            setBgItemNonSelect(listItem);
            binding.tvM.setBackground(null);
            binding.tvFt.setBackgroundResource(R.drawable.bg_item_config_selected);
            SharePrefUtils.putInt(SharePrefUtils.VISIBILITY_UNIT, Constants.VISIBILITY_FT);
        });
    }

    private void setBgItemNonSelect(List<TextView> listConfig) {
        for (TextView tv : listConfig) {
            tv.setBackgroundResource(R.drawable.bg_item_config_non_select);
        }

        listConfig.get(listConfig.size() - 1).setBackground(null);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        HomeFragment.updateData = true;
        String locationFirst = SharePrefUtils.getString(SharePrefUtils.HISTORY_SEARCH_LOCATION_FIRST, "");
        String locationSecond = SharePrefUtils.getString(SharePrefUtils.HISTORY_SEARCH_LOCATION_SECOND, "");

        if (!locationFirst.isEmpty()) {
            HomeFragment.updateDataFirst = true;
        }

        if (!locationSecond.isEmpty()) {
            HomeFragment.updateDataSecond = true;
        }
    }
}
