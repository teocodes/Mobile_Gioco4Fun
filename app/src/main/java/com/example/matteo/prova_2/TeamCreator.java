package com.example.matteo.prova_2;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.matteo.model.Team;
import com.example.matteo.utility.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by matteo on 07/03/2016.
 */
public class TeamCreator {

    List<Team> teamList = null;
    int teamsNumber = 0;
    Set<String> teamsName = null;
    int turnsNumber = 0;
    Team team = null;

    public TeamCreator(Context context) {
        teamList = new ArrayList<>();

        SharedPreferences preferences = context.getSharedPreferences(Constants.PREF_FILE, context.MODE_PRIVATE);
        teamsNumber = preferences.getInt("teamsNumber", 0);
        teamsName = preferences.getStringSet("teamsName", null);
        turnsNumber = preferences.getInt("turnsNumber", 0);

        if(teamsNumber != 0 && teamsName != null && turnsNumber != 0 && (teamsNumber == teamsName.size())){
            for(String name : teamsName){
                team = new Team(name, 0);
                teamList.add(team);
            }
        }
    }

    public List<Team> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<Team> teamList) {
        this.teamList = teamList;
    }
}
