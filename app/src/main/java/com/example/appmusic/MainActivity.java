package com.example.appmusic;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int position, countTypePlay = 0;
    TextView txtTitle, txtTimeSong, txtTimeTotal;
    SeekBar seekbarSong;
    ImageButton btnPre, btnNext, btnPlayPause, btnPre10, btnNext10, btnTypePlay;
    ImageView imgAvatarSong, btnChangeLayout;

    ArrayList<Song> arraySong;
    MediaPlayer mediaPlayer;

    SimpleDateFormat formatTime = new SimpleDateFormat("mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoadItemLayout();
        AddSong();
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            Song song = (Song) bundle.get("object song");
            position = song.getIDSong();
            countTypePlay = 0;

            CreateMediaPlayer();
            StartSong();
            LoadAnimation();
        }
        else{
            position = 0;
            CreateMediaPlayer();
            LoadTimeTotalSong();
        }

        btnTypePlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(countTypePlay == 0){
                    countTypePlay = 1;
                    btnTypePlay.setImageResource(R.drawable.icon_repeat);
                    position = position;
                }
                else{
                    if(mediaPlayer.isPlaying()){
                        mediaPlayer.pause();
                    }
                    if(countTypePlay == 1){
                        countTypePlay = 2;
                        btnTypePlay.setImageResource(R.drawable.icon_random);
                    }
                    else{
                        countTypePlay = 0;
                        btnTypePlay.setImageResource(R.drawable.icon_loop);
                    }
                }
            }
        });

        btnChangeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,MainListSong.class);
                startActivity(intent);
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
            }
        });

        btnPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    imgAvatarSong.animate().cancel();
                    btnPlayPause.setImageResource(R.drawable.icon_play);
                }
                else{
                    mediaPlayer.start();
                    LoadAnimation();
                    btnPlayPause.setImageResource(R.drawable.icon_pause);
                }
                LoadTimeTotalSong();
                UpdateTimeSong();
            }
        });

        btnPre10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seekbarSong.setProgress(mediaPlayer.getCurrentPosition() - 10000);

                if(mediaPlayer.getCurrentPosition() <= 10000){
                    seekbarSong.setProgress(0);
                }
                mediaPlayer.seekTo(seekbarSong.getProgress());
                txtTimeSong.setText(formatTime.format(seekbarSong.getProgress()));
            }
        });

        btnNext10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seekbarSong.setProgress(mediaPlayer.getCurrentPosition() + 10000);

                if(mediaPlayer.getCurrentPosition() == mediaPlayer.getDuration() - 9000){
                    seekbarSong.setProgress(mediaPlayer.getDuration());
                }

                mediaPlayer.seekTo(seekbarSong.getProgress());
                txtTimeSong.setText(formatTime.format(seekbarSong.getProgress()));
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                if(countTypePlay == 0){
                    position++;
                    if(position > arraySong.size() - 1){
                        position = 0;
                    }
                }
                else if(countTypePlay == 1){
                    position += 0;
                }
                else{
                    Random rd = new Random();
                    position = rd.nextInt((7-2+1)+2);
                }
                CreateMediaPlayer();
                StartSong();
                LoadAnimation();
            }
        });

        btnPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                if(countTypePlay == 0){
                    position--;
                    if(position < 0){
                        position = arraySong.size() - 1;
                    }
                }
                else if(countTypePlay == 1){
                    position += 0;
                }
                else{
                    Random rd = new Random();
                    position = rd.nextInt((7-2+1)+2);
                }
                CreateMediaPlayer();
                StartSong();
                LoadAnimation();
            }
        });

        seekbarSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekbarSong.getProgress());
                txtTimeSong.setText(formatTime.format(seekbarSong.getProgress()));
            }
        });
    }

    private void StartSong(){
        mediaPlayer.start();
        btnPlayPause.setImageResource(R.drawable.icon_pause);
        LoadTimeTotalSong();
        UpdateTimeSong();
    }

    private void LoadAnimation() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                imgAvatarSong.animate().rotationBy(360).withEndAction(this).setDuration(9000).setInterpolator(new LinearInterpolator()).start();
            }
        };
        imgAvatarSong.animate().rotationBy(360).withEndAction(runnable).setDuration(9000).setInterpolator(new LinearInterpolator()).start();
    }

    private void UpdateTimeSong(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                txtTimeSong.setText(formatTime.format(mediaPlayer.getCurrentPosition()));
                seekbarSong.setProgress(mediaPlayer.getCurrentPosition());
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaP) {
                        if(mediaPlayer.isPlaying()){
                            mediaPlayer.stop();
                        }
                        if(countTypePlay == 0){
                            position++;
                            if(position > arraySong.size() - 1){
                                position = 0;
                            }
                        }
                        else if(countTypePlay == 1){
                            position += 0;
                        }
                        else{
                            Random rd = new Random();
                            position = rd.nextInt((7-2+1)+2);
                        }
                        CreateMediaPlayer();
                        StartSong();
                        LoadAnimation();
                    }
                });
                handler.postDelayed(this,500);
            }
        },100);
    }

    private void LoadTimeTotalSong() {
        txtTimeTotal.setText(formatTime.format(mediaPlayer.getDuration()));
        seekbarSong.setProgress(0);
        txtTimeSong.setText(formatTime.format(seekbarSong.getProgress()));
        seekbarSong.setMax(mediaPlayer.getDuration());
    }

    private void CreateMediaPlayer(){
        mediaPlayer = MediaPlayer.create(MainActivity.this, arraySong.get(position).getFileMusic());
        imgAvatarSong.setImageResource(arraySong.get(position).getFileAvatar());
        txtTitle.setText(arraySong.get(position).getTitle() + " - " + arraySong.get(position).getAuthor());
    }

    private void AddSong() {
        arraySong = new ArrayList<>();
        arraySong.add(new Song("Lạ lùng","Vũ", R.raw.la_lung,R.drawable.song6,0));
        arraySong.add(new Song("You are my crush","Quân AP", R.raw.youaremycrush, R.drawable.song1,1));
        arraySong.add(new Song("Waiting for you","Mono", R.raw.waitingforyou, R.drawable.song2,2));
        arraySong.add(new Song("Suýt nữa thì","Andiez", R.raw.suytnuathi,R.drawable.song3,3));
        arraySong.add(new Song("Bước qua nhau","Vũ", R.raw.buoc_qua_nhau, R.drawable.song6,4));
        arraySong.add(new Song("3107 full album","W/n", R.raw.nau, R.drawable.song4,5));
        arraySong.add(new Song("Bông hoa đẹp nhất","Quân AP", R.raw.bonghoadepnhat, R.drawable.song5,6));
        arraySong.add(new Song("Anh nhớ ra","Vũ", R.raw.anh_nho_ra, R.drawable.song6,7));
    }

    private void LoadItemLayout() {
        txtTimeSong     = (TextView)findViewById(R.id.startTimeSong);
        txtTimeTotal    = (TextView) findViewById(R.id.totalTimeSong);
        txtTitle        = (TextView) findViewById(R.id.textViewTitle);
        seekbarSong     = (SeekBar) findViewById(R.id.playerSeekbar);
        btnNext         = (ImageButton) findViewById(R.id.btnNext);
        btnNext10       = (ImageButton)findViewById(R.id.btnNext10);
        btnPre          = (ImageButton) findViewById(R.id.btnPrevious);
        btnPre10        = (ImageButton)findViewById(R.id.btnPre10);
        btnPlayPause    = (ImageButton) findViewById(R.id.btnPlayPause);
        btnChangeLayout = (ImageView) findViewById(R.id.btnShowListSong);
        imgAvatarSong   = (ImageView) findViewById(R.id.img_avatarSong);
        btnTypePlay     = (ImageButton) findViewById(R.id.btnTypePlay);
    }
}