package com.example.FlowChallenge.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Toast;

import com.example.FlowChallenge.R;

import com.example.FlowChallenge.model.WeatherTodayResult;
import com.example.FlowChallenge.view.fragment.DetailsFragment;
import com.example.FlowChallenge.view.fragment.MainFragment;

import com.facebook.stetho.Stetho;


public class MainActivity extends AppCompatActivity implements MainFragment.listener {

    private long backPressedTime;
    private Toast backToast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Stetho.initializeWithDefaults(this);

        setFragment(new MainFragment());
    }


    public void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.constraintMainFragContainer, fragment);
        fragmentTransaction.commit();
    }


    @Override
    public void mainFragmentListener(WeatherTodayResult weatherResult) {
        DetailsFragment detailsFragment = DetailsFragment.newInstance(weatherResult);
        setFragment(detailsFragment);

    }


    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();

        } else {
            backToast = Toast.makeText(getBaseContext(), "Presiona atras nuevamente para salir", Toast.LENGTH_SHORT);
            backToast.show();
            setFragment(new MainFragment());

        }
        backPressedTime = System.currentTimeMillis();
    }

}