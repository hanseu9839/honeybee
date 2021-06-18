package com.example.honeybeeproject.story;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.honeybeeproject.MainActivity;
import com.example.honeybeeproject.R;

public class storyMainActivity extends AppCompatActivity {
    private ImageView iv_mainphotoadd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_story);
        ImageView addphoto_button = findViewById(R.id.iv_mainphotoadd);

        addphoto_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(storyMainActivity.this,storyPostActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(storyMainActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
