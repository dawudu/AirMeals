package com.notify.dawudu.airmeals;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MealSeats extends AppCompatActivity {

    TextView mViewMealSeat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_seats);

        mViewMealSeat = findViewById(R.id.viewMealSeat);
        mViewMealSeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MealSeats.this, OrderStatusAgent.class);
                startActivity(intent);
            }
        });
    }
}
