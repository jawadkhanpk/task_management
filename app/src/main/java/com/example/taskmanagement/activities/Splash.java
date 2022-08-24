package com.example.taskmanagement.activities;

import static com.example.taskmanagement.Constant.COMPANIES;
import static com.example.taskmanagement.Constant.USERS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.taskmanagement.Firebase_Auth_SDP;
import com.example.taskmanagement.R;
import com.example.taskmanagement.activities.hrdashboard.HRDashboard;
import com.example.taskmanagement.activities.login.LoginActivity;
import com.example.taskmanagement.activities.pmdashboard.PMDashboard;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class Splash extends AppCompatActivity {

    Firebase_Auth_SDP obj;

    String designation,email,loginEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        obj = Firebase_Auth_SDP.getInstance();

        loginEmail=obj.getAuth().getCurrentUser().getEmail();
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(Splash.this);
//



        obj.getFirebaseDatabase().getReference().child(COMPANIES).child(USERS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    Log.i("mehmood", "Exist: ");
                    for (DataSnapshot dataSnapshot2 : snapshot.getChildren()) {

                        com.example.taskmanagement.model.CreateHP user = dataSnapshot2.getValue(com.example.taskmanagement.model.CreateHP.class);
                        {
                            designation=user.getDesignation();
                            email=user.getEmail();

                            if (loginEmail.equals(email) && designation.equals("HR")) {
                                Log.i("mehmood", "HR: " + designation);
                                Intent intent=new Intent(Splash.this, HRDashboard.class);
                                startActivity(intent);
                                finish();
                            }
                            else if (loginEmail.equals(email) && designation.equals("Project Manager"))
                            {
                                Log.i("mehmood", "Project Manager: " + designation);
                                Intent intent=new Intent(Splash.this, PMDashboard.class);
                                startActivity(intent);
                                finish();

                            }
                        }

                    }

                    if (acct!=null)
                    {
                        Intent intent=new Intent(Splash.this, ProjectManagerDashboard.class);
                        startActivity(intent);
                        finish();
                    }
                    else if (acct==null)
                    {

                            if (Firebase_Auth_SDP.getInstance().getAuth().getCurrentUser()!=null)
                            {
                                if (Firebase_Auth_SDP.getInstance().getAuth().getCurrentUser().getEmail().equals(email))
                                {
                                    Log.i("mehmood", "run: "+designation);
                                    if (loginEmail.equals(email) && designation.equals("HR"))
                                    {
                                        Intent intent=new Intent(Splash.this, HRDashboard.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else if (loginEmail.equals(email) && designation.equals("Project Manager"))
                                    {
                                        Intent intent=new Intent(Splash.this, PMDashboard.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            }
                            else
                            {
                                Intent intent=new Intent(Splash.this, MainDashboard.class);
                                startActivity(intent);
                                finish();
                            }

                    }
                } else {
                    Log.i("mehmood", "Not: ");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (acct!=null)
                            {
                                Intent intent=new Intent(Splash.this, ProjectManagerDashboard.class);
                                startActivity(intent);
                                finish();
                            }
                            else if (acct==null)
                            {
                                if (Firebase_Auth_SDP.getInstance().getAuth().getCurrentUser()!=null)
                                {
                                    if (Firebase_Auth_SDP.getInstance().getAuth().getCurrentUser().getEmail().equals(email))
                                    {
                                        Log.i("mehmood", "run: "+designation);
                                    }
                                }
                                else
                                {
                                    Intent intent=new Intent(Splash.this, MainDashboard.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                            else
                            {
                                Intent intent=new Intent(Splash.this, MainDashboard.class);
                                startActivity(intent);
                                finish();
                            }

                        }
                    }, 300);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Log.i("mehmood", "onCancelled: " + error.getMessage());
            }
        });

    }
}