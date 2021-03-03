package com.example.maintenancesql.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maintenancesql.Models.Update;
import com.example.maintenancesql.R;

import java.util.ArrayList;
import java.util.List;

public class UpdateAdapter extends RecyclerView.Adapter<UpdateAdapter.MyHolder> {
    Context context;
    ArrayList<Update> updateList;
    ArrayList<Update> updateArrayList;
    String postId;
    private SharedPreferences preferences;

    public UpdateAdapter(Context context, ArrayList<Update> updateList) {
        this.context = context;
        this.updateList = updateList;
        this.updateArrayList = updateArrayList;
        this.postId = postId;
        preferences = context.getApplicationContext().getSharedPreferences("user",Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_update,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Update update = updateList.get(position);
        holder.nomorTextView.setText(update.getNo());
        holder.tanggalTextView.setText(update.getTanggalMaintenance());
        holder.tanggalDuaView.setText(update.getTanggalMaintenanceSelanjutnya());
        holder.tindakanTextView.setText(update.getTindakan());
        holder.keteranganTextView.setText(update.getKeterangan());

    }

    @Override
    public int getItemCount() {
        return updateList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView nomorTextView, tanggalTextView, tanggalDuaView,tindakanTextView, keteranganTextView;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            nomorTextView = itemView.findViewById(R.id.nomorTextView);
            tanggalTextView = itemView.findViewById(R.id.tanggalTextView);
            tanggalDuaView = itemView.findViewById(R.id.tanggalDuaView);
            tindakanTextView = itemView.findViewById(R.id.tindakanTextView);
            keteranganTextView = itemView.findViewById(R.id.keteranganTextView);
        }

    }
}
