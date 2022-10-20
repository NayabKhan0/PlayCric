package com.example.playcric.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.playcric.DataBase.DataTable;
import com.example.playcric.Interface.OnEditClick;
import com.example.playcric.R;

import java.util.ArrayList;

public class TeamsAdapter extends RecyclerView.Adapter {

    ArrayList<DataTable> arrayList;
    Context context;
    boolean isRename = false;
    OnEditClick onEditClick;

    public TeamsAdapter(ArrayList<DataTable> arrayList, Context context,OnEditClick onEditClick) {
        this.arrayList = arrayList;
        this.context = context;
        this.onEditClick = onEditClick;
        isRename = false;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.teams_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;
        if (!arrayList.isEmpty()) {
            DataTable dataTable = arrayList.get(position);
            myViewHolder.titleTextView.setText(dataTable.getTeamName());
            myViewHolder.renameImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onEditClick.onSelect(dataTable,position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView titleTextView;
        ImageView renameImageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.titleTextView);
            renameImageView = itemView.findViewById(R.id.renameImageView);
        }
    }

}
