package com.christianserwedevs.comprevo.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.christianserwedevs.comprevo.R;
import com.christianserwedevs.comprevo.SoundEffectPlayer;

public class UserCreationActivity extends AppCompatActivity {

    private EditText userNameEditText;
    private Button createUserNameButton;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_creation);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

        userNameEditText = findViewById(R.id.userNameEditText);
        createUserNameButton = findViewById(R.id.createUserNameButton);

        createUserNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = userNameEditText.getText().toString().trim();
                SoundEffectPlayer.playButtonSound(v.getContext());
                if (!username.isEmpty()) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", username);
                    editor.apply();

                    Toast.makeText(UserCreationActivity.this, "Username saved!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(UserCreationActivity.this, StartMenuActivity.class);
                    startActivity(intent);

                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                } else {
                    Toast.makeText(UserCreationActivity.this, "Please enter a username", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}