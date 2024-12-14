package uk.ac.nott.cs.comp2013.frogger;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import uk.ac.nott.cs.comp2013.frogger.controller.*;
import uk.ac.nott.cs.comp2013.frogger.model.*;


public class Main extends Application {
	AnimationTimer timer;
	MyStage background;
	Animal animal;
	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
	    background = new MyStage();
	    Scene scene  = new Scene(background,600,800);
	    
		Obstacle obstacle = new Obstacle("file:src/main/resources/truck1Right.png", 25, 25, 3, 200, 100);
		Obstacle obstacle1 = new Obstacle("file:src/main/resources/truck2Right.png", 100, 100,2, 200, 100 );
		Obstacle obstacle2 = new Obstacle("file:src/main/resources/truck1Right.png",0,  150, 1, 200, 100);

		BackgroundImage froggerback = new BackgroundImage("file:src/main/resources/Screen Shot 2017-05-29 at 10.02.14 PM.png");
	    
		background.add(froggerback);
		
		background.add(new Log("file:src/main/resources/log3.png", 150, 0, 166, 0.75));
		background.add(new Log("file:src/main/resources/log3.png", 150, 220, 166, 0.75));
		background.add(new Log("file:src/main/resources/log3.png", 150, 440, 166, 0.75));
		//background.add(new Log("file:src/main/resources/log3.png", 150, 0, 166, 0.75));
		background.add(new Log("file:src/main/resources/logs.png", 300, 0, 276, -2));
		background.add(new Log("file:src/main/resources/logs.png", 300, 400, 276, -2));
		//background.add(new Log("file:src/main/resources/logs.png", 300, 800, 276, -2));
		background.add(new Log("file:src/main/resources/log3.png", 150, 50, 329, 0.75));
		background.add(new Log("file:src/main/resources/log3.png", 150, 270, 329, 0.75));
		background.add(new Log("file:src/main/resources/log3.png", 150, 490, 329, 0.75));
		//background.add(new Log("file:src/main/resources/log3.png", 150, 570, 329, 0.75));
		
		background.add(new Turtle(500, 376, -1, 130, 130));
		background.add(new Turtle(300, 376, -1, 130, 130));
		background.add(new WetTurtle(700, 376, -1, 130, 130));
		background.add(new WetTurtle(600, 217, -1, 130, 130));
		background.add(new WetTurtle(400, 217, -1, 130, 130));
		background.add(new WetTurtle(200, 217, -1, 130, 130));
//		background.add(new Log("file:src/main/resources/log2.png", 200, 100, 1, 1));
//		background.add(new Log("file:src/main/resources/log2.png", 0, 100, 1, 1));
//		background.add(new Log("file:src/main/resources/log2.png", 100, 120, -1, 1));
//		background.add(new Log("file:src/main/resources/log2.png", 200, 120, -1, 1));
//		background.add(new Log("file:src/main/resources/log2.png", 100, 140, 1, 1));
//		background.add(new Log("file:src/main/resources/log2.png", 200, 140, 1, 1));
//		background.add(new Log("file:src/main/resources/log2.png", 100, 160, -1, 1));
//		background.add(new Log("file:src/main/resources/log2.png", 300, 160, -1, 1));
//		background.add(new Log("file:src/main/resources/log2.png", 100, 180, 1, 1));
//		background.add(new Log("file:src/main/resources/log2.png", 200, 180, 1, 1));
//		background.add(new Log("file:src/main/resources/log2.png", 100, 200, -1, 1));
//		background.add(new Log("file:src/main/resources/log2.png", 200, 200, -1, 1));
//		background.add(new Log("file:src/main/resources/log2.png", 100, 220, 1, 1));
//		background.add(new Log("file:src/main/resources/log2.png", 200, 220, 1, 1));
//		background.add(new Log("file:src/main/resources/log2.png", 400, 220, 1, 1));

		// Frog Homes
		background.add(new End(13,96));
		background.add(new End(141,96));
		background.add(new End(141 + 141-13,96));
		background.add(new End(141 + 141-13+141-13+1,96));
		background.add(new End(141 + 141-13+141-13+141-13+3,96));

		// Controllable Frog
		animal = new Animal("froggerUp.png");
		background.add(animal);

		// Moving obstacles
		background.add(new Obstacle("truck1Right.png", 0, 649, 1, 120, 120));
		background.add(new Obstacle("truck1Right.png", 300, 649, 1, 120, 120));
		background.add(new Obstacle("truck1Right.png", 600, 649, 1, 120, 120));
		background.add(new Obstacle("file:src/main/resources/truck1"+"Right.png", 720, 649, 1, 120, 120));
		background.add(new Obstacle("car1Left.png", 100, 597, -1, 50, 50));
		background.add(new Obstacle("car1Left.png", 250, 597, -1, 50, 50));
		background.add(new Obstacle("car1Left.png", 400, 597, -1, 50, 50));
		background.add(new Obstacle("car1Left.png", 550, 597, -1, 50, 50));
		background.add(new Obstacle("truck2Right.png", 0, 540, 1, 200, 200));
		background.add(new Obstacle("truck2Right.png", 500, 540, 1, 200, 200));
		background.add(new Obstacle("car1Left.png", 500, 490, -5, 50, 50));

		//Top Bar Digits
		background.add(new digit(0, 30, 360, 25));
		background.add(new digit(0, 30, 370, 25));

		background.start();
		primaryStage.setScene(scene);
		primaryStage.show();
		start();
	}
	public void createTimer() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
            	if (animal.changeScore()) {
            		setNumber(animal.getPoints());
            	}
            	if (animal.getStop()) {
            		System.out.print("STOP:");
            		background.stopMusic();
            		stop();
            		background.stop();
            		Alert alert = new Alert(AlertType.INFORMATION);
            		alert.setTitle("You Have Won The Game!");
            		alert.setHeaderText("Your High Score: "+animal.getPoints()+"!");
            		alert.setContentText("Highest Possible Score: 800");
            		alert.show();
            	}
            }
        };
    }
	public void start() {
		background.playMusic();
    	createTimer();
        timer.start();
    }

    public void stop() {
        timer.stop();
    }
    
    public void setNumber(int n) {
    	int shift = 0;
    	while (n < 0) {
    		  int d = n / 10;
    		  int k = n - d * 10;
    		  n = d;
    		  background.add(new digit(k, 30, 360 - shift, 25));
    		  shift+=30;
    		}
    }
}
