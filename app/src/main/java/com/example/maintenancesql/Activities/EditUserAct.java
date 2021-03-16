package com.example.maintenancesql.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
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

public class EditUserAct extends AppCompatActivity {

    EditText editUser,editLastName,passwordAcc;
    LinearLayout logoutUser,saveUser;
    SharedPreferences preferences;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        init();

    }

    private void init() {
        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        preferences = getApplicationContext().getSharedPreferences("user",MODE_PRIVATE);
        editUser = findViewById(R.id.editUser);
        editLastName = findViewById(R.id.editLastName);
        passwordAcc = findViewById(R.id.passwordAcc);
        logoutUser = findViewById(R.id.logoutUSer);
        saveUser = findViewById(R.id.saveUser);

        editUser.setText(preferences.getString("name",""));
        editLastName.setText(preferences.getString("lastname",""));


        logoutUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        saveUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfle();
            }
        });
    }

    private void updateProfle() {
        dialog.setMessage("Updating");
        dialog.show();
        StringRequest request = new StringRequest(Request.Method.POST,Constant.SAVE_USER_INFO,response -> {

            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")){
                   SharedPreferences.Editor editor = preferences.edit();
                   editor.putString("name",editUser.getText().toString().trim());
                   editor.putString("lastname",editLastName.getText().toString().trim());
                   editor.putString("password",passwordAcc.getText().toString().trim());
                   editor.apply();
                    Toast.makeText(this, "Updated success", Toast.LENGTH_SHORT).show();
                    finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            dialog.dismiss();
        },error -> {
            error.printStackTrace();
            dialog.dismiss();
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = preferences.getString("token","");
                HashMap<String, String> map = new HashMap<>();
                map.put("Authorization","Bearer" +token);
                return map;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("name",editUser.getText().toString().trim());
                map.put("lastname",editLastName.getText().toString().trim());
                map.put("password",passwordAcc.getText().toString().trim());
                return map;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    private void logout() {
        dialog.setMessage("Logout");
        dialog.show();
        StringRequest request = new StringRequest(Request.Method.GET,Constant.LOGOUT,response -> {
            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")){
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.apply();
                    startActivity(new Intent(EditUserAct.this,LoginAct.class));
                    finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        },error -> {

        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = preferences.getString("token","");
                HashMap<String, String> map = new HashMap<>();
                map.put("Authorization","Bearer"+token);
                return map;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}
