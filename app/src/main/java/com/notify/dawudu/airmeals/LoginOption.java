package com.notify.dawudu.airmeals;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginOption extends AppCompatActivity {

    private Button mpassanger, magent;


    public LoginOption() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_option);

        mpassanger = findViewById(R.id.btnPassanger);
        magent = findViewById(R.id.btnAgent);

        mpassanger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginOption.this, PassangerLoginActivity.class);
                startActivity(intent);
                finish();
                return;

            }
        });

        magent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginOption.this, AgentLoginActivity.class);
                startActivity(intent);
                finish();
                return;

            }
        });
    }
}


