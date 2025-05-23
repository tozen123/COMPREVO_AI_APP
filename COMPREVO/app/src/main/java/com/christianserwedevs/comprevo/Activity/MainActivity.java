package com.christianserwedevs.comprevo.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.christianserwedevs.comprevo.R;
import com.christianserwedevs.comprevo.SoundEffectPlayer;

public class MainActivity extends AppCompatActivity {
    public TextView userNameTxt;
    private SharedPreferences sharedPreferences;
    private Button settingsButton, trophyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            return insets;
        });

        sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

        userNameTxt = findViewById(R.id.userNameTxt);
        userNameTxt.setText("Hello, " + sharedPreferences.getString("username", "null"));


        settingsButton = findViewById(R.id.settingsButton);
        trophyButton = findViewById(R.id.trophyButton);

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundEffectPlayer.playButtonSound(v.getContext());
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            }
        });

        trophyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundEffectPlayer.playButtonSound(v.getContext());
                startActivity(new Intent(MainActivity.this, ScoreHistoryProgressActivity.class));
            }
        });

        findViewById(R.id.card1).setOnClickListener(v -> openBookActivity("HOW THE WORLD WAS CREATED (PANAYAN)"));
        findViewById(R.id.card2).setOnClickListener(v -> openBookActivity("My Father Goes To Court"));
        findViewById(R.id.card3).setOnClickListener(v -> openBookActivity("Obesity (Essay)"));
        findViewById(R.id.card4).setOnClickListener(v -> openBookActivity("The God Stealer"));
        findViewById(R.id.card5).setOnClickListener(v -> openBookActivity("The Philippines Battle Against Deforestation"));
    }

    private void openBookActivity(String bookTitle) {
        SoundEffectPlayer.playButtonSound(MainActivity.this);
        Intent intent = new Intent(MainActivity.this, BookActivity.class);
        intent.putExtra("bookTitle", bookTitle);
        startActivity(intent);
    }
}
