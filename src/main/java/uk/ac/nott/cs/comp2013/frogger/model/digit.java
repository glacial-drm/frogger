package uk.ac.nott.cs.comp2013.frogger.model;

import javafx.scene.image.Image;

public class digit extends Actor {
	int dim;
	Image im1;
	@Override
	public void act(long now) {
		// TODO Auto-generated method stub
		
	}
	
	public digit(int n, int dim, int x, int y) {
		im1 = new Image(n+".png", dim, dim, true, true);
		setImage(im1);
		setX(x);
		setY(y);
	}

	public static class BackgroundImage extends Actor {

		@Override
		public void act(long now) {


		}

		public BackgroundImage(String imageLink) {
			setImage(new Image(imageLink, 600, 800, true, true));

		}

	}
}
