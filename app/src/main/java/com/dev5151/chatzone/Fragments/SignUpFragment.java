package com.dev5151.chatzone.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dev5151.chatzone.Activities.AuthActivity;
import com.dev5151.chatzone.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.content.ContentValues.TAG;

public class SignUpFragment extends Fragment {

    private EditText edtEmail, edtUsername, edtPassword;
    private Button btnSignUp;
    private TextView tvLogin, tvSentence;
    private FirebaseAuth mAuth;
    private DatabaseReference userRef;
    String email, password, username;
    int int1, int2, int3;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        initializeView(view);

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthActivity.getAuthInterface().switchToLogin();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtEmail.getText() != null && edtEmail.getText().length() > 0) {
                    email = edtEmail.getText().toString();
                    int1 = 1;
                } else {
                    edtEmail.setError("Email cannot be null");
                }
                if (edtPassword.getText() != null && edtPassword.getText().length() > 0) {
                    password = edtPassword.getText().toString();
                    int2 = 1;
                } else {
                    edtPassword.setError("Password cannot be null");
                }
                if (edtUsername.getText() != null && edtUsername.getText().length() > 0) {
                    username = edtUsername.getText().toString();
                    int3 = 1;
                } else {
                    edtUsername.setError("UserName cannot be null");
                }

                if (int1 == 1 && int2 == 1 && int3 == 1) {
                    userRegistration(email, password, username);
                    AuthActivity.authInterface.switchToLogin();
                }
            }
        });

        return view;
    }

    private void initializeView(View view) {
        edtEmail = view.findViewById(R.id.et_email);
        edtUsername = view.findViewById(R.id.et_user_name);
        edtPassword = view.findViewById(R.id.et_password);
        btnSignUp = view.findViewById(R.id.btn_sign_in);
        tvLogin = view.findViewById(R.id.tv_login);
        tvSentence = view.findViewById(R.id.tv_sentence);
        mAuth = FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference().child("users");
    }


    private void userRegistration(final String email, final String password, final String username) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String uid = mAuth.getUid();
                            userRef.child(uid).child("email").setValue(email);
                            userRef.child(uid).child("password").setValue(password);
                            userRef.child(uid).child("username").setValue(username);
                            userRef.child(uid).child("imgUrl").setValue("default");
                            userRef.child(uid).child("uid").setValue(uid);
                            userRef.child(uid).child("status").setValue("offline");
                            userRef.child(uid).child("search").setValue(username.toLowerCase());
                        } else {
                            Toast.makeText(getActivity(), task.getException().toString(), Toast.LENGTH_LONG).show();
                            Log.e(TAG, task.getException() + "");
                        }
                    }
                });
    }


}
