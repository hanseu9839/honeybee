package com.example.honeybeeproject.register;

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
import com.example.honeybeeproject.R;
import com.example.honeybeeproject.login.loginActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class registerActivity extends AppCompatActivity {
    private EditText et_Email,et_Password,et_userName,et_userBirthday,et_userPhone;
    private Button btn_register;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_Email = findViewById(R.id.et_Email);
        et_Password = findViewById(R.id.et_Password);
        et_userName = findViewById(R.id.et_userName);
        et_userBirthday = findViewById(R.id.et_userBirthday);
        et_userPhone = findViewById(R.id.et_userPhone);

        //회원가입 버튼 클릭하면 액션활동되게함
        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                String userEmail = et_Email.getText().toString();
                String userPassword = et_Password.getText().toString();
                String userName = et_userName.getText().toString();
                int userBirthday = Integer.parseInt(et_userBirthday.getText().toString());
                int userPhone = Integer.parseInt(et_userPhone.getText().toString());

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) { // 회원등록에 성공한 경우
                                Toast.makeText(getApplicationContext(),"회원 등록에 성공하였습니다.",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(registerActivity.this, loginActivity.class);
                                startActivity(intent);
                            } else { // 회원등록에 실패한 경우
                                Toast.makeText(getApplicationContext(),"회원 등록에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                registerRequest registerRequest = new registerRequest(userEmail,userPassword,userName,userBirthday,userPhone, responseListener);
                RequestQueue queue = Volley.newRequestQueue(registerActivity.this);
                queue.add(registerRequest);
            }
        });
    }
}
