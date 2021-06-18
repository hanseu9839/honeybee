package com.example.honeybeeproject.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.honeybeeproject.MainActivity;
import com.example.honeybeeproject.R;
import org.json.JSONException;
import org.json.JSONObject;

public class calendarPlusActivity extends AppCompatActivity {
    private EditText et_Year, et_Month, et_Day, et_Hour, et_Minute, et_Plan;
    private Button btn_PlanPlus;
    calendarMainActivity calendarMainActivity = new calendarMainActivity();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plus_calendar);
        et_Year = findViewById(R.id.et_Year);
        et_Month = findViewById(R.id.et_Month);
        et_Day = findViewById(R.id.et_Day);
        et_Hour = findViewById(R.id.et_Hour);
        et_Minute = findViewById(R.id.et_Minute);
        et_Plan = findViewById(R.id. et_Plan);
        calendarMainActivity calendarMainActivity = new calendarMainActivity();

        btn_PlanPlus = findViewById(R.id.btn_PlanPlus);
        btn_PlanPlus.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view){
                String calYear = et_Year.getText().toString();
                String calMonth = et_Month.getText().toString();
                String calDay =  et_Day.getText().toString();
                String calHour = et_Hour.getText().toString();
                String calMinute = et_Minute.getText().toString();
                String calPlan = et_Plan.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    public void onResponse(String response) {

                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                Toast.makeText(getApplicationContext(),"일정이 추가 되었습니다.",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(calendarPlusActivity.this, calendarMainActivity.class);

                                intent.putExtra("calHour",""+calHour);
                                intent.putExtra("calMinute",""+calMinute);
                                intent.putExtra("calMonth",""+calMonth);
                                intent.putExtra("calDay",""+calDay);
                                intent.putExtra("calPlan",""+calPlan);
                                startActivity(intent);

                            } else {
                                Toast.makeText(getApplicationContext(),"일정 추가에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                calendarRequest calenderRequest = new calendarRequest(calYear,calMonth,calDay,calHour,calMinute,calPlan, responseListener);
                RequestQueue queue = Volley.newRequestQueue(calendarPlusActivity.this);
                queue.add(calenderRequest);

            }
        });
    }
}
