package com.dev5151.chatzone.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dev5151.chatzone.Activities.MessageActivity;
import com.dev5151.chatzone.Adapters.ChatRoomAdapter;
import com.dev5151.chatzone.Adapters.MessageAdapter;
import com.dev5151.chatzone.Models.Chat;
import com.dev5151.chatzone.Models.ChatList;
import com.dev5151.chatzone.Models.ChatRoomModel;
import com.dev5151.chatzone.Models.User;
import com.dev5151.chatzone.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatRoomFragment extends Fragment {

    DatabaseReference chatRef, userRef;
    RecyclerView recyclerView;
    EditText edtMessage;
    ImageButton send;
    List<ChatRoomModel> chatList;
    ChatRoomAdapter chatRoomAdapter;
    String message;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_room_fragment, container, false);
        initializeView(view);

        userRef.child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                readMessages(user.getImgUrl());
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
                    sendMessage(FirebaseAuth.getInstance().getUid(), message);
                    edtMessage.setText(null);
                } else {
                    Toast.makeText(getContext(), "You can't send an empty message", Toast.LENGTH_LONG).show();
                }
            }
        });

        recyclerView.hasFixedSize();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        return view;
    }

    private void initializeView(View view) {
        chatRef = FirebaseDatabase.getInstance().getReference().child("chatRoom");
        userRef = FirebaseDatabase.getInstance().getReference().child("users");
        recyclerView = view.findViewById(R.id.recyclerView);
        edtMessage = view.findViewById(R.id.message);
        send = view.findViewById(R.id.send);
        chatList = new ArrayList<>();
    }

    private void sendMessage(String sender, String message) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("message", message);

        chatRef.push().setValue(hashMap);

    }

    private void readMessages(final String imgUrl) {
        chatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    ChatRoomModel chat = dataSnapshot1.getValue(ChatRoomModel.class);
                    {
                        chatList.add(chat);
                    }
                }
                chatRoomAdapter = new ChatRoomAdapter(chatList, getActivity(), imgUrl);
                recyclerView.setAdapter(chatRoomAdapter);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
