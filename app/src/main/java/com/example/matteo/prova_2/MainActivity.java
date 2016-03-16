package com.example.matteo.prova_2;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;

import com.example.matteo.adapter.TeamAdapter;
import com.example.matteo.file.FileOperation;
import com.example.matteo.utility.Dice;
import com.example.matteo.utility.Letters;
import com.example.matteo.utility.TimerView;
import com.example.matteo.utility.Words;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TeamAdapter teamAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        FakeTeam fakeTeam = new FakeTeam();
        teamAdapter = new TeamAdapter(fakeTeam.getFakeList());

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


        //timer
        FloatingActionButton timer = (FloatingActionButton) findViewById(R.id.fab_timer);
        timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog d=new Dialog(MainActivity.this);
                d.setTitle("Set minutes");
                d.setCancelable(true);
                d.setContentView(R.layout.layout_timer);

                final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker1);
                np.setMaxValue(100);
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
                        d2.setTitle("Timer");
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
                                btnStart.setText("Restart");
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
        FloatingActionButton dice = (FloatingActionButton) findViewById(R.id.fab_dice);
        dice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Dialog d=new Dialog(MainActivity.this);
                d.setTitle("Roll dice");
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

        //carico words file nel db
        FileOperation fo = new FileOperation();
        final List<String> listWords = fo.readFile(MainActivity.this, R.raw.list_category_fantasy);

        Log.i("STAMP", "arriva");
        for(int i=0;i<listWords.size();i++) {
            Log.i("LIST", listWords.get(i));
        }

        FloatingActionButton words = (FloatingActionButton) findViewById(R.id.fab_words);
        words.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog d=new Dialog(MainActivity.this);
                d.setTitle("Choose word");
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

        FloatingActionButton letters = (FloatingActionButton) findViewById(R.id.fab_letter);
        letters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog d = new Dialog(MainActivity.this);
                d.setTitle("Choose letter");
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


    }
}
