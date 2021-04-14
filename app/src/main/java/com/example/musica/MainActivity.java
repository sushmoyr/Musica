package com.example.musica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton playButton;
    private FloatingActionButton pauseButton;
    private FloatingActionButton stopButton;
    private ImageView coverArt;
    private TextView playerState;
    private MediaPlayer mediaPlayer;
    private int audioPosition = 0;
    private Animation coverAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        //Assigning buttons to variables
        playButton = findViewById(R.id.playButton);
        pauseButton = findViewById(R.id.pauseButton);
        stopButton = findViewById(R.id.stopButton);
        playerState = findViewById(R.id.player_state);
        coverArt = findViewById(R.id.coverArt);

        setupAnimation();

        //Setting up on click listeners
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playMusic();
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopMusic();
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseMusic();
            }
        });
    }

    private void setupAnimation() {
        coverAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.album_animation);
    }

    private void playMusic(){
        if(mediaPlayer==null)
        {
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sunflower);
            mediaPlayer.start();
            playerState.setText("Playing Music");
            coverArt.startAnimation(coverAnimation);
        }
        else if(!mediaPlayer.isPlaying())
        {
            mediaPlayer.seekTo(audioPosition);
            mediaPlayer.start();
            playerState.setText("Playing Music");
            coverArt.startAnimation(coverAnimation);
        }
    }

    private void stopMusic()
    {
        if(mediaPlayer!=null)
        {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            playerState.setText("Not Playing");
            coverArt.clearAnimation();
        }
    }

    private void pauseMusic()
    {
        if(mediaPlayer!=null)
        {
            mediaPlayer.pause();
            audioPosition = mediaPlayer.getCurrentPosition();
            playerState.setText("Player Paused");
            coverArt.clearAnimation();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mediaPlayer!=null)
        {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

}