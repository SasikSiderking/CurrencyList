package com.example.currencylist;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import Data.CurrencyAppDatabase;
import Services.UpdateService;

public class MainActivity extends AppCompatActivity {

private static final String KEY_UPDATE_SERVICE="ServiceBool";
private Boolean serviceRunning = false;

    private CurrencyAppDatabase currencyAppDatabase;//Here's our DB builder

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState!=null){
            serviceRunning = savedInstanceState.getBoolean(KEY_UPDATE_SERVICE);
        }
        if (!serviceRunning){
            Intent serviceIntent = new Intent(getApplicationContext(), UpdateService.class);
            startService(serviceIntent);
            serviceRunning = true;
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(bottomNavigationView,navController);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_UPDATE_SERVICE,serviceRunning);
    }
}