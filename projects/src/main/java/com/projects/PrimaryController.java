package com.projects;

import java.io.IOException;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

public class PrimaryController {

    @FXML
    private Label progressLabel;

    @FXML
    private Label questionLabel;

    @FXML
    private ToggleGroup optionsGroup;

    @FXML
    private RadioButton option0;

    @FXML
    private RadioButton option1;

    @FXML
    private RadioButton option2;

    @FXML
    private RadioButton option3;

    @FXML
    private Label feedbackLabel;

    @FXML
    private Button nextButton;

    private boolean answered;

    @FXML
    private void initialize() {
        loadCurrentQuestion();
    }

    @FXML
    private void onOptionSelected() {
        if (!answered) {
            nextButton.setDisable(false);
            feedbackLabel.setText("Ready when you are.");
        }
    }

    @FXML
    private void onNext() throws IOException {
        QuizService quiz = App.getQuizService();

        if (!answered) {
            int selectedIndex = getSelectedIndex();
            if (selectedIndex < 0) {
                feedbackLabel.setText("Please select an answer.");
                return;
            }

            boolean correct = quiz.submitAnswer(selectedIndex);
            answered = true;

            int correctIndex = quiz.getCurrentQuestion().getCorrectIndex();
            setOptionStyles(correctIndex, selectedIndex);

            if (correct) {
                feedbackLabel.setText("Correct!");
            } else {
                feedbackLabel.setText("Not quite. Correct answer highlighted.");
            }

            nextButton.setText(quiz.hasNext() ? "Next" : "Finish");
            return;
        }

        if (quiz.hasNext()) {
            quiz.next();
            loadCurrentQuestion();
        } else {
            App.setRoot("secondary");
        }
    }

    private void loadCurrentQuestion() {
        QuizService quiz = App.getQuizService();
        Question question = quiz.getCurrentQuestion();
        List<String> choices = question.getChoices();

        progressLabel.setText(quiz.getCurrentNumber1Based() + " / " + quiz.getTotalQuestions());
        questionLabel.setText(question.getPrompt());

        option0.setText(getChoice(choices, 0));
        option1.setText(getChoice(choices, 1));
        option2.setText(getChoice(choices, 2));
        option3.setText(getChoice(choices, 3));

        option0.setVisible(choices.size() > 0);
        option1.setVisible(choices.size() > 1);
        option2.setVisible(choices.size() > 2);
        option3.setVisible(choices.size() > 3);

        option0.setManaged(option0.isVisible());
        option1.setManaged(option1.isVisible());
        option2.setManaged(option2.isVisible());
        option3.setManaged(option3.isVisible());

        clearOptionStyles();
        optionsGroup.selectToggle(null);
        answered = false;
        nextButton.setDisable(true);
        nextButton.setText("Next");
        feedbackLabel.setText("Select an answer to continue");
    }

    private static String getChoice(List<String> choices, int index) {
        if (index < 0 || index >= choices.size()) {
            return "";
        }
        return choices.get(index);
    }

    private int getSelectedIndex() {
        Toggle selected = optionsGroup.getSelectedToggle();
        if (selected == null) {
            return -1;
        }
        if (selected == option0) {
            return 0;
        }
        if (selected == option1) {
            return 1;
        }
        if (selected == option2) {
            return 2;
        }
        if (selected == option3) {
            return 3;
        }
        return -1;
    }

    private void clearOptionStyles() {
        option0.getStyleClass().removeAll("correct", "wrong");
        option1.getStyleClass().removeAll("correct", "wrong");
        option2.getStyleClass().removeAll("correct", "wrong");
        option3.getStyleClass().removeAll("correct", "wrong");
    }

    private void setOptionStyles(int correctIndex, int selectedIndex) {
        clearOptionStyles();

        RadioButton correctButton = getOptionButton(correctIndex);
        if (correctButton != null) {
            correctButton.getStyleClass().add("correct");
        }

        if (selectedIndex != correctIndex) {
            RadioButton selectedButton = getOptionButton(selectedIndex);
            if (selectedButton != null) {
                selectedButton.getStyleClass().add("wrong");
            }
        }
    }

    private RadioButton getOptionButton(int index) {
        switch (index) {
            case 0:
                return option0;
            case 1:
                return option1;
            case 2:
                return option2;
            case 3:
                return option3;
            default:
                return null;
        }
    }
}
