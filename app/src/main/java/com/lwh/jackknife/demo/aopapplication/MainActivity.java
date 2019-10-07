package com.lwh.jackknife.demo.aopapplication;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.lwh.jackknife.permission.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btn);
        btn.setOnClickListener(this);
    }

    @Permission(com.lwh.jackknife.permission.runtime.Permission.CALL_PHONE)
    @SingleClick
    @Override
    public void onClick(View view) {
        Toast.makeText(this, "click", Toast.LENGTH_SHORT).show();
    }
}
