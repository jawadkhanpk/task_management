package com.example.taskmanagement.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.example.taskmanagement.R;
import com.example.taskmanagement.activities.login.LoginActivity;
import com.example.taskmanagement.databinding.ActivityMainDashboardBinding;

public class MainDashboard extends AppCompatActivity {

    ActivityMainDashboardBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main_dashboard);



        binding.createCompany.setOnClickListener(view -> {
            startActivity(new Intent(MainDashboard.this, CreateCompany.class));
        });

        binding.joinCompany.setOnClickListener(view -> {
            startActivity(new Intent(MainDashboard.this, LoginActivity.class));
        });
    }
}