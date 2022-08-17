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
import android.util.Log;
import android.widget.Toast;

import com.example.taskmanagement.Firebase_Auth_SDP;
import com.example.taskmanagement.R;
import com.example.taskmanagement.activities.login.LoginActivity;
import com.example.taskmanagement.databinding.ActivityDashboardBinding;
import com.example.taskmanagement.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class Dashboard extends AppCompatActivity {


    private ActivityDashboardBinding binding;
    private String email, uID, companyName, designation;
    private Firebase_Auth_SDP obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);

        obj = Firebase_Auth_SDP.getInstance();
        email = getIntent().getStringExtra("email");
        uID = getIntent().getStringExtra("uID");
        Log.i("rafaqat", "email..: " + email);
        Log.i("rafaqat", "uID.....: " + uID);


//        User user = new User("", "", "", "", "",companyName);
//        binding.setItem(user);


        obj.getFirebaseDatabase().getReference().child(COMPANY_NAME).child(SUPPORTIVE_HAND).child(USERS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//
//                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        for (DataSnapshot dataSnapshot2 : snapshot.getChildren()) {
                            User user = dataSnapshot2.getValue(User.class);
                            {

                                if (user.getuID().equals(uID)) {
                                    companyName = user.getCompanyName();
                                    designation = user.getDesignation();
                                    binding.companyName.setText("Welcome " + companyName);
                                    binding.designation.setText("Welcome " + designation);
                                }
                            }
//                        }
//                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Log.i("mehmood", "onCancelled: " + error.getMessage());
            }
        });


        binding.join.setOnClickListener(view -> {
            ProgressDialog dialog = new ProgressDialog(Dashboard.this);
            dialog.setTitle("Wait....");
            dialog.setMessage("Detail Match in Database");
            dialog.show();

            obj.getFirebaseDatabase().getReference().child(COMPANY_NAME).child(SUPPORTIVE_HAND).child(USERS).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

//                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//
//                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            for (DataSnapshot dataSnapshot2 : snapshot.getChildren()) {
                                User user = dataSnapshot2.getValue(User.class);
                                {

                                    if (user.getuID().equals(uID) && user.getDesignation().equals(designation)) {
                                        startActivity(new Intent(Dashboard.this, ProjectManagerDashboard.class));
                                        finish();
                                        dialog.dismiss();
                                    }

                                }
//                            }
//                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                    Log.i("mehmood", "onCancelled: " + error.getMessage());
                }
            });

        });

    }
}