package com.example.honeybeeproject.login;

import android.app.Activity;
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
import com.example.honeybeeproject.calendar.calendarMainActivity;
import com.example.honeybeeproject.register.registerActivity;
import com.example.honeybeeproject.story.storyMainActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class loginActivity extends AppCompatActivity {
    //대충 xml파일에 있는 edittext와 button을 가져오려고 함수를 만들어주는거임
    private EditText et_userEmail, et_userPassword;
    private Button btn_login, btn_register;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //만들어놓은 함수안에 xml 입력되어있는 id를 찾아가져오는거임
        et_userEmail = findViewById(R.id.et_userEmail);
        et_userPassword = findViewById(R.id.et_userPassword);
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);

        //회원가입 버튼 클릭하면 액션할 수 있게 만들어주는거임
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(loginActivity.this, registerActivity.class);
                startActivity(intent);
            }
        });
        //login 버튼 클릭 시 해당하는 액션 돌아가게 하는거임
        btn_login.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                String userEmail = et_userEmail.getText().toString();
                String userPassword = et_userPassword.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println("hongchuil" + response);
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) { // 로그인에 성공한 경우
                                String userEmail = jsonObject.optString("userEmail");
                                String userPassword = jsonObject.optString("userPassword");

                                Toast.makeText(getApplicationContext(),"로그인에 성공하였습니다.",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(loginActivity.this, MainActivity.class);
                                intent.putExtra("userEmail",userEmail);
                                intent.putExtra("userPassword",userPassword);

                                startActivity(intent);
                            } else { // 로그인에 실패한 경우
                                Toast.makeText(getApplicationContext(),"로그인에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                };
                loginRequest loginRequest = new loginRequest(userEmail, userPassword , responseListener);
                RequestQueue queue = Volley.newRequestQueue(loginActivity.this);
                queue.add(loginRequest);
            }
        });
    }


}