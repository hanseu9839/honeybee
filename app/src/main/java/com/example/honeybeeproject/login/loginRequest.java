package com.example.honeybeeproject.login;


import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class loginRequest extends StringRequest {
    final static private String URL = "http://honeybee54953.dothome.co.kr/Login.php";
    private Map<String, String> map;

    public loginRequest(String userEmail, String userPassword, Response .Listener<String> listener){
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userEmail",userEmail);
        map.put("userPassword",userPassword);
    }

    protected Map<String, String> getParams() throws AuthFailureError{
        return map;
    }
}