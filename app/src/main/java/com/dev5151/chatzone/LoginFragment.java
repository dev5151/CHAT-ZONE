package com.dev5151.chatzone;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.content.ContentValues.TAG;

public class LoginFragment extends Fragment {

    EditText edtEmail, edtPassword;
    Button btnLogin;
    TextView tvLogin, tvSentence;
    FirebaseAuth mAuth;
    DatabaseReference userRef;
    String email, password;
    int int1, int2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initializeView(view);

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthActivity.getAuthInterface().switchToSignUp();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
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
                }
                if (int1 == 1 && int2 == 1) {
                    userLogin(email, password);
                }
            }
        });

        return view;
    }

    private void initializeView(View view) {
        edtEmail = view.findViewById(R.id.et_email);
        edtPassword = view.findViewById(R.id.et_password);
        btnLogin = view.findViewById(R.id.btn_sign_in);
        tvLogin = view.findViewById(R.id.tv_login);
        tvSentence = view.findViewById(R.id.tv_sentence);
        mAuth = FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference().child("users");
    }

    private void userLogin(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                        } else {
                            Toast.makeText(getActivity(), task.getException().toString(), Toast.LENGTH_LONG).show();
                            Log.e(TAG, task.getException() + "");
                        }
                    }
                });
    }
}
