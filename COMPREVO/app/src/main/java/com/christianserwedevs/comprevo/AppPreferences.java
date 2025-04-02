package com.christianserwedevs.comprevo;

import android.content.Context;

public class AppPreferences {
    public static final String PREFS_NAME = "AppSettingsPrefs";
    public static final String MUSIC_ENABLED = "music_enabled";
    public static final String SFX_ENABLED = "sfx_enabled";

    public static boolean isMusicEnabled(Context context) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .getBoolean(MUSIC_ENABLED, true);
    }

    public static boolean isSoundEffectsEnabled(Context context) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .getBoolean(SFX_ENABLED, true);
    }

    public static void setMusicEnabled(Context context, boolean enabled) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit().putBoolean(MUSIC_ENABLED, enabled).apply();
    }

    public static void setSoundEffectsEnabled(Context context, boolean enabled) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit().putBoolean(SFX_ENABLED, enabled).apply();
    }
}

