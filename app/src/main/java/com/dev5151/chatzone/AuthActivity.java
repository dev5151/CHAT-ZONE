package com.dev5151.chatzone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.transition.Fade;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

public class AuthActivity extends AppCompatActivity {

    public static AuthInterface authInterface;
    private FragmentManager fragmentManager;
    private FrameLayout frame;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private int loginState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        initializeView();

        loginState = sharedPreferences.getInt("loginState", 0);

        if (loginState == 0) {
            fragmentTransition(new LoginFragment());
        } else {
            startActivity(new Intent(AuthActivity.this, MainActivity.class));
            finish();
        }

        authInterface = new AuthInterface() {
            @Override
            public void switchToLogin() {
                fragmentTransition(new LoginFragment());
            }

            @Override
            public void switchToSignUp() {
                fragmentTransition(new SignUpFragment());
            }

            @Override
            public void switchToMainActivity() {
                startActivity(new Intent(AuthActivity.this, MainActivity.class));
                finish();
            }
        };


    }

    private void fragmentTransition(Fragment fragment) {
        fragment.setEnterTransition(new Fade());
        fragment.setExitTransition(new Fade());
        fragmentManager.beginTransaction()
                .replace(R.id.frame, fragment)
                .commit();
    }

    public static AuthInterface getAuthInterface() {
        return authInterface;
    }

    private void initializeView() {
        sharedPreferences = getSharedPreferences("User Details", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        frame = findViewById(R.id.frame);
        fragmentManager = getSupportFragmentManager();
    }

}
