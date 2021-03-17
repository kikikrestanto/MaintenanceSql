package com.example.maintenancesql.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.maintenancesql.Activities.Constant;
import com.example.maintenancesql.Activities.DetailAct;
import com.example.maintenancesql.Models.Post;
import com.example.maintenancesql.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AccountPostAdapter extends RecyclerView.Adapter<AccountPostAdapter. MyHolder> {

    Context context;
    ArrayList<Post> postArrayList;
    ArrayList<Post> listAll;
    SharedPreferences preferences;

    public AccountPostAdapter(Context context, ArrayList<Post> postArrayList) {
        this.context = context;
        this.postArrayList = postArrayList;
        this.listAll = new ArrayList<>(postArrayList);
        preferences = context.getApplicationContext().getSharedPreferences("user",Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_account_post,parent,false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        //postArrayList.clear();
        Post post = postArrayList.get(position);
        holder.nameUserAccount.setText(post.getUser().getUserName());
        holder.jenisTextViewAccount.setText(post.getJenisEdit());
        holder.merkViewAccount.setText(post.getMerkEdit());
        holder.lokasiViewAccount.setText(post.getLokasiEdit());
        holder.inventarisViewAccount.setText(post.getInventarisEdit());
        holder.jangkaViewAccount.setText(post.getJangkaWaktu());

        holder.linearPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detail = new Intent(context, DetailAct.class);
                detail.putExtra("name",post.getUser().getUserName());
                detail.putExtra("post_id",post.getId());
                detail.putExtra("user_id",post.getUser_id());
                detail.putExtra("position",position);
                detail.putExtra("inventarisEdit",post.getInventarisEdit());
                detail.putExtra("jangkaWaktu",post.getJangkaWaktu());
                detail.putExtra("jenisEdit",post.getJenisEdit());
                detail.putExtra("lokasiEdit",post.getLokasiEdit());
                detail.putExtra("merkEdit",post.getMerkEdit());
                detail.putExtra("dateEdit",post.getDateEdit());
                context.startActivity(detail);
            }
        });

        if (post.getUser().getId()==preferences.getInt("id",0)){
            holder.moreBtnMainAccount.setVisibility(View.VISIBLE);
        } else {
            holder.moreBtnMainAccount.setVisibility(View.GONE);
        }
        holder.moreBtnMainAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context,holder.moreBtnMainAccount);
                popupMenu.inflate(R.menu.menu_post_option);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.item_delete: {
                                deletePost(post.getId(),position);
                                return true;
                            }
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

    private void deletePost(int postId, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Confirm");
        builder.setMessage("Delete Post ?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                StringRequest request = new StringRequest(Request.Method.POST, Constant.DELETE_POST, response -> {
                    try {
                        JSONObject object = new JSONObject(response);
                        if (object.getBoolean("success")){
                            postArrayList.remove(position);
                            notifyItemRemoved(position);
                            notifyDataSetChanged();
                            listAll.clear();
                            listAll.addAll(postArrayList);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },error -> {

                }){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        String token = preferences.getString("token", "");
                        HashMap<String, String> map = new HashMap<>();
                        map.put("Authorization","Bearer"+token);
                        return map;
                    }

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("id",postId+"");
                        return map;
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(context);
                queue.add(request);

                Toast.makeText(context, "Delete Success", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }

    @Override
    public int getItemCount() {
        return postArrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        LinearLayout nameLinear, merkLinear, lokasiLinear, inventarisLinear,linearPost;
        ImageButton moreBtnMainAccount;
        TextView nameUserAccount,nameTextView, jenisTextViewAccount,
                merkText, merkViewAccount,
                lokasiText, lokasiViewAccount,
                inventarisText, inventarisViewAccount,
                jangkaText,jangkaViewAccount;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            nameUserAccount= itemView.findViewById(R.id.nameUserAccount);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            jenisTextViewAccount = itemView.findViewById(R.id.jenisTextViewAccount);
            merkText = itemView.findViewById(R.id.merkText);
            merkViewAccount = itemView.findViewById(R.id.merkViewAccount);
            lokasiText = itemView.findViewById(R.id.lokasiText);
            lokasiViewAccount = itemView.findViewById(R.id.lokasiViewAccount);
            inventarisText = itemView.findViewById(R.id.inventarisText);
            inventarisViewAccount = itemView.findViewById(R.id.inventarisViewAccount);

            jangkaText = itemView.findViewById(R.id.jangkaText);
            jangkaViewAccount = itemView.findViewById(R.id.jangkaViewAccount);

            nameLinear = itemView.findViewById(R.id.nameLinear);
            merkLinear = itemView.findViewById(R.id.merkLinear);
            lokasiLinear = itemView.findViewById(R.id.lokasiLinear);
            inventarisLinear = itemView.findViewById(R.id.inventarisLinear);
            linearPost = itemView.findViewById(R.id.linearPost);

            moreBtnMainAccount = itemView.findViewById(R.id.moreBtnMainAccount);
            moreBtnMainAccount.setVisibility(View.GONE);
        }
    }
}
