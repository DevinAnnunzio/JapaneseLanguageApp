package com.example.android.japaneselanguageapp;
import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class PhrasesActivity extends AppCompatActivity {


    private MediaPlayer mMediaPlayer;



    /* Handles audio focus */
    private AudioManager mAudioManager;

    /* This listener gets triggered whenever the audio focus changes*/


    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                /* The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                 short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                 our app is allowed to continue playing sound but at a lower volume. */
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                releaseMediaPlayer();
            }
        }
    };

    /* This listener gets triggered when the media player has completed */
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.words_list);

        /* Create and setup the link AudioManager to request audio focus*/
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        /*Create an array list of words*/
        final ArrayList<Word> words = new ArrayList<Word>();

        words.add(new Word("Good morning", "Ohayou Gozaimasu", R.raw.outkast));
        words.add(new Word("Hello/Good afternoon", "Kon-nichiwa", R.raw.outkast));
        words.add(new Word("It's been a while since I've seen you", "Hisashiburi", R.raw.outkast));
        words.add(new Word("See you later", "Jaa mata", R.raw.outkast));
        words.add(new Word("What is your name?", "O namae wa nan desuka", R.raw.outkast));
        words.add(new Word("My name is ______", "Watashi no namae wa _____ desu", R.raw.outkast));
        words.add(new Word("I like it", "Suki desu", R.raw.outkast));
        words.add(new Word("It's good", "Ii desu yo", R.raw.outkast));
        words.add(new Word("It's not good", "Yoku nai", R.raw.outkast));
        words.add(new Word("Lets tak in Japanese", "Nihongo de hanashimashou", R.raw.outkast));
        words.add(new Word("Please say it again", "Mou ichido itte kudasai", R.raw.outkast));
        words.add(new Word("Thank you", "Arigatou gozaimasu", R.raw.outkast));
        words.add(new Word("You're welcome", "Douitashimashite", R.raw.outkast));
        words.add(new Word("No problem", "Mondai nai yo", R.raw.outkast));
        words.add(new Word("Please(requesting something)", "_____ o kudasai", R.raw.outkast));
        words.add(new Word("Offering (something)", "Douzo", R.raw.outkast));
        words.add(new Word("Thank you for your efforts", "Otsukaresama desu", R.raw.outkast));
        words.add(new Word("Sorry for my interruption(when entering someones home)", "Shitsurei shimasu", R.raw.outkast));
        words.add(new Word("Excuse me", "Sumimasen", R.raw.outkast));
        words.add(new Word("I'm sorry", "Gomenasai", R.raw.outkast));
        words.add(new Word("I'm hungry", "Onaka ga suite imasen", R.raw.outkast));
        words.add(new Word("I haven't eaten yet", "Mada tebete imasen", R.raw.outkast));
        words.add(new Word("Please bring me a menu", "Menyuu onegaishimasu", R.raw.outkast));
        words.add(new Word("What is this?", "Kore wa nan desuka", R.raw.outkast));
        words.add(new Word("What is that?", "Sore wa nan desuka", R.raw.outkast));
        words.add(new Word("I'd like to try this", "Kore o tabete mitai desu", R.raw.outkast));
        words.add(new Word("Do you have _____?", "______ ga arimasu ka", R.raw.outkast));
        words.add(new Word("Does it come with ________?", "_______ tsuki desuka", R.raw.outkast));
        words.add(new Word("I can't eat _______", "______ ga taberaremasen", R.raw.outkast));
        words.add(new Word("I'm allergic to ________", "_______ arerugii ga arimasu", R.raw.outkast));
        words.add(new Word("It's delicious", "Oishii desu", R.raw.outkast));
        words.add(new Word("It's terrible", "Mazui desu", R.raw.outkast));
        words.add(new Word("Im' full", "Onaka ga ippai desu", R.raw.outkast));
        words.add(new Word("Check please", "Okaikei onegaishimasu", R.raw.outkast));
        words.add(new Word("Let's eat(right before eating)", "Itadakimasu", R.raw.outkast));
        words.add(new Word("Thanks for the meal", "Gochisousama deshita", R.raw.outkast));
        words.add(new Word("Welcome (said by workers)", "Irasshaimase", R.raw.outkast));
        words.add(new Word("How much is this?", "Kore wa ikura desuka", R.raw.outkast));
        words.add(new Word("Can I use my credit card?", "Kurejitto kaado wa tsukaemasuka", R.raw.outkast));



        /* Create an link word adapter, whose data source is a list of words*/
        WordAdapter adapter = new WordAdapter(this, words);

        /* Finds the link list view object in the view */
        ListView listView = (ListView) findViewById(R.id.list);

        /*Make the link list view using link word adapter*/
        listView.setAdapter(adapter);

        /* Set a click listener to play the audio when the list item is clicked on*/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                /* Release the media player if it exists*/
                releaseMediaPlayer();

                /*Get the  words object at the given position the user clicked on*/
                Word word = words.get(position);

                /* Request audio focus to play the audio file*/
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    /*Create and setup the link media player for the audio resource associated with the current words*/
                    mMediaPlayer = MediaPlayer.create(PhrasesActivity.this, word.getAudioResourceId());

                    mMediaPlayer.start();

                    /*Setup a listener on the media player so we can release when finished*/
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        /* When the activity is finished, release the media player*/
        releaseMediaPlayer();
    }

    /*Cleans media player by releasing resources*/
    private void releaseMediaPlayer() {
        /*If the media player is not null, then it may be currently playing a sound.*/
        if (mMediaPlayer != null) {
            /*release its resources*/
            mMediaPlayer.release();

            /*Set the media player back to null*/
            mMediaPlayer = null;

            /* unregisters AudioFocusChangeListener so we don't get anymore callbacks.*/
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}