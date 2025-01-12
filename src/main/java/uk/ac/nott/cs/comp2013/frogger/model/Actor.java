package uk.ac.nott.cs.comp2013.frogger.model;

import javafx.scene.image.ImageView;
import uk.ac.nott.cs.comp2013.frogger.view.MyStage;

import java.util.ArrayList;

/**
 * Abstract class that is the foundation for all Actor-based objects, inherited to create objects added to the stage
 */
public abstract class Actor extends ImageView{

    /**
     * Used to move an Actor at a pre-specified speed
     * @param dx is the speed along the x-axis
     * @param dy is the speed along the y-axis
     */
    public void move(double dx, double dy) {
        if(dx < 0){ // right moving
            wrap(false);
        }
        else if(dx > 0){ // left moving
            wrap(true);
        }
        setX(getX() - dx);
        setY(getY() - dy);
    }

    /**
     * Allows Actors to flow off an edge and reappear on the other edge
     *
     * @param direction is the direction that the Actor is moving
     */
    public void wrap(boolean direction){
        if(direction){ // if left/right edge collides with boundary of window, reposition
            if(getX() + getWidth() == 0){
                //System.out.println("hit the LEFT edge\n");
                setX(600);
            }
        }
        else{
            if(getX() == 600){
                //System.out.println("hit the RIGHT edge\n");
                setX(0-getWidth());
            }
        }


    }

    /**
     * Getter to return the stage the Actor has been added to
     */
    public MyStage getWorld() {
        return (MyStage) getParent();
    }

    /**
     * Getter to return the width of the Actor for intersection calculations
     */
    public double getWidth() {
        return this.getBoundsInLocal().getWidth();
    }

    /**
     * Returns a list of cls objects, that extend Actor, that intersect with the current Actor
     * @param cls Class that extends Actor
     */
    public <A extends Actor> java.util.List<A> getIntersectingObjects(java.lang.Class<A> cls){
        ArrayList<A> someArray = new ArrayList<A>();
        for (A actor: getWorld().getObjects(cls)) {
            if (actor != this && actor.intersects(this.getBoundsInLocal())) {
                someArray.add(actor);
            }
        }
        return someArray;
    }
    /**
     * Abstract method that specifies the main function of each Actor subclass
     */
    public abstract void act(long now);

}
