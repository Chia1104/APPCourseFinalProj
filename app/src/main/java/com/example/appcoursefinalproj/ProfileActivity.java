package com.example.appcoursefinalproj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    Button btnSignOut, btnHome, btnProfile, btnChangeName;
    TextView tv1;
    EditText editTextTextPersonName;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();

        btnSignOut = findViewById(R.id.signOut);
        btnHome = findViewById(R.id.btnHome);
        btnProfile = findViewById(R.id.btnProfile);
        btnChangeName = findViewById(R.id.btnChangeName);
        tv1 = findViewById(R.id.userName);
        editTextTextPersonName = findViewById(R.id.editTextTextPersonName);

        btnSignOut.setOnClickListener(view -> {
            mAuth.signOut();
            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
        });

        btnHome.setOnClickListener(view -> {
            startActivity(new Intent(ProfileActivity.this, MainActivity.class));
        });

        btnProfile.setOnClickListener(view -> {
            startActivity(new Intent(ProfileActivity.this, ProfileActivity.class));
        });

        btnChangeName.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name = editTextTextPersonName.getText().toString();
                tv1.setText(name);

                if (TextUtils.isEmpty(name)){
                    editTextTextPersonName.setError("請再輸入一次!");
                    editTextTextPersonName.requestFocus();
                }else{
                    Intent intent = new Intent();
                    intent.setClass(ProfileActivity.this,MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("username", tv1.getText().toString());
                    intent.putExtras(bundle);
                    startActivity(intent);

                    Toast.makeText(ProfileActivity.this, "名字已更改", Toast.LENGTH_SHORT).show();
                }



            }
        });

    }
}