package com.example.FlowChallenge.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.FlowChallenge.R;
import com.example.FlowChallenge.model.Daily;
import com.example.FlowChallenge.model.WeatherResult;
import com.example.FlowChallenge.view.adapter.RecyclerAdapter;
import com.example.FlowChallenge.viewModel.WeatherViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsFragment extends Fragment implements RecyclerAdapter.listener {

    public static final String KEY_RESULT = "result";

    private WeatherViewModel weatherViewModel;
    private WeatherResult weatherResult;
    private String cityName;

    @BindView(R.id.textViewCityDetails)
    TextView textViewCity;
    @BindView(R.id.textViewTodayDetails)
    TextView textViewTodayDetails;
    @BindView(R.id.progressBarDetails)
    ProgressBar progressBar;
    @BindView(R.id.textViewTempDetails)
    TextView textViewTempDetails;
    @BindView(R.id.textViewHumDetails)
    TextView textViewHumDetails;
    @BindView(R.id.textViewResumeDetails)
    TextView textViewResumeDetails;
    @BindView(R.id.imageViewWeatherDetails)
    ImageView imageViewWeather;
    @BindView(R.id.textViewSensationDetails)
    TextView textViewSens;
    @BindView(R.id.textViewMaxDetails)
    TextView textViewMax;
    @BindView(R.id.textViewMinDetails)
    TextView textViewMin;
    @BindView(R.id.recyclerDetails)
    RecyclerView recyclerView;


    public DetailsFragment() {
        // Required empty public constructor
    }


    public static DetailsFragment newInstance(WeatherResult result) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_RESULT, result);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            weatherResult = (WeatherResult) getArguments().getSerializable(KEY_RESULT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        ButterKnife.bind(this, view);

        weatherViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);

        cityName = weatherResult.getCityName();

        setWeatherResults();
        getNextFiveDays();


        return view;
    }


    private void setWeatherResults() {
        progressBar.setVisibility(View.INVISIBLE);
        Daily dailyToday = weatherResult.getDaily().get(0);
        String city = cityName;
        textViewCity.setText(city);
        textViewTodayDetails.setVisibility(View.VISIBLE);
        String temp = "Temperatura: " + getTempFormat(weatherResult.getCurrent().getTemp());
        textViewTempDetails.setText(temp);
        String hum = "Humedad: " + dailyToday.getHumidity() + "%";
        textViewHumDetails.setText(hum);
        String res = dailyToday.getWeather().get(0).getDescription();
        textViewResumeDetails.setText(res);
        String url = dailyToday.getWeather().get(0).getIcon();
        url = "https://openweathermap.org/img/wn/" + url + "@2x.png";
        Glide.with(this).load(url).into(imageViewWeather);
        String sens = "Sensación Térmica: " + getTempFormat(weatherResult.getCurrent().getFeelsLike());
        textViewSens.setText(sens);
        String max = "Máxima: " + getTempFormat(dailyToday.getTemp().getMax());
        textViewMax.setText(max);
        String min = "Mínima: " + getTempFormat(dailyToday.getTemp().getMin());
        textViewMin.setText(min);
    }

    private String getTempFormat(String stringTemp) {
        double temp = Double.parseDouble(stringTemp);
        return Math.round(temp) + "°C";
    }


    private void getNextFiveDays() {
        List<Daily> dailyList = weatherResult.getDaily();
        dailyList.remove(0);
        dailyList.remove(6);
        dailyList.remove(5);
        initRecyclerView(dailyList);
    }


    public void initRecyclerView(List<Daily> list) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(10);
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(list, DetailsFragment.this);
        recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public void recyclerListener(WeatherResult weatherResult) {

    }
}