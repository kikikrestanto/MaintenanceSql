package com.example.maintenancesql.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.maintenancesql.Activities.Constant;
import com.example.maintenancesql.Activities.EditUserAct;
import com.example.maintenancesql.Activities.HomeAct;
import com.example.maintenancesql.Adapters.AccountPostAdapter;
import com.example.maintenancesql.Adapters.PostAdapter;
import com.example.maintenancesql.Models.Post;
import com.example.maintenancesql.Models.User;
import com.example.maintenancesql.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AccountFragment extends Fragment {
    private View view;
    private TextView userAccoountText,nipAccountText;
    private TextView editProfile;
    private RecyclerView recyclerView;
    private ArrayList<Post> arrayList;
    private SharedPreferences preferences;
    private AccountPostAdapter adapter;

    public AccountFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_account,container,false);
        userAccoountText = view.findViewById(R.id.userAccountText);
        nipAccountText = view.findViewById(R.id.nipAcoountText);
        editProfile = view.findViewById(R.id.editProfile);
        preferences = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        recyclerView = view.findViewById(R.id.recyclerAccount);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        init();
        return view;
    }

    private void init() {
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),EditUserAct.class);
                startActivity(intent);
            }
        });
    }

    private void getData() {
        arrayList = new ArrayList<>();
        StringRequest request = new StringRequest(Request.Method.GET, Constant.MY_POST,response -> {

            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")){
                    JSONArray array = object.getJSONArray("posts");
                    JSONObject userAccount = object.getJSONObject("user");
                    for (int i = 0; i< array.length(); i++){
                        JSONObject postObject = array.getJSONObject(i);


                        User user = new User();
                        user.setId(userAccount.getInt("id"));
                        user.setUserName(userAccount.getString("name")+" "+userAccount.getString("lastname"));

                        Post post = new Post();
                        post.setId(postObject.getInt("id"));
                        post.setUser(user);
                        post.setInventarisEdit(postObject.getString("inventarisEdit"));
                        post.setJangkaWaktu(postObject.getString("jangkaWaktu"));
                        post.setJenisEdit(postObject.getString("jenisEdit"));
                        post.setLokasiEdit(postObject.getString("lokasiEdit"));
                        post.setMerkEdit(postObject.getString("merkEdit"));
                        arrayList.add(post);
                    }
                    JSONObject user = object.getJSONObject("user");
                    userAccoountText.setText(user.getString("name")+" "+user.getString("lastname"));
                    nipAccountText.setText(user.getString("nip"));
                    adapter = new AccountPostAdapter(getContext(),arrayList);
                    recyclerView.setAdapter(adapter);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        },error -> {
            error.printStackTrace();
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = preferences.getString("token","");
                HashMap<String,String> map = new HashMap<>();
                map.put("Authorization","Bearer" + token);
                return map;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }
}
