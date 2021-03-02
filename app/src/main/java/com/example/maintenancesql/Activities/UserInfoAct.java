package com.example.maintenancesql.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.maintenancesql.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserInfoAct extends AppCompatActivity {

    EditText txtNama, txtEmail, txtPass;
    Button btnContinue;

    private SharedPreferences userPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        userPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        txtNama = (EditText) findViewById(R.id.txtNama);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPass = (EditText) findViewById(R.id.txtPass);
        btnContinue = (Button) findViewById(R.id.btnContinue);

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("LoginFirstTime", true);
        editor.commit();

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserInfo();
            }
        });
    }

    private void saveUserInfo() {
        String name = txtNama.getText().toString().trim();
        String email = txtEmail.getText().toString().trim();
        String password = txtPass.getText().toString().trim();

        StringRequest request = new StringRequest(Request.Method.POST, Constant.SAVE_USER_INFO, response -> {

            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")) {
                    JSONObject user = object.getJSONObject("user");
                    //make shared preference user
                    SharedPreferences userPref = this.getApplicationContext().getSharedPreferences("user",this.MODE_PRIVATE);
                    SharedPreferences.Editor editor = userPref.edit();
                    //editor.putString("token",object.getString("token"));
                    editor.putString("name",user.getString("name"));
                    editor.putString("email",user.getString("email"));
                    //editor.putString("password",user.getString("password"));
                    editor.putBoolean("proses_completed",false);
                    editor.putInt("id", user.getInt("id"));
                    editor.putBoolean("isLoggedFirstTime",true);
                    editor.putBoolean("isLoggedIn", true);
                    editor.apply();

                    startActivity(new Intent(UserInfoAct.this, HomeAct.class));
                    finish();
                    Toast.makeText(this, "Update Success", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        },error -> {
            error.printStackTrace();
        }) {
            //add token to headers


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                String token = userPref.getString("token","");
                map.put("Authorization", "Bearer" +token);
                return map;
            }

            //add params

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("name",name);
                map.put("email", email);
                map.put("password", password);
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(UserInfoAct.this);
        queue.add(request);
    }
}
