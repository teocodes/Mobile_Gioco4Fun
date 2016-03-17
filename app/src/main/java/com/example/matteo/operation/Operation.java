package com.example.matteo.operation;


import android.content.Context;
import android.content.SharedPreferences;

import com.example.matteo.file.FileOperation;
import com.example.matteo.prova_2.R;
import com.example.matteo.utility.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Created by matteo on 16/03/2016.
 */
public class Operation {

    //1-squadra,
    //2-turno,
    //3-categoria + istruzione fissa + istruzione differente(se prevista)
    private List<String> valueTodisplay = null;

    private List<String> listCategory = null;
    private List<String> listCategoryDescription = null;

    private List<String> listLetters = null;
    private List<String> listTeamPlay = null;

    private FileOperation fo = null;
    private Random rnd = null;

    private Set<String> listTeam = null;
    private int maxTurn = 0;

    private SharedPreferences preferences;

    public Operation(Context context) {
        fo = new FileOperation();

        listCategory = Arrays.asList(context.getResources().getStringArray(R.array.category_array));
        listCategoryDescription = Arrays.asList(context.getResources().getStringArray(R.array.category_description_array));

        listLetters = fo.readFile(context, R.raw.list_category_letters);
        listTeamPlay = fo.readFile(context, R.raw.list_category_team_play);

        preferences = context.getSharedPreferences(Constants.PREF_FILE, context.MODE_PRIVATE);
        listTeam = preferences.getStringSet("teamsName", null);
        maxTurn = preferences.getInt("turnsNumber", 0);

        SharedPreferences.Editor edit = preferences.edit();
        edit.putInt("currentTurn", 1);

        rnd = new Random();
        valueTodisplay = new ArrayList<>();
    }


    public List<String> getTurnData(){

        valueTodisplay.clear();
        int turn = preferences.getInt("currentTurn", 1);

        //1-squadra
        if(turn <= maxTurn){
            if(listTeam.iterator().hasNext())
                valueTodisplay.add(listTeam.iterator().next()); //squadra
            else {
                listTeam = preferences.getStringSet("teamsName", null);
                valueTodisplay.add(listTeam.iterator().next()); //squadra
            }
            valueTodisplay.add(String.valueOf(turn)); //turno
            valueTodisplay.add(areaData()); //stringa area
        }

        return valueTodisplay;
    }

    public String areaData(){
        int value = rnd.nextInt(3);
//        String category = listCategory.get(value);
        String description = listCategoryDescription.get(value);

        String instruction = null;
        if(value == 1)
            instruction = "";
        else if(value == 0)
            instruction = listLetters.get(rnd.nextInt(listLetters.size()));
        else
            instruction = listTeamPlay.get(rnd.nextInt(listTeamPlay.size()));

        return description+instruction;
    }

}
