package com.projects;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SecondaryController {
    @FXML
    private Label scoreLabel;

    @FXML
    private Label messageLabel;

    @FXML
    private void initialize() {
        QuizService quiz = App.getQuizService();
        int correct = quiz.getCorrectCount();
        int total = quiz.getTotalQuestions();
        int percent = quiz.getScorePercent();

        scoreLabel.setText("Score: " + correct + "/" + total + " (" + percent + "%)");
        messageLabel.setText(percent >= 70 ? "Great job!" : "Good try — hit Restart to improve.");
    }

    @FXML
    private void restartQuiz() throws IOException {
        App.getQuizService().reset();
        App.setRoot("primary");
    }
}