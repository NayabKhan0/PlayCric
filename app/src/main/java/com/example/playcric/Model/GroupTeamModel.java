package com.example.playcric.Model;

import com.example.playcric.DataBase.DataTable;

public class GroupTeamModel {
    DataTable firstTeam, secondTeam;

    public DataTable getFirstTeam() {
        return firstTeam;
    }

    public void setFirstTeam(DataTable firstTeam) {
        this.firstTeam = firstTeam;
    }

    public DataTable getSecondTeam() {
        return secondTeam;
    }

    public void setSecondTeam(DataTable secondTeam) {
        this.secondTeam = secondTeam;
    }
}
