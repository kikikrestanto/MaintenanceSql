package com.example.maintenancesql.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
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

public class LoginAct extends AppCompatActivity {

    EditText emailLogin,passLogin,nipLogin;
    LinearLayout signIn,linearEmail,linearPass;
    TextView toReg;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        nipLogin = (EditText) findViewById(R.id.nipLogin);
        passLogin = (EditText) findViewById(R.id.passLogin);
        signIn = (LinearLayout) findViewById(R.id.signIn);
        linearEmail = (LinearLayout) findViewById(R.id.linearEmail);
        linearPass = (LinearLayout) findViewById(R.id.linearPass);
        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("onBoard", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isFirstTime",true);
        editor.commit();

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getApplicationContext().getSharedPreferences("user",Context.MODE_PRIVATE);
                boolean loginFirstTime = preferences.getBoolean("loginFirstTime", false);
                if (loginFirstTime){

                    //loginFirstTime();
                    startActivity(new Intent(LoginAct.this,UserInfoAct.class));
                    finish();
                }
                else {
                    loginUser();
                }

            }
        });
    }

    /*private void loginFirstTime() {
        dialog.setMessage("Update data");
        dialog.show();

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        boolean isLoginFirstTime = preferences.getBoolean("isLoggedFirstTime",false);


        StringRequest request = new StringRequest(Request.Method.POST, Constant.LOGIN,response -> {
            try {

                if (isLoginFirstTime){
                    startActivity(new Intent(LoginAct.this,UserInfoAct.class));
                    finish();
                }
                JSONObject object = new JSONObject((response));
                if (object.getBoolean("success")){
                    JSONObject user = object.getJSONObject("user");
                    //make shared preference user
                    SharedPreferences userPref = this.getApplicationContext().getSharedPreferences("user",this.MODE_PRIVATE);
                    SharedPreferences.Editor editor = userPref.edit();
                    editor.putString("token",object.getString("token"));
                    editor.putString("nip",user.getString("nip"));
                    editor.putString("name",user.getString("name"));
                    editor.putString("lastname",user.getString("lastname"));
                    editor.putBoolean("proses_completed",false);
                    editor.putInt("id",user.getInt("id"));
                    editor.putBoolean("isLoggedIn", true);
                    editor.apply();

                    startActivity(new Intent(LoginAct.this, UserInfoAct.class));
                    finish();
                    //if success
                    Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        },error -> {
            error.printStackTrace();
            dialog.dismiss();
        }){
            //add parameters

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("nip", nipLogin.getText().toString().trim());
                map.put("password", passLogin.getText().toString().trim());
                return map;
            }
        };
        //add this request to requestqueue
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    } */

    private void loginUser() {
        dialog.setMessage("Logging in");
        dialog.show();
        //SharedPreferences preferences = getApplicationContext().getSharedPreferences("user",Context.MODE_PRIVATE);
        //boolean loginUer = preferences.getBoolean("loginUser",false);
        StringRequest request = new StringRequest(Request.Method.POST, Constant.LOGIN,response->{
            //we get response if connection success
            try {
                //if (loginUer){
                  //  startActivity(new Intent(LoginAct.this,HomeAct.class));
                    //finish();
                //}
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")){
                    JSONObject user = object.getJSONObject("user");
                    //make shared preference user
                    SharedPreferences userPref = this.getApplicationContext().getSharedPreferences("user",this.MODE_PRIVATE);
                    SharedPreferences.Editor editor = userPref.edit();
                    editor.putString("token",object.getString("token"));
                    editor.putString("nip",user.getString("nip"));
                    editor.putString("name",user.getString("name"));
                    editor.putString("lastname",user.getString("lastname"));
                    editor.putBoolean("proses_completed",true);
                    editor.putInt("id",user.getInt("id"));
                    editor.putBoolean("isLoggedIn", true);
                    editor.apply();

                    startActivity(new Intent(LoginAct.this, HomeAct.class));
                    finish();
                    //if success
                    Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show();
                } /*else if(object.getBoolean("success")){
                    {
                        JSONObject user = object.getJSONObject("user");
                        //make shared preference user
                    SharedPreferences userPref = this.getApplicationContext().getSharedPreferences("user",this.MODE_PRIVATE);
                    SharedPreferences.Editor editor = userPref.edit();
                    editor.putString("token",object.getString("token"));
                    editor.putString("nip",user.getString("nip"));
                    editor.putString("name",user.getString("name"));
                    editor.putString("email",user.getString("email"));
                    editor.putBoolean("proses_completed",true );
                    editor.putInt("id",user.getInt("id"));
                    editor.putBoolean("isLoggedIn",true);
                    editor.apply();

                    startActivity(new Intent(LoginAct.this,HomeAct.class));
                    finish();
                        Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show();
                    }
                } */

            } catch (JSONException e) {
                e.printStackTrace();
            }
            dialog.dismiss();
        },error -> {
            //error if connection not success
            error.printStackTrace();
            dialog.dismiss();
        }){
            //add parameters

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("nip",nipLogin.getText().toString().trim());
                map.put("password",passLogin.getText().toString()   .trim());
                return  map;
            }
        };

        //add this request to requestqueue
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); //go previous activty
        return super.onSupportNavigateUp();
    }
}
