package com.example.matteo.utility;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.matteo.prova_2.R;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by matteo on 16/03/2016.
 */
public class Letters {

    LinearLayout alphabet_picture;        //reference to dice picture
    Random rng = new Random();    //generate random numbers
    SoundPool shake_sound = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
    int sound_id;        //Used to control sound stream return by SoundPool
    Handler handler;    //Post message to start roll
    Timer timer = new Timer();    //Used to implement feedback to user
    boolean rolling = false;        //Is die rolling?
    Context context;
    TextView letter;
    public Letters(Context context, LinearLayout alphabet_picture) {
        sound_id = shake_sound.load(context, R.raw.shake_letters, 1);
        this.alphabet_picture = alphabet_picture;
        handler = new Handler(callback);
        this.context = context;
        this.letter = (TextView) alphabet_picture.findViewById(R.id.letters);

        this.alphabet_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClick(v);
            }
        });

    }

    //User pressed dice, lets start
    public void handleClick(View arg0) {
        if (!rolling) {
            rolling = true;
            //Show rolling image
            alphabet_picture.setBackgroundResource(R.drawable.letters);
            letter.setVisibility(View.INVISIBLE);
            //Start rolling sound
            shake_sound.play(sound_id, 1.0f, 1.0f, 0, 0, 1.0f);
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
            alphabet_picture.setBackgroundResource(R.drawable.letter_border);
            List<String> alphabetList = Arrays.asList(context.getResources().getStringArray(R.array.alphabet_array));

            letter.setText(alphabetList.get(rng.nextInt(26)));

            letter.setVisibility(View.VISIBLE);
            rolling = false;    //user can press again
            return true;
        }
    };
}
