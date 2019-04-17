package com.example.android.japaneselanguageapp;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {

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

        /* the last parameter is switched by added audio files to raw folder and declaring*/
        words.add(new Word("One", "Ichi", R.drawable.japanese_flag, R.raw.outkast));
        words.add(new Word("Two", "Ni", R.drawable.japanese_flag, R.raw.outkast));
        words.add(new Word("Three", "San", R.drawable.japanese_flag, R.raw.outkast));
        words.add(new Word("Four", "Yon/Shi", R.drawable.japanese_flag, R.raw.outkast));
        words.add(new Word("Five", "Go", R.drawable.japanese_flag, R.raw.outkast));
        words.add(new Word("Six", "Roku", R.drawable.japanese_flag, R.raw.outkast));
        words.add(new Word("Seven", "Nana/Shichi",R.drawable.japanese_flag, R.raw.outkast));
        words.add(new Word("Eight", "Hachi", R.drawable.japanese_flag, R.raw.outkast));
        words.add(new Word("Nine", "Kyu", R.drawable.japanese_flag, R.raw.outkast));
        words.add(new Word("Ten", "Jyu", R.drawable.japanese_flag, R.raw.outkast));
        words.add(new Word("Eleven", "Jyu ichi", R.drawable.japanese_flag, R.raw.outkast));
        words.add(new Word("Twelve", "Jyu ni", R.drawable.japanese_flag, R.raw.outkast));
        words.add(new Word("Thirteen", "Jyu san", R.drawable.japanese_flag, R.raw.outkast));
        words.add(new Word("Fourteen", "Jyu yon", R.drawable.japanese_flag, R.raw.outkast));
        words.add(new Word("Fifteen", "Jyu go", R.drawable.japanese_flag, R.raw.outkast));
        words.add(new Word("Sixteen", "Jyu roku", R.drawable.japanese_flag, R.raw.outkast));
        words.add(new Word("Seventeen", "Jyu nana", R.drawable.japanese_flag, R.raw.outkast));
        words.add(new Word("Eighteen", "Jyu hachi", R.drawable.japanese_flag, R.raw.outkast));
        words.add(new Word("Nineteen", "Jyu kyu", R.drawable.japanese_flag, R.raw.outkast));
        words.add(new Word("Twenty", "Ni jyu", R.drawable.japanese_flag, R.raw.outkast));
        words.add(new Word("Thirty", "San jyu", R.drawable.japanese_flag, R.raw.outkast));
        words.add(new Word("Forty", "Yon jyu", R.drawable.japanese_flag, R.raw.outkast));
        words.add(new Word("Fifty", "Go jyu", R.drawable.japanese_flag, R.raw.outkast));
        words.add(new Word("Sixty", "Roku jyu", R.drawable.japanese_flag, R.raw.outkast));
        words.add(new Word("Seventy", "Nana jyu", R.drawable.japanese_flag, R.raw.outkast));
        words.add(new Word("Eighty", "Hachi jyu", R.drawable.japanese_flag, R.raw.outkast));
        words.add(new Word("Ninety", "Kyu jyu", R.drawable.japanese_flag, R.raw.outkast));
        words.add(new Word("One hundred", "Hyaku", R.drawable.japanese_flag, R.raw.outkast));
        words.add(new Word("One thousand", "Sen", R.drawable.japanese_flag, R.raw.outkast));
        words.add(new Word("Ten thousand", "Man", R.drawable.japanese_flag, R.raw.outkast));

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
                    mMediaPlayer = MediaPlayer.create(NumbersActivity.this, word.getAudioResourceId());

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