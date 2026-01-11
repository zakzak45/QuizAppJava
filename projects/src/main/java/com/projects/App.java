package com.projects;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

	private static final String STYLESHEET_PATH = "/com/projects/styles.css";

	private static Scene scene;
	private static final QuizService quizService = new QuizService();

	@Override
	public void start(Stage stage) throws IOException {
		scene = new Scene(loadFXML("primary"), 720, 480);
		scene.getStylesheets().add(App.class.getResource(STYLESHEET_PATH).toExternalForm());
		stage.setTitle("Quiz App");
		stage.setScene(scene);
		stage.show();
	}

	static void setRoot(String fxml) throws IOException {
		scene.setRoot(loadFXML(fxml));
	}

	static QuizService getQuizService() {
		return quizService;
	}

	private static Parent loadFXML(String fxml) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/projects/" + fxml + ".fxml"));
		return fxmlLoader.load();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
