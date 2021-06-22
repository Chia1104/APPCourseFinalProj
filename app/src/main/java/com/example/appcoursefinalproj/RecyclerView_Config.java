package com.example.appcoursefinalproj;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RecyclerView_Config {
    private Context mContext;
    private ChatsAdapter mChatsAdapter;
    public void setConfig(RecyclerView recyclerView, Context context, List<Chat> chats, List<String> keys){

        mContext = context;
        mChatsAdapter = new ChatsAdapter(chats, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mChatsAdapter);
    }

    class ChatItemView extends RecyclerView.ViewHolder{
        private TextView mName;
        private TextView mDate;
        private TextView mContent;

        private String key;

        public ChatItemView(ViewGroup parent) {
            super(LayoutInflater.from(mContext).inflate(R.layout.chat_list_item, parent, false));

            mName = (TextView) itemView.findViewById(R.id.name_txtView);
            mDate = (TextView) itemView.findViewById(R.id.date_txtView);
            mContent = (TextView) itemView.findViewById(R.id.content_txtView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ChatDetailsActivity.class);
                    intent.putExtra("key", key);
                    intent.putExtra("name", mName.getText().toString());
                    intent.putExtra("content", mContent.getText().toString());
                    intent.putExtra("date", mDate.getText().toString());

                    mContext.startActivity(intent);
                }
            });
        }

        public void bind(Chat chat, String key) {
            mName.setText(chat.getName());
            mDate.setText(chat.getDate());
            mContent.setText(chat.getContent());
            this.key = key;
        }

    }
    class ChatsAdapter extends RecyclerView.Adapter<ChatItemView> {
        private List<Chat> mChatList;
        private List<String> mKeys;

        public ChatsAdapter(List<Chat> mChatList, List<String> mKeys) {
            this.mChatList = mChatList;
            this.mKeys = mKeys;
        }

        @NonNull
        @NotNull
        @Override
        public ChatItemView onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
            return new ChatItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull @NotNull ChatItemView holder, int position) {
            holder.bind(mChatList.get(position), mKeys.get(position));

        }

        @Override
        public int getItemCount() {
            return mChatList.size();
        }
    }
}
