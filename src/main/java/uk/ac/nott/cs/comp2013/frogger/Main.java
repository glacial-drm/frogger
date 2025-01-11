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


		ui.startGameScreen(background);

		scene = new Scene(background, 600, 800);
		background.start();

		primaryStage.setScene(scene);
		primaryStage.show();
		background.playMusic();
	}
}
