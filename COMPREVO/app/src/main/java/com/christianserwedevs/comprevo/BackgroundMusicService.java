package com.christianserwedevs.comprevo;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import androidx.annotation.Nullable;

public class BackgroundMusicService extends Service {
    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize MediaPlayer with your music file from res/raw
        mediaPlayer = MediaPlayer.create(this, R.raw.bg_music);
        mediaPlayer.setLooping(true);  // Loop the music continuously
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Start the music if it's not already playing
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
        // Ensure the service is restarted if it gets terminated
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Clean up the MediaPlayer
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // We don't support binding, so return null
        return null;
    }
}
