package com.example.musta.pagecomputercontrol;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomepageActivity extends AppCompatActivity {

    Button btn;
    List<String> pc_names = new ArrayList<String>();
    List<String> devices_id = new ArrayList<String>();
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        //user token al
        Bundle extras = getIntent().getExtras();
        String userToken = extras.getString("userToken");
        Log.v("user token = ", userToken);
        lv = (ListView) findViewById(R.id.ListDevices);
        getDevices(userToken);
    }

    public void getDevices(final String userToken) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://e-kontrol.volkanbicen.xyz/user/devices?token=" + userToken;

        final StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        Log.v("response =", response.toString());
                        try {

                            JSONObject obj = new JSONObject(response.toString());
                            JSONArray devices = obj.getJSONArray("data");
                            int deneme = devices.length();
                            String t1 = Integer.toString(deneme);
                            Log.v("uzunluk = =====", t1);

                            for (int i = 0; i < devices.length(); i++) {
                                obj = devices.getJSONObject(i);
                                Log.v("pc name", obj.getString("pc_name"));
                                pc_names.add(obj.getString("pc_name"));
                                devices_id.add(obj.getString("device_id"));
                            }
                            ArrayAdapter<String> arrAdapter = new ArrayAdapter<String>(HomepageActivity.this, android.R.layout.simple_list_item_1, pc_names);
                            lv.setAdapter(arrAdapter);
                            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    String pc_name = pc_names.get(position);
                                    String device_id = devices_id.get(position);
                                    Log.v("position",device_id);
                                    Log.v("name",pc_name);
                                    Intent intent = new Intent(HomepageActivity.this,TimePage.class);
                                    intent.putExtra("deviceId",device_id);
                                    intent.putExtra("pcName",pc_name);
                                    intent.putExtra("userToken",userToken);
                                    startActivity(intent);
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);
    }

}
