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
import com.example.FlowChallenge.model.WeatherForecastResult;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerAdapter extends RecyclerView.Adapter {

    private List<com.example.FlowChallenge.model.List> weatherList;
    private listener listener;

    public RecyclerAdapter(List<com.example.FlowChallenge.model.List> weatherList, listener listener) {
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
        com.example.FlowChallenge.model.List list = weatherList.get(position);
        RecyclerViewHolder RecyclerViewHolder = (RecyclerViewHolder) holder;
        RecyclerViewHolder.bind(list);
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

        public void bind(com.example.FlowChallenge.model.List data) {
            textViewDayRecyclerCell.setText(getDay());
            String url = data.getWeather().get(0).getIcon();
            url = "https://openweathermap.org/img/wn/" + url + "@2x.png";
            Glide.with(itemView).load(url).into(imageViewWeather);
            textViewTempRecCell.setText(getTempFormat(data.getMain().getTempMax()));
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

           /* switch (num) {
                case 8:
                    num = 1;
                    break;
                case 9:
                    num = 2;
                    break;
                case 10:
                    num = 3;
                    break;
                case 11:
                    num = 4;
                    break;
            }
            switch (num) {
                case 1:
                    day = "Sunday";
                    break;
                case 2:
                    day = "Monday";
                    break;
                case 3:
                    day = "Tuesday";
                    break;
                case 4:
                    day = "Wednesday";
                    break;
                case 5:
                    day = "Thursday";
                    break;
                case 6:
                    day = "Friday";
                    break;
                case 7:
                    day = "Saturday";
                    break;
            }*/
            return day;
        }
    }

    public interface listener {
        void recyclerListener(WeatherForecastResult weatherResult);
    }
}
