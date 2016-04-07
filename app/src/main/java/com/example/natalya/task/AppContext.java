package com.example.natalya.task;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.sql.SQLException;

import db.DBSource;

public class AppContext  extends Application{
    private static Context context;

    private db.DBSource ds;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
    public static Context getContext(){
        return context;
    }
    public DBSource getDataSource() {
        if(ds == null) {
            ds = new db.DBSource(this);
            try {
                ds.open();
            } catch (SQLException e) {
                Log.e("freestyle", "error during creating database");
                e.printStackTrace();
            }
        }
        return ds;
    }
}
