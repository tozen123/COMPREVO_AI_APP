package com.christianserwedevs.comprevo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.christianserwedevs.comprevo.Activity.SplashScreenActivity;
import com.christianserwedevs.comprevo.Activity.UserCreationActivity;

public class LauncherActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);

        if (username == null || username.isEmpty()) {
            startActivity(new Intent(this, UserCreationActivity.class));
        } else {
            startActivity(new Intent(this, SplashScreenActivity.class));
        }

        finish();
    }
}
