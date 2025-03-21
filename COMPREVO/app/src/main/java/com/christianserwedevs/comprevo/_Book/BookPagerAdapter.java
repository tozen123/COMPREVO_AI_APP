package com.christianserwedevs.comprevo._Book;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.christianserwedevs.comprevo.R;
import com.christianserwedevs.comprevo.Utilities.WordBottomSheetDialog;
import com.google.android.flexbox.FlexboxLayout;

import java.util.List;

public class BookPagerAdapter extends RecyclerView.Adapter<BookPagerAdapter.PageViewHolder> {

    private Book book;
    private Context context;
    private ViewPager2 viewPager;
    private int totalScore = 0;

    public BookPagerAdapter(Book book, Context context, ViewPager2 viewPager) {
        this.book = book;
        this.context = context;
        this.viewPager = viewPager;
    }

    @NonNull
    @Override
    public PageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.page_item, parent, false);
        return new PageViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull PageViewHolder holder, int position) {
        BookPage bookPage = book.getPages().get(position);

        Log.d("PageTracking", "Loading Page: " + position);
        holder.imageView.setImageResource(bookPage.getImageResId());
        setupWordTextViews(holder.wordContainer, bookPage.getText());


    }


    private void setupWordTextViews(FlexboxLayout wordContainer, String text) {
        wordContainer.removeAllViews();
        String[] words = text.split("\\s+");

        for (String word : words) {
            TextView wordTextView = new TextView(context);
            wordTextView.setText(word);
            wordTextView.setTextSize(17);
            wordTextView.setPadding(6, 3, 6, 3);
            wordTextView.setTextColor(context.getResources().getColor(R.color.font));

            Typeface typeface = ResourcesCompat.getFont(context, R.font.noto_sans);
            wordTextView.setTypeface(typeface);

            wordTextView.setBackgroundResource(R.drawable.transparent_background);

            wordTextView.setOnClickListener(v -> animateWordHighlight(wordTextView));

            wordTextView.setOnLongClickListener(v -> {
                animateWordHighlight(wordTextView);
                if (context instanceof Activity && !((Activity) context).isFinishing()) {
                    WordBottomSheetDialog dialog = new WordBottomSheetDialog(context, word);
                    dialog.show();
                }
                return true;
            });

            wordContainer.addView(wordTextView);
        }
    }

    private void animateWordHighlight(TextView wordTextView) {
        wordTextView.setBackgroundResource(R.drawable.word_background);

        wordTextView.setAlpha(0f);
        wordTextView.animate().alpha(1f).setDuration(250).start();

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            wordTextView.animate().alpha(0f).setDuration(300).withEndAction(() -> {
                wordTextView.setBackgroundResource(R.drawable.transparent_background);
                wordTextView.setAlpha(1f);
            }).start();
        }, 1000);
    }

    public void showQuizDialog(BookPage bookPage, PageViewHolder holder, int pagePosition) {
        List<QuizSet> quizSets = bookPage.getQuizSets();
        int[] currentQuestionIndex = {0};
        int[] correctAnswers = {0};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);

        View dialogView = LayoutInflater.from(context).inflate(R.layout.quiz_layout, null);
        TextView questionText = dialogView.findViewById(R.id.questionText);
        TextView counterText = dialogView.findViewById(R.id.counterText);
        RadioGroup radioGroup = dialogView.findViewById(R.id.radioGroup);
        LinearLayout explanationLayout = dialogView.findViewById(R.id.explanationLayout);
        TextView answerStatusText = dialogView.findViewById(R.id.answerStatusText);
        TextView explanationText = dialogView.findViewById(R.id.explanationText);
        Button nextButton = dialogView.findViewById(R.id.btnNext);

        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        updateQuestion(questionText, counterText, radioGroup, quizSets, explanationLayout, answerStatusText, explanationText, nextButton, currentQuestionIndex[0]);

        final boolean[] isAnswerCorrect = {false}; // Track if the selected answer is correct

        // Show explanation immediately after clicking an answer
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            QuizSet currentQuiz = quizSets.get(currentQuestionIndex[0]);

            // Map RadioButton position (0 = A, 1 = B, 2 = C, 3 = D) to QuizSet format (1 = A, 2 = B, etc.)
            int selectedAnswerIndex = -1;
            for (int i = 0; i < radioGroup.getChildCount(); i++) {
                if (radioGroup.getChildAt(i).getId() == checkedId) {
                    selectedAnswerIndex = i + 1; // +1 to match your QuizSet indexing
                    break;
                }
            }

            explanationLayout.setVisibility(View.VISIBLE);

            // Determine Correct or Wrong Answer
            if (selectedAnswerIndex == currentQuiz.getCorrectAnswerIndex()) {
                answerStatusText.setText("Correct Answer");
                answerStatusText.setBackgroundColor(context.getResources().getColor(R.color.correct_answer));
                isAnswerCorrect[0] = true; // Mark this as a correct answer
            } else {
                answerStatusText.setText("Wrong Answer");
                answerStatusText.setBackgroundColor(context.getResources().getColor(R.color.wrong_answer));
                isAnswerCorrect[0] = false; // Mark this as an incorrect answer
            }

            explanationText.setText("Explanation: " + currentQuiz.getExplanation());

            // Disable all RadioButtons after selection
            for (int i = 0; i < radioGroup.getChildCount(); i++) {
                radioGroup.getChildAt(i).setEnabled(false);
            }
        });

        nextButton.setOnClickListener(v -> {
            int selectedId = radioGroup.getCheckedRadioButtonId();
            if (selectedId == -1) {
                Toast.makeText(context, "Please select an answer!", Toast.LENGTH_SHORT).show();
            } else {
                if (isAnswerCorrect[0]) {
                    correctAnswers[0]++; // Count score correctly
                }

                if (currentQuestionIndex[0] < quizSets.size() - 1) {
                    currentQuestionIndex[0]++;
                    radioGroup.clearCheck();
                    updateQuestion(questionText, counterText, radioGroup, quizSets, explanationLayout, answerStatusText, explanationText, nextButton, currentQuestionIndex[0]);
                } else {
                    totalScore += correctAnswers[0];
                    dialog.dismiss();
                    showFinalScore(correctAnswers[0], quizSets.size(), holder, pagePosition);
                }
            }
        });

        dialog.show();
    }







    private void updateQuestion(TextView questionText, TextView counterText, RadioGroup radioGroup,
                                List<QuizSet> quizSets, LinearLayout explanationLayout, TextView answerStatusText,
                                TextView explanationText, Button nextButton, int questionIndex) {
        QuizSet quizSet = quizSets.get(questionIndex);
        questionText.setText(quizSet.getQuestion());
        counterText.setText((questionIndex + 1) + "/" + quizSets.size());

        radioGroup.removeAllViews();
        for (int i = 0; i < quizSet.getChoices().length; i++) {
            RadioButton radioButton = new RadioButton(context);
            radioButton.setText(quizSet.getChoices()[i]);

            Typeface typeface = ResourcesCompat.getFont(context, R.font.lilitaone_regular); // Change this to your desired font
            radioButton.setTypeface(typeface);

            radioButton.setTextColor(context.getResources().getColor(R.color.truewhite));
            radioButton.setTextSize(16);



            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(0, 18, 0, 0); // Top Margin: 12dp for spacing
            radioButton.setLayoutParams(layoutParams);

            radioButton.setId(View.generateViewId());
            radioButton.setEnabled(true);
            radioGroup.addView(radioButton);
        }

        // Hide explanation layout and reset Correct/Wrong Label
        explanationLayout.setVisibility(View.GONE);
        answerStatusText.setText("");  // Clear Correct/Wrong status

        // Change Next Button to "Finish" if it's the last question
        if (questionIndex == quizSets.size() - 1) {
            nextButton.setText("Finish");
        } else {
            nextButton.setText("Next");
        }
    }







    private void showFinalScore(int correctAnswers, int totalQuestions, PageViewHolder holder, int pagePosition) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // Inflate the custom layout
        View dialogView = LayoutInflater.from(context).inflate(R.layout.final_score_dialog, null);
        TextView scoreText = dialogView.findViewById(R.id.scoreText);
        TextView resultText = dialogView.findViewById(R.id.resultText);
        TextView encouragementText = dialogView.findViewById(R.id.encouragementText);
        Button btnOk = dialogView.findViewById(R.id.btnOk);

        // Display Score
        resultText.setText(correctAnswers + "/" + totalQuestions);

        // Custom Encouragement Message Based on Performance
        if (correctAnswers == totalQuestions) {
            encouragementText.setText("Perfect Score! Excellent work!");
        } else if (correctAnswers >= totalQuestions / 2) {
            encouragementText.setText("Great job! Keep it up!");
        } else {
            encouragementText.setText("Don't worry, practice makes perfect!");
        }

        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        btnOk.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }


    @Override
    public int getItemCount() {
        return book.getPages().size();
    }

    public static class PageViewHolder extends RecyclerView.ViewHolder {
        FlexboxLayout wordContainer;
        ImageView imageView;

        public PageViewHolder(View itemView) {
            super(itemView);
            wordContainer = itemView.findViewById(R.id.wordContainer);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
