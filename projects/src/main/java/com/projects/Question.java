package com.projects;

import java.util.List;
import java.util.Objects;

public final class Question {

    private final String prompt;
    private final List<String> choices;
    private final int correctIndex;

    public Question(String prompt, List<String> choices, int correctIndex) {
        this.prompt = Objects.requireNonNull(prompt, "prompt");
        this.choices = List.copyOf(Objects.requireNonNull(choices, "choices"));
        if (this.choices.size() < 2) {
            throw new IllegalArgumentException("choices must have at least 2 items");
        }
        if (correctIndex < 0 || correctIndex >= this.choices.size()) {
            throw new IllegalArgumentException("correctIndex out of range");
        }
        this.correctIndex = correctIndex;
    }

    public String getPrompt() {
        return prompt;
    }

    public List<String> getChoices() {
        return choices;
    }

    public int getCorrectIndex() {
        return correctIndex;
    }
}
