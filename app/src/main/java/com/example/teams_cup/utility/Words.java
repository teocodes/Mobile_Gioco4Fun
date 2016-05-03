package com.example.teams_cup.utility;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.teams_cup.activity.R;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by teams_cup on 15/03/2016.
 */
public class Words {

    LinearLayout book_picture;        //reference to dice picture
    Random rng = new Random();    //generate random numbers
    SoundPool book_sound = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
    int sound_id;        //Used to control sound stream return by SoundPool
    Handler handler;    //Post message to start roll
    Timer timer = new Timer();    //Used to implement feedback to user
    boolean rolling = false;        //Is die rolling?
    Context context;
    TextView word;
    List<String> list = null;

    public Words(Context context, LinearLayout book_picture, List<String> list) {
        sound_id = book_sound.load(context, R.raw.page_flip, 1);
        this.book_picture = book_picture;
        handler = new Handler(callback);
        this.context = context;
        this.word = (TextView)book_picture.findViewById(R.id.words);
        this.book_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClick(v);
            }
        });
        this.list = list;

    }

    //User pressed dice, lets start
    public void handleClick(View arg0) {
        if (!rolling) {
            rolling = true;
            //Show rolling image
            book_picture.setBackgroundResource(R.drawable.book_not_stretch);
            word.setVisibility(View.INVISIBLE);
            //Start rolling sound
            book_sound.play(sound_id, 1.0f, 1.0f, 0, 0, 1.0f);
            //Pause to allow image to update
            timer.schedule(new Roll(), 400);
        }
    }

    //When pause completed message sent to callback
    class Roll extends TimerTask {
        public void run() {
            handler.sendEmptyMessage(0);
        }
    }

    //Receives message from timer to start dice roll
    Handler.Callback callback = new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            book_picture.setBackgroundResource(R.drawable.book_open);
            word.setText(list.get(rng.nextInt(list.size())) );
            word.setVisibility(View.VISIBLE);
            rolling = false;    //user can press again
            return true;
        }
    };
}

