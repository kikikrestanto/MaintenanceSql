package com.example.maintenancesql.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.maintenancesql.Adapters.PostAdapter;
import com.example.maintenancesql.Models.Post;
import com.example.maintenancesql.Models.User;
import com.example.maintenancesql.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailAct extends AppCompatActivity {

    Context context;
    ArrayList<Post> list;
    PostAdapter adapter;
    int position = 0, id = 0;
    TextView nameUserDetail,nameTextViewDetail, jenisTextViewDetail,
                        merkTextDetail,merkViewDetail,lokasiTextDetail,lokasiViewDetail,inventarisTextDetail,
                        inventarisViewDetail, jangkaText;
    ImageButton moreBtnMainDetail;
    int myId;
    String uid,postId;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        postId =intent.getStringExtra("postId");
        id = getIntent().getIntExtra("postId",0);
        init();
        myId = preferences.getInt("id",0);
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

        position = getIntent().getIntExtra("position", 0);
        id = getIntent().getIntExtra("postId", 0);
        nameUserDetail.setText(getIntent().getStringExtra("name"));
        inventarisViewDetail.setText(getIntent().getStringExtra("inventarisEdit"));
        jangkaText.setText(getIntent().getStringExtra("jangkaWaktu"));
        jenisTextViewDetail.setText(getIntent().getStringExtra("jenisEdit"));
        lokasiViewDetail.setText(getIntent().getStringExtra("lokasiEdit"));
        merkViewDetail.setText(getIntent().getStringExtra("merkEdit"));

        //adapter = new PostAdapter(this,list);


    }


    public void showMoreOptions() {
        if(id == (myId)){
        moreBtnMainDetail.setVisibility(View.VISIBLE);
        } else {
            moreBtnMainDetail.setVisibility(View.GONE);
        }
        PopupMenu popupMenu = new PopupMenu(this,moreBtnMainDetail);
        popupMenu.inflate(R.menu.menu_update);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.item_update: {
                        Intent intent = new Intent(DetailAct.this,UpdateAct.class);
                        intent.putExtra("postId",postId);
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
