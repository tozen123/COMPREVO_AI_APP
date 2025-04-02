package com.christianserwedevs.comprevo;

import android.content.Context;
import android.media.MediaPlayer;

public class SoundEffectPlayer {

    public static void playButtonSound(Context context) {
        if (!AppPreferences.isSoundEffectsEnabled(context)) return;

        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.tap);
        if (mediaPlayer != null) {
            mediaPlayer.setOnCompletionListener(MediaPlayer::release);
            mediaPlayer.start();
        }
    }

    public static void playWrongSound(Context context) {
        if (!AppPreferences.isSoundEffectsEnabled(context)) return;

        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.wronganswer);
        if (mediaPlayer != null) {
            mediaPlayer.setOnCompletionListener(MediaPlayer::release);
            mediaPlayer.start();
        }
    }

    public static void playCorrectSound(Context context) {
        if (!AppPreferences.isSoundEffectsEnabled(context)) return;

        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.correct_answer);
        if (mediaPlayer != null) {
            mediaPlayer.setOnCompletionListener(MediaPlayer::release);
            mediaPlayer.start();
        }
    }

    public static void playConfettiSound(Context context) {
        if (!AppPreferences.isSoundEffectsEnabled(context)) return;

        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.confetti);
        if (mediaPlayer != null) {
            mediaPlayer.setOnCompletionListener(MediaPlayer::release);
            mediaPlayer.start();
        }
    }
}
