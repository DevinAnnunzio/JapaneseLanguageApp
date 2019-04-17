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

public class ColorsActivity extends AppCompatActivity {

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

        words.add(new Word("Orange", "Orenji", R.drawable.japanese_flag, R.raw.outkast));
        words.add(new Word("Yellow", "Ki-Iro", R.drawable.japanese_flag, R.raw.outkast));
        words.add(new Word("Blue", "Ao", R.drawable.japanese_flag, R.raw.outkast));
        words.add(new Word("Green", "Midori", R.drawable.japanese_flag, R.raw.outkast));
        words.add(new Word("Black", "Kuro", R.drawable.japanese_flag, R.raw.outkast));
        words.add(new Word("Brown", "Cha-Iro", R.drawable.japanese_flag, R.raw.outkast));
        words.add(new Word("Pink", "Pinku", R.drawable.japanese_flag, R.raw.outkast));
        words.add(new Word("Purple", "Murasaki", R.drawable.japanese_flag, R.raw.outkast));
        words.add(new Word("White", "Shiro", R.drawable.japanese_flag, R.raw.outkast));
        words.add(new Word("Gray", "Hai-Iro", R.drawable.japanese_flag, R.raw.outkast));
        words.add(new Word("Gold", "Kin-Iro", R.drawable.japanese_flag, R.raw.outkast));
        words.add(new Word("Silver", "Gin-Iro", R.drawable.japanese_flag, R.raw.outkast));
        words.add(new Word("Bronze", "Buronzu-Iro", R.drawable.japanese_flag, R.raw.outkast));
        words.add(new Word("Multi-Colored", "Tashokuno", R.drawable.japanese_flag, R.raw.outkast));
        words.add(new Word("Rainbow", "Niji", R.drawable.japanese_flag, R.raw.outkast));

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
                    mMediaPlayer = MediaPlayer.create(ColorsActivity.this, word.getAudioResourceId());

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