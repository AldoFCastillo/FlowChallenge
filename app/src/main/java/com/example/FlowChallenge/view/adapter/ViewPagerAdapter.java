package com.example.FlowChallenge.view.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.FlowChallenge.R;
import com.example.FlowChallenge.model.WeatherResult;
import com.example.FlowChallenge.utils.WeatherUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder> {

    private List<WeatherResult> weatherList;
    private listener listener;

    public ViewPagerAdapter(List<WeatherResult> weatherList, listener listener) {
        this.weatherList = weatherList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewPagerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.viewpager_cell, parent, false);
        return new ViewPagerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewPagerViewHolder holder, int position) {
        WeatherResult weatherResult = weatherList.get(position);
        holder.bind(weatherResult);
    }


    @Override
    public int getItemCount() {
        return weatherList.size();
    }

    public class ViewPagerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textViewCityCell)
        TextView textViewCityCell;
        @BindView(R.id.imageViewWeatherCell)
        ImageView imageViewWeatherCell;


        public ViewPagerViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            itemView.setOnClickListener(v -> {
                WeatherResult data = weatherList.get(getAdapterPosition());
                listener.pagerListener(WeatherUtils.getLatAndLon(data));
            });
        }

        public void bind(WeatherResult data) {
            String city = data.getCityName();
            textViewCityCell.setText(city);
            imageViewWeatherCell.setImageResource(WeatherUtils.getIcon(city));

        }
    }

    public interface listener {
        void pagerListener(WeatherResult weatherResult);
    }

}
