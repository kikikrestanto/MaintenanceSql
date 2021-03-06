package com.example.maintenancesql.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.maintenancesql.Adapters.PostAdapter;
import com.example.maintenancesql.Adapters.UpdateAdapter;
import com.example.maintenancesql.Models.Post;
import com.example.maintenancesql.Models.Update;
import com.example.maintenancesql.Models.User;
import com.example.maintenancesql.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailAct extends AppCompatActivity {

    Context context;
    public static ArrayList<Update> updates;
    public static RecyclerView recycleViewDetail;
    SwipeRefreshLayout refreshLayout;
    private UpdateAdapter updateAdapter;
    int position = 0, id = 0;
    TextView nameUserDetail,nameTextViewDetail, jenisTextViewDetail,
                        merkTextDetail,merkViewDetail,lokasiTextDetail,lokasiViewDetail,inventarisTextDetail,
                        inventarisViewDetail, jangkaText;
    ImageButton moreBtnMainDetail;
    int myId,user_id, post_id;
    //String uid,post_id;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        preferences = getApplicationContext().getSharedPreferences("user",Context.MODE_PRIVATE);
        Log.d("MyUSER", String.valueOf(preferences));
        Intent intent = getIntent();
        post_id = (int) intent.getIntExtra("post_id",0);
        id = getIntent().getIntExtra("post_id",0);
        Log.d("Id_post", String.valueOf(id));
        user_id = getIntent().getIntExtra("user_id",0);
        Log.d("USER_ID", String.valueOf(user_id));
        init();
        getUpdate();

        myId = preferences.getInt("id",0);
        Log.d("USER_Id_lagi_login", String.valueOf(myId));
        if(user_id == (myId))
        {
            moreBtnMainDetail.setVisibility(View.VISIBLE);
        } else {
            moreBtnMainDetail.setVisibility(View.GONE);
        }
        moreBtnMainDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMoreOptions();
            }
        });
    }

    /*public DetailAct(Context context, ArrayList<Post> list){
        this.context = context;
        this.list = list;
    } */

    public void init() {
        preferences = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        refreshLayout = findViewById(R.id.swipeDetail);
        nameUserDetail = findViewById(R.id.nameUserDetail);
        nameTextViewDetail = findViewById(R.id.nameTextViewDetail);
        jenisTextViewDetail = findViewById(R.id.jenisTextViewDetail);
        merkTextDetail = findViewById(R.id.merkTextDetail);
        merkViewDetail = findViewById(R.id.merkViewDetail);
        lokasiViewDetail = findViewById(R.id.lokasiViewDetail);
        inventarisTextDetail = findViewById(R.id.inventarisTextDetail);
        inventarisViewDetail = findViewById(R.id.inventarisViewDetail);
        jangkaText = findViewById(R.id.jangkaText);
        moreBtnMainDetail = findViewById(R.id.moreBtnMainDetail);

        recycleViewDetail=findViewById(R.id.recycleViewDetail);


        position = getIntent().getIntExtra("position", 0);
        id = getIntent().getIntExtra("postId", 0);
        nameUserDetail.setText(getIntent().getStringExtra("name"));
        inventarisViewDetail.setText(getIntent().getStringExtra("inventarisEdit"));
        jangkaText.setText(getIntent().getStringExtra("jangkaWaktu"));
        jenisTextViewDetail.setText(getIntent().getStringExtra("jenisEdit"));
        lokasiViewDetail.setText(getIntent().getStringExtra("lokasiEdit"));
        merkViewDetail.setText(getIntent().getStringExtra("merkEdit"));

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getUpdate();
            }
        });
    }

    private void getUpdate() {
        refreshLayout.setRefreshing(true);
        recycleViewDetail.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recycleViewDetail.setLayoutManager(linearLayoutManager);
        updates = new ArrayList<>();

        StringRequest request = new StringRequest(Request.Method.GET,Constant.UPDATES+ "?id=" + post_id,response -> {
            Log.d("URL",Constant.UPDATES+"?id" +post_id);
            try {
                JSONObject object = new JSONObject(response);
                Log.d("RESPONSE",String.valueOf(object));
                if (object.getBoolean("success")){
                    JSONArray array = new JSONArray(object.getString("updates"));
                    for (int i = 0; i < array.length(); i++){
                        JSONObject updateObject = array.getJSONObject(i);
                        JSONObject userObject = updateObject.getJSONObject("user");

                        User user = new User();
                        user.setId(userObject.getInt("id"));
                        user.setUserName(userObject.getString("name")+" "+userObject.getString("lastname"));

                        Update update = new Update();
                        update.setId(updateObject.getInt("id"));
                        update.setUser(user);
                       // update.setPost_id(userObject.getInt("post_id"));
                        update.setUser_id(updateObject.getInt("user_id"));
                        update.setPost_id(updateObject.getInt("post_id"));
                        update.setNo(updateObject.getString("no"));
                        update.setTanggalMaintenance(updateObject.getString("tanggalMaintenance"));
                        update.setTanggalMaintenanceSelanjutnya(updateObject.getString("tanggalMaintenanceSelanjutnya"));
                        update.setTindakan(updateObject.getString("tindakan"));
                        update.setKeterangan(updateObject.getString("keterangan"));

                        updates.add(update);
                    }
                    updateAdapter = new UpdateAdapter(getApplicationContext(),updates);
                    recycleViewDetail.setAdapter(updateAdapter);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            refreshLayout.setRefreshing(false);
        },error -> {
            error.printStackTrace();
            refreshLayout.setRefreshing(false);
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = preferences.getString("token","");
                HashMap<String,String> map = new HashMap<>();
                map.put("Authorization","Bearer" + token);

                return map;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(DetailAct.this);
        queue.add(request);
    }


    public void showMoreOptions() {
        PopupMenu popupMenu = new PopupMenu(this,moreBtnMainDetail);
        popupMenu.inflate(R.menu.menu_update);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.item_update: {
                        Intent intent = new Intent(DetailAct.this,UpdateAct.class);
                        intent.putExtra("post_id",post_id);
                        startActivity(intent);
                        return true;
                    }
                }
                return false;
            }
        });
        popupMenu.show();


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); //go previous activty
        return super.onSupportNavigateUp();
    }
}
