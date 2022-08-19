package com.example.taskmanagement.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.taskmanagement.R;
import com.example.taskmanagement.databinding.ActivityGoogleLoginBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class GoogleLoginActivity extends AppCompatActivity {

    ActivityGoogleLoginBinding binding;
    GoogleSignInClient mGoogleSignInClient;
    ActivityResultLauncher<Intent> startActivityIntent;
    GoogleSignInAccount acct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_google_login);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        binding.google.setOnClickListener(view -> {
                signIn();
        });

        startActivityIntent = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        com.google.android.gms.tasks.Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                        handleSignInResult(task);


                    }
                });
    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityIntent.launch(signInIntent);


    }

    private void handleSignInResult(com.google.android.gms.tasks.Task<GoogleSignInAccount> completedTask) {

        acct = GoogleSignIn.getLastSignedInAccount(GoogleLoginActivity.this);
        if (acct != null) {
            String email = acct.getEmail();
            String id=acct.getId();
            Intent intent=new Intent(this,CreateCompany.class);
            intent.putExtra("email",email);
            intent.putExtra("id",id);
            startActivity(intent);
            finish();

        }

    }


}