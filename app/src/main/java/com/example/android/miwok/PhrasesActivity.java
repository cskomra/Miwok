package com.example.android.miwok;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class PhrasesActivity extends AppCompatActivity {

    private ListView listView;
    private WordAdapter adapter;
    private MediaPlayer mp;

    /**
     * This listener gets triggered when the {@Link MediaPlayer} has completed
     * playing this audio file.
     */
    private MediaPlayer.OnCompletionListener completionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phrases);

        listView = (ListView) findViewById(R.id.phrases_list);
        final ArrayList<Word> words = new ArrayList<Word>();

        words.add(new Word("Where are you going?", "minto wuksus", R.raw.phrase_where_are_you_going));
        words.add(new Word("What is your name?", "tinnә oyaase'nә", R.raw.phrase_what_is_your_name));
        words.add(new Word("My name is...", "oyaaset...", R.raw.phrase_my_name_is));
        words.add(new Word("How are you feeling?", "michәksәs?", R.raw.phrase_how_are_you_feeling));
        words.add(new Word("I'm feeling good.", "kuchi achit", R.raw.phrase_im_feeling_good));
        words.add(new Word("Are you coming?", "әәnәs'aa?", R.raw.phrase_are_you_coming));
        words.add(new Word("Yes. I'm coming.", "hәә’ әәnәm", R.raw.phrase_yes_im_coming));
        words.add(new Word("I'm coming.", "әәnәm", R.raw.phrase_im_coming));
        words.add(new Word("Let's go.", "yoowutis", R.raw.phrase_lets_go));
        words.add(new Word("Come here.", "әnni'nem", R.raw.phrase_come_here));

        adapter = new WordAdapter(this, words, R.color.category_phrases);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the {@link Word} object at the given position the user clicked on
                Word word = words.get(position);

                Log.v("PhrasesActivity", "Current word: " + word);

                /*
                 * Release the {@link MediaPlayer} object if it currently exists
                 * because we are about to play a different sound file.
                 */
                releaseMediaPlayer();

                /* Create and setup the {@link MediaPlayer} for the audio resource
                 * associated with the current word
                 */
                mp = MediaPlayer.create(PhrasesActivity.this, word.getRawResourceID());

                // Start the audio file
                mp.start();
                // Release resources when done
                mp.setOnCompletionListener(completionListener);
            }
        });
    }
    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mp != null) {
            /**
             * Regardless of the current state of the media player, release its resources
             * because we no longer need it.
             */
            mp.release();

            /*
             * Set the media player back to null. For our code, we've decided that
             * setting the media player to null is an easy way to tell that the media player
             * is not configured to play an audio file at the moment.
             */
            mp = null;
        }
    }
}
