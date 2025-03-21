package com.christianserwedevs.comprevo._Book;

public class QuizSet {
    private String question;
    private String[] choices;
    private int correctAnswerIndex;
    private String explanation;
    public QuizSet(String question, String[] choices, int correctAnswerIndex, String explanation) {
        this.question = question;
        this.choices = choices;
        this.correctAnswerIndex = correctAnswerIndex;
        this.explanation = explanation;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getChoices() {
        return choices;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    public String getExplanation() {
        return explanation;
    }
}
