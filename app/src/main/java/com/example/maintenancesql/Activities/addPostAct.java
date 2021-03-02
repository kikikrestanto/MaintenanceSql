package com.example.maintenancesql.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.maintenancesql.Fragments.HomeFragment;
import com.example.maintenancesql.Models.Post;
import com.example.maintenancesql.Models.User;
import com.example.maintenancesql.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class addPostAct extends AppCompatActivity {

    private EditText merkEdit, lokasiEdit, inventarisEdit;
    private Button uploadM;
    private Spinner jenisEdit;
    private RadioGroup radioGroup;
    private RadioButton satuBulan, tigaBulan, enamBulan, satuTahun, satuMinggu,duaMinggu;
    String jangkaWaktu="";
    String setNamaInventaris;
    private ProgressDialog dialog;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        init();
    }

    private void init() {
        preferences = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        jenisEdit = findViewById(R.id.jenisEdit);
        merkEdit = findViewById(R.id.merkEdit);
        lokasiEdit = findViewById(R.id.lokasiEdit);
        inventarisEdit = findViewById(R.id.inventarisEdit);
        uploadM = findViewById(R.id.uploadM);
        radioGroup = findViewById(R.id.radioGrup);
        satuMinggu = findViewById(R.id.satuMinggu);
        duaMinggu = findViewById(R.id.duaMinggu);
        satuBulan = findViewById(R.id.satuBulan);
        tigaBulan = findViewById(R.id.tigaBulan);
        enamBulan = findViewById(R.id.enamBulan);
        satuTahun = findViewById(R.id.satuTahun);

        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);


        uploadM.setOnClickListener(v->{

            String jenis = jenisEdit.getSelectedItem().toString();
            //String jenis = jenisEdit.getText().toString().trim();
            String merk = merkEdit.getText().toString().trim();
            String lokasi = lokasiEdit.getText().toString().trim();
            String inventaris = inventarisEdit.getText().toString().trim();

            /*if (TextUtils.isEmpty(jenis)){
                Toast.makeText(addPostAct.this, "Enter Type...", Toast.LENGTH_SHORT).show();
                return;
            } */
            if (TextUtils.isEmpty(merk)){
                Toast.makeText(addPostAct.this, "Enter Merk...", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(lokasi)){
                Toast.makeText(addPostAct.this, "Enter Location...", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(inventaris)){
                Toast.makeText(addPostAct.this, "Enter Inventory.... ", Toast.LENGTH_SHORT).show();
                return;
            }
            if (satuMinggu.isChecked()){
                jangkaWaktu = satuMinggu.getText().toString();
            } if (duaMinggu.isChecked()){
                jangkaWaktu = duaMinggu.getText().toString();
            }
            if (satuBulan.isChecked()){
                jangkaWaktu = satuBulan.getText().toString();
            } if (tigaBulan.isChecked()){
                jangkaWaktu = tigaBulan.getText().toString();
            } if (enamBulan.isChecked()){
                jangkaWaktu = enamBulan.getText().toString();
            } if (satuTahun.isChecked()){
                jangkaWaktu = satuTahun.getText().toString();
            }
            post();
        });
    }

    /*private void RadioButtonClicked(View view) {
        String jangkaWaktu ="";

        boolean checked = ((RadioButton)view).isChecked();

        switch (view.getId()){
            case R.id.satuBulan:
                if (checked)
                    jangkaWaktu = "1 Bulan";
                break;
            case R.id.tigaBulan:
                if (checked)
                    jangkaWaktu = "3 Bulan";
                break;
            case R.id.enamBulan:
                if (checked)
                    jangkaWaktu = "6 Bulan";
                break;
            case R.id.satuTahun:
                if (checked)
                    jangkaWaktu = "1 tahun";
                break;
        }
    } */

    private void post() {
        dialog.setMessage("Posting");
        dialog.show();

        StringRequest request = new StringRequest(Request.Method.POST,Constant.ADD_POST,response -> {

            try {
                JSONObject object = new JSONObject(response);
                if(object.getBoolean("success")){
                    JSONObject postObject = object.getJSONObject("post");
                    JSONObject userObject = postObject.getJSONObject("user");

                    User user = new User();
                    user.setId(userObject.getInt("id"));
                    user.setUserName(userObject.getString("name"+ " "+userObject.getString("lastname")));

                    Post post = new Post();
                    post.setUser(user);
                    post.setInventarisEdit(postObject.getString("inventarisEdit"));
                    post.setJangkaWaktu(postObject.getString("jangkaWaktu"));
                    post.setJenisEdit(postObject.getString("jenisEdit"));
                    post.setLokasiEdit(postObject.getString("lokasiEdit"));
                    post.setMerkEdit(postObject.getString("merkEdit"));
                    post.setDateEdit(postObject.getString("created_at"));

                    HomeFragment.arrayList.add(0,post);
                    HomeFragment.recyclerView.getAdapter().notifyItemInserted(0);
                    HomeFragment.recyclerView.getAdapter().notifyDataSetChanged();
                    Toast.makeText(this, "Posted", Toast.LENGTH_SHORT).show();
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
            //add token to header

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = preferences.getString("token","");
                HashMap<String, String> map = new HashMap<>();
                map.put("Authorization","Bearer" + token);
                return map;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("inventarisEdit",inventarisEdit.getText().toString().trim());
                map.put("jangkaWaktu",jangkaWaktu);
                map.put("jenisEdit",jenisEdit.getSelectedItem().toString().trim());
                map.put("lokasiEdit",lokasiEdit.getText().toString().trim());
                map.put("merkEdit",merkEdit.getText().toString().trim());

                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(addPostAct.this);
        queue.add(request);
    }

}
