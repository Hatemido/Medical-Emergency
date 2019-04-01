package com.example.mido.medicalemergency.Slider;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.mido.medicalemergency.GlideApp;
import com.example.mido.medicalemergency.R;

import java.util.List;

public class SliderAdapter extends PagerAdapter {
    Context context;
    LayoutInflater mLayoutInflater;
    List<Integer> views;

    public SliderAdapter(Context context, List<Integer> views) {
        this.context = context;
        this.mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.views = views;
    }

    @Override
    public int getCount() {
        return views.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = mLayoutInflater.inflate(views.get(position), container, false);
//        View currentView = view.findViewById(R.id.sliderView);
//        currentView = views.get(position);
//        GlideApp.with(context)
//                .load(images.get(position))
//                .centerCrop()
//                .into(imageView);
        container.addView(view);
        // save image to storage

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
