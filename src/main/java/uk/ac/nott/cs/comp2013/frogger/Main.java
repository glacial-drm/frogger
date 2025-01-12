package uk.ac.nott.cs.comp2013.frogger;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uk.ac.nott.cs.comp2013.frogger.view.*;
import uk.ac.nott.cs.comp2013.frogger.controller.FroggerUI;


public class Main extends Application {
	AnimationTimer timer;
	MyStage background;
	Scene scene;
	FroggerUI ui;

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
	    background = new MyStage();
		ui = new FroggerUI();


		ui.startGameScreen(background);

		scene = new Scene(background, 600, 800);
		background.start();

		primaryStage.setScene(scene);
		primaryStage.show();
		background.playMusic();
	}
}
