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
		setX(300);
		setY(679.8+movement);
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
		                move(0, -movement);
		                changeScore = false;
		                setImage(imgW1);
		                second = false;
		            }
		            else if (event.getCode() == KeyCode.A) {	            	
		            	 move(-movementX, 0);
		            	 setImage(imgA1);
		            	 second = false;
		            }
		            else if (event.getCode() == KeyCode.S) {	            	
		            	 move(0, movement);
		            	 setImage(imgS1);
		            	 second = false;
		            }
		            else if (event.getCode() == KeyCode.D) {	            	
		            	 move(movementX, 0);
		            	 setImage(imgD1);
		            	 second = false;
		            }
				}
				else if (event.getCode() == KeyCode.W) {	            	
	                move(0, -movement);
	                setImage(imgW2);
	                second = true;
	            }
	            else if (event.getCode() == KeyCode.A) {	            	
	            	 move(-movementX, 0);
	            	 setImage(imgA2);
	            	 second = true;
	            }
	            else if (event.getCode() == KeyCode.S) {	            	
	            	 move(0, movement);
	            	 setImage(imgS2);
	            	 second = true;
	            }
	            else if (event.getCode() == KeyCode.D) {	            	
	            	 move(movementX, 0);
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
	                move(0, -movement);
	                setImage(imgW1);
	                second = false;
	            }
	            else if (event.getCode() == KeyCode.A) {	            	
	            	 move(-movementX, 0);
	            	 setImage(imgA1);
	            	 second = false;
	            }
	            else if (event.getCode() == KeyCode.S) {	            	
	            	 move(0, movement);
	            	 setImage(imgS1);
	            	 second = false;
	            }
	            else if (event.getCode() == KeyCode.D) {	            	
	            	 move(movementX, 0);
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
		if (getX()<0) {
			move(movement*2, 0);
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
		
		if (getX()>600) {
			move(-movement*2, 0);
		}
		if (getIntersectingObjects(Obstacle.class).size() >= 1) {
			carDeath = true;
		}
		if (getX() == 240 && getY() == 82) {
			stop = true;
		}
		if (getIntersectingObjects(Log.class).size() >= 1 && !noMove) {
			if(getIntersectingObjects(Log.class).get(0).getLeft())
				move(-2,0);
			else
				move (.75,0);
		}
		else if (getIntersectingObjects(Turtle.class).size() >= 1 && !noMove) {
			move(-1,0);
		}
		else if (getIntersectingObjects(WetTurtle.class).size() >= 1) {
			if (getIntersectingObjects(WetTurtle.class).get(0).isSunk()) {
				waterDeath = true;
			} else {
				move(-1,0);
			}
		}
		else if (getIntersectingObjects(End.class).size() >= 1) {
			inter = (ArrayList<End>) getIntersectingObjects(End.class);
			if (getIntersectingObjects(End.class).get(0).isActivated()) {
				end--;
				points-=50;
			}
			points+=50;
			changeScore = true;
			w=800;
			getIntersectingObjects(End.class).get(0).setEnd();
			end++;
			setX(300);
			setY(679.8+movement);
		}
		else if (getY()<413){
			waterDeath = true;
			//setX(300);
			//setY(679.8+movement);
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


	public static class WetTurtle extends Actor {
		Image t1;
		Image t2;
		Image t3;
		Image t4;
		int speed;
		int i = 1;
		boolean bool = true;
		boolean sunk = false;
		@Override
		public void act(long now) {

					if (now/900000000  % 4 ==0) {
						setImage(t2);
						sunk = false;

					}
					else if (now/900000000 % 4 == 1) {
						setImage(t1);
						sunk = false;
					}
					else if (now/900000000 %4 == 2) {
						setImage(t3);
						sunk = false;
					} else if (now/900000000 %4 == 3) {
						setImage(t4);
						sunk = true;
					}

			move(speed , 0);
			if (getX() > 600 && speed>0)
				setX(-200);
			if (getX() < -75 && speed<0)
				setX(600);
		}
		public WetTurtle(int xpos, int ypos, int s, int w, int h) {
			t1 = new Image("TurtleAnimation1.png", h, w, true, true);
			t2 = new Image("TurtleAnimation2Wet.png", h, w, true, true);
			t3 = new Image("TurtleAnimation3Wet.png", w, h, true, true);
			t4 = new Image("TurtleAnimation4Wet.png", w, h, true, true);
			setX(xpos);
			setY(ypos);
			speed = s;
			setImage(t2);
		}
		public boolean isSunk() {
			return sunk;
		}
	}

	public static class Turtle extends Actor {
		Image turtle1;
		Image turtle2;
		Image turtle3;
		public int speed;
		int i = 1;
		boolean bool = true;
		@Override
		public void act(long now) {

					if (now/900000000  % 3 ==0) {
						setImage(turtle2);

					}
					else if (now/900000000 % 3 == 1) {
						setImage(turtle1);

					}
					else if (now/900000000 %3 == 2) {
						setImage(turtle3);

					}

			move(speed , 0);
			if (getX() > 600 && speed>0)
				setX(-200);
			if (getX() < -75 && speed<0)
				setX(600);
		}
		public Turtle(int xpos, int ypos, int s, int w, int h) {
			turtle1 = new Image("TurtleAnimation1.png", w, h, true, true);
			turtle2 = new Image("TurtleAnimation2.png", w, h, true, true);
			turtle3 = new Image("TurtleAnimation3.png", w, h, true, true);
			setX(xpos);
			setY(ypos);
			speed = s;
			setImage(turtle2);
		}
	}

	public static class Log extends Actor {

		private double speed;
		@Override
		public void act(long now) {
			move(speed , 0);
			if (getX()>600 && speed>0)
				setX(-180);
			if (getX()<-300 && speed<0)
				setX(700);
		}

		public Log(String imageLink, int size, int xpos, int ypos, double s) {
			setImage(new Image(imageLink, size,size, true, true));
			setX(xpos);
			setY(ypos);
			speed = s;

		}
		public boolean getLeft() {
			return speed < 0;
		}
	}
}
