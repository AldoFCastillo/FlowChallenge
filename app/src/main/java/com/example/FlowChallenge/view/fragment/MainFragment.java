package com.example.FlowChallenge.view.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.FlowChallenge.R;
import com.example.FlowChallenge.model.WeatherTodayResult;
import com.example.FlowChallenge.utils.HorizontalFlipTransformation;
import com.example.FlowChallenge.view.adapter.ViewPagerAdapter;
import com.example.FlowChallenge.viewModel.IPViewModel;
import com.example.FlowChallenge.viewModel.IpapiViewModel;
import com.example.FlowChallenge.viewModel.WeatherViewModel;
import com.facebook.stetho.Stetho;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainFragment extends Fragment implements ViewPagerAdapter.listener {

    private WeatherViewModel weatherViewModel;
    private IpapiViewModel ipapiViewModel;
    private IPViewModel ipViewModel;
    private listener listener;

    @BindView(R.id.cardViewMain)
    CardView cardViewMain;
    @BindView(R.id.textViewCityMain)
    TextView textViewCity;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.textViewTempMain)
    TextView textViewTempMain;
    @BindView(R.id.textViewResumeMain)
    TextView textViewResumeMain;
    @BindView(R.id.imageViewWeatherMain)
    ImageView imageViewWeather;
    @BindView(R.id.viewPagerCardViewMain)
    ViewPager2 viewPagerCardViewMain;
    @BindView(R.id.tabLayoutMain)
    TabLayout tabLayout;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (listener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        Stetho.initializeWithDefaults(container.getContext());
        ButterKnife.bind(this, view);

        weatherViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        ipapiViewModel = new ViewModelProvider(this).get(IpapiViewModel.class);
        ipViewModel = new ViewModelProvider(this).get(IPViewModel.class);

        getIP();
        //initOtherCities();


        return view;
    }

    private void getIP() {
        ipViewModel.getIP();
        IPObserver();
    }

    private void IPObserver() {
        ipViewModel.data.observe(getViewLifecycleOwner(), ipAddress -> {
            if (ipAddress != null) {
                getCurrentLocation(ipAddress.getIp());
            }
        });
        ipViewModel.error.observe(getViewLifecycleOwner(), error -> {
            if (error) {
                Toast.makeText(getContext(), "Ocurrio un error obteniendo el IP", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getCurrentLocation(String ip) {
        ipapiViewModel.getIpapiResult(ip);
        currentLocObserver();
    }

    private void currentLocObserver() {

        ipapiViewModel.data.observe(getViewLifecycleOwner(), ipapiResult -> {
            if (ipapiResult != null) {
                String city = ipapiResult.getCity();
                getWeather(city);

            }
        });
        ipapiViewModel.error.observe(getViewLifecycleOwner(), error -> {
            if (error) {
                Toast.makeText(getContext(), "ERROR IP-API", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getWeather(String city) {
        weatherViewModel.getWeatherTodayResult(city);
        weatherObserver();
    }

    private void weatherObserver() {

        weatherViewModel.dataToday.observe(getViewLifecycleOwner(), weatherTodayResult -> {
            if (weatherTodayResult != null) {
                setWeatherResults(weatherTodayResult);
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

    private void setWeatherResults(WeatherTodayResult data) {
        String city = data.getName();
        textViewCity.setText(city);
        double temp = Double.parseDouble(data.getMain().getTemp().toString());
        String stringTemp = Math.round(temp) + "°C";
        textViewTempMain.setText(stringTemp);
        String res = data.getWeather().get(0).getDescription();
        textViewResumeMain.setText(res);
        String url = data.getWeather().get(0).getIcon();
        url = "https://openweathermap.org/img/wn/" + url + "@2x.png";
        Glide.with(this).load(url).into(imageViewWeather);
        chooseCurrentCity(data);
        initOtherCities();
    }

    private void chooseCurrentCity(WeatherTodayResult data) {
        cardViewMain.setOnClickListener(v -> {
            listener.mainFragmentListener(data);
        });
    }

    private void initOtherCities() {
        List<String> citiesList = Arrays.asList("New York", "Paris", "Rio de Janeiro", "Berlin", "Madrid");
        List<WeatherTodayResult> resultList = new ArrayList<>();
        for (String cityString : citiesList) {
            WeatherTodayResult weatherResult = new WeatherTodayResult();
            weatherResult.setName(cityString);
            resultList.add(weatherResult);
        }
        setViewPager(resultList);
    }

    private void setViewPager(List<WeatherTodayResult> resultList) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(resultList, this );
        viewPagerCardViewMain.setPageTransformer(new HorizontalFlipTransformation());
        viewPagerCardViewMain.setAdapter(viewPagerAdapter);
        viewPagerCardViewMain.setCurrentItem(0);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPagerCardViewMain, (tab, position) -> {
        });
        tabLayoutMediator.attach();
    }

    @Override
    public void pagerListener(WeatherTodayResult weatherResult) {
        listener.mainFragmentListener(weatherResult);
    }

    public interface listener{
        void mainFragmentListener(WeatherTodayResult weatherResult);

    }
}