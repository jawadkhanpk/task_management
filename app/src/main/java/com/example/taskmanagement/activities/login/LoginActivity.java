package com.example.taskmanagement.activities.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.example.taskmanagement.Firebase_Auth_SDP;
import com.example.taskmanagement.R;
import com.example.taskmanagement.activities.Dashboard;
import com.example.taskmanagement.databinding.ActivityLoginBinding;
import com.example.taskmanagement.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    Firebase_Auth_SDP obj;
    String str_email, str_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);


        obj = Firebase_Auth_SDP.getInstance();

        User user = new User("", str_email, "", "", str_password,"");
        binding.setItem(user);


        binding.Login.setOnClickListener(view -> {
            str_email = binding.email.getText().toString();
            str_password = binding.password.getText().toString();
            if (TextUtils.isEmpty(str_email)) {
                binding.email.setError("Enter Email");
            } else if (TextUtils.isEmpty(str_password)) {
                binding.password.setError("Enter Password");
            } else {

                loginInApp_Admin(str_email, str_password);


            }
        });
    }

    private void loginInApp_Admin(String s_email, String s_password) {
        ProgressDialog dialog = new ProgressDialog(LoginActivity.this);
        dialog.setTitle("Wait....");
        dialog.setMessage("Detail Match in Database");
        dialog.show();
        obj.getAuth().signInWithEmailAndPassword(s_email, s_password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    String uid = FirebaseAuth.getInstance().getUid();
                    Intent intent = new Intent(LoginActivity.this, Dashboard.class);
                    intent.putExtra("email", str_email);
                    intent.putExtra("uID", uid);
                    startActivity(intent);
                    finish();
                    dialog.dismiss();


                } else {
                    dialog.dismiss();
                    String error = task.getException().toString();
                    binding.email.setError("Email not registered!");
                    binding.email.requestFocus();
                }
            }

        });


    }

}