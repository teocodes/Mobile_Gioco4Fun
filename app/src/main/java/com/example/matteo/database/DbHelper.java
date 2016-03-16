package com.example.matteo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.matteo.utility.Constants;

/**
 * Created by matteo on 16/03/2016.
 */
public class DbHelper extends SQLiteOpenHelper {



    public DbHelper(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_TEAM = "CREATE TABLE "+ Constants.TABLE_TEAM +" ("
                + Constants.ID_TEAM +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Constants.NAME_TEAM +" TEXT NOT NULL,"
                + Constants.POINTS_TEAM +" INTEGER DEFAULT 0"
                + ")";

        String CREATE_TABLE_CATEGORY = "CREATE TABLE "+ Constants.TABLE_CATEGORY +" ("
                + Constants.ID_CATEGORY +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Constants.NAME_CATEGORY +" TEXT NOT NULL"
                + ")";

        String CREATE_TABLE_QUESTION = "CREATE TABLE "+ Constants.TABLE_QUESTION +" ("
                + Constants.ID_QUESTION +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Constants.CONTENT_QUESTION +" TEXT NOT NULL,"
                + Constants.ID_CATEGORY_QUESTION  +" INTEGER,"
                + "FOREIGN KEY (" + Constants.ID_CATEGORY_QUESTION
                    + " REFERENCES " + Constants.TABLE_CATEGORY + "(" + Constants.ID_CATEGORY + ")"
                + ")";

        db.execSQL(CREATE_TABLE_TEAM);
        db.execSQL(CREATE_TABLE_CATEGORY);
        db.execSQL(CREATE_TABLE_QUESTION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String DROP_TABLE_TEAM = "DROP TABLE IF EXISTS "+ Constants.TABLE_TEAM;
        String DROP_TABLE_CATEGORY = "DROP TABLE IF EXISTS "+ Constants.TABLE_CATEGORY;
        String DROP_TABLE_QUESTION = "DROP TABLE IF EXISTS "+ Constants.TABLE_QUESTION;

        db.execSQL(DROP_TABLE_TEAM);
        db.execSQL(DROP_TABLE_CATEGORY);
        db.execSQL(DROP_TABLE_QUESTION);
    }
}
