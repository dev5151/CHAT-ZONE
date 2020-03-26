package com.dev5151.chatzone.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.dev5151.chatzone.Adapters.MessageAdapter;
import com.dev5151.chatzone.Models.Chat;
import com.dev5151.chatzone.Models.User;
import com.dev5151.chatzone.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageActivity extends AppCompatActivity {

    private CircleImageView circleImageView;
    private TextView username;
    Intent intent;
    androidx.appcompat.widget.Toolbar toolbar;
    AppBarLayout appBarLayout;
    String uid;
    DatabaseReference userRef, chatRef;
    private EditText edtMessage;
    private ImageButton send;
    private RecyclerView recyclerView;
    List<Chat> chatList;
    String message;
    MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        initViews();

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        intent = getIntent();
        uid = intent.getStringExtra("uid");
        userRef.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                username.setText(user.getUsername());
                if (user.getImgUrl().equals("default")) {
                    circleImageView.setImageResource(R.drawable.ic_user_pic);
                } else {
                    Glide.with(MessageActivity.this).load(user.getImgUrl()).into(circleImageView);
                }
                readMessage(FirebaseAuth.getInstance().getUid(), uid, user.getImgUrl());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message = edtMessage.getText().toString();
                if (!message.equals("")) {
                    sendMessage(FirebaseAuth.getInstance().getUid(), uid, message);
                    edtMessage.setText(null);
                } else {
                    Toast.makeText(getApplicationContext(), "You can't send an empty message", Toast.LENGTH_LONG).show();
                }
            }
        });

        recyclerView.hasFixedSize();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);


    }

    private void initViews() {
        circleImageView = findViewById(R.id.circularImageView);
        username = findViewById(R.id.username);
        toolbar = findViewById(R.id.toolbar);
        userRef = FirebaseDatabase.getInstance().getReference().child("users");
        recyclerView = findViewById(R.id.recyclerView);
        edtMessage = findViewById(R.id.message);
        send = findViewById(R.id.send);
        chatRef = FirebaseDatabase.getInstance().getReference().child("chats");
        chatList = new ArrayList<>();

    }

    private void sendMessage(String sender, String receiver, String message) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);
        chatRef.push().setValue(hashMap);

        final DatabaseReference myRef= FirebaseDatabase.getInstance().getReference().child("chatList")
                .child(FirebaseAuth.getInstance().getUid())
                .child(uid);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()){
                   myRef.child("id").setValue(uid);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void readMessage(final String myId, final String userId, final String imgUrl) {

        chatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Chat chat = dataSnapshot1.getValue(Chat.class);
                    if (chat.getReceiver().equals(myId) && chat.getSender().equals(uid) || chat.getReceiver().equals(userId) && chat.getSender().equals(myId)) {
                        chatList.add(chat);
                    }
                }
                messageAdapter = new MessageAdapter(chatList, MessageActivity.this, imgUrl);
                recyclerView.setAdapter(messageAdapter);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
