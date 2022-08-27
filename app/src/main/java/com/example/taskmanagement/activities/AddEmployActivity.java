package com.example.taskmanagement.activities;

import static com.example.taskmanagement.Constant.COMPANIES;
import static com.example.taskmanagement.Constant.EMPLOY;
import static com.example.taskmanagement.Constant.USERS;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.example.taskmanagement.Firebase_Auth_SDP;
import com.example.taskmanagement.R;
import com.example.taskmanagement.activities.hrdashboard.HRDashboard;
import com.example.taskmanagement.activities.login.CreateHP;
import com.example.taskmanagement.databinding.ActivityAddEmployBinding;
import com.example.taskmanagement.fragment.Employ;
import com.example.taskmanagement.model.RegisterEmployModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddEmployActivity extends AppCompatActivity {

    ActivityAddEmployBinding binding;
    Intent uri;
    String name, email, role, key, password;
    Firebase_Auth_SDP obj;
    ProgressDialog dialog;
    String companyName,des;
    RegisterEmployModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_add_employ);

        obj = Firebase_Auth_SDP.getInstance();
        model = new RegisterEmployModel();
        binding.setItem(model);

        companyName=getIntent().getStringExtra("companyName");
        des=getIntent().getStringExtra("designation");
        model.setCompanyName(companyName);

        dialog = new ProgressDialog(this);
        binding.selectedImage.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            someActivityResultLauncher.launch(intent);

        });

        binding.register.setOnClickListener(view -> {
            name = binding.name.getText().toString();
            role=binding.role.getText().toString();
            email = binding.email.getText().toString();
            password = binding.password.getText().toString();


            if (uri == null) {
                Toast.makeText(this, "Upload Logo", Toast.LENGTH_SHORT).show();
            } else if (name.isEmpty()) {
                binding.name.setError("Please Name");
            }else if (role.isEmpty()) {
                binding.role.setError("Please Role");
            }
            else if (email.isEmpty()) {
                binding.email.setError("Please Email");
            }
            else if (password.isEmpty()) {
                binding.password.setError("Please Password");
            } else {


                sendData(name,role, email, password,uri.getData(),key);
            }

        });
    }



    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        uri = result.getData();

                        binding.selectedImage.setImageURI(uri.getData());
                    }
                }
            });


    private void sendData(String name, String role, String email, String password, Uri uri,String key) {
        dialog.show();
        dialog.setTitle("Upload Detail");
        StorageReference sRef = obj.getStorageReference().getReference("Register Employ People").child(String.valueOf(System.currentTimeMillis()));
        key = obj.getDatabaseReference().push().getKey();
        String finalKey = key;
        sRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                   @Override
                                                   public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                       dialog.dismiss();
                                                       taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                           @Override
                                                           public void onSuccess(Uri downloadUri) {


                                                               RegisterEmployModel model=new RegisterEmployModel(name,role,email,password,downloadUri.toString(),companyName,finalKey,obj.getAuth().getCurrentUser().getEmail(),des);

                                                               obj.getAuth().createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                                   @Override
                                                                   public void onComplete(@NonNull Task<AuthResult> task) {
                                                                       if (task.isSuccessful()) {
                                                                           obj.getFirebaseDatabase().getReference().child(COMPANIES).addValueEventListener(new ValueEventListener() {
                                                                               @Override
                                                                               public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                                   obj.getFirebaseDatabase().getReference().child(COMPANIES).child(EMPLOY).child(finalKey).setValue(model);
//                                                                                   startActivity(new Intent(AddEmployActivity.this, HRDashboard.class));
//                                                                                   finish();
//                                                                                    finish();

//                                                                                   FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
//                                                                                   fragmentTransaction.replace(R.id.lay_container,new Employ()).commit();
                                                                               }

                                                                               @Override
                                                                               public void onCancelled(@NonNull DatabaseError error) {
                                                                               }
                                                                           });

                                                                       }
                                                                   }
                                                               });

                                                           }
                                                       });
                                                   }
                                               }
        ).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                float percent = (100 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                dialog.setMessage("Uploaded :" + (int) percent + "%");
                dialog.setCanceledOnTouchOutside(false);
                dialog.setCancelable(false);
            }
        });
    }
}