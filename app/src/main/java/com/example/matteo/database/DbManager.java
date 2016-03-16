package com.example.matteo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * Created by matteo on 16/03/2016.
 */
public abstract class DbManager {

    private DbHelper helper = null;
    public SQLiteDatabase db = null;

    public DbManager(Context context) {
        helper = new DbHelper(context);
    }

    public void openWrite(){
        db = helper.getWritableDatabase();
    }

    public void openRead(){
        db = helper.getReadableDatabase();
    }

    public void close(){
        db.close();
    }

    //crud
    public abstract Object getSingleObject(int id);
    public abstract List<? extends Object> getAllObjects();
    public abstract boolean deleteObject(int id);
    public abstract boolean updateObject(int id, Object newObject);
    public abstract boolean insertObject(int id, Object object);


}
