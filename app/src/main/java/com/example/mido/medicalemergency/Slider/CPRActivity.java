package com.example.mido.medicalemergency.Slider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.mido.medicalemergency.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CPRActivity extends AppCompatActivity {

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.next)
    ImageView nextButton;
    @BindView(R.id.previous)
    ImageView previousButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpr);
        ButterKnife.bind(this);

        nextButton.setImageResource(R.drawable.ic_chevron_right);
        previousButton.setImageResource(R.drawable.ic_chevron_left);
        List<Integer> views = new ArrayList<>();
        views.add(R.layout.cpr_step_1);
        views.add(R.layout.cpr_step_2);
        views.add(R.layout.cpr_step_3);
        views.add(R.layout.cpr_step_4);
        SliderAdapter sliderAdapter = new SliderAdapter(this, views);

        viewPager.setAdapter(sliderAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position,
                                       float positionOffset,
                                       int positionOffsetPixels
            ) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        previousButton.setVisibility(View.INVISIBLE);
                        break;

                    case 1:
                        previousButton.setVisibility(View.VISIBLE);
                        nextButton.setImageResource(R.drawable.ic_chevron_right);
                        break;
                    case 2:
                        previousButton.setVisibility(View.VISIBLE);
                        nextButton.setImageResource(R.drawable.ic_chevron_right);
                        break;

                    case 3:
                        nextButton.setImageResource(R.drawable.ic_don);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    void next() {
        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);

    }

    void previous() {
        viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);

    }

    public void next(View view) {
        if (viewPager.getCurrentItem() != 3) {
            next();
        } else {
            done();
        }
    }

    private void done() {
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        sharedPreferences.edit().putBoolean(Splash.FRIST, false).apply();
//        startActivity(new Intent(this, AuthActivity.class));
        finish();
    }

    public void previous(View view) {
        if (viewPager.getCurrentItem() != 0) {
            previous();
        }
    }
}
