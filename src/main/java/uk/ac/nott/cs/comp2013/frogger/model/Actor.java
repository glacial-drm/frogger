package uk.ac.nott.cs.comp2013.frogger.model;

import javafx.scene.image.ImageView;
import javafx.scene.input.InputEvent;
import uk.ac.nott.cs.comp2013.frogger.controller.World;

import java.util.ArrayList;


public abstract class Actor extends ImageView{

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

    public void wrap(boolean direction){
        if(direction){
            if(getX() + getWidth() == 0){ // if left/right edge collides with boundary of window, reposition
                System.out.println("hit the LEFT edge\n");
                //some sort of wait/block mechanism until we are clear of the width of the item
                setX(600);
            }
        }
        else{
            if(getX() == 600){
                System.out.println("hit the RIGHT edge\n");
                setX(0-getWidth());
            }
        }


    }

    public World getWorld() {
        return (World) getParent();
    }

    public double getWidth() {
        return this.getBoundsInLocal().getWidth();
    }

    public double getHeight() {
        return this.getBoundsInLocal().getHeight();
    }

    public <A extends Actor> java.util.List<A> getIntersectingObjects(java.lang.Class<A> cls){
        ArrayList<A> someArray = new ArrayList<A>();
        for (A actor: getWorld().getObjects(cls)) {
            if (actor != this && actor.intersects(this.getBoundsInLocal())) {
                someArray.add(actor);
            }
        }
        return someArray;
    }
    
    public void manageInput(InputEvent e) {
        
    }

    public <A extends Actor> A getOneIntersectingObject(java.lang.Class<A> cls) {
        ArrayList<A> someArray = new ArrayList<A>();
        for (A actor: getWorld().getObjects(cls)) {
            if (actor != this && actor.intersects(this.getBoundsInLocal())) {
                someArray.add(actor);
                break;
            }
        }
        return someArray.get(0);
    }

    public abstract void act(long now);

}
