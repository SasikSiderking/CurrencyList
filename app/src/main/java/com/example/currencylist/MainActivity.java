package com.example.currencylist;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import Data.CurrencyAppDatabase;
import Services.UpdateService;

public class MainActivity extends AppCompatActivity {

    private CurrencyAppDatabase currencyAppDatabase;//Here's our DB builder

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent serviceIntent = new Intent(getApplicationContext(), UpdateService.class);
        startService(serviceIntent);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(bottomNavigationView,navController);
    }
}