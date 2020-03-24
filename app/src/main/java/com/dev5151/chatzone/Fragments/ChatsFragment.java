package com.dev5151.chatzone.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dev5151.chatzone.Adapters.UserAdapter;
import com.dev5151.chatzone.Models.Chat;
import com.dev5151.chatzone.Models.User;
import com.dev5151.chatzone.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatsFragment extends Fragment {

    UserAdapter userAdapter;
    List<String> userList;
    List<User> users;
    DatabaseReference userRef, chatRef;
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chats, container, false);
        initViews(view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.hasFixedSize();

        chatRef.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        userList.clear();

                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            Chat chat = dataSnapshot1.getValue(Chat.class);

                            if (chat.getSender().equals(FirebaseAuth.getInstance().getUid())) {
                                userList.add(chat.getReceiver());
                            }

                            if (chat.getReceiver().equals(FirebaseAuth.getInstance().getUid())) {
                                userList.add(chat.getSender());
                            }
                        }

                        readChats();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        return view;
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        userList = new ArrayList<>();
        userRef = FirebaseDatabase.getInstance().getReference().child("users");
        chatRef = FirebaseDatabase.getInstance().getReference().child("chats");

    }

    private void readChats() {
        users = new ArrayList<>();

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                users.clear();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    User user = dataSnapshot1.getValue(User.class);

                    for (String id : userList) {
                        if (user.getUid().equals(id)) {
                            if (users.size() != 0) {
                                for (User user1 : users) {
                                    if (!user.getUid().equals(user1.getUid())) {
                                        users.add(user);
                                    }
                                }
                            } else {
                                users.add(user);
                            }
                        }
                    }
                }

                userAdapter = new UserAdapter(users, getActivity());
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
