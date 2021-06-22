package com.example.appcoursefinalproj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btnHome, btnProfile, btnSend;
    FirebaseAuth mAuth;
    TextView userName;

    private RecyclerView mRecyclerView;
    private EditText mContent_editTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_chats);
        new FirebaseDatabaseHelper().readChat(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Chat> chats, List<String> keys) {
                new RecyclerView_Config().setConfig(mRecyclerView, MainActivity.this, chats, keys);
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });

        mAuth = FirebaseAuth.getInstance();
        btnHome = findViewById(R.id.btnHome);
        btnProfile = findViewById(R.id.btnProfile);
        mContent_editTxt = (EditText) findViewById(R.id.editTextContent);
        btnSend = (Button) findViewById(R.id.btnSend);
        userName = (TextView) findViewById(R.id.userName);

        btnHome.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, MainActivity.class));
        });

        btnProfile.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
        });
        
        btnSend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Calendar mCal = Calendar.getInstance();
                String dateformat = "yyyy/MM/dd";
                SimpleDateFormat df = new SimpleDateFormat(dateformat);
                String today = df.format(mCal.getTime());
                Chat chat = new Chat();

                try {
                    String content = mContent_editTxt.getText().toString();

                    chat.setContent(content);
                    chat.setDate(today);

                    Bundle bundle = getIntent().getExtras();
                    String name = bundle.getString("username");
                    chat.setName(name);

                    new FirebaseDatabaseHelper().addChat(chat, new FirebaseDatabaseHelper.DataStatus() {
                        @Override
                        public void DataIsLoaded(List<Chat> chats, List<String> keys) {

                        }

                        @Override
                        public void DataIsInserted() {

                            Toast.makeText(MainActivity.this, "訊息已傳送", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void DataIsUpdated() {

                        }

                        @Override
                        public void DataIsDeleted() {

                        }
                    });

                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "請先更改名字，再輸入訊息", Toast.LENGTH_SHORT).show();
                }



            }
        });
    }

    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
    }
}