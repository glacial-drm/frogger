package uk.ac.nott.cs.comp2013.frogger.model;

import javafx.scene.image.Image;

public class BackgroundImage extends Actor {

	private String path;
	private double dimX;
	private double dimY;

	@Override
	public void act(long now) {

	}

	public BackgroundImage(String imagelink) {
		setImage(new Image(imagelink, 600, 800, true, true));
		path = imagelink;
		dimX = 600;
		dimY = 800;
	}
	public BackgroundImage(String imagelink, double x, double y) {
		setImage(new Image(imagelink, x, y, true, true));
		path = imagelink;
		dimX = x;
		dimY = y;
	}

	public double getDim(char character) {
		switch (character) {
			case 'x': return dimX;
		}
		return dimY;
	}

	public String getPath(){
		return path;
	}

}
