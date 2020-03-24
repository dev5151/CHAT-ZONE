package com.dev5151.chatzone.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dev5151.chatzone.Models.Chat;
import com.dev5151.chatzone.Models.User;
import com.dev5151.chatzone.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GithubAuthCredential;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private final int MESSAGE_TYPE_LEFT = 0;
    private final int MESSAGE_TYPE_RIGHT = 1;

    FirebaseAuth mAuth;

    private List<Chat> chatList;
    private Context context;
    private String imgUrl;

    public MessageAdapter(List<Chat> chatList, Context context, String imgUrl) {
        this.chatList = chatList;
        this.context = context;
        this.imgUrl = imgUrl;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MESSAGE_TYPE_RIGHT) {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_right, parent, false);
            return new MessageAdapter.MessageViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_left, parent, false);
            return new MessageAdapter.MessageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {

        Chat chat = chatList.get(position);

        holder.show_message.setText(chat.getMessage());

        if (imgUrl.equals("default")) {
            holder.circularImageView.setImageResource(R.drawable.ic_user_pic);
        } else {
            Glide.with(context).load(imgUrl).into(holder.circularImageView);
        }
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    class MessageViewHolder extends RecyclerView.ViewHolder {

        TextView show_message;
        ImageView circularImageView;

        MessageViewHolder(@NonNull View itemView) {
            super(itemView);

            show_message = itemView.findViewById(R.id.show_message);
            circularImageView = itemView.findViewById(R.id.circularImageView);
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (chatList.get(position).getSender().equals(FirebaseAuth.getInstance().getUid())) {
            return MESSAGE_TYPE_RIGHT;
        } else {
            return MESSAGE_TYPE_LEFT;
        }
    }
}
