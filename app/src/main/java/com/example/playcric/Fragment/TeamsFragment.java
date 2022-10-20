package com.example.playcric.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.playcric.DataBase.DataBaseClient;
import com.example.playcric.DataBase.DataTable;
import com.example.playcric.Adapter.TeamsAdapter;
import com.example.playcric.Interface.OnEditClick;
import com.example.playcric.R;
import com.example.playcric.RenameDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class TeamsFragment extends Fragment {

    ArrayList<DataTable> tableArrayList = new ArrayList<>();
    TeamsAdapter teamsAdapter;
    RelativeLayout mainLayout;
    LottieAnimationView animation;
    Button playButton;
    RecyclerView teamRecyclerView;
    OnEditClick onEditClick;
    RenameDialog renameDialog;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teams, container, false);
        init(view);
        onClick();

        return view;
    }

    private void init(View view) {
        animation = view.findViewById(R.id.animation);
        animation.setAnimation("animation.json");
        mainLayout = view.findViewById(R.id.mainLayout);
        playButton = view.findViewById(R.id.playButton);
        teamRecyclerView = view.findViewById(R.id.teamRecyclerView);
        teamRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        onEditClick = new OnEditClick() {
            @Override
            public void onSelect(DataTable dataTable, int position) {
                handleDialog(dataTable,position);
            }
        };

        teamsAdapter = new TeamsAdapter(tableArrayList,getActivity(),onEditClick);
        teamRecyclerView.setAdapter(teamsAdapter);
    }

    private void handleDialog(DataTable dataTable,int position) {
        renameDialog = new RenameDialog(getActivity(),dataTable);
        renameDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        renameDialog.setCanceledOnTouchOutside(false);
        renameDialog.setCancelable(true);
        renameDialog.show();

        renameDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {

                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        List<DataTable> dataTables = DataBaseClient.getInstance(getActivity())
                                .getAppDatabase().dataDao().getAll();
                        tableArrayList.clear();
                        tableArrayList.addAll(dataTables);
                        new Handler(Looper.getMainLooper()).post(() -> {
                            teamsAdapter.notifyDataSetChanged();
                        });
                    }
                });
            }
        });
    }

    private void onClick() {
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainLayout.setVisibility(View.GONE);
                animation.setVisibility(View.VISIBLE);
                animation.playAnimation();
                Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Navigation.findNavController(view).navigate(R.id.action_teamsFragment_to_groupTeamsFragment);
                    }
                }, 2000);

            }
        });

    }

    private void addData() {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                tableArrayList.clear();
                DataBaseClient.getInstance(getActivity()).getAppDatabase().dataDao().deleteAllData();
                ArrayList<String> arrayList = new ArrayList<>();
                arrayList.add("Mumbai Indians");
                arrayList.add("Chennai Super Kings");
                arrayList.add("Sunrisers Hyderabad");
                arrayList.add("Kolkata Knight Riders");
                arrayList.add("Punjab Kings");
                arrayList.add("Delhi Capitals");
                arrayList.add("Royal Challengers Bangalore");
                arrayList.add("Rajasthan Royals");

                for (String s : arrayList) {
                    DataTable dataTable = new DataTable();
                    dataTable.setTeamName(s);
                    dataTable.setTeamStatus("-");
                    DataBaseClient.getInstance(getActivity()).getAppDatabase().dataDao().insert(dataTable);
                    tableArrayList.add(dataTable);
                }
                new Handler(Looper.getMainLooper()).post(() -> {
                    teamsAdapter.notifyDataSetChanged();
                });
            }
        });
    }



}