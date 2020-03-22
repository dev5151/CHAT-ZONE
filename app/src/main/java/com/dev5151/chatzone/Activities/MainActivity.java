package com.dev5151.chatzone.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.dev5151.chatzone.Adapters.ViewPagerAdapter;
import com.dev5151.chatzone.Fragments.ChatsFragment;
import com.dev5151.chatzone.Fragments.UsersFragment;
import com.dev5151.chatzone.R;
import com.dev5151.chatzone.Models.User;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    CircleImageView circleImageView;
    TextView tv;
    AppBarLayout appBarLayout;
    FirebaseAuth mAuth;
    DatabaseReference userRef;
    String uid, password, email, username;
    Uri imgUrl;
    androidx.appcompat.widget.Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;
    List<Fragment> fragmentList;
    List<String> titleList;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                mAuth.signOut();
                Intent intent = new Intent(MainActivity.this, AuthActivity.class);
                int loginState = 0;
                getSharedPreferences("User Details", MODE_PRIVATE).edit().putInt("loginState", loginState).apply();
                Toast.makeText(getApplicationContext(), "LOGOUT SUCCESS", Toast.LENGTH_LONG).show();
                startActivity(intent);
                finish();
                return true;

        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeView();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("CHAT ZONE");

        userRef.child(mAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                username = user.getUsername();
                tv.setText(username);
                getSupportActionBar().setSubtitle(username);
                String imgUrl = user.getImgUrl();
                if (imgUrl.equals("default")) {
                    circleImageView.setImageResource(R.drawable.ic_user_pic);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        fragmentList.add(new ChatsFragment());
        fragmentList.add(new UsersFragment());
        titleList.add("Chats");
        titleList.add("Users");

        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager(),fragmentList,titleList);

        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);


    }

    private void initializeView() {
        circleImageView = findViewById(R.id.circularImageView);
        tv = findViewById(R.id.username);
        mAuth = FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference().child("users");
        uid = mAuth.getUid();
        toolbar = findViewById(R.id.toolbar);
        appBarLayout = findViewById(R.id.app_bar_layout);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        fragmentList=new ArrayList<>();
        titleList=new ArrayList<>();
    }

}
