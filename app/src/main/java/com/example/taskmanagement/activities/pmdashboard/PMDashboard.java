package com.example.taskmanagement.activities.pmdashboard;

import static com.example.taskmanagement.Constant.COMPANIES;
import static com.example.taskmanagement.Constant.USERS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.util.Log;

import com.example.taskmanagement.Firebase_Auth_SDP;
import com.example.taskmanagement.R;
import com.example.taskmanagement.activities.TeamActivity;
import com.example.taskmanagement.activities.login.CreateHP;
import com.example.taskmanagement.databinding.ActivityPmdashboardBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class PMDashboard extends AppCompatActivity {

    ActivityPmdashboardBinding binding;
    Firebase_Auth_SDP obj;
    String companyName,designation,name,email,email2,storeDesignation,storeName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding= DataBindingUtil.setContentView(this,R.layout.activity_pmdashboard);


       obj=Firebase_Auth_SDP.getInstance();
       email=obj.getAuth().getCurrentUser().getEmail();


//       email2=getIntent().getStringExtra("email");

        obj.getFirebaseDatabase().getReference().child(COMPANIES).child(USERS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    com.example.taskmanagement.model.CreateHP model_class = dataSnapshot.getValue(com.example.taskmanagement.model.CreateHP.class);
                    companyName = model_class.getCompanyName();
//                    email=model_class.getEmail();

//                    if (email2.equals(model_class.getEmail())) {
//                        designation = model_class.getDesignation();
//                        name = model_class.getName();
////                        email = model_class.getEmail();
//                        storeDesignation=designation;
//                        storeName=name;
//                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.projectManager.setOnClickListener(view -> {
           Intent intent = new Intent(getApplicationContext(), TeamActivity.class);
           intent.putExtra("companyName",companyName);
//           intent.putExtra("designation",storeDesignation);
//           intent.putExtra("name",storeName);
           intent.putExtra("email",email);
           startActivity(intent);
       });
    }
}