package com.christianserwedevs.comprevo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.christianserwedevs.comprevo.R;
import com.christianserwedevs.comprevo.Utilities.BookItemView;

public class ScoreHistoryProgressActivity extends AppCompatActivity {

    private LinearLayout bookContainer;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_history_progress);
        
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        bookContainer = findViewById(R.id.bookContainer);
        backButton = findViewById(R.id.backButton);

        addBook("HOW THE WORLD WAS CREATED (PANAYAN)", R.drawable.how_the_world_was_created_cover);
        addBook("Obesity (Essay)", R.drawable.obesity_book_cover);
        addBook("The God Stealer", R.drawable.the_god_stealer_cover_cover);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ScoreHistoryProgressActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    private void addBook(String title, int imageResId) {
        BookItemView bookItemView = new BookItemView(this, null);
        bookItemView.setBookData(title, imageResId, this);
        bookContainer.addView(bookItemView);
    }
}
