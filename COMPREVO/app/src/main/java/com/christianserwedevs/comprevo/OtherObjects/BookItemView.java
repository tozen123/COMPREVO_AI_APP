package com.christianserwedevs.comprevo.Utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.christianserwedevs.comprevo.R;

public class BookItemView extends LinearLayout {

    private ImageView bookImageCover;
    private TextView bookTitle, bookPreviousScore, bookCurrentScore;

    public BookItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.score_history_item_book, this, true);

        bookImageCover = findViewById(R.id.bookImageCover);
        bookTitle = findViewById(R.id.bookTitle);
        bookPreviousScore = findViewById(R.id.bookPreviousScore);
        bookCurrentScore = findViewById(R.id.bookCurrentScore);
    }

    public void setBookData(String title, int imageResId, Context context) {
        bookImageCover.setImageResource(imageResId);
        bookTitle.setText(title);

        // Load scores from SharedPreferences
        SharedPreferences sharedPreferences = context.getSharedPreferences("BookScores", Context.MODE_PRIVATE);
        int previousScore = sharedPreferences.getInt(title + "_prev", 0);
        int currentScore = sharedPreferences.getInt(title, 0);

        bookPreviousScore.setText("Previous Score: " + previousScore);
        bookCurrentScore.setText("Latest Score: " + currentScore);
    }
}
