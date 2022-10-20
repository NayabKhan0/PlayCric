package com.example.playcric.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.playcric.Model.GroupTeamModel;
import com.example.playcric.R;

import java.util.ArrayList;

public class GroupTeamsAdapter extends RecyclerView.Adapter{

    Context context;
    ArrayList<GroupTeamModel> arrayList;

    public GroupTeamsAdapter(Context context, ArrayList<GroupTeamModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item2_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        if (arrayList.size() > 0) {
            GroupTeamModel groupTeamModel = arrayList.get(position);
            myViewHolder.team1TextView.setText(groupTeamModel.getFirstTeam().getTeamName());
            myViewHolder.team2TextView.setText(groupTeamModel.getSecondTeam().getTeamName());
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        TextView team1TextView,team2TextView;

        public MyViewHolder(View view) {
            super(view);

            team1TextView = view.findViewById(R.id.team1TextView);
            team2TextView = view.findViewById(R.id.team2TextView);

        }
    }
}
