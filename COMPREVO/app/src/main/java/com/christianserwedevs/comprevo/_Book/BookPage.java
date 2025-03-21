package com.christianserwedevs.comprevo._Book;

import java.util.List;

public class BookPage {
    private String text;
    private int imageResId;
    private List<QuizSet> quizSets;
    private boolean quizShown = false; // Added flag

    public BookPage(String text, int imageResId, List<QuizSet> quizSets) {
        this.text = text;
        this.imageResId = imageResId;
        this.quizSets = quizSets;
    }

    public String getText() {
        return text;
    }

    public int getImageResId() {
        return imageResId;
    }

    public List<QuizSet> getQuizSets() {
        return quizSets;
    }

    public boolean hasQuiz() {
        return quizSets != null && !quizSets.isEmpty();
    }

    // New methods for tracking quiz display status
    public boolean isQuizShown() {
        return quizShown;
    }

    public void setQuizShown(boolean quizShown) {
        this.quizShown = quizShown;
    }
}
