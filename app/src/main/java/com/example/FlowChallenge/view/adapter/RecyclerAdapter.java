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
            textViewDayRecyclerCell.setText(getDay());
            String url = data.getWeather().get(0).getIcon();
            url = "https://openweathermap.org/img/wn/" + url + "@2x.png";
            Glide.with(itemView).load(url).into(imageViewWeather);
            textViewTempRecCell.setText(getTempFormat(data.getTemp().getMax()));
        }

        private String getTempFormat(String stringTemp) {
            double temp = Double.parseDouble(stringTemp);
            return Math.round(temp) + "°C";
        }

        private String getDay() {
            Calendar calendario = Calendar.getInstance();
            String day = "";
            int num = calendario.get(Calendar.DAY_OF_WEEK) + getAdapterPosition() + 1;
            if (getAdapterPosition() == 0) {
                day = "Mañana";
            } else {
                if (num == 1 || num == 8) day = "Domingo";
                if (num == 2 || num == 9) day = "Lunes";
                if (num == 3 || num == 10) day = "Martes";
                if (num == 4 || num == 11) day = "Miercoles";
                if (num == 5) day = "Jueves";
                if (num == 6) day = "Viernes";
                if (num == 7) day = "Sabado";
            }

            return day;
        }
    }

    public interface listener {
        void recyclerListener(WeatherResult weatherResult);
    }
}
