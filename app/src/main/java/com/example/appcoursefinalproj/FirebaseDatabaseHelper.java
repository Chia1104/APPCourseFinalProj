package com.example.appcoursefinalproj;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceChats;
    private List<Chat> chats = new ArrayList<>();

    public interface DataStatus {
        void DataIsLoaded(List<Chat> chats, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }
    public FirebaseDatabaseHelper() {
        mDatabase = FirebaseDatabase.getInstance();
        mReferenceChats = mDatabase.getReference("chat");

    }

    public void readChat(final DataStatus dataStatus){
        mReferenceChats.addValueEventListener(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chats.clear();
                List<String> keys = new ArrayList<>();
                for(DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    keys.add(keyNode.getKey());
                    Chat chat = keyNode.getValue(Chat.class);
                    chats.add(chat);
                }
                dataStatus.DataIsLoaded(chats, keys);
            }
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
