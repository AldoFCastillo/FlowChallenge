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
import com.example.FlowChallenge.model.WeatherTodayResult;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewPagerAdapter extends RecyclerView.Adapter {

    private List<WeatherTodayResult> weatherList;
    private listener listener;

    public ViewPagerAdapter(List<WeatherTodayResult> weatherList, listener listener) {
        this.weatherList = weatherList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.viewpager_cell, parent, false);
        return new ViewPagerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        WeatherTodayResult weatherResult = weatherList.get(position);
        ViewPagerViewHolder viewPagerViewHolder = (ViewPagerViewHolder) holder;
        viewPagerViewHolder.bind(weatherResult);
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
                listener.pagerListener(weatherList.get(getAdapterPosition()));
            });
        }

        public void bind(WeatherTodayResult data) {
            String city = data.getName();
            textViewCityCell.setText(city);

            switch (city) {
                case "New York":
                    imageViewWeatherCell.setImageResource(R.drawable.ic_ny);
                    break;
                case "Paris":
                    imageViewWeatherCell.setImageResource(R.drawable.ic_paris);
                    break;
                case "Rio de Janeiro":
                    imageViewWeatherCell.setImageResource(R.drawable.ic_rio);
                    break;
                case "Berlin":
                    imageViewWeatherCell.setImageResource(R.drawable.ic_berlin);
                    break;
                case "Madrid":
                    imageViewWeatherCell.setImageResource(R.drawable.ic_madrid);
                    break;
            }

        }
    }

    public interface listener {
        void pagerListener(WeatherTodayResult weatherResult);
    }

}
