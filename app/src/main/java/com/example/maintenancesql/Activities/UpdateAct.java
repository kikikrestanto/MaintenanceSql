package com.example.maintenancesql.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.maintenancesql.Models.Update;
import com.example.maintenancesql.Models.User;
import com.example.maintenancesql.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UpdateAct extends AppCompatActivity {

    int post_id;
    int id=0;
    TextView noText,tanggalText, tindakanText, ketText, tanggalDua;

    EditText tanggalEditDua,noView, tanggalView,tindakanView,ketView;

    Button updateButton;

    SharedPreferences preferences;
    ProgressDialog pd;

    Calendar calendar = Calendar.getInstance();
    Calendar cal = Calendar.getInstance();
    Date tanggalViewDate,tanggalViewDateTwo;
    DatePickerDialog.OnDateSetListener setListener, setClickListener;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMMM-yyyy");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        init();
        Intent intent = getIntent();
        post_id = intent.getIntExtra("post_id",0);

    }

    private void init() {
        preferences = getApplicationContext().getSharedPreferences("user",MODE_PRIVATE);
        noView = findViewById(R.id.noView);
        tanggalText = findViewById(R.id.tanggalText);
        tanggalView = findViewById(R.id.tanggalView);
        tindakanText = findViewById(R.id.tindakanText);
        tindakanView = findViewById(R.id.tindakanView);
        ketText = findViewById(R.id.ketText);
        ketView = findViewById(R.id.ketView);
        updateButton = findViewById(R.id.updateButton);

        tanggalEditDua = findViewById(R.id.tanggalEditDua);
        tanggalDua = findViewById(R.id.tanggalDua);

        id = getIntent().getIntExtra("post_id",0);

        pd = new ProgressDialog(this);
        pd.setCancelable(false);


        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nomor = noView.getText().toString().trim();
                String tanggal = tanggalView.getText().toString().trim();
                String tanggalDua = tanggalEditDua.getText().toString();
                String tindakan = tindakanView.getText().toString().trim();
                String keterangan = ketView.getText().toString().trim();

                /*String satu = satuBulan.getText().toString().trim();
                String tiga = tigaBulan.getText().toString().trim();
                String enam = enamBulan.getText().toString().trim();
                String Satutahun = satuTahun.getText().toString().trim(); */

                if (TextUtils.isEmpty(nomor)){
                    Toast.makeText(UpdateAct.this, "Enter No....", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(tanggal)){
                    Toast.makeText(UpdateAct.this, "Enter Tanggal ....", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(tanggalDua)){
                    Toast.makeText(UpdateAct.this, "Enter Tanggal ....", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(tindakan)){
                    Toast.makeText(UpdateAct.this, "Enter Action ....", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(keterangan)){
                    Toast.makeText(UpdateAct.this, "Enter Information ....", Toast.LENGTH_SHORT).show();
                    return;
                }

                update();
            }
        });

        tanggalView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(UpdateAct.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth
                        ,setListener, year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                //month = month + 1;
                //Log.d(TAG, "onDateSet: mm/dd/yyy :" + month + "/" + day + "/" + year);
                //String date = month + "/" + day + "/" + year;
                calendar.set(year,month,day);
                tanggalView.setText(simpleDateFormat.format(calendar.getTime()));
                tanggalViewDate = calendar.getTime();
            }
        };
        tanggalEditDua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(UpdateAct.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth
                        ,setClickListener, year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        setClickListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                cal.set(year,month,day);
                tanggalEditDua.setText(simpleDateFormat.format(cal.getTime()));
                tanggalViewDateTwo = cal.getTime();
            }
        };
    }

    private void update(){
        //postid = preferences.getInt("id",0);
        //final String post_id = String.valueOf(postid);

        String nomor = noView.getText().toString().trim();
        String tanggal = tanggalView.getText().toString().trim();
        String tanggalDua = tanggalEditDua.getText().toString();
        String tindakan = tindakanView.getText().toString().trim();
        String keterangan = ketView.getText().toString().trim();
        pd.setMessage("Update");
        pd.show();

        StringRequest request = new StringRequest(Request.Method.POST,Constant.ADD_UPDATE,response -> {
            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")){
                    JSONObject updateObject = object.getJSONObject("update");
                    JSONObject userObject = updateObject.getJSONObject("user");

                    User user = new User();
                    user.setId(userObject.getInt("id"));
                    user.setUserName(userObject.getString("name")+" "+userObject.getString("lastname"));

                    Update update = new Update();
                    update.setUser(user);
                    //update.setPost_id(updateObject.getString("post_id"));
                    update.setUser_id(updateObject.getInt("user_id"));
                    update.setPost_id(updateObject.getInt("post_id"));
                    update.setNo(updateObject.getString("no"));
                    update.setTanggalMaintenance(updateObject.getString("tanggalMaintenance"));
                    update.setTanggalMaintenanceSelanjutnya(updateObject.getString("tanggalMaintenanceSelanjutnya"));
                    update.setTindakan(updateObject.getString("tindakan"));
                    update.setKeterangan(updateObject.getString("keterangan"));

                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                    finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            pd.dismiss();
        },error -> {
            error.printStackTrace();
            pd.dismiss();
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
                HashMap<String, String> map = new HashMap<>();
                //map.put("post_id",post_id);
                //Log.d("POST_ID",post_id);
                map.put("post_id", String.valueOf(id));
                Log.d("POST_id", String.valueOf(id));
                map.put("no",nomor);
                map.put("tanggalMaintenance",tanggal);
                map.put("tanggalMaintenanceSelanjutnya",tanggalDua);
                map.put("tindakan",tindakan);
                map.put("keterangan",keterangan);
                return map;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(UpdateAct.this);
        queue.add(request);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); //go previous activty
        return super.onSupportNavigateUp();
    }
}
