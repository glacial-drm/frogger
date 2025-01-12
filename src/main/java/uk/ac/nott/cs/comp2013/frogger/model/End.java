package uk.ac.nott.cs.comp2013.frogger.model;

import javafx.scene.image.Image;
import uk.ac.nott.cs.comp2013.frogger.controller.FroggerUI;

public class End extends Actor {
	boolean activated = false;
	FroggerUI froggerUI;

	@Override
	public void act(long now) {
	}
	
	public End(int x, int y, FroggerUI ui) {
		setX(x);
		setY(y);
		setImage(new Image("End.png", 60, 60, true, true));
		froggerUI = ui;
	}
	
	public void setEnd() {
		setImage(new Image("FrogEnd.png", 70, 70, true, true));
		activated = true;
		froggerUI.addScore(500);
	}

	public void reset(){
		setImage(new Image("End.png", 60, 60, true, true));
	}

	public boolean isActivated() {
		return activated;
	}
	

}
