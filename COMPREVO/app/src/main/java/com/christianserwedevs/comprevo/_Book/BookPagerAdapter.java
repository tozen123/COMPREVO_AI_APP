package com.christianserwedevs.comprevo._Book;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.text.Layout;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.christianserwedevs.comprevo.AI.OpenAIHelper;
import com.christianserwedevs.comprevo.R;
import com.christianserwedevs.comprevo.Utilities.WordBottomSheetDialog;
import com.google.android.flexbox.FlexboxLayout;

import java.util.HashMap;
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

        if (bookPage instanceof BookPageQuiz) {
            showQuizDialog((BookPageQuiz) bookPage, holder, position);
        } else {
            holder.imageView.setImageResource(bookPage.getImageResId());
            setupWordTextViews(holder.wordContainer, bookPage.getText());
        }


    }

    private void setupWordTextViews(FlexboxLayout wordContainer, String text) {
        wordContainer.removeAllViews();

        String[] words = text.split("\\s+");

        for (String word : words) {
            TextView wordTextView = new TextView(context);
            wordTextView.setText(word);
            wordTextView.setTextSize(18);
            wordTextView.setPadding(8, 4, 8, 4);
            wordTextView.setTextColor(context.getResources().getColor(R.color.font));

            Typeface typeface = ResourcesCompat.getFont(context, R.font.noto_sans);
            wordTextView.setTypeface(typeface);



            // Add shadow (radius, dx, dy, shadow color)
            wordTextView.setShadowLayer(30.0f, 3.0f, 4.0f, Color.WHITE);

            wordTextView.setOnLongClickListener(v -> {
                if (context instanceof Activity && !((Activity) context).isFinishing()) {
                    WordBottomSheetDialog dialog = new WordBottomSheetDialog(context, word);
                    dialog.show();
                }
                return true;
            });

            // Add to Flexbox layout
            wordContainer.addView(wordTextView);
        }
    }






    private void showQuizDialog(BookPageQuiz quizPage, PageViewHolder holder, int pagePosition) {
        List<QuizSet> quizSets = quizPage.getQuizSets();
        int[] currentQuestionIndex = {0};
        int[] correctAnswers = {0};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);

        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_quiz, null);
        TextView questionText = dialogView.findViewById(R.id.questionText);
        TextView counterText = dialogView.findViewById(R.id.counterText);
        RadioGroup radioGroup = dialogView.findViewById(R.id.radioGroup);
        Button nextButton = dialogView.findViewById(R.id.btnNext);

        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        updateQuestion(questionText, counterText, radioGroup, quizSets, currentQuestionIndex[0]);

        nextButton.setOnClickListener(v -> {
            int selectedId = radioGroup.getCheckedRadioButtonId();
            if (selectedId == -1) {
                Toast.makeText(context, "Please select an answer!", Toast.LENGTH_SHORT).show();
            } else {
                QuizSet currentQuiz = quizSets.get(currentQuestionIndex[0]);
                if (selectedId == currentQuiz.getCorrectAnswerIndex()) {
                    Toast.makeText(context, "Correct!", Toast.LENGTH_SHORT).show();
                    correctAnswers[0]++;
                } else {
                    Toast.makeText(context, "Wrong answer!", Toast.LENGTH_SHORT).show();
                }

                if (currentQuestionIndex[0] < quizSets.size() - 1) {
                    currentQuestionIndex[0]++;
                    updateQuestion(questionText, counterText, radioGroup, quizSets, currentQuestionIndex[0]);
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
                                List<QuizSet> quizSets, int questionIndex) {
        QuizSet quizSet = quizSets.get(questionIndex);
        questionText.setText(quizSet.getQuestion());
        counterText.setText((questionIndex + 1) + "/" + quizSets.size());

        radioGroup.removeAllViews();
        for (int i = 0; i < quizSet.getChoices().length; i++) {
            RadioButton radioButton = new RadioButton(context);
            radioButton.setText(quizSet.getChoices()[i]);
            radioButton.setId(i);
            radioGroup.addView(radioButton);
        }
    }

    private void showFinalScore(int correctAnswers, int totalQuestions, PageViewHolder holder, int pagePosition) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Quiz Completed!");
        builder.setMessage("You answered " + correctAnswers + " out of " + totalQuestions + " questions correctly!");

        builder.setPositiveButton("OK", (dialog, which) -> {
            dialog.dismiss();
            removeQuizPage(pagePosition);
        });

        builder.setCancelable(false);
        builder.show();
    }

    private void removeQuizPage(int pagePosition) {
        if (book.getPages().get(pagePosition) instanceof BookPageQuiz) {
            book.getPages().remove(pagePosition);
            notifyItemRemoved(pagePosition);
            notifyItemRangeChanged(pagePosition, getItemCount());
        }

        // Move to the next page after removal
        moveToNextPage(pagePosition);
    }

    private void moveToNextPage(int currentPage) {
        int nextPage = Math.min(currentPage, getItemCount() - 1); // Ensure we stay within bounds
        if (nextPage < getItemCount()) {
            viewPager.post(() -> viewPager.setCurrentItem(nextPage, true));
        } else {
            Toast.makeText(context, "You've completed the book!", Toast.LENGTH_SHORT).show();
        }
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
