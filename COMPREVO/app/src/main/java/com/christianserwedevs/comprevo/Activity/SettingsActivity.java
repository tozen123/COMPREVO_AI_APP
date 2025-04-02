package com.christianserwedevs.comprevo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.christianserwedevs.comprevo.AppPreferences;
import com.christianserwedevs.comprevo.BackgroundMusicService;
import com.christianserwedevs.comprevo.R;

public class SettingsActivity extends AppCompatActivity {


    private Switch switchMusic, switchSoundEffects;
    private Button backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        switchMusic = findViewById(R.id.switchMusic);
        switchSoundEffects = findViewById(R.id.switchSoundEffects);
        Button backButton = findViewById(R.id.backButton);

        // Load saved states
        switchMusic.setChecked(AppPreferences.isMusicEnabled(this));
        switchSoundEffects.setChecked(AppPreferences.isSoundEffectsEnabled(this));

        // Music toggle
        switchMusic.setOnCheckedChangeListener((buttonView, isChecked) -> {
            AppPreferences.setMusicEnabled(this, isChecked);
            if (isChecked) {
                startService(new Intent(this, BackgroundMusicService.class));
            } else {
                stopService(new Intent(this, BackgroundMusicService.class));
            }
        });

        // SFX toggle
        switchSoundEffects.setOnCheckedChangeListener((buttonView, isChecked) -> {
            AppPreferences.setSoundEffectsEnabled(this, isChecked);
        });

        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}