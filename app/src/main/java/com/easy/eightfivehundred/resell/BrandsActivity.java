package com.easy.eightfivehundred.resell;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Toast;

public class BrandsActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brands);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Shop By Brands");

        CardView bapeCardview = findViewById(R.id.bape_card);
        bapeCardview.setOnClickListener(this);
        CardView nikeCardview = findViewById(R.id.nike_card);
        nikeCardview.setOnClickListener(this);
        CardView jordanCardview = findViewById(R.id.jordan_card);
        jordanCardview.setOnClickListener(this);
        CardView palaceCardview = findViewById(R.id.palace_card);
        palaceCardview.setOnClickListener(this);
        CardView asscCardview = findViewById(R.id.assc_card);
        asscCardview.setOnClickListener(this);
        CardView supremeCardview = findViewById(R.id.supreme_card);
        supremeCardview.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bape_card:
                Toast.makeText(this, "Click!", Toast.LENGTH_SHORT).show();;
                break;
            case R.id.nike_card:
                Toast.makeText(this, "Click!", Toast.LENGTH_SHORT).show();;
                break;
            case R.id.jordan_card:
                Toast.makeText(this, "Click!", Toast.LENGTH_SHORT).show();;
                break;
            case R.id.palace_card:
                Toast.makeText(this, "Click!", Toast.LENGTH_SHORT).show();;
                break;
            case R.id.assc_card:
                Toast.makeText(this, "Click!", Toast.LENGTH_SHORT).show();;
                break;
            case R.id.supreme_card:
                Toast.makeText(this, "Click!", Toast.LENGTH_SHORT).show();;
                break;
        }
    }
}
