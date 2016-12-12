package com.thetechaupair.rheedplayer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import java.io.File;
import java.util.ArrayList;

public class Player extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    int position;

    Uri uri;

    MediaPlayer mp;
    ArrayList<File> mySongs;

    SeekBar seekBar;
    Button previousTrack;
    Button playTrack;
    Button nextTrack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        previousTrack = (Button) findViewById(R.id.previousTrack);
        playTrack = (Button) findViewById(R.id.playTrack);
        nextTrack = (Button) findViewById(R.id.nextTrack);

        previousTrack.setOnClickListener(this);
        playTrack.setOnClickListener(this);
        nextTrack.setOnClickListener(this);

        previousTrack.setOnLongClickListener(this);
        nextTrack.setOnLongClickListener(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mySongs = (ArrayList) bundle.getParcelableArrayList("songslist");
        position = bundle.getInt("pos", 0);

        uri = Uri.parse(mySongs.get(position).toString());
        mp = MediaPlayer.create(getApplicationContext(), uri);
        mp.start();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.previousTrack:
                mp.stop();
                mp.release();
                position = (position - 1 < 0) ? mySongs.size() : position - 1;
                uri = Uri.parse(mySongs.get(position).toString());
                mp = MediaPlayer.create(getApplicationContext(), uri);
                mp.start();
                break;
            case R.id.playTrack:
                if (mp.isPlaying()) {
                    playTrack.setText("Play");
                    mp.pause();
                } else {
                    playTrack.setText("Pause");
                    mp.start();
                }
                break;
            case R.id.nextTrack:
                mp.stop();
                mp.release();
                position = (position + 1) % mySongs.size();
                uri = Uri.parse(mySongs.get(position).toString());
                mp = MediaPlayer.create(getApplicationContext(), uri);
                mp.start();
                break;
        }
    }

    @Override
    public boolean onLongClick(View view) {
        switch (view.getId()) {
            case R.id.previousTrack:
                mp.seekTo(mp.getCurrentPosition() + 5000);
                break;
            case R.id.nextTrack:
                mp.seekTo(mp.getCurrentPosition() - 5000);
                break;
        }
        return false;
    }
}
