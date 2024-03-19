package Mp3.com;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener{
    Button mainBtn;
    int curSong = 0;
    ImageView img;
    MediaPlayer mediaPlayer;
    TextView textSeek;
    TextView textSong;
    Timer myTimer;
    int[] songs;
    String[] songsTite;
    int[] songsImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        img = findViewById(R.id.image);
        textSeek = findViewById(R.id.textSeek);
        textSong = findViewById(R.id.songName);

        songs = new int[]{R.raw.music1, R.raw.music2, R.raw.music3};
        songsTite = new String[]{"Music1", "Music2", "Music3"};
        songsImg = new int[]{R.drawable.photo1, R.drawable.photo2, R.drawable.photo3};


//        mediaPlayer = MediaPlayer.create(this, songs[curSong] );
        mediaPlayer = MediaPlayer.create(getApplicationContext(), songs[curSong] );
        SeekBar seekBar = findViewById(R.id.seekBar);
        seekBar.setMax(mediaPlayer.getDuration());
        seekBar.setOnSeekBarChangeListener(this);

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
            }
        }, 0, 1000);
    }

    public void mainBtn(View view) {
        mainBtn = findViewById(R.id.mainBtn);
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            mainBtn.setText("Play");
        }else{
            mediaPlayer.start();
            mainBtn.setText("Pause");
        }
    }

//    onProgressChanged() — уведомляет об изменении положения ползунка;
//    onStartTrackingTouch() — уведомляет о том, что пользователь начал перемещать ползунок;
//    onStopTrackingTouch() — уведомляет о том, что пользователь закончил перемещать ползунок
    @Override
    // нужен
    public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
        // поделил на 1000 тк до этого показывало слишком большие цифры , но с минутами не разобрался
        textSeek.setText(mediaPlayer.getCurrentPosition() / 1000 + " / " + mediaPlayer.getDuration() / 1000);
    }
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }


    public void previous(View view) {
        if(curSong > 0){
            curSong--;
            SwitchMusic();
        }
    }
    public void next(View view) {
        if(curSong < songs.length-1){
            curSong++;
            SwitchMusic();
        }
    }
    public void SwitchMusic(){
        mediaPlayer.reset();
        mediaPlayer = MediaPlayer.create(this, songs[curSong]);
        mediaPlayer.start();

        textSong.setText(songsTite[curSong]);
        img.setImageResource(songsImg[curSong]);
    }
}
