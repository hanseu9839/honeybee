package com.example.honeybeeproject.story;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import com.example.honeybeeproject.MainActivity;
import com.example.honeybeeproject.R;

import org.json.JSONException;
import org.json.JSONObject;


public class storyPostActivity extends AppCompatActivity {
    ImageView iv_photoadd;
    Button btn_post;
    TextView text_post;
    EditText et_userEmail;
    private final int GET_GALLERY_IMAGE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_story);
        iv_photoadd = findViewById(R.id.iv_photoadd);
        btn_post = findViewById(R.id.btn_post);
        text_post = findViewById(R.id.text_post);
        et_userEmail = findViewById(R.id.et_userEmail);



        iv_photoadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                startActivityForResult(intent,GET_GALLERY_IMAGE);

            }
        });

        btn_post.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                int postID = 0;
                String storyPost = text_post.getText().toString();
                String uEmail = et_userEmail.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) { // 회원등록에 성공한 경우
                                Toast.makeText(getApplicationContext(),"회원 등록에 성공하였습니다.",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(storyPostActivity.this, MainActivity.class);
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
                storyRequest storyRequest = new storyRequest(postID, storyPost,uEmail, responseListener);
                RequestQueue queue = Volley.newRequestQueue(storyPostActivity.this);
                queue.add(storyRequest);
            }
        });

    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            iv_photoadd.setImageURI(selectedImageUri);
        }
    }
}
