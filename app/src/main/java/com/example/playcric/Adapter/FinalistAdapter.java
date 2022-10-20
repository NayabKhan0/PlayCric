package com.example.playcric.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.playcric.DataBase.DataTable;
import com.example.playcric.R;

import java.util.ArrayList;

public class FinalistAdapter extends RecyclerView.Adapter {

    ArrayList<DataTable> arrayList;
    Context context;

    public FinalistAdapter(ArrayList<DataTable> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.finalist_item, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        if (arrayList.size() > 0) {
            DataTable dataTable = arrayList.get(position);
            myViewHolder.teamTextView.setText(dataTable.getTeamName());
            int pos = 1+position;
            if (pos == 1) {
                myViewHolder.positionTextView.setText(pos+"st Position");
            } else if (pos == 2) {
                myViewHolder.positionTextView.setText(pos+"nd Position");
            } else {
                myViewHolder.positionTextView.setText(pos+"rd Position");
            }

        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        TextView teamTextView,positionTextView;

        public MyViewHolder(View view) {
            super(view);

            teamTextView = view.findViewById(R.id.teamTextView);
            positionTextView = view.findViewById(R.id.positionTextView);
        }
    }
}
