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
import com.example.FlowChallenge.model.WeatherForecastResult;
import com.example.FlowChallenge.model.WeatherTodayResult;
import com.example.FlowChallenge.view.adapter.RecyclerAdapter;
import com.example.FlowChallenge.viewModel.WeatherViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsFragment extends Fragment implements RecyclerAdapter.listener {

    public static final String KEY_RESULT = "result";

    private WeatherViewModel weatherViewModel;
    private WeatherTodayResult weatherResultToday;
    private WeatherForecastResult weatherResultForecast;

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


    public static DetailsFragment newInstance(WeatherTodayResult result) {
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
            weatherResultToday = (WeatherTodayResult) getArguments().getSerializable(KEY_RESULT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        ButterKnife.bind(this, view);

        weatherViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);

        getForecastWeather(weatherResultToday.getName());

        if (weatherResultToday.getClouds() != null) {
            setWeatherResults();
        } else {
            getWeather(weatherResultToday.getName());
        }

        return view;
    }

    private void getWeather(String city) {
        weatherViewModel.getWeatherTodayResult(city);
        weatherTodayObserver();
    }

    private void getForecastWeather(String city) {
        weatherViewModel.getWeatherForecastResult(city);
        weatherForecastObserver();
    }

    private void weatherForecastObserver() {
        weatherViewModel.data.observe(getViewLifecycleOwner(), weatherForecastResult -> {
            weatherResultForecast = weatherForecastResult;
            convertListToDaily();
        });
        weatherViewModel.loading.observe(getViewLifecycleOwner(), loading -> {
            if (!loading) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
        weatherViewModel.error.observe(getViewLifecycleOwner(), error -> {
            if (error) {
                Toast.makeText(getContext(), "ERROR WEATHER", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void weatherTodayObserver() {

        weatherViewModel.dataToday.observe(getViewLifecycleOwner(), weatherResult -> {
            if (weatherResult != null) {
                this.weatherResultToday = weatherResult;
                setWeatherResults();
            }
        });
        weatherViewModel.loading.observe(getViewLifecycleOwner(), loading -> {
            if (!loading) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
        weatherViewModel.error.observe(getViewLifecycleOwner(), error -> {
            if (error) {
                Toast.makeText(getContext(), "ERROR WEATHER", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setWeatherResults() {

        String city = weatherResultToday.getName();
        textViewCity.setText(city);
        textViewTodayDetails.setVisibility(View.VISIBLE);
        String temp = "Temperatura: "+getTempFormat(weatherResultToday.getMain().getTemp());
        textViewTempDetails.setText(temp);
        String hum = "Humedad: "+weatherResultToday.getMain().getHumidity() + "%";
        textViewHumDetails.setText(hum);
        String res = weatherResultToday.getWeather().get(0).getDescription();
        textViewResumeDetails.setText(res);
        String url = weatherResultToday.getWeather().get(0).getIcon();
        url = "https://openweathermap.org/img/wn/" + url + "@2x.png";
        Glide.with(this).load(url).into(imageViewWeather);
        String sens = "Sensación Térmica: "+getTempFormat(weatherResultToday.getMain().getFeelsLike());
        textViewSens.setText(sens);
        String max = "Máxima: "+getTempFormat(weatherResultToday.getMain().getTempMax());
        textViewMax.setText(max);
        String min = "Mínima: "+getTempFormat(weatherResultToday.getMain().getTempMin());
        textViewMin.setText(min);
    }

    private String getTempFormat(String stringTemp){
        double temp = Double.parseDouble(stringTemp);
        return Math.round(temp) + "°C";
    }


    private void convertListToDaily() {
        List<com.example.FlowChallenge.model.List> list = new ArrayList<>();
        for (int i = 7; i < 41; i = i + 7) {
            list.add(weatherResultForecast.getList().get(i));
        }
        initRecyclerView(list);
    }


    public void initRecyclerView(List<com.example.FlowChallenge.model.List> list) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(10);
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(list, DetailsFragment.this);
        recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public void recyclerListener(WeatherForecastResult weatherResult) {

    }
}