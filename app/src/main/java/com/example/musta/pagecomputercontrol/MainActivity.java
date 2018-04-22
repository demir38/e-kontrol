package com.example.musta.pagecomputercontrol;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    EditText username,password,val;
    Button loginButton;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText)findViewById(R.id.LoginEmail);
        password = (EditText)findViewById(R.id.LoginPassword);
        loginButton = (Button)findViewById(R.id.btnLogin);
    }

    public void onLogin(View view) {
        val = (EditText)findViewById(R.id.editText);
        //val.setText(username.getText().toString()+"---"+password.getText().toString());

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://e-kontrol.volkanbicen.xyz/user/login?username="+username.getText().toString()+
                "&password="+password.getText().toString()+"&type=mobile";

        final StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        // yanıt string'inin ilk 500 karakterini gösterelim
                        //mTextView.setText("Gelen yanıt: "+ response.substring(0,500));
                        val.setText(response.toString());
                        try {
                            JSONObject obj = new JSONObject(response.toString());
                            JSONObject data = obj.getJSONObject("data");
                            val.setText(data.getString("token"));
                            String userToken = data.getString("token");
                            if(obj.getString("code").equals("200")){
                                Intent intent = new Intent(MainActivity.this,HomepageActivity.class);
                                intent.putExtra("userToken",userToken);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //mTextView.setText("Çalışmıyor!");
            }
        });
        queue.add(stringRequest);
    }


}
