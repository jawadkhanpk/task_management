package com.example.taskmanagement.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.example.taskmanagement.R;
import com.example.taskmanagement.activities.login.CreateHP;
import com.example.taskmanagement.activities.login.LoginActivity;
import com.example.taskmanagement.databinding.ActivityProjectManagerDashboardBinding;

public class ProjectManagerDashboard extends AppCompatActivity {

    ActivityProjectManagerDashboardBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= DataBindingUtil.setContentView(this,R.layout.activity_project_manager_dashboard);

        binding.addClassBtn.setOnClickListener(view -> {
            startActivity(new Intent(ProjectManagerDashboard.this, CreateHP.class));
            finish();

        });
    }
}