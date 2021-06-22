package com.example.appcoursefinalproj;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class ChatDetailsActivity extends AppCompatActivity {

    private EditText mName_editTxt, mContent_editTxt, mDate_editTxt;
    private Button btnUpdate, btnDelete, btnBack;

    private String key, name, content, date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_details);

        key = getIntent().getStringExtra("key");
        name = getIntent().getStringExtra("name");
        content = getIntent().getStringExtra("content");
        date = getIntent().getStringExtra("date");


        mName_editTxt = (EditText) findViewById(R.id.changeName);
        mName_editTxt.setText(name);
        mContent_editTxt = (EditText) findViewById(R.id.changeContent);
        mContent_editTxt.setText(content);
        mDate_editTxt = (EditText) findViewById(R.id.changeDate);
        mDate_editTxt.setText(date);

        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnBack = (Button) findViewById(R.id.btnBack);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Chat chat = new Chat();
                chat.setName(mName_editTxt.getText().toString());
                chat.setContent(mContent_editTxt.getText().toString());
                chat.setDate(mDate_editTxt.getText().toString());

                new FirebaseDatabaseHelper().updateChat(key, chat, new FirebaseDatabaseHelper.DataStatus() {

                    @Override
                    public void DataIsLoaded(List<Chat> chats, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {

                    }

                    @Override
                    public void DataIsUpdated() {

                        Toast.makeText(ChatDetailsActivity.this, "訊息已更新", Toast.LENGTH_SHORT).show();
                        finish(); return;
                    }

                    @Override
                    public void DataIsDeleted() {

                    }
                });
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FirebaseDatabaseHelper().deleteChat(key, new FirebaseDatabaseHelper.DataStatus() {

                    @Override
                    public void DataIsLoaded(List<Chat> chats, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {

                    }

                    @Override
                    public void DataIsUpdated() {

                    }

                    @Override
                    public void DataIsDeleted() {
                        Toast.makeText(ChatDetailsActivity.this, "訊息已刪除", Toast.LENGTH_SHORT).show();
                        finish(); return;

                    }
                });
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); return;
            }
        });

    }
}