package com.example.playcric.DataBase;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DataDao {

    @Query("SELECT * FROM datatable")
    List<DataTable> getAll();

    @Query("DELETE FROM datatable")
    void deleteAllData();

    @Query("SELECT * FROM datatable Where TeamStatus = :string ")
    List<DataTable> getSemiFinalTeam(String string);

    @Insert
    void insert(DataTable dataTable);

    @Delete
    void delete(DataTable dataTable);

    @Update
    void update(DataTable dataTable);

    @Query("UPDATE datatable SET TeamName = :replaceTeamName Where TeamName = :teamName")
    void updateTeamName(String teamName,String replaceTeamName);

}
