package com.yplay;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.yplay.modules.search.SearchActivity;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.Locale;

public class PlayerActivity extends AppCompatActivity {

    static final int RECEIVE_AUDIO_URL = 1;  // The request code

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(PlayerActivity.this, SearchActivity.class), RECEIVE_AUDIO_URL);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            String audioId = data.getExtras().getStringArray("audio")[0]; // TODO: check for NullPointerException
            String audioTitle = data.getExtras().getStringArray("audio")[1]; // TODO: check for NullPointerException
            String android_id = data.getExtras().getStringArray("audio")[2]; // TODO: check for NullPointerException

            TextView audioTitleTextView = (TextView) findViewById(R.id.audio_title_textView);
            audioTitleTextView.setText(audioTitle);

            final MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                mediaPlayer.setDataSource("http://82.196.0.94/audio/" + android_id + "/" + audioId + ".mp3");
                mediaPlayer.prepare();
            } catch (IOException e) {
                System.err.println("Something went wrong: " + e.getMessage());
                e.printStackTrace();
            }
            mediaPlayer.start();

            // update the play button
            final ImageButton playImageButton = (ImageButton) findViewById(R.id.play_imageButton);
            playImageButton.setImageResource(R.drawable.pause_button);
            playImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                        playImageButton.setImageResource(R.drawable.play_button);
                    } else {
                        mediaPlayer.start();
                        playImageButton.setImageResource(R.drawable.pause_button);
                    }
                }
            });

            // update the duration TextView
            TextView durationTextView = (TextView) findViewById(R.id.player_duration_textview);
            int duration = mediaPlayer.getDuration();
            durationTextView.setText(Integer.toString(duration / 60000) + ":" + Integer.toString(duration % 60000));

            // update the progressbar (seekbar)
            final SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
            seekBar.setMax(mediaPlayer.getDuration());

            final Handler handler = new Handler();
            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    handler.postDelayed(this, 1000);
                }
            });

            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        mediaPlayer.seekTo(progress);
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

           /* final Button playButton = (Button) findViewById(R.id.play_Button);
            playButton.setBackgroundResource(R.drawable.pause_button);
            playButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                        playButton.setBackgroundResource(R.drawable.play_button);
                    } else {
                        mediaPlayer.start();
                        playButton.setBackgroundResource(R.drawable.pause_button);
                    }
                }
            });*/
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
