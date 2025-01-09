package uk.ac.nott.cs.comp2013.frogger;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Background;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import uk.ac.nott.cs.comp2013.frogger.controller.*;
import uk.ac.nott.cs.comp2013.frogger.model.*;
import uk.ac.nott.cs.comp2013.frogger.view.FroggerUI;
import uk.ac.nott.cs.comp2013.frogger.view.Level;


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
		// Start Screen (keep references so we can background.remove() them)
			// Logo
			// Select Frog (anti is reverse controls, angry is 2x movement)
			// Select Difficulty(?)

		background.add(new BackgroundImage("screenstart.png"));
		ui.startGameScreen(background);

		// Game Screen
//		background.add(new BackgroundImage("screengame.png"));
//		Level level = new Level(background, ui);
//		ui.startLevelScreen(background);

		// End Screen ()
			// Score
			// Difficulty
			// Level
			// (Extension) ENTER NAME AND SAVE TO FILE,
			// LEADERBOARD SCREEN ON START MENU
		//background.add(new BackgroundImage("screenend.png"));

		scene = new Scene(background, 600, 800);
		background.start();
		primaryStage.setScene(scene);
		primaryStage.show();
		start();



	}

	public void start() {
		background.playMusic();
    	//createTimer();
        //timer.start();
    }

    public void stop() {
        //timer.stop();
    }
}
