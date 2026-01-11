package com.projects;

import java.util.ArrayList;
import java.util.List;

public final class QuizService {

    private final List<Question> questions;
    private int currentIndex;
    private int correctCount;

    public QuizService() {
        this.questions = defaultQuestions();
        reset();
    }

    public void reset() {
        currentIndex = 0;
        correctCount = 0;
    }

    public int getTotalQuestions() {
        return questions.size();
    }

    public int getCurrentNumber1Based() {
        return Math.min(currentIndex + 1, questions.size());
    }

    public Question getCurrentQuestion() {
        if (questions.isEmpty()) {
            throw new IllegalStateException("No questions configured");
        }
        if (currentIndex < 0 || currentIndex >= questions.size()) {
            throw new IllegalStateException("Quiz index out of range");
        }
        return questions.get(currentIndex);
    }

    public boolean submitAnswer(int selectedIndex) {
        Question question = getCurrentQuestion();
        boolean isCorrect = selectedIndex == question.getCorrectIndex();
        if (isCorrect) {
            correctCount++;
        }
        return isCorrect;
    }

    public boolean hasNext() {
        return currentIndex + 1 < questions.size();
    }

    public void next() {
        if (!hasNext()) {
            throw new IllegalStateException("No next question");
        }
        currentIndex++;
    }

    public int getCorrectCount() {
        return correctCount;
    }

    public int getScorePercent() {
        if (questions.isEmpty()) {
            return 0;
        }
        return (int) Math.round((correctCount * 100.0) / questions.size());
    }

    private static List<Question> defaultQuestions() {
        List<Question> list = new ArrayList<>();

        list.add(new Question(
                "Which keyword is used to inherit a class in Java?",
                List.of("this", "extends", "implements", "super"),
                1));

        list.add(new Question(
                "Which JavaFX file is used to describe UI layouts?",
                List.of(".css", ".fxml", ".json", ".xmlx"),
                1));

        list.add(new Question(
                "What does JVM stand for?",
                List.of("Java Variable Method", "Java Virtual Machine", "Just Virtual Memory", "Joint Vector Model"),
                1));

        list.add(new Question(
                "Which collection does NOT allow duplicates?",
                List.of("List", "Set", "ArrayList", "LinkedList"),
                1));

        list.add(new Question(
                "In Java, which operator compares primitive values for equality?",
                List.of("==", "equals", "!=", "="),
                0));

        list.add(new Question(
                "Which JavaFX control is best for single-choice options?",
                List.of("RadioButton", "TextArea", "ProgressBar", "ImageView"),
                0));

        list.add(new Question(
                "Which is a valid access modifier?",
                List.of("visible", "global", "private", "friend"),
                2));

        list.add(new Question(
                "What does CSS stand for?",
                List.of("Cascading Style Sheets", "Computer Style System", "Code Syntax Styling", "Creative Style Script"),
                0));

        return List.copyOf(list);
    }
}
