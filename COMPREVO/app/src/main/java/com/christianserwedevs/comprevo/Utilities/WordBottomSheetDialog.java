package com.christianserwedevs.comprevo.Utilities;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.christianserwedevs.comprevo.AI.OpenAIHelper;
import com.christianserwedevs.comprevo.R;

import java.util.HashMap;

import android.os.Handler;
import android.os.Looper;
import android.widget.ScrollView;

import android.os.Handler;
import android.os.Looper;
import android.widget.ScrollView;

public class WordBottomSheetDialog extends BottomSheetDialog {
    private TextView definitionTextView, usageTextView, exampleTextView, similarTextView, pronunciationTextView, loadingTextView;
    private ScrollView detailsContainer;
    private static HashMap<String, String[]> aiCache = new HashMap<>();
    private boolean isLoading = false; // Flag to track if loading animation is running

    public WordBottomSheetDialog(Context context, String word) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_word_details, null);
        setContentView(view);

        TextView wordTextView = view.findViewById(R.id.wordText);
        definitionTextView = view.findViewById(R.id.definitionText);
        usageTextView = view.findViewById(R.id.usageText);
        exampleTextView = view.findViewById(R.id.exampleText);
        similarTextView = view.findViewById(R.id.similarText);
        pronunciationTextView = view.findViewById(R.id.pronunciationText);
        loadingTextView = view.findViewById(R.id.loadingText);
        detailsContainer = view.findViewById(R.id.detailsContainer);

        // Format the word properly
        String formattedWord = formatWord(word);
        wordTextView.setText(formattedWord);

        // Check if the word is already cached
        if (aiCache.containsKey(formattedWord)) {
            setWordDetails(aiCache.get(formattedWord)); // Load from cache
            stopLoadingAnimation(); // Ensure loading is hidden
        } else {
            showLoadingAnimation(); // Start loading animation
            fetchWordDetails(formattedWord);
        }

        setOnDismissListener(dialog -> {
            if (getContext() instanceof Activity && ((Activity) getContext()).isFinishing()) {
                dismiss();
            }
        });
    }

    private void fetchWordDetails(String word) {
        OpenAIHelper.fetchWordDetails(word, new OpenAIHelper.OpenAIResponseCallback() {
            @Override
            public void onSuccess(String definition, String usage, String example, String similar, String pronunciation) {
                String[] result = {definition, usage, example, similar, pronunciation};

                // Save to cache
                aiCache.put(word, result);

                updateDetails(result);
            }

            @Override
            public void onFailure(String error) {
                updateDetails(new String[]{"Error fetching details: " + error, "", "", "", ""});
            }
        });
    }

    private void updateDetails(String[] details) {
        new Handler(Looper.getMainLooper()).post(() -> {
            setWordDetails(details);
            stopLoadingAnimation(); // Hide loading and show details
        });
    }

    private void setWordDetails(String[] details) {
        definitionTextView.setText(details[0]);
        usageTextView.setText(details[1]);
        exampleTextView.setText(details[2]);
        similarTextView.setText(details[3]);
        pronunciationTextView.setText(details[4]);
    }

    private void showLoadingAnimation() {
        new Handler(Looper.getMainLooper()).post(() -> {
            isLoading = true;
            detailsContainer.setVisibility(View.GONE); // Hide details
            loadingTextView.setVisibility(View.VISIBLE); // Show "Loading..."
            animateLoadingText(); // Start animation
        });
    }

    private void stopLoadingAnimation() {
        new Handler(Looper.getMainLooper()).post(() -> {
            isLoading = false; // Stop animation
            loadingTextView.setVisibility(View.GONE); // Hide "Loading..."
            detailsContainer.setVisibility(View.VISIBLE); // Show details
        });
    }

    private void animateLoadingText() {
        new Thread(() -> {
            String[] dots = {"Loading.", "Loading..", "Loading..."};
            int i = 0;
            while (isLoading) { // Continue only if still loading
                final String text = dots[i % dots.length];
                new Handler(Looper.getMainLooper()).post(() -> loadingTextView.setText(text));
                i++;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    break; // Stop thread if interrupted
                }
            }
        }).start();
    }

    private String formatWord(String word) {
        String cleanedWord = word.replaceAll("[,\\.\\s]", "");
        return cleanedWord.isEmpty() ? word : cleanedWord.substring(0, 1).toUpperCase() + cleanedWord.substring(1);
    }
}
