package com.example.FlowChallenge.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.FlowChallenge.R;
import com.example.FlowChallenge.model.Daily;
import com.example.FlowChallenge.model.WeatherResult;
import com.example.FlowChallenge.utils.WeatherUtils;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerAdapter extends RecyclerView.Adapter {

    private List<Daily> weatherList;
    private listener listener;

    public RecyclerAdapter(List<Daily> weatherList, listener listener) {
        this.weatherList = weatherList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_cell, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Daily daily = weatherList.get(position);
        RecyclerViewHolder RecyclerViewHolder = (RecyclerViewHolder) holder;
        RecyclerViewHolder.bind(daily);
    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.textViewDayRecyclerCell)
        TextView textViewDayRecyclerCell;
        @BindView(R.id.imageViewWeatherRecyclerCell)
        ImageView imageViewWeather;
        @BindView(R.id.textViewTempRecyclerCell)
        TextView textViewTempRecCell;


        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(v -> {
                //////listener
            });
        }

        public void bind(Daily data) {
            String day = WeatherUtils.getDay(getAdapterPosition());
            textViewDayRecyclerCell.setText(day);
            String url = data.getWeather().get(0).getIcon();
            url = "https://openweathermap.org/img/wn/" + url + "@2x.png";
            Glide.with(itemView).load(url).into(imageViewWeather);
            String temp = WeatherUtils.getTempFormat(data.getTemp().getMax());
            textViewTempRecCell.setText(temp);
        }
    }

    public interface listener {
        void recyclerListener(WeatherResult weatherResult);
    }
}
