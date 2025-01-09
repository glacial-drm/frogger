package uk.ac.nott.cs.comp2013.frogger.model;

import java.util.ArrayList;

import javafx.event.EventHandler;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import uk.ac.nott.cs.comp2013.frogger.view.FroggerUI;


public class Animal extends Actor {
	int points = 0;
	int end = 0;
	int carD = 0;
	int imgSize = 40;

	double w = 800;
	double movement = 13.3333333*2;
	double movementX = 10.666666*2;

	boolean jumpFlag = false;
	boolean noMove = false;
	boolean carDeath = false;
	boolean waterDeath = false;
	boolean changeScore = false;

	ArrayList<End> inter = new ArrayList<End>();
	FroggerUI ui;

	Image imgW1 = new Image("frog-up.png", imgSize, imgSize, true, true);
	Image imgA1 = new Image("frog-left.png", imgSize, imgSize, true, true);
	Image imgS1 = new Image("frog-down.png", imgSize, imgSize, true, true);
	Image imgD1 = new Image("frog-right.png", imgSize, imgSize, true, true);
	Image imgW2 = new Image("frog-up-jump.png", imgSize, imgSize, true, true);
	Image imgA2 = new Image("frog-left-jump.png", imgSize, imgSize, true, true);
	Image imgS2 = new Image("frog-down-jump.png", imgSize, imgSize, true, true);
	Image imgD2 = new Image("frog-right-jump.png", imgSize, imgSize, true, true);

	public Animal(FroggerUI froggerui) {
		setImage(new Image("frog-up.png", imgSize, imgSize, true, true));
		setX(300);
		setY(679.8+movement);

		ui = froggerui;

		setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event){
				if (!noMove){
					if (jumpFlag) {
						setSprite(false, event);
					}
					else{
						setSprite(true, event);
					}
	        	}
			}
		});	
		setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
				if (!noMove){
					setSprite(false, event);
	        	}
			}
			
		});
	}
	
	@Override
	public void act(long now) {
		if (getY()<0 || getY()>734) {
			setX(300);
			setY(679.8+movement);
		}

		if (getX() < 0 || getX() > 575) {
			waterDeath = true;
		}
		if (!getIntersectingObjects(Obstacle.class).isEmpty()) { // Kill on car/truck collision
			carDeath = true;
		}

		if (carDeath) {
			carDeath(now);
		}
		else if (waterDeath) {
			waterDeath(now);
		}

		if (!getIntersectingObjects(Log.class).isEmpty() && !noMove) { // Stood on Log
			move(getIntersectingObjects(Log.class).getFirst().getSpeed(), 0);
		}
		else if (!getIntersectingObjects(Turtle.class).isEmpty() && !noMove) { // Stood on Turtle
			move(getIntersectingObjects(Turtle.class).getFirst().getSpeed(),0);
		}
		else if (!getIntersectingObjects(WetTurtle.class).isEmpty()) { // Stood on Wet Turtle
			if (getIntersectingObjects(WetTurtle.class).getFirst().isSunk()) { // Kill if stood on sunken wet turtle
				waterDeath = true;
			} else {
				move(getIntersectingObjects(WetTurtle.class).getFirst().getSpeed(),0);
			}
		}
		else if (!getIntersectingObjects(End.class).isEmpty()) { // Respawn on end (frog home)
			inter = (ArrayList<End>) getIntersectingObjects(End.class);
			if (getIntersectingObjects(End.class).getFirst().isActivated()) { // In case that home is already activated
				end--;
				points-=50;
			}
			points+=50;
			changeScore = true;
			w=800;
			getIntersectingObjects(End.class).getFirst().setEnd();
			end++;
			setX(300);
			setY(679.8+movement);
		}
		else if (getY()<413){ // Death on water tiles
			waterDeath = true;
		}
	}

	public void carDeath(long now){
		noMove = true;
		if ((now)% 11 ==0) {
			carD++;
		}
		else if (carD==1) {
			setImage(new Image("cardeath1.png", imgSize, imgSize, true, true));
		}
		else if (carD==2) {
			setImage(new Image("cardeath2.png", imgSize, imgSize, true, true));
		}
		else if (carD==3) {
			setImage(new Image("cardeath3.png", imgSize, imgSize, true, true));
		}
		else if (carD == 4) {
			setX(300);
			setY(679.8+movement);
			carDeath = false;
			carD = 0;
			setImage(new Image("froggerUp.png", imgSize, imgSize, true, true));
			noMove = false;
			if (points>50) {
				points-=50;
				changeScore = true;
			}
		}
	}

	public void waterDeath(long now) {
		noMove = true;
		if ((now)% 11 ==0) {
			carD++;
		}
		else if (carD==1) {
			setImage(new Image("waterdeath1.png", imgSize,imgSize , true, true));
		}
		else if (carD==2) {
			setImage(new Image("waterdeath2.png", imgSize,imgSize , true, true));
		}
		else if (carD==3) {
			setImage(new Image("waterdeath3.png", imgSize,imgSize , true, true));
		}
		else if (carD == 4) {
			setImage(new Image("waterdeath4.png", imgSize,imgSize , true, true));
		}
		else if (carD == 5) {
			setX(300);
			setY(679.8+movement);
			waterDeath = false;
			carD = 0;
			setImage(new Image("froggerUp.png", imgSize, imgSize, true, true));
			noMove = false;
			if (points>50) {
				points-=50;
				changeScore = true;
			}
		}
	}

	public void setSprite(boolean jump, KeyEvent event){
		if(jump){
			if (event.getCode() == KeyCode.W) {
				move(0, movement);
				ui.addScore(10);
				setImage(imgW2);
				jumpFlag = true;
			}
			else if (event.getCode() == KeyCode.A) {
				move(movementX, 0);
				setImage(imgA2);
				jumpFlag = true;
			}
			else if (event.getCode() == KeyCode.S) {
				move(0, -movement);
				setImage(imgS2);
				jumpFlag = true;
			}
			else if (event.getCode() == KeyCode.D) {
				move(-movementX, 0);
				setImage(imgD2);
				jumpFlag = true;
			}
		}
		else{
			if (event.getCode() == KeyCode.W) {
				move(0, movement);
				ui.addScore(10);
				changeScore = false;
				setImage(imgW1);
				jumpFlag = false;
			}
			else if (event.getCode() == KeyCode.A) {
				move(movementX, 0);
				setImage(imgA1);
				jumpFlag = false;
			}
			else if (event.getCode() == KeyCode.S) {
				move(0, -movement);
				setImage(imgS1);
				jumpFlag = false;
			}
			else if (event.getCode() == KeyCode.D) {
				move(-movementX, 0);
				setImage(imgD1);
				jumpFlag = false;
			}
		}
	}

	public boolean getStop() {
		return end==5;
	}
	
	public int getPoints() {
		return points;
	}
	
	public boolean changeScore() {
		if (changeScore) {
			changeScore = false;
			return true;
		}
		return false;
		
	}
}
