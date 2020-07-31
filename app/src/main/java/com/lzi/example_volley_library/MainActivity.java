package com.lzi.example_volley_library;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.lzi.example_volley_library.adapters.UserAdapter;
import com.lzi.example_volley_library.entities.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btnSend;
    private ListView lvUsers;
    private ProgressDialog progressDialog;

    private List<User> userList = new ArrayList<>();
    private UserAdapter userAdapter;
    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    private static final String URL = "https://run.mocky.io/v3/2c13de56-5054-4b22-a06f-a9747dce70f7";
    private static final String TAG = MainActivity.class.getName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        progressDialog = new ProgressDialog(this);
        userAdapter = new UserAdapter(this,userList);


        btnSend = findViewById(R.id.btn_send);
        lvUsers = findViewById(R.id.lv_users);

        userList.add(new User(3,"Saad Ougaal","saad1@gmail.com","0645481200","male"));
        lvUsers.setAdapter(userAdapter);


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequestResponse();
            }
        });

    }

    private void sendRequestResponse() {
        // Showing progress dialog before making http request
        showPDialog();

        userList.clear();
        userAdapter.notifyDataSetChanged();

        requestQueue = Volley.newRequestQueue(this);
        stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //Hiding progress dialog after receiving http response
                hideProgressDialog();

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
                /*
                *  notifying list adapter about data changes
                *  so that it renders the list view with updated data
                * */
                userAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, error.getMessage());
            }
        });

        requestQueue.add(stringRequest);
    }

    private void showPDialog(){
        progressDialog.setMessage("Loading ...");
        progressDialog.show();
    }
    private void hideProgressDialog(){
        if (progressDialog != null){
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
    private void checkUserList(){
        if (!userList.isEmpty())
            Log.i(TAG,"User List is not empty");
        else
            Log.i(TAG,"User List is empty");
    }
}
