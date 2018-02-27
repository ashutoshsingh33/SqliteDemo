package com.examapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.examapp.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        Button buttonNewApplicant = (Button) findViewById(R.id.button_new_aaplicant);
        buttonNewApplicant.setOnClickListener(this);
        Button buttonLogin = (Button) findViewById(R.id.button_login);
        buttonLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.button_new_aaplicant :
                Intent intent = new Intent(this, NewApplicantActivity.class);
                startActivity(intent);
                break;

            case R.id.button_login :
                Intent intentLogin = new Intent(this, LoginActivity.class);
                startActivity(intentLogin);
                break;
        }
    }
}
