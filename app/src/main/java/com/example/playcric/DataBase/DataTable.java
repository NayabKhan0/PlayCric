package com.example.playcric.DataBase;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DataTable {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "TeamName")
    private String TeamName;

    @ColumnInfo(name = "TeamStatus")
    private String TeamStatus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTeamName() {
        return TeamName;
    }

    public void setTeamName(String teamName) {
        TeamName = teamName;
    }

    public String getTeamStatus() {
        return TeamStatus;
    }

    public void setTeamStatus(String teamStatus) {
        TeamStatus = teamStatus;
    }
}
