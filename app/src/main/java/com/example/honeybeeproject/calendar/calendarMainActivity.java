package com.example.honeybeeproject.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.honeybeeproject.MainActivity;
import com.example.honeybeeproject.R;
import com.example.honeybeeproject.story.storyMainActivity;

import org.w3c.dom.Text;

public class calendarMainActivity extends AppCompatActivity {
    private TextView textView,planView,timeView,dayView;
    private CalendarView calendarView;
    private ImageView iv_dateadd;


    String calMinute=null;
    String calHour=null;
    String calMonth=null;
    String calDay=null;
    String calPlan=null;
    final static private String URL = "http://honeybee54953.dothome.co.kr/Date.php";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_calendar);

        textView = findViewById(R.id.textView);
        calendarView = findViewById(R.id.calendarView);
        iv_dateadd = findViewById(R.id.iv_dateadd);
        dayView = findViewById(R.id.dayView);
        planView = findViewById(R.id.planView);
        timeView = findViewById(R.id.timeView);


        iv_dateadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(calendarMainActivity.this,calendarPlusActivity.class);
                startActivity(intent);
            }
        });


        try {
            calMinute = getIntent().getExtras().getString("calMinute");
            calHour = getIntent().getExtras().getString("calHour");
            calMonth = getIntent().getExtras().getString("calMonth");
            calDay = getIntent().getExtras().getString("calDay");
            calPlan = getIntent().getExtras().getString("calPlan");
        }catch (NullPointerException e){

        }


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                month += 1;
                textView.setText(String.format("%d년 %d월 %d일",year,month,dayOfMonth));
                dayView.setText(String.format("%d년 %d월 %d일",year,month,dayOfMonth));
                try {
                    if (month == Integer.parseInt(calMonth) && dayOfMonth == Integer.parseInt(calDay)) {
                        timeView.setText(String.format("%s시 %s분", calHour, calMinute));
                        planView.setText(String.format("%s", calPlan));
                    } else {
                        timeView.setText("");
                        planView.setText("");
                    }
                }catch (NumberFormatException e){

                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(calendarMainActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
