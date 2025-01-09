package uk.ac.nott.cs.comp2013.frogger.model;

import javafx.scene.image.Image;

public class BackgroundImage extends Actor {
	@Override
	public void act(long now) {

	}

	public BackgroundImage(String imageLink) {
		setImage(new Image(imageLink, 600, 800, true, true));
		
	}
	public BackgroundImage(String imagelink, double x, double y) {
		setImage(new Image(imagelink, x, y, true, true));
	}

}
