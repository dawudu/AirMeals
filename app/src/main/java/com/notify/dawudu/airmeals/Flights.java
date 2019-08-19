package com.notify.dawudu.airmeals;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Flights extends AppCompatActivity {

    ImageView mFlight1, mFlight2, mFlight3, mFlight4, mFlight5, mFlight6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flights);

        mFlight1 = (ImageView) findViewById(R.id.flight1);
        mFlight2 = (ImageView) findViewById(R.id.flight2);
        mFlight3 = (ImageView) findViewById(R.id.flight3);
        mFlight4 = (ImageView) findViewById(R.id.flight4);
        mFlight5 = (ImageView) findViewById(R.id.flight5);
        mFlight6 = (ImageView) findViewById(R.id.flight6);

        //Flight1
        mFlight1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Flights.this, MealSeats.class);
                startActivity(intent);
            }
        });

        //Flight2
        mFlight2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Flights.this, MealSeats.class);
                startActivity(intent);
            }
        });

        //Flight3
        mFlight3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Flights.this, MealSeats.class);
                startActivity(intent);
            }
        });

        //Flight4
        mFlight4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Flights.this, MealSeats.class);
                startActivity(intent);
            }
        });

        //Flight5
        mFlight5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Flights.this, MealSeats.class);
                startActivity(intent);
            }
        });

        //Flight6
        mFlight6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Flights.this, MealSeats.class);
                startActivity(intent);
            }
        });
    }
}
