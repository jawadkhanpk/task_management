package com.example.taskmanagement.activities;

import static com.example.taskmanagement.Constant.COMPANY_NAME;
import static com.example.taskmanagement.Constant.SUPPORTIVE_HAND;

import static com.example.taskmanagement.Constant.USERS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import com.example.taskmanagement.Firebase_Auth_SDP;
import com.example.taskmanagement.R;
import com.example.taskmanagement.activities.login.LoginActivity;
import com.example.taskmanagement.databinding.ActivityTeamBinding;
import com.example.taskmanagement.model.CreateTeam;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class TeamActivity extends AppCompatActivity {

    ActivityTeamBinding binding;
    String teamName, status;
    Firebase_Auth_SDP obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_team);

        obj = Firebase_Auth_SDP.getInstance();
        CreateTeam team = new CreateTeam(teamName, status);
        binding.setItem(team);

        binding.createTeam.setOnClickListener(view -> {
            teamName = binding.teamName.getText().toString();
            status = binding.status.getText().toString();
            if (TextUtils.isEmpty(teamName)) {
                binding.teamName.setError("Enter Team Name");
            } else if (TextUtils.isEmpty(status)) {
                binding.status.setError("Enter status");
            } else {


                sendData(teamName, status);

            }
        });
    }

    private void sendData(String teamName, String status) {
        ProgressDialog dialog = new ProgressDialog(TeamActivity.this);
        dialog.setTitle("Wait....");
        dialog.setMessage("Detail Save in Database");
        dialog.show();

        CreateTeam team = new CreateTeam(teamName, status);
        obj.getFirebaseDatabase().getReference().child(COMPANY_NAME).child(SUPPORTIVE_HAND).child(USERS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                obj.getFirebaseDatabase().getReference().child(COMPANY_NAME).child(SUPPORTIVE_HAND).child(teamName).setValue(team);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(TeamActivity.this, ProjectManagerDashboard.class);
                        startActivity(intent);
                        finish();
                        dialog.dismiss();

                    }
                }, 500);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}