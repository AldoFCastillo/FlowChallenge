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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewPagerAdapter extends RecyclerView.Adapter {

    private List<WeatherResult> weatherList;
    private listener listener;

    public ViewPagerAdapter(List<WeatherResult> weatherList, listener listener) {
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
        WeatherResult weatherResult = weatherList.get(position);
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
                WeatherResult data = weatherList.get(getAdapterPosition());
                listener.pagerListener(getLatAndLon(data));
            });
        }

        public void bind(WeatherResult data) {
            String city = data.getCityName();
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
                case "Ciudad Actual":
                    imageViewWeatherCell.setImageResource(R.drawable.ic_city);
            }

        }
    }

    private WeatherResult getLatAndLon(WeatherResult data) {

        switch (data.getCityName()) {
            case "New York":
                data.setLat("40.6643");
                data.setLon("-73.9385");
                break;
            case "Paris":
                data.setLat("48.8032");
                data.setLon("2.3511");
                break;
            case "Rio de Janeiro":
                data.setLat("-22.9035");
                data.setLon("-43.2096");
                break;
            case "Berlin":
                data.setLat("52.52437");
                data.setLon("13.41053");
                break;
            case "Madrid":
                data.setLat("40.4167");
                data.setLon("-3.70325");
                break;

        }

        return data;
    }

    public interface listener {
        void pagerListener(WeatherResult weatherResult);
    }

}
