package com.example.matteo.prova_2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.matteo.utility.Constants;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by matteo on 09/03/2016.
 */
public class SettingsActivity extends Activity {
    Spinner spinner = null;
    int teamsNumber = 0;
    int turnsNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_settings);

        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.insert_team_area);

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.number_teams_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                linearLayout.removeAllViews();
                teamsNumber = Integer.valueOf(spinner.getSelectedItem().toString());

                for(int i=0;i< teamsNumber;i++){
                    EditText editText = new EditText(SettingsActivity.this);
                    editText.setId(i);
                    editText.setPadding(0, 0, 0, 5);
                    editText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT));
                    linearLayout.addView(editText);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        Button btnConfirm = (Button)findViewById(R.id.btn_confirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Set<String> names = new HashSet<String>();
                for(int i=0;i< teamsNumber;i++){
                    EditText et = (EditText) findViewById(i);

                    if(et != null){
                        if(et.getText().toString() != null && et.getText().toString().compareTo("") != 0)
                            names.add(et.getText().toString());
                        else
                            Toast.makeText(SettingsActivity.this,"Campo "+ (i+1) +" vuoto!\nInserire nome squadra", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Log.i("noeditText","no edit text "+ i);
                    }
                }

                EditText etTurns = (EditText) findViewById(R.id.number_of_turns);

                if(etTurns.getText().toString() != null && etTurns.getText().toString().compareTo("") != 0) {
                    if (Integer.valueOf(etTurns.getText().toString()) > 0)
                        turnsNumber = Integer.valueOf(etTurns.getText().toString());
                    else
                        Toast.makeText(SettingsActivity.this, "Campo turno vuoto!\nInserire numero di turni", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(SettingsActivity.this, "Campo turno vuoto!\nInserire numero di turni", Toast.LENGTH_SHORT).show();


                if(names.size() == teamsNumber && turnsNumber != 0) {

                    SharedPreferences preferences = getSharedPreferences(Constants.PREF_FILE, MODE_PRIVATE);
                    SharedPreferences.Editor edit = preferences.edit();

                    edit.putInt("teamsNumber", teamsNumber);
                    edit.putStringSet("teamsName", names);
                    edit.putInt("turnsNumber", turnsNumber);
                    edit.commit();

                    Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
