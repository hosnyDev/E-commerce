package com.hosnydevtest.shopapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.hosnydevtest.shopapp.R;

import java.util.HashMap;
import java.util.Map;

public class DetailsProductActivity extends AppCompatActivity {

    private SliderLayout sliderLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_product);

        TextView edDetails = findViewById(R.id.txt_details);
        TextView edPrice = findViewById(R.id.txt_price);
        Toolbar toolbar = findViewById(R.id.toolbar_details_product);
        sliderLayout = findViewById(R.id.slider);

        //get intent data
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String image1 = intent.getStringExtra("image1");
        String image2 = intent.getStringExtra("image2");
        String image3 = intent.getStringExtra("image3");
        String details = intent.getStringExtra("details");
        int price = intent.getIntExtra("price", 0);


        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle(name);
        toolbar.setNavigationOnClickListener(v -> finish());

        edDetails.setText(details);
        edPrice.setText(String.valueOf(price));

        setSliderImage(image1, image2, image3);

    }

    private void setSliderImage(String url1, String url2, String url3) {

        Map<String, String> slider = new HashMap<>();
        slider.put("1", url1);
        slider.put("2", url2);
        slider.put("3", url3);

        for (String name : slider.keySet()) {
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(slider.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            sliderLayout.addSlider(textSliderView);
        }
    }

    @Override
    protected void onStop() {
        sliderLayout.stopAutoCycle();
        super.onStop();
    }

}