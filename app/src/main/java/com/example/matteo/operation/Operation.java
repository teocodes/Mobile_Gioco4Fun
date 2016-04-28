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

    private static Operation operation = null;
    private int turn = 1;
    private int teamTurn = 0;

    //1-squadra,
    //2-turno,
    //3-categoria + istruzione fissa + istruzione differente(se prevista)
    private static List<String> valueTodisplay = null;

    private List<String> listCategory = null;
    private List<String> listCategoryDescription = null;

    private List<String> listLetters = null;
    private List<String> listTeamPlay = null;

    private FileOperation fo = null;
    private Random rnd = null;

    private static Set<String> setTeam = null;
    private static List<String> listTeam = null;
    private static int maxTurn = 0;

    private static SharedPreferences preferences;

    private Operation(Context context) {

        fo = new FileOperation();

        listCategory = Arrays.asList(context.getResources().getStringArray(R.array.category_array));
        listCategoryDescription = Arrays.asList(context.getResources().getStringArray(R.array.category_description_array));

        listLetters = fo.readFile(context, R.raw.list_category_letters);
        listTeamPlay = fo.readFile(context, R.raw.list_category_team_play);

        rnd = new Random();

        preferences = context.getSharedPreferences(Constants.PREF_FILE, context.MODE_PRIVATE);
        setTeam = preferences.getStringSet("teamsName", null);
        listTeam = new ArrayList<>();

        for(String t : setTeam){
            listTeam.add(t);
        }

        maxTurn = preferences.getInt("turnsNumber", 0);
        valueTodisplay = Arrays.asList(" ", " ", " ");

    }

    public static Operation getIstance(Context context){
        if(operation == null)
            operation = new Operation(context);

        preferences = context.getSharedPreferences(Constants.PREF_FILE, context.MODE_PRIVATE);
        setTeam = preferences.getStringSet("teamsName", null);
        listTeam = new ArrayList<>();

        for(String t : setTeam){
            listTeam.add(t);
        }

        maxTurn = preferences.getInt("turnsNumber", 0);
        valueTodisplay = Arrays.asList(" ", " ", " ");
        maxTurn = preferences.getInt("turnsNumber", 0);

        return operation;
    }

    public List<String> getTurnData(){

       // valueTodisplay.clear();

        //int turn = preferences.getInt("currentTurn", 1);

        //turn = 1 e teamTurn = 0

        //1-squadra
        if(turn <= maxTurn){
            valueTodisplay.set(1, String.valueOf(turn)); //turno
            valueTodisplay.set(2, areaData()); //stringa area

            if(teamTurn < listTeam.size()) {
                valueTodisplay.set(0, listTeam.get(teamTurn)); //squadra
                teamTurn++;

                if(teamTurn == listTeam.size()) {
                    teamTurn=0;
                    turn++;
                }
            }

        }
        else{
            valueTodisplay.set(0, ""); //squadra
            valueTodisplay.set(1, ""); //turno
            valueTodisplay.set(2, ""); //stringa area

            turn = 1;
            teamTurn = 0;
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
