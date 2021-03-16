package com.example.maintenancesql.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
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
import com.example.maintenancesql.Activities.HomeAct;
import com.example.maintenancesql.Activities.UpdateAct;
import com.example.maintenancesql.Models.Post;
import com.example.maintenancesql.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder>{

    Context context;
    ArrayList<Post> list;
    ArrayList<Post> listAll;
    private SharedPreferences preferences;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public PostAdapter(Context context,ArrayList<Post> list) {
        this.context = context;
        this.list = list;
        this.listAll = new ArrayList<>(list);
        preferences = context.getApplicationContext().getSharedPreferences("user",Context.MODE_PRIVATE);
    }


    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_post,parent,false);
        return new PostHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        Post post = list.get(position);
        holder.nameUser.setText(post.getUser().getUserName());
        holder.jenisTextView.setText(post.getJenisEdit());
        holder.merkView.setText(post.getMerkEdit());
        holder.lokasiView.setText(post.getLokasiEdit());
        holder.inventarisView.setText(post.getInventarisEdit());
        holder.jangkaView.setText(post.getJangkaWaktu());

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
            holder.moreBtnMain.setVisibility(View.VISIBLE);
        } else {
            holder.moreBtnMain.setVisibility(View.GONE);
        }
        holder.moreBtnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context,holder.moreBtnMain);
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

    private void deletePost(int post_id, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Confirm");
        builder.setMessage("Delete Post ?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                StringRequest request = new StringRequest(Request.Method.POST, Constant.DELETE_POST,response -> {
                    try {
                        JSONObject object = new JSONObject(response);
                        if (object.getBoolean("success")){
                            list.remove(position);
                            notifyItemRemoved(position);
                            notifyDataSetChanged();
                            listAll.clear();
                            listAll.addAll(list);
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
                        map.put("id",post_id+"");
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
        return list.size();
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Post> filteredList = new ArrayList<>();
            if (charSequence.toString().isEmpty()){
                filteredList.addAll(listAll);
            } else{
                for (Post post : listAll){
                    if (post.getJenisEdit().toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filteredList.add(post);
                    } if (post.getJangkaWaktu().toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filteredList.add(post);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            list.clear();
            list.addAll((Collection<? extends Post>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public Filter getFilter() {
        return filter;
    }

    class PostHolder extends RecyclerView.ViewHolder{
        LinearLayout nameLinear, merkLinear, lokasiLinear, inventarisLinear,linearPost;
        ImageButton moreBtnMain;
        TextView nameUser,nameTextView, jenisTextView,
                merkText, merkView,
                lokasiText, lokasiView,
                inventarisText, inventarisView,
                jangkaText,jangkaView;

        public PostHolder(@NonNull View itemView){
            super(itemView);
            //init views
            nameUser = itemView.findViewById(R.id.nameUser);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            jenisTextView = itemView.findViewById(R.id.jenisTextView);
            merkText = itemView.findViewById(R.id.merkText);
            merkView = itemView.findViewById(R.id.merkView);
            lokasiText = itemView.findViewById(R.id.lokasiText);
            lokasiView = itemView.findViewById(R.id.lokasiView);
            inventarisText = itemView.findViewById(R.id.inventarisText);
            inventarisView = itemView.findViewById(R.id.inventarisView);

            jangkaText = itemView.findViewById(R.id.jangkaText);
            jangkaView = itemView.findViewById(R.id.jangkaView);

            nameLinear = itemView.findViewById(R.id.nameLinear);
            merkLinear = itemView.findViewById(R.id.merkLinear);
            lokasiLinear = itemView.findViewById(R.id.lokasiLinear);
            inventarisLinear = itemView.findViewById(R.id.inventarisLinear);
            linearPost = itemView.findViewById(R.id.linearPost);

            moreBtnMain = itemView.findViewById(R.id.moreBtnMain);
            moreBtnMain.setVisibility(View.GONE);
        }
    }
}
