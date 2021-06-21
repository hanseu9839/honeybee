package com.example.honeybeeproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.honeybeeproject.R;
import com.example.honeybeeproject.calendar.calendarMainActivity;
import com.example.honeybeeproject.chat.chatMainActivity;
import com.example.honeybeeproject.story.storyMainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private long backKeyPressedTime = 0;
    private Toast toast;
    private ImageView btn_chat,btn_story,btn_info,btn_calendar,iv_location;

    Intent intent = getIntent();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_chat = findViewById(R.id.btn_chat);
        btn_story = findViewById(R.id.btn_story);
        btn_info = findViewById(R.id.btn_info);
        btn_calendar = findViewById(R.id.btn_calendar);
        iv_location = findViewById(R.id.iv_location);


        btn_chat.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Intent intent = new Intent(MainActivity.this,  chatMainActivity.class);
             startActivity(intent);
         }
     });

        btn_story.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, storyMainActivity.class);
                startActivity(intent);
            }
        });


        btn_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, calendarMainActivity.class);
                startActivity(intent);
            }
        });
        iv_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,locationMainActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finishAffinity();
            toast.cancel();
        }
    }

}
