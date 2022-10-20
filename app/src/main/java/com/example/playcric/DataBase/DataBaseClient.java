package com.example.playcric.DataBase;


import android.content.Context;

import androidx.room.Room;

public class DataBaseClient {

    private final Context mCtx;
    private static DataBaseClient mInstance;

    private final AppDataBase appDatabase;

    private DataBaseClient(Context mCtx) {
        this.mCtx = mCtx;
        appDatabase = Room.databaseBuilder(mCtx, AppDataBase.class, "MyToDos").build();
    }

    public static synchronized DataBaseClient getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new DataBaseClient(mCtx);
        }
        return mInstance;
    }

    public AppDataBase getAppDatabase() {
        return appDatabase;
    }
}
