package utility;


import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import com.example.matteo.prova_2.R;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Dice{
    ImageView dice_picture;        //reference to dice picture
    Random rng = new Random();    //generate random numbers
    SoundPool dice_sound = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
    int sound_id;        //Used to control sound stream return by SoundPool
    Handler handler;    //Post message to start roll
    Timer timer = new Timer();    //Used to implement feedback to user
    boolean rolling = false;        //Is die rolling?

    public Dice(Context context, ImageView dice_picture) {
        sound_id = dice_sound.load(context, R.raw.shake_dice, 1);
        this.dice_picture = dice_picture;
        handler = new Handler(callback);

        this.dice_picture.setOnClickListener(new View.OnClickListener() {
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
            dice_picture.setImageResource(R.drawable.dice3droll);
            //Start rolling sound
            dice_sound.play(sound_id, 1.0f, 1.0f, 0, 0, 1.0f);
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
    Callback callback = new Callback() {
        public boolean handleMessage(Message msg) {
            //Get roll result
            //Remember nextInt returns 0 to 5 for argument of 6
            //hence + 1
            switch (rng.nextInt(6) + 1) {
                case 1:
                    dice_picture.setImageResource(R.drawable.one);
                    break;
                case 2:
                    dice_picture.setImageResource(R.drawable.two);
                    break;
                case 3:
                    dice_picture.setImageResource(R.drawable.three);
                    break;
                case 4:
                    dice_picture.setImageResource(R.drawable.four);
                    break;
                case 5:
                    dice_picture.setImageResource(R.drawable.five);
                    break;
                case 6:
                    dice_picture.setImageResource(R.drawable.six);
                    break;
                default:
            }
            rolling = false;    //user can press again
            return true;
        }
    };


//    //Clean up
//    @Override
//    protected void onPause() {
//        super.onPause();
//        dice_sound.pause(sound_id);
//    }
//
//    protected void onDestroy() {
//        super.onDestroy();
//        timer.cancel();
//    }
}