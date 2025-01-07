package uk.ac.nott.cs.comp2013.frogger.model;

import java.util.ArrayList;

import javafx.event.EventHandler;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


public class Animal extends Actor {
	Image imgW1;
	Image imgA1;
	Image imgS1;
	Image imgD1;
	Image imgW2;
	Image imgA2;
	Image imgS2;
	Image imgD2;
	int points = 0;
	int end = 0;
	private boolean second = false;
	boolean noMove = false;
	double movement = 13.3333333*2;
	double movementX = 10.666666*2;
	int imgSize = 40;
	boolean carDeath = false;
	boolean waterDeath = false;
	boolean stop = false;
	boolean changeScore = false;
	int carD = 0;
	double w = 800;
	ArrayList<End> inter = new ArrayList<End>();

	public Animal(String imageLink) {
		setImage(new Image(imageLink, imgSize, imgSize, true, true));
		setX(/*300*/30);
		setY(/*679.8*/166/*+movement*/);
		imgW1 = new Image("froggerUp.png", imgSize, imgSize, true, true);
		imgA1 = new Image("froggerLeft.png", imgSize, imgSize, true, true);
		imgS1 = new Image("froggerDown.png", imgSize, imgSize, true, true);
		imgD1 = new Image("froggerRight.png", imgSize, imgSize, true, true);

		imgW2 = new Image("froggerUpJump.png", imgSize, imgSize, true, true);
		imgA2 = new Image("froggerLeftJump.png", imgSize, imgSize, true, true);
		imgS2 = new Image("froggerDownJump.png", imgSize, imgSize, true, true);
		imgD2 = new Image("froggerRightJump.png", imgSize, imgSize, true, true);

		setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event){
				if (noMove) {
					
				}
				else {
				if (second) {
					if (event.getCode() == KeyCode.W) {
		                move(0, movement);
		                changeScore = false;
		                setImage(imgW1);
		                second = false;
		            }
		            else if (event.getCode() == KeyCode.A) {	            	
		            	 move(movementX, 0);
		            	 setImage(imgA1);
		            	 second = false;
		            }
		            else if (event.getCode() == KeyCode.S) {
		            	 move(0, -movement);
		            	 setImage(imgS1);
		            	 second = false;
		            }
		            else if (event.getCode() == KeyCode.D) {	            	
		            	 move(-movementX, 0);
		            	 setImage(imgD1);
		            	 second = false;
		            }
				}
				else if (event.getCode() == KeyCode.W) {
	                move(0, movement);
	                setImage(imgW2);
	                second = true;
	            }
	            else if (event.getCode() == KeyCode.A) {	            	
	            	 move(movementX, 0);
	            	 setImage(imgA2);
	            	 second = true;
	            }
	            else if (event.getCode() == KeyCode.S) {
	            	 move(0, -movement);
	            	 setImage(imgS2);
	            	 second = true;
	            }
	            else if (event.getCode() == KeyCode.D) {	            	
	            	 move(-movementX, 0);
	            	 setImage(imgD2);
	            	 second = true;
	            }
	        }
			}
		});	
		setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
				if (noMove) {}
				else {
				if (event.getCode() == KeyCode.W) {	  
					if (getY() < w) {
						changeScore = true;
						w = getY();
						points+=10;
					}
	                move(0, movement);
	                setImage(imgW1);
	                second = false;
	            }
	            else if (event.getCode() == KeyCode.A) {	            	
	            	 move(movementX, 0);
	            	 setImage(imgA1);
	            	 second = false;
	            }
	            else if (event.getCode() == KeyCode.S) {	            	
	            	 move(0, -movement);
	            	 setImage(imgS1);
	            	 second = false;
	            }
	            else if (event.getCode() == KeyCode.D) {	            	
	            	 move(-movementX, 0);
	            	 setImage(imgD1);
	            	 second = false;
	            }
	        }
			}
			
		});
	}
	
	@Override
	public void act(long now) {
		int bounds = 0;
		if (getY()<0 || getY()>734) {
			setX(300);
			setY(679.8+movement);
		}
		if (getX() < 0 || getX() > 575) {
			waterDeath = true;
		}
		if (carDeath) {
			noMove = true;
			if ((now)% 11 ==0) {
				carD++;
			}
			if (carD==1) {
				setImage(new Image("cardeath1.png", imgSize, imgSize, true, true));
			}
			if (carD==2) {
				setImage(new Image("cardeath2.png", imgSize, imgSize, true, true));
			}
			if (carD==3) {
				setImage(new Image("cardeath3.png", imgSize, imgSize, true, true));
			}
			if (carD == 4) {
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
		if (waterDeath) {
			noMove = true;
			if ((now)% 11 ==0) {
				carD++;
			}
			if (carD==1) {
				setImage(new Image("waterdeath1.png", imgSize,imgSize , true, true));
			}
			if (carD==2) {
				setImage(new Image("waterdeath2.png", imgSize,imgSize , true, true));
			}
			if (carD==3) {
				setImage(new Image("waterdeath3.png", imgSize,imgSize , true, true));
			}
			if (carD == 4) {
				setImage(new Image("waterdeath4.png", imgSize,imgSize , true, true));
			}
			if (carD == 5) {
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
		
		if (getX()>600) { // Prevent movement off right board
			//move(-movement*2, 0);
		}
		if (!getIntersectingObjects(Obstacle.class).isEmpty()) { // Kill on car/truck collision
			carDeath = true;
		}
		if (getX() == 240 && getY() == 82) { // Respawn on frog home(? only checks one of them)
			stop = true;
		}

		if (!getIntersectingObjects(Log.class).isEmpty() && !noMove) { // Stood on Log
			move(getIntersectingObjects(Log.class).getFirst().getSpeed(), 0);
		}
		else if (!getIntersectingObjects(Turtle.class).isEmpty() && !noMove) { // Stood on Turtle
			move(getIntersectingObjects(Turtle.class).getFirst().getSpeed(),0);
		}
		else if (!getIntersectingObjects(WetTurtle.class).isEmpty()) { // Stood on Wet Turtle
			if (getIntersectingObjects(WetTurtle.class).getFirst().isSunk()) { // Kill if stood on sunken wet turtle?
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
