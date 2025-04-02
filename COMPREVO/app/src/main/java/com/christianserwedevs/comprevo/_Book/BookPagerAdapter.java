package com.christianserwedevs.comprevo._Book;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
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
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.christianserwedevs.comprevo.R;
import com.christianserwedevs.comprevo.SoundEffectPlayer;
import com.christianserwedevs.comprevo.Utilities.ConfirmationDialog;
import com.christianserwedevs.comprevo.Utilities.WordBottomSheetDialog;
import com.google.android.flexbox.FlexboxLayout;

import java.util.List;
import java.util.concurrent.TimeUnit;

import nl.dionsegijn.konfetti.core.Party;
import nl.dionsegijn.konfetti.core.PartyFactory;
import nl.dionsegijn.konfetti.core.Position;
import nl.dionsegijn.konfetti.core.emitter.Emitter;
import nl.dionsegijn.konfetti.core.emitter.EmitterConfig;
import nl.dionsegijn.konfetti.core.models.Shape;
import nl.dionsegijn.konfetti.core.models.Size;
import nl.dionsegijn.konfetti.xml.KonfettiView;
import nl.dionsegijn.konfetti.xml.image.ImageUtil;


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

        holder.bookTitle.setText(book.getTitle());
        holder.bookCurrentPage.setText("Page " + (position + 1) + " / " + book.getPages().size());

        holder.backButton.setOnClickListener(v -> {
            ConfirmationDialog.show(context, new ConfirmationDialog.ConfirmationListener() {
                @Override
                public void onConfirm() {
                    if (context instanceof Activity) {
                        ((Activity) context).finish();
                    }
                }

                @Override
                public void onCancel() {
                }
            });
        });

        holder.mainMenuBackButton.setOnClickListener(v -> {
            if (context instanceof Activity) {
                ((Activity) context).finish();
            }
        });



    }

    private void setupWordTextViews(FlexboxLayout wordContainer, String text) {
        wordContainer.removeAllViews();
        String[] words = text.split("\\s+");

        for (String word : words) {
            TextView wordTextView = new TextView(context);
            wordTextView.setText(word);
            wordTextView.setTextSize(16);
            wordTextView.setPadding(6, 2, 6, 2);
            wordTextView.setTextColor(context.getResources().getColor(R.color.font));

            Typeface typeface = ResourcesCompat.getFont(context, R.font.noto_sans);
            wordTextView.setTypeface(typeface);

            wordTextView.setBackgroundResource(R.drawable.transparent_background);

            // Set text alignment to start (left-aligned)
            wordTextView.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
            // Alternatively, you can use:
            wordTextView.setGravity(Gravity.START);

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
    private void startCountdown(Runnable onFinish, TextView countdownText, LinearLayout countdownLayout, LinearLayout quizContentLayout) {
        countdownLayout.setVisibility(View.VISIBLE);
        quizContentLayout.setVisibility(View.GONE);
        countdownText.setVisibility(View.VISIBLE);

        final int[] count = {3};
        Handler handler = new Handler(Looper.getMainLooper());

        Runnable countdownRunnable = new Runnable() {
            @Override
            public void run() {
                if (count[0] > 0) {
                    countdownText.setText(String.valueOf(count[0]));

                    // Animate scale (resizing effect)
                    countdownText.setScaleX(0f);
                    countdownText.setScaleY(0f);
                    countdownText.animate()
                            .scaleX(1f)
                            .scaleY(1f)
                            .setDuration(300)
                            .start();

                    count[0]--;
                    handler.postDelayed(this, 1000);
                } else {
                    countdownLayout.setVisibility(View.GONE);
                    quizContentLayout.setVisibility(View.VISIBLE);
                    onFinish.run();
                }
            }
        };

        handler.post(countdownRunnable);
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
        TextView detailedExplanationText = dialogView.findViewById(R.id.detailedExplanationText); // New Detailed Explanation TextView
        Button btnMoreInfo = dialogView.findViewById(R.id.btnMoreInfo); // New "More Info" Button
        Button nextButton = dialogView.findViewById(R.id.btnNext);
        TextView countdownText = dialogView.findViewById(R.id.countdownText);
        LinearLayout countdownLayout = dialogView.findViewById(R.id.countdownLayout);
        LinearLayout quizContentLayout = dialogView.findViewById(R.id.quizContentLayout);

        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        startCountdown(() -> {
            updateQuestion(questionText, counterText, radioGroup, quizSets,
                    explanationLayout, answerStatusText, explanationText,
                    detailedExplanationText, nextButton, btnMoreInfo, currentQuestionIndex[0]);
        }, countdownText, countdownLayout, quizContentLayout);

        final boolean[] isAnswerCorrect = {false};
        final boolean[] soundPlayed = {false};

        // Show explanation immediately after clicking an answer
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            QuizSet currentQuiz = quizSets.get(currentQuestionIndex[0]);
            int selectedAnswerIndex = -1;

            for (int i = 0; i < radioGroup.getChildCount(); i++) {
                if (radioGroup.getChildAt(i).getId() == checkedId) {
                    selectedAnswerIndex = i + 1;
                    break;
                }
            }

            explanationLayout.setVisibility(View.VISIBLE);

            if (selectedAnswerIndex == currentQuiz.getCorrectAnswerIndex()) {
                if (!soundPlayed[0]) {
                    SoundEffectPlayer.playCorrectSound(context);
                    soundPlayed[0] = true;
                }
                answerStatusText.setText("Correct Answer");
                answerStatusText.setBackgroundColor(context.getResources().getColor(R.color.correct_answer));
                isAnswerCorrect[0] = true;


            } else {
                if (!soundPlayed[0]) {
                    SoundEffectPlayer.playWrongSound(context);
                    soundPlayed[0] = true;
                }
                answerStatusText.setText("Wrong Answer");
                answerStatusText.setBackgroundColor(context.getResources().getColor(R.color.wrong_answer));
                isAnswerCorrect[0] = false;
            }

            explanationText.setText("Explanation: " + currentQuiz.getExplanation());
            if(currentQuiz.getDetailedExplanation().isEmpty()){
                detailedExplanationText.setText("No detailed explanation");

            } else {
                detailedExplanationText.setText(currentQuiz.getDetailedExplanation());

            }

            btnMoreInfo.setVisibility(View.VISIBLE);
            detailedExplanationText.setVisibility(View.GONE);

            btnMoreInfo.setOnClickListener(v -> {
                if (detailedExplanationText.getVisibility() == View.GONE) {
                    detailedExplanationText.setVisibility(View.VISIBLE);
                    btnMoreInfo.setText("Hide Info");
                } else {
                    detailedExplanationText.setVisibility(View.GONE);
                    btnMoreInfo.setText("More Info");
                }
            });

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
                    correctAnswers[0]++;
                }

                if (currentQuestionIndex[0] < quizSets.size() - 1) {
                    currentQuestionIndex[0]++;
                    radioGroup.clearCheck();
                    soundPlayed[0] = false;
                    startCountdown(() -> {
                        updateQuestion(questionText, counterText, radioGroup, quizSets,
                                explanationLayout, answerStatusText, explanationText,
                                detailedExplanationText, nextButton, btnMoreInfo, currentQuestionIndex[0]);
                    }, countdownText, countdownLayout, quizContentLayout);
                } else {
                    dialog.dismiss();
                    showFinalScore(correctAnswers[0], quizSets.size(), holder, pagePosition);
                }
            }
        });

        dialog.show();
    }






    private int dpToPx(int dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }


    private void updateQuestion(TextView questionText, TextView counterText, RadioGroup radioGroup,
                                List<QuizSet> quizSets, LinearLayout explanationLayout,
                                TextView answerStatusText, TextView explanationText,
                                TextView detailedExplanationText, Button nextButton,
                                Button btnMoreInfo, int questionIndex) {

        QuizSet quizSet = quizSets.get(questionIndex);
        questionText.setText(quizSet.getQuestion());
        counterText.setText((questionIndex + 1) + "/" + quizSets.size());

        radioGroup.removeAllViews();
        for (int i = 0; i < quizSet.getChoices().length; i++) {
            RadioButton radioButton = new RadioButton(context);
            radioButton.setText(quizSet.getChoices()[i]);
            radioButton.setId(View.generateViewId());

            Typeface typeface = ResourcesCompat.getFont(context, R.font.lilitaone_regular);
            radioButton.setTypeface(typeface);

            radioButton.setTextColor(context.getResources().getColor(R.color.truewhite));
            radioButton.setTextSize(16);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, dpToPx(12), 0, 0);
            radioButton.setLayoutParams(params);
            radioGroup.addView(radioButton);
        }

        explanationLayout.setVisibility(View.GONE);
        answerStatusText.setText("");

        if (questionIndex == quizSets.size() - 1) {
            nextButton.setText("Finish");
        } else {
            nextButton.setText("Next");
        }

        // Reset Explanation Visibility
        explanationLayout.setVisibility(View.GONE);
        detailedExplanationText.setVisibility(View.GONE);
        btnMoreInfo.setVisibility(View.GONE);
    }


    private void saveScoreToPreferences(String bookTitle, int currentScore) {
        int previousScore = context.getSharedPreferences("BookScores", Context.MODE_PRIVATE)
                .getInt(bookTitle, 0);

        context.getSharedPreferences("BookScores", Context.MODE_PRIVATE)
                .edit()
                .putInt(bookTitle + "_prev", previousScore)
                .putInt(bookTitle, currentScore)
                .apply();
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
        totalScore += correctAnswers;


        if (correctAnswers == totalQuestions) {
            encouragementText.setText("Perfect Score! Excellent work!");
        } else if (correctAnswers >= totalQuestions / 2) {
            encouragementText.setText("Great job! Keep it up!");
        } else {
            encouragementText.setText("Don't worry, practice makes perfect!");
        }

        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        btnOk.setOnClickListener(v -> {
            dialog.dismiss();

            if (pagePosition == book.getPages().size() - 1) {
                holder.finalPageDisplay.setVisibility(View.VISIBLE);
                holder.wordContainer.setVisibility(View.INVISIBLE);

                int previousScore = context.getSharedPreferences("BookScores", Context.MODE_PRIVATE)
                        .getInt(book.getTitle() + "_prev", 0);

                saveScoreToPreferences(book.getTitle(), totalScore);

                holder.label2.setText("Your Previous Score: " + previousScore);
                holder.label3.setText("Your Current score: " + totalScore );


                if (book.getTitle().equalsIgnoreCase("HOW THE WORLD WAS CREATED (PANAYAN)")) {
                    if (totalScore >= 1 && totalScore <= 9) {
                        holder.label4.setText("Every great achiever started as a beginner. Don't give up!");
                    } else if (totalScore >= 10 && totalScore <= 12) {
                        holder.label4.setText("Step by step, you’re getting stronger. Keep learning!");
                    } else if (totalScore >= 13 && totalScore <= 15) {
                        holder.label4.setText("You’re shining bright! Keep up the amazing work!");
                    }
                } else if (book.getTitle().equalsIgnoreCase("Obesity (Essay)")) {
                    if (totalScore >= 1 && totalScore <= 9) {
                        holder.label4.setText("Every try brings you closer to success. Keep pushing forward!");
                    } else if (totalScore >= 10 && totalScore <= 12) {
                        holder.label4.setText("You're making progress! Learning is a journey, not a race!");
                    } else if (totalScore >= 13 && totalScore <= 15) {
                        holder.label4.setText("Unstoppable! You've conquered this challenge with excellence!");
                    }
                } else if (book.getTitle().equalsIgnoreCase("The God Stealer")) {
                    if (totalScore >= 1 && totalScore <= 9) {
                        holder.label4.setText("Step by step, you're getting there. Keep going!");
                    } else if (totalScore >= 10 && totalScore <= 12) {
                        holder.label4.setText("Look at how far you’ve come. Push forward!");
                    } else if (totalScore >= 13 && totalScore <= 15) {
                        holder.label4.setText("Victory! You made it to the finish line! Great job!");
                    }
                }
                else if (book.getTitle().equalsIgnoreCase("My Father Goes To Court")) {
                    if (totalScore >= 1 && totalScore <= 9) {
                        holder.label4.setText("Each attempt is a progress. Don't give up!");
                    } else if (totalScore >= 10 && totalScore <= 12) {
                        holder.label4.setText("That was pretty good! You're just one more 'aha'! moment from perfection");
                    } else if (totalScore >= 13 && totalScore <= 15) {
                        holder.label4.setText("You've demonstrated your ability— keep reaching for the top");
                    }
                }else if (book.getTitle().equalsIgnoreCase("The Philippines Battle Against Deforestation")) {
                if (totalScore >= 1 && totalScore <= 9) {
                    holder.label4.setText("This is just a stepping stone to greater achievements. Keep climbing!");
                } else if (totalScore >= 10 && totalScore <= 12) {
                    holder.label4.setText("This is your cue. Sprint towards your full potential!");
                } else if (totalScore >= 13 && totalScore <= 15) {
                    holder.label4.setText("You've cracked the code of success. Well done!");
                }
            }
                else {
                    holder.label4.setText("Great effort! Keep pushing forward!");
                }



                KonfettiView konfettiView = holder.konfettiView;

                final Drawable drawable = ContextCompat.getDrawable(context, R.drawable.ic_heart);
                Shape.DrawableShape drawableShape = ImageUtil.loadDrawable(drawable, true, true);

                EmitterConfig emitterConfig = new Emitter(5L, TimeUnit.SECONDS).perSecond(50);

                SoundEffectPlayer.playConfettiSound(context);
                Party party = new PartyFactory(emitterConfig)
                        .angle(270)
                        .spread(90)
                        .setSpeedBetween(1f, 5f)
                        .timeToLive(5000L)
                        .shapes(new Shape.Rectangle(0.2f), drawableShape) // Corrected
                        .sizes(new Size(12, 5f, 0.2f))
                        .position(0.0, 0.0, 1.0, 0.0)
                        .build();

                konfettiView.start(party);
                Toast.makeText(context, "Story Completed! Score Saved!", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }



    @Override
    public int getItemCount() {
        return book.getPages().size();
    }

    public static class PageViewHolder extends RecyclerView.ViewHolder {
        public FlexboxLayout wordContainer;
        ImageView imageView;

        LinearLayout headerLayout;
        Button backButton;
        Button mainMenuBackButton;
        TextView bookTitle;
        TextView bookCurrentPage;
        public TextView label2, label3, label4;  // Change this to public

        KonfettiView konfettiView;
        // New Final Page Display
        public LinearLayout finalPageDisplay;

        public PageViewHolder(View itemView) {
            super(itemView);
            wordContainer = itemView.findViewById(R.id.wordContainer);
            imageView = itemView.findViewById(R.id.imageView);

            headerLayout = itemView.findViewById(R.id.linearLayout);
            backButton = itemView.findViewById(R.id.backButton);
            bookTitle = itemView.findViewById(R.id.bookTitle);
            bookCurrentPage = itemView.findViewById(R.id.bookCurrentPage);

            finalPageDisplay = itemView.findViewById(R.id.finalPageDisplay);
            label2 = itemView.findViewById(R.id.label2);
            label3 = itemView.findViewById(R.id.label3);
            label4 = itemView.findViewById(R.id.label4);
            mainMenuBackButton = itemView.findViewById(R.id.mainMenuBackButton);
            konfettiView = itemView.findViewById(R.id.konfettiView);
        }
    }



}
