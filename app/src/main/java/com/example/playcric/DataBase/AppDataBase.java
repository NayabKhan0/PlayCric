package com.example.playcric.DataBase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {DataTable.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract DataDao dataDao();
}
