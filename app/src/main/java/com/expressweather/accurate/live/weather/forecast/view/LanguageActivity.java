package com.expressweather.accurate.live.weather.forecast.view.language;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.expressweather.accurate.live.weather.forecast.base.BaseActivity;
import com.expressweather.accurate.live.weather.forecast.databinding.ActivityLanguageBinding;
import com.expressweather.accurate.live.weather.forecast.locale.SystemUtils;
import com.expressweather.accurate.live.weather.forecast.model.LanguageModel;
import com.expressweather.accurate.live.weather.forecast.utils.Constants;
import com.expressweather.accurate.live.weather.forecast.view.MainActivity;
import com.expressweather.accurate.live.weather.forecast.view.intro.IntroActivity;
import com.expressweather.accurate.live.weather.forecast.view.language.adapter.LanguageStartAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class LanguageActivity extends BaseActivity<ActivityLanguageBinding> {
    List<LanguageModel> listLanguage;
    String codeLang;
    private boolean languageStart = false;
// khởi tạo ViewBinding để lấy các attri của layout dễ dàng hơn
    @Override
    protected ActivityLanguageBinding setViewBinding() {
        return ActivityLanguageBinding.inflate(LayoutInflater.from(this));
    }

    @Override
    protected void initView() {
        initData();
        // tạo 1 LinearLayoutManager có vai trò quản lý cách sắp xếp các item trong RecyclerView. default: vertical
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        LanguageStartAdapter languageAdapter = new LanguageStartAdapter(listLanguage, code -> codeLang = code, this);

        String codeLangDefault = Locale.getDefault().getLanguage();
        String[] langDefault = {"fr", "pt", "es", "hi"};
        // kiểm tra codeLangDefault có nằm trong langDefault hay ko
        if (!Arrays.asList(langDefault).contains(codeLangDefault)) codeLang = "en";

        languageAdapter.setCheck(codeLang);
        binding.rvLanguage.setLayoutManager(linearLayoutManager);
        binding.rvLanguage.setAdapter(languageAdapter);

        getIntentData();

        if (languageStart) {
            binding.icBack.setVisibility(View.GONE);
        }
    }
// sử lý sự kiện 
    @Override
    protected void viewListener() {
        // nếu click vào icBack thì gọi onBackPressed() để quay lại 
        binding.icBack.setOnClickListener(view -> onBackPressed());
        // nếu click vào imgChooseLanguage thì Lưu ngôn ngữ được chọn và điều hướng đến IntroActivity hoặc MainActivity, sau đó đóng tất cả các activity liên quan.
        binding.imgChooseLanguage.setOnClickListener(view -> {
            SystemUtils.saveLocale(getBaseContext(), codeLang);
            startActivity(new Intent(LanguageActivity.this, languageStart ? IntroActivity.class : MainActivity.class));
            finishAffinity();
        });
    }
    // Kiểm tra và lấy dữ liệu từ Intent nếu có khóa Constants.LANGUAGE_START.
    private void getIntentData() {
        if (getIntent().hasExtra(Constants.LANGUAGE_START)) {
            languageStart = getIntent().getBooleanExtra(Constants.LANGUAGE_START, false);
        }
    }

    private void initData() {
        codeLang = Locale.getDefault().getLanguage();
        listLanguage = new ArrayList<>();
        String lang = Locale.getDefault().getLanguage();
        listLanguage.add(new LanguageModel("English", "en"));
        listLanguage.add(new LanguageModel("French", "fr"));
        listLanguage.add(new LanguageModel("Portuguese", "pt"));
        listLanguage.add(new LanguageModel("Spanish", "es"));
        listLanguage.add(new LanguageModel("Hindi", "hi"));

        for (int i = 0; i < listLanguage.size(); i++) {
            if (listLanguage.get(i).getCode().equals(lang)) {
                listLanguage.add(0, listLanguage.get(i));
                listLanguage.remove(i + 1);
            }
        }
    }
}
