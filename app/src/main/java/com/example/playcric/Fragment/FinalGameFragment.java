package com.example.playcric.Fragment;

import android.os.Bundle;

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

import com.airbnb.lottie.LottieAnimationView;
import com.example.playcric.Adapter.FinalistAdapter;
import com.example.playcric.DataBase.DataBaseClient;
import com.example.playcric.DataBase.DataTable;
import com.example.playcric.Model.GroupTeamModel;
import com.example.playcric.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;

public class FinalGameFragment extends Fragment {

    Button thirdPositionButton,restartButton;
    RecyclerView finalRecyclerView;
    RelativeLayout mainLayout;
    FinalistAdapter finalistAdapter;
    ArrayList<DataTable> arrayList = new ArrayList<>();
    ArrayList<GroupTeamModel> thirdTeamArraylist = new ArrayList<>();
    LottieAnimationView animation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_final_game, container, false);
        init(view);
        onClick();
        setUpData();
        return view;
    }

    private void init(View view) {
        animation = view.findViewById(R.id.animation);
        mainLayout = view.findViewById(R.id.mainLayout);
        finalRecyclerView = view.findViewById(R.id.finalRecyclerView);
        restartButton = view.findViewById(R.id.restartButton);
        thirdPositionButton = view.findViewById(R.id.thirdPositionButton);

        finalRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        finalistAdapter = new FinalistAdapter(arrayList,getActivity());
        finalRecyclerView.setAdapter(finalistAdapter);
        animation.setAnimation("animation.json");
    }

    private void onClick() {
        thirdPositionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thirdPositionButton.setVisibility(View.GONE);
                mainLayout.setVisibility(View.GONE);
                animation.setVisibility(View.VISIBLE);
                animation.playAnimation();
                Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        calculateThirdTeam();
                        mainLayout.setVisibility(View.VISIBLE);
                        animation.setVisibility(View.GONE);
                    }
                }, 2000);

            }
        });

        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_finalGameFragment_to_teamsFragment);
            }
        });
    }

    private void calculateThirdTeam() {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                List<DataTable> dataTables = DataBaseClient.getInstance(getActivity())
                        .getAppDatabase().dataDao().getSemiFinalTeam("Semi Final");
                Collections.shuffle(dataTables);
                for (int i = 0; i < dataTables.size(); i += 2) {
                    DataTable dataTable1 = dataTables.get(i);
                    DataTable dataTable2 = dataTables.get(i + 1);
                    GroupTeamModel groupTeamModel = new GroupTeamModel();
                    groupTeamModel.setFirstTeam(dataTable1);
                    groupTeamModel.setSecondTeam(dataTable2);
                    thirdTeamArraylist.add(groupTeamModel);
                }

                for (GroupTeamModel groupTeamModel : thirdTeamArraylist) {
                    DataTable firstTeam = groupTeamModel.getFirstTeam();
                    DataTable secondTeam = groupTeamModel.getSecondTeam();
                    Random r = new Random();
                    int i1 = r.nextInt(3 - 1) + 1; //generation random position for win and loss
                    if (i1 == 1) { // i1 = 1 mean first team win else second team
                        firstTeam.setTeamStatus("Winner");
                        secondTeam.setTeamStatus("-");
                    } else {
                        secondTeam.setTeamStatus("Winner");
                        firstTeam.setTeamStatus("-");
                    }
                    DataBaseClient.getInstance(getActivity())
                            .getAppDatabase().dataDao().update(firstTeam);
                    DataBaseClient.getInstance(getActivity())
                            .getAppDatabase().dataDao().update(secondTeam);
                }

                arrayList.clear();
                List<DataTable> dataTables1 = DataBaseClient.getInstance(getActivity())
                        .getAppDatabase().dataDao().getSemiFinalTeam("Winner");
                arrayList.addAll(dataTables1);

                new Handler(Looper.getMainLooper()).post(() -> {
                    finalistAdapter.notifyDataSetChanged();
                });

            }
        });
    }

    private void setUpData() {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                List<DataTable> dataTables = DataBaseClient.getInstance(getActivity())
                        .getAppDatabase().dataDao().getSemiFinalTeam("Winner");
                arrayList.addAll(dataTables);

                new Handler(Looper.getMainLooper()).post(() -> {
                    finalistAdapter.notifyDataSetChanged();
                });

            }
        });
    }
}