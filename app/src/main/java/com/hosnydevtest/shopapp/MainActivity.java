package com.hosnydevtest.shopapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private TextView title;

    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = findViewById(R.id.title);
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav);

        navController=Navigation.findNavController(this,R.id.nav_host_fragment);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            switch (item.getItemId()){

                case R.id.home_menu:
                    title.setText(R.string.home);
                    navController.navigate(R.id.homeFragment);
                    return true;

                case R.id.offers_menu:
                    title.setText(R.string.offers);
                    navController.navigate(R.id.offersFragment);
                    return true;

                case R.id.orders_menu:
                    title.setText(R.string.orders);
                    navController.navigate(R.id.ordersFragment);
                    return true;

                case R.id.profile_menu:
                    title.setText(R.string.profile);
                    navController.navigate(R.id.profileFragment);
                    return true;
            }

            return false;
        });

    }
}