package com.lzi.example_volley_library;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.lzi.example_volley_library.entities.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btnSend;

    private List<User> userList = new ArrayList<>();
    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    private static final String URL = "https://run.mocky.io/v3/2c13de56-5054-4b22-a06f-a9747dce70f7";
    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnSend = findViewById(R.id.btn_send);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequestResponse();
            }
        });
        checkUserList();

    }

    private void sendRequestResponse() {
        requestQueue = Volley.newRequestQueue(this);
        stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //display the response on screen
                Toast.makeText(getApplicationContext(),"Response :" + response.toString(), Toast.LENGTH_LONG).show();
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("users");

                    User user;
                    for (int i=0; i< jsonArray.length(); i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                        int id = jsonObject1.getInt("id");
                        String name = jsonObject1.getString("name");
                        String email = jsonObject1.getString("email");
                        String gender = jsonObject1.getString("gender");

                        JSONObject jsonObject2 = jsonObject1.getJSONObject("contact");
                        String phone = jsonObject2.getString("mobile");

                        user = new User(id,name,email,phone,gender);
                        userList.add(user);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, error.getMessage());
            }
        });

        requestQueue.add(stringRequest);
    }

    private void checkUserList(){
        if (!userList.isEmpty())
            Log.i(TAG,"User List is not empty");
        else
            Log.i(TAG,"User List is empty");
    }
}
