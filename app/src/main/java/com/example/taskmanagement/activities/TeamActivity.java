package com.example.taskmanagement.activities;

import static com.example.taskmanagement.Constant.COMPANIES;
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
import android.util.Log;

import com.example.taskmanagement.Firebase_Auth_SDP;
import com.example.taskmanagement.R;
import com.example.taskmanagement.activities.login.LoginActivity;
import com.example.taskmanagement.activities.pmdashboard.PMDashboard;
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
    String companyName,designation,name,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_team);

        obj = Firebase_Auth_SDP.getInstance();
//        CreateTeam team = new CreateTeam(teamName, status,);
//        binding.setItem(team);

        companyName=getIntent().getStringExtra("companyName");
//        designation=getIntent().getStringExtra("designation");
//        name=getIntent().getStringExtra("name");
        email=getIntent().getStringExtra("email");
        Log.i("mehmood", "onCreate............................: "+email);
        binding.createTeam.setOnClickListener(view -> {
            teamName = binding.teamName.getText().toString();
            status = binding.status.getText().toString();
            if (TextUtils.isEmpty(teamName)) {
                binding.teamName.setError("Enter Team Name");
            } else if (TextUtils.isEmpty(status)) {
                binding.status.setError("Enter status");
            } else {


                sendData(teamName, status,email);

            }
        });
    }

    private void sendData(String teamName, String status,String email) {
        ProgressDialog dialog = new ProgressDialog(TeamActivity.this);
        dialog.setTitle("Wait....");
        dialog.setMessage("Detail Save in Database");
        dialog.show();

        CreateTeam team = new CreateTeam(teamName, status,"","",email);
        obj.getFirebaseDatabase().getReference().child(COMPANIES).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                obj.getFirebaseDatabase().getReference().child(COMPANIES).child(companyName).child(teamName).setValue(team);

                Intent intent = new Intent(TeamActivity.this, PMDashboard.class);
                startActivity(intent);
                finish();
                dialog.dismiss();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}