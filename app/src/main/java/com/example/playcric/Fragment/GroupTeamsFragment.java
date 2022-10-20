package com.example.playcric.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.telephony.AccessNetworkConstants;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.example.playcric.Adapter.GroupTeamsAdapter;
import com.example.playcric.DataBase.DataBaseClient;
import com.example.playcric.DataBase.DataTable;
import com.example.playcric.Model.GroupTeamModel;
import com.example.playcric.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;

public class GroupTeamsFragment extends Fragment {

    ArrayList<GroupTeamModel> arrayList = new ArrayList<>();
    GroupTeamsAdapter groupTeamsAdapter;
    RelativeLayout mainLayout;
    LottieAnimationView animation;
    RecyclerView recyclerView;
    Button stimulate;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_teams, container, false);
        init(view);
        onClick();
        setupList();
        return view;
    }

    private void init(View view) {
        mainLayout = view.findViewById(R.id.mainLayout);
        animation = view.findViewById(R.id.animation);
        animation.setAnimation("animation.json");
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        groupTeamsAdapter = new GroupTeamsAdapter(getActivity(), arrayList);
        recyclerView.setAdapter(groupTeamsAdapter);
        stimulate = view.findViewById(R.id.stimulate);
    }

    private void onClick() {
        stimulate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupWinLoss();
                mainLayout.setVisibility(View.GONE);
                animation.setVisibility(View.VISIBLE);
                animation.playAnimation();
                Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Navigation.findNavController(view).navigate(R.id.action_groupTeamsFragment_to_semiFinalTeamsFragment);
                    }
                }, 2000);
            }
        });
    }

    private void setupWinLoss() {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                for (GroupTeamModel groupTeamModel : arrayList) {
                    DataTable firstTeam = groupTeamModel.getFirstTeam();
                    DataTable secondTeam = groupTeamModel.getSecondTeam();
                    Random r = new Random();
                    int i1 = r.nextInt(3 - 1) + 1; //generation random position for win and loss
                    if (i1 == 1) { // i1 = 1 mean first team win else second team
                        firstTeam.setTeamStatus("Semi Final");
                        secondTeam.setTeamStatus("Loss");
                    } else {
                        secondTeam.setTeamStatus("Semi Final");
                        firstTeam.setTeamStatus("Loss");
                    }
                    DataBaseClient.getInstance(getActivity())
                            .getAppDatabase().dataDao().update(firstTeam);
                    DataBaseClient.getInstance(getActivity())
                            .getAppDatabase().dataDao().update(secondTeam);
                }
            }
        });



    }

    private void setupList() {
        arrayList.clear();
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                List<DataTable> dataTables = DataBaseClient.getInstance(getActivity())
                        .getAppDatabase().dataDao().getAll();
                Collections.shuffle(dataTables);
                for (int i = 0; i < dataTables.size(); i += 2) {
                    DataTable dataTable1 = dataTables.get(i);
                    DataTable dataTable2 = dataTables.get(i + 1);
                    GroupTeamModel groupTeamModel = new GroupTeamModel();
                    groupTeamModel.setFirstTeam(dataTable1);
                    groupTeamModel.setSecondTeam(dataTable2);
                    arrayList.add(groupTeamModel);
                }

                new Handler(Looper.getMainLooper()).post(() -> {
                    groupTeamsAdapter.notifyDataSetChanged();
                });
            }
        });

    }
}