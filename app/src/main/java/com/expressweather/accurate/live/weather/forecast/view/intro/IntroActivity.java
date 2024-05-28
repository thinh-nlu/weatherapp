package com.expressweather.accurate.live.weather.forecast.view.intro;

import android.content.Intent;
import android.view.LayoutInflater;

import androidx.viewpager2.widget.ViewPager2;

import com.expressweather.accurate.live.weather.forecast.R;
import com.expressweather.accurate.live.weather.forecast.base.BaseActivity;
import com.expressweather.accurate.live.weather.forecast.databinding.ActivityIntroBinding;
import com.expressweather.accurate.live.weather.forecast.utils.SharePrefUtils;
import com.expressweather.accurate.live.weather.forecast.view.MainActivity;
import com.expressweather.accurate.live.weather.forecast.view.PermissionActivity;
import com.expressweather.accurate.live.weather.forecast.view.intro.adapter.IntroAdapter;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends BaseActivity<ActivityIntroBinding> {
    private boolean isNextActivity = false;
    private ArrayList<String> introTextContentList;
    private ArrayList<String> introTextTitleList;

    @Override
    protected ActivityIntroBinding setViewBinding() {
        return ActivityIntroBinding.inflate(LayoutInflater.from(this));
    }

    @Override
    protected void initView() {
        initIntroSlide();
    }

    @Override
    protected void viewListener() {
        binding.btnNext.setOnClickListener(v -> {
            if (binding.vpSlideIntro.getCurrentItem() < 2) {
                binding.vpSlideIntro.setCurrentItem(binding.vpSlideIntro.getCurrentItem() + 1);
            } else {
                if (!isNextActivity) {
                    Intent intent;
                    if (SharePrefUtils.getCountOpenFirstLang(this) == 0) {
                        intent = new Intent(this, PermissionActivity.class);
                    } else {
                        intent = new Intent(this, MainActivity.class);
                    }

                    startActivity(intent);
                    finish();
                }
                isNextActivity = true;
            }
        });

        binding.vpSlideIntro.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                changeColor();
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                changeColor();
            }
        });
    }

    private void initIntroSlide() {
        List<Integer> introImageList = new ArrayList<>();
        introImageList.add(R.drawable.img_intro_1);
        introImageList.add(R.drawable.img_intro_2);
        introImageList.add(R.drawable.img_intro_3);

        IntroAdapter slideAdapter = new IntroAdapter(introImageList);
        binding.vpSlideIntro.setAdapter(slideAdapter);

        introTextTitleList = new ArrayList<>();
        introTextTitleList.add(getString(R.string.intro_title_1));
        introTextTitleList.add(getString(R.string.intro_title_2));
        introTextTitleList.add(getString(R.string.intro_title_3));

        introTextContentList = new ArrayList<>();
        introTextContentList.add(getString(R.string.intro_content_1));
        introTextContentList.add(getString(R.string.intro_content_2));
        introTextContentList.add(getString(R.string.intro_content_3));
    }

    void changeColor() {
        switch (binding.vpSlideIntro.getCurrentItem()) {
            case 0:
                binding.imgCircle1.setImageResource(R.drawable.ic_dot_selected);
                binding.imgCircle2.setImageResource(R.drawable.ic_dot_not_select);
                binding.imgCircle3.setImageResource(R.drawable.ic_dot_not_select);

                binding.tvTitleIntro.setText(introTextTitleList.get(0));
                binding.tvDescriptionIntro.setText(introTextContentList.get(0));
                binding.btnNext.setText(getString(R.string.next));
                break;
            case 1:
                binding.imgCircle1.setImageResource(R.drawable.ic_dot_not_select);
                binding.imgCircle2.setImageResource(R.drawable.ic_dot_selected);
                binding.imgCircle3.setImageResource(R.drawable.ic_dot_not_select);

                binding.tvTitleIntro.setText(introTextTitleList.get(1));
                binding.tvDescriptionIntro.setText(introTextContentList.get(1));
                binding.btnNext.setText(getString(R.string.next));
                break;
            case 2:
                binding.imgCircle1.setImageResource(R.drawable.ic_dot_not_select);
                binding.imgCircle2.setImageResource(R.drawable.ic_dot_not_select);
                binding.imgCircle3.setImageResource(R.drawable.ic_dot_selected);

                binding.tvTitleIntro.setText(introTextTitleList.get(2));
                binding.tvDescriptionIntro.setText(introTextContentList.get(2));
                binding.btnNext.setText(getString(R.string.get_started));
                break;
        }
    }
}
