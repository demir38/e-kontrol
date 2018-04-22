package com.example.musta.pagecomputercontrol;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class TimePage extends AppCompatActivity {
    TextView txt1;
    String userToken,deviceId,pcName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_page);

        txt1 = (TextView)findViewById(R.id.txtPcaAdı);

        Bundle extras = getIntent().getExtras();
        userToken = extras.getString("userToken");
        deviceId = extras.getString("deviceId");
        pcName = extras.getString("pcName");

        //txt1.setText(userToken+"\n"+deviceId+"\n"+pcName);
        txt1.setText(pcName);
    }

    public void btn60(View view){
        addSession(60);
    }
    public void btn120(View view){
        addSession(120);
    }
    public void btn180(View view){
        addSession(180);
    }
    public void btnClose(View view){
        shutdown();
    }
    public void addSession(int time){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://e-kontrol.volkanbicen.xyz/session/add?token="+userToken+"&device_id="+deviceId+"&time="+time;

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        Log.v("response ====",response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //mTextView.setText("Çalışmıyor!");
            }
        });
        queue.add(stringRequest);
    }
    public void shutdown(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://e-kontrol.volkanbicen.xyz/session/shutdown?token="+userToken+"&device_id="+deviceId;

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        Log.v("response ====",response.toString());
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

