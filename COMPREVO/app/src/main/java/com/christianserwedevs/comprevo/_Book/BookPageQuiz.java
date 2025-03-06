package com.christianserwedevs.comprevo._Book;
import java.util.List;
public class BookPageQuiz extends BookPage {
    private List<QuizSet> quizSets;

    public BookPageQuiz(List<QuizSet> quizSets) {
        super("", 0);
        this.quizSets = quizSets;
    }

    public List<QuizSet> getQuizSets() {
        return quizSets;
    }
}
