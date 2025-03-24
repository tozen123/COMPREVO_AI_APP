package com.christianserwedevs.comprevo._Book;

public class QuizSet {
    private String question;
    private String[] choices;
    private int correctAnswerIndex;
    private String explanation;
    private String detailedExplanation;
    public QuizSet(String question, String[] choices, int correctAnswerIndex, String explanation, String detailedExplanation) {
        this.question = question;
        this.choices = choices;
        this.correctAnswerIndex = correctAnswerIndex;
        this.explanation = explanation;
        this.detailedExplanation = detailedExplanation;
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
    public String getDetailedExplanation() {
        return detailedExplanation;
    }
}
