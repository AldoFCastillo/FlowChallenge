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
import com.example.FlowChallenge.utils.WeatherUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

    private List<Daily> weatherList;

    public RecyclerAdapter(List<Daily> weatherList) {
        this.weatherList = weatherList;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_cell, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        Daily daily = weatherList.get(position);
        holder.bind(daily);
    }


    @Override
    public int getItemCount() {
        return weatherList.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.textViewDayRecyclerCell)
        TextView textViewDayRecyclerCell;
        @BindView(R.id.imageViewWeatherRecyclerCell)
        ImageView imageViewWeather;
        @BindView(R.id.textViewTempRecyclerCell)
        TextView textViewTempRecCell;


        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
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


}
