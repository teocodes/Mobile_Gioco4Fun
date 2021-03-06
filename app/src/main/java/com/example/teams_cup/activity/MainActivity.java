package com.example.teams_cup.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.teams_cup.adapter.TeamAdapter;
import com.example.teams_cup.file.FileOperation;
import com.example.teams_cup.operation.Operation;
import com.example.teams_cup.utility.Dice;
import com.example.teams_cup.utility.Letters;
import com.example.teams_cup.utility.TimerView;
import com.example.teams_cup.utility.Words;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TeamAdapter teamAdapter = null;

    private Operation op = null;
    private List<String> data = null;
    private TextView teamName = null;
    private TextView currentTurn = null;
    private TextView textView = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        TeamCreator teamCreator = new TeamCreator(MainActivity.this);
        teamAdapter = new TeamAdapter(teamCreator.getTeamList());

        ListView listView = (ListView) findViewById(R.id.drawer_list);
        listView.setAdapter(teamAdapter);

        final ImageView btnDrawer = (ImageView) findViewById(R.id.btn_drawer);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        btnDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(drawer.isDrawerOpen(Gravity.LEFT))
                    drawer.closeDrawer(Gravity.LEFT);
                else
                    drawer.openDrawer(Gravity.LEFT);
            }
        });

        op = Operation.getIstance(MainActivity.this);
        data = op.getTurnData();

        teamName = (TextView) findViewById(R.id.current_team);
        teamName.setText(data.get(0));

        currentTurn = (TextView) findViewById(R.id.current_turn);
        currentTurn.setText(data.get(1));

        textView = (TextView) findViewById(R.id.textArea);
        textView.setText(data.get(2));

        //timer
        final FloatingActionButton timer = (FloatingActionButton) findViewById(R.id.fab_timer);
        timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog d=new Dialog(MainActivity.this);
                d.setTitle(R.string.set_minutes);
                d.setCancelable(true);
                d.setContentView(R.layout.layout_timer);

                final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker1);
                np.setMaxValue(59);
                np.setMinValue(1);
                np.setWrapSelectorWheel(false);

                d.show();

                final Button btnCancel = (Button) d.findViewById(R.id.btn_cancel_timer);
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.dismiss();
                    }
                });

                final Button btnConfirm = (Button) d.findViewById(R.id.btn_ok_timer);
                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog d2=new Dialog(MainActivity.this);
                        d2.setTitle(R.string.timer);
                        d2.setCancelable(true);
                        d2.setContentView(R.layout.layout_timer_action);

                        LinearLayout lt = (LinearLayout) d2.findViewById(R.id.space_for_timer);

                        final TimerView tv = new TimerView(MainActivity.this, np.getValue() * 60000);
//                        tv.setMaxTime(np.getValue() * 1000);
                        tv.setRadius(300);
                        lt.addView(tv);

                        final Button btnStart = (Button) d2.findViewById(R.id.btn_start);
                        btnStart.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                tv.start(true);
                                btnStart.setText(R.string.restart);
                            }
                        });

                        Button cancel = (Button) d2.findViewById(R.id.btn_cancel_timer_action);
                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                d2.dismiss();
                                d.dismiss();
                            }
                        });

                        d2.show();
                    }
                });

            }
        });


        //dice
        final FloatingActionButton dice = (FloatingActionButton) findViewById(R.id.fab_dice);
        dice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Dialog d=new Dialog(MainActivity.this);
                d.setTitle(R.string.dice);
                d.setCancelable(true);
                d.setContentView(R.layout.layout_dice);

//                LinearLayout lt = (LinearLayout)findViewById(R.id.content_dice);

                ImageView diceImage = (ImageView) d.findViewById(R.id.dice);
                new Dice(MainActivity.this, diceImage);

                Button cancel = (Button) d.findViewById(R.id.btn_cancel_dice);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.dismiss();
                    }
                });

                d.show();

            }
        });

        FileOperation fo = new FileOperation();
        final List<String> listWords = fo.readFile(MainActivity.this, R.raw.list_words);

        final FloatingActionButton words = (FloatingActionButton) findViewById(R.id.fab_words);
        words.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog d=new Dialog(MainActivity.this);
                d.setTitle(R.string.word);
                d.setCancelable(true);
                d.setContentView(R.layout.layout_words);

                LinearLayout linearLayout = (LinearLayout) d.findViewById(R.id.background_book);
                new Words(MainActivity.this,linearLayout,listWords);

                Button cancel = (Button) d.findViewById(R.id.btn_cancel_words);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.dismiss();
                    }
                });

                d.show();
            }
        });

        final FloatingActionButton letters = (FloatingActionButton) findViewById(R.id.fab_letter);
        letters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog d = new Dialog(MainActivity.this);
                d.setTitle(R.string.letter);
                d.setCancelable(true);
                d.setContentView(R.layout.layout_letters);

                LinearLayout linearLayout = (LinearLayout) d.findViewById(R.id.background_letter);
                new Letters(MainActivity.this, linearLayout);

                Button cancel = (Button) d.findViewById(R.id.btn_cancel_letters);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.dismiss();
                    }
                });

                d.show();
            }
        });

        final Button changeCard = (Button) findViewById(R.id.btn_change);
        changeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(op.areaData());
            }
        });

        final Button btnContinue = (Button) findViewById(R.id.btn_continue);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<String> list = op.getTurnData();
                teamName.setText(list.get(0));
                currentTurn.setText(list.get(1));
                textView.setText(list.get(2));

                if(list.get(0).compareTo("") == 0){
                    btnContinue.setEnabled(false);
                    changeCard.setEnabled(false);
                    letters.setEnabled(false);
                    words.setEnabled(false);
                    dice.setEnabled(false);
                    timer.setEnabled(false);
                    btnDrawer.setEnabled(false);

                    textView.setText(R.string.ranking);
                    textView.append("\n\n");

                    for(int i=0;i<teamAdapter.getCount();i++){
                        textView.append(teamAdapter.getItem(i).getName()+" - "+String.valueOf(teamAdapter.getItem(i).getPoints())+"\n" );
                    }

                    final Dialog d=new Dialog(MainActivity.this);
                    d.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                    d.setTitle(R.string.game_over);
                    d.setCancelable(true);
                    d.setContentView(R.layout.layout_winner);

                    TextView winnerTeam = (TextView) d.findViewById(R.id.winner_team);

                    if(teamAdapter.getItem(0).getPoints() == teamAdapter.getItem(1).getPoints())
                        winnerTeam.setText("Pareggio!");
                    else
                        winnerTeam.setText(teamAdapter.getItem(0).getName());

                    Button restart = (Button) d.findViewById(R.id.btn_restart_winner);
                    restart.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });

                    Button cancel = (Button) d.findViewById(R.id.btn_cancel_winner);
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            d.dismiss();
                        }
                    });

                    d.show();
                }


            }
        });


        final ImageView btnReturn = (ImageView) findViewById(R.id.btn_restart);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog d=new Dialog(MainActivity.this);
                d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                d.setTitle(R.string.restart);
                d.setCancelable(true);
                d.setContentView(R.layout.layout_restart);


                Button restart = (Button) d.findViewById(R.id.btn_restart_restart);
                restart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

                Button cancel = (Button) d.findViewById(R.id.btn_cancel_restart);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.dismiss();
                    }
                });

                d.show();
            }
        });

    }



}
