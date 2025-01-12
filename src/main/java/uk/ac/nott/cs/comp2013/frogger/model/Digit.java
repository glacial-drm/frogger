package uk.ac.nott.cs.comp2013.frogger.model;

import javafx.scene.image.Image;

public class Digit extends Actor {
	int dim;
	Image im1;

	@Override
	public void act(long now) {
	}
	
	public Digit(int n, int dimension, int x, int y) {
		//currently only handles integers, need to handle letters, can create new constructor tbh
		dim = dimension;
		setDigit(n);
		setX(x);
		setY(y);
	}

	public void setDigit(int n) {
		im1 = new Image("Sprite-"+n+".png", dim, dim, true, true);
		setImage(im1);
	}
}
