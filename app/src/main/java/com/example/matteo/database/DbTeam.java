package com.example.matteo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.matteo.model.Team;
import com.example.matteo.utility.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matteo on 16/03/2016.
 */
public class DbTeam extends DbManager {

    public DbTeam(Context context) {
        super(context);
    }

    @Override
    public Team getSingleObject(int id) {
        openRead();
        Cursor cursor = db.query(Constants.TABLE_TEAM,
                new String[]{Constants.ID_TEAM,Constants.NAME_TEAM,Constants.POINTS_TEAM},
                Constants.ID_TEAM +"=?",
                new String[]{String.valueOf(id)},
                null,null,null);

        if(cursor != null)
            cursor.moveToFirst();

        close();

        return new Team(cursor.getString(1), cursor.getInt(2));
    }

    @Override
    public List<Team> getAllObjects() {
        List<Team> list = new ArrayList<>();
        openRead();

        Cursor cursor = db.query(Constants.TABLE_TEAM,
                null,null, null, null,null,null);

        if(cursor != null)
            cursor.moveToFirst();

        while (cursor.moveToNext()){
            Team team = new Team(cursor.getString(1), cursor.getInt(2));
            list.add(team);
        }

        close();
        return list;
    }

    @Override
    public boolean deleteObject(int id) {
        openWrite();
        int returnValue = db.delete(Constants.TABLE_TEAM, Constants.ID_TEAM + "=?",
                new String[]{String.valueOf(id)});
        close();

        return returnValue > 0;
    }

    @Override
    public boolean updateObject(int id, Object newObject) {
        Team newTeam = (Team) newObject;

        openWrite();

        ContentValues args = new ContentValues();
        args.put(Constants.NAME_TEAM, newTeam.getName() );
        args.put(Constants.POINTS_TEAM, newTeam.getPoints());

        // applico il metodo update
        int returnValue = db.update(Constants.TABLE_TEAM, args, Constants.ID_TEAM + "=?",
                new String[]{String.valueOf(id)});
        close();

        return returnValue > 0;
    }

    @Override
    public boolean insertObject(int id, Object object) {
        Team team = (Team) object;

        openWrite();

        ContentValues args = new ContentValues();
        args.put(Constants.NAME_TEAM, team.getName() );
        args.put(Constants.POINTS_TEAM, team.getPoints());

        // applico il metodo update
        long returnValue = db.insert(Constants.TABLE_TEAM, null, args);
        close();

        return returnValue != -1;
    }
}
