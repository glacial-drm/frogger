package uk.ac.nott.cs.comp2013.frogger.model;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import uk.ac.nott.cs.comp2013.frogger.controller.FroggerUI;

import java.util.ArrayList;
import java.util.Objects;


public class Animal extends Actor {
    int points = 0;
    int end = 0;
    int carD = 0;
    int imgSize = 40;

    double w = 800;
    double movement = 13.3333333 * 2;
    double movementX = 10.666666 * 2;

    boolean jumpFlag = false;
    boolean noMove = false;
    boolean carDeath = false;
    boolean waterDeath = false;

    ArrayList<End> inter = new ArrayList<End>();
    FroggerUI ui;

    Image imgW1;
    Image imgA1;
    Image imgS1;
    Image imgD1;
    Image imgW2;
    Image imgA2;
    Image imgS2;
    Image imgD2;

    KeyCode[] keyCodesVert = new KeyCode[2];
    KeyCode[] keyCodesHor = new KeyCode[2];

    public Animal(FroggerUI froggerui) {
        ui = froggerui;
        setFrogSprite();
        setImage(imgW1);
        setX(300);
        setY(679.8 + movement);


        setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                if (!noMove) {
                    setSprite(!jumpFlag, event);
                }
            }
        });
        setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                if (!noMove) {
                    setSprite(false, event);
                }
            }

        });
    }

    public void setFrogSprite() {

        String[] frogToken = ui.getEquipped_frog().split("[.]");
        System.out.println(frogToken[0]);
        String[] frogType = frogToken[0].split("-");

        imgW1 = new Image(ui.getEquipped_frog(), imgSize, imgSize, true, true);
        imgA1 = new Image("frog-" + frogType[1] + "-left.png", imgSize, imgSize, true, true);
        imgS1 = new Image("frog-" + frogType[1] + "-down.png", imgSize, imgSize, true, true);
        imgD1 = new Image("frog-" + frogType[1] + "-right.png", imgSize, imgSize, true, true);
        imgW2 = new Image("frog-" + frogType[1] + "-up-jump.png", imgSize, imgSize, true, true);
        imgA2 = new Image("frog-" + frogType[1] + "-left-jump.png", imgSize, imgSize, true, true);
        imgS2 = new Image("frog-" + frogType[1] + "-down-jump.png", imgSize, imgSize, true, true);
        imgD2 = new Image("frog-" + frogType[1] + "-right-jump.png", imgSize, imgSize, true, true);
    }

    @Override
    public void act(long now) {
        if (getY() < 0 || getY() > 734) {
            setX(300);
            setY(679.8 + movement);
        }

        if (getX() < 0 || getX() > 575) {
            waterDeath = true;
        }
        if (!getIntersectingObjects(Obstacle.class).isEmpty()) { // Kill on car/truck collision
            carDeath = true;
        }

        if (carDeath) {
            carDeath(now);
        } else if (waterDeath) {
            waterDeath(now);
        }

        if (!getIntersectingObjects(Log.class).isEmpty() && !noMove) { // Stood on Log
            move(getIntersectingObjects(Log.class).getFirst().getSpeed(), 0);
        } else if (!getIntersectingObjects(Turtle.class).isEmpty() && !noMove) { // Stood on Turtle
            move(getIntersectingObjects(Turtle.class).getFirst().getSpeed(), 0);
        } else if (!getIntersectingObjects(WetTurtle.class).isEmpty()) { // Stood on Wet Turtle
            if (getIntersectingObjects(WetTurtle.class).getFirst().isSunk()) { // Kill if stood on sunken wet turtle
                waterDeath = true;
            } else {
                move(getIntersectingObjects(WetTurtle.class).getFirst().getSpeed(), 0);
            }
        } else if (!getIntersectingObjects(End.class).isEmpty()) { // Respawn on end (frog home)
            inter = (ArrayList<End>) getIntersectingObjects(End.class);
            if (getIntersectingObjects(End.class).getFirst().isActivated()) { // In case that home is already activated
                end--;
            } else {
                ui.restartTimer();
            }
            w = 800;
            getIntersectingObjects(End.class).getFirst().setEnd();
            end++;
            if (end == 5) {
                for (End n : ui.getBackground().getObjects(End.class)) {
                    n.reset();
                }

            }
            setX(300);
            setY(679.8 + movement);
        } else if (getY() < 413) { // Death on water tiles
            waterDeath = true;
        }
    }

    public void carDeath(long now) {
        noMove = true;
        if ((now) % 11 == 0) {
            carD++;
        } else if (carD == 1) {
            setImage(new Image("cardeath1.png", imgSize, imgSize, true, true));
        } else if (carD == 2) {
            setImage(new Image("cardeath2.png", imgSize, imgSize, true, true));
        } else if (carD == 3) {
            setImage(new Image("cardeath3.png", imgSize, imgSize, true, true));
        } else if (carD == 4) {
            setX(300);
            setY(679.8 + movement);
            carDeath = false;
            carD = 0;
            setImage(imgW1);
            noMove = false;
            if (points > 50) {
                points -= 50;
            }
        }
    }

    public void waterDeath(long now) {
        noMove = true;
        if ((now) % 11 == 0) {
            carD++;
        } else if (carD == 1) {
            setImage(new Image("waterdeath1.png", imgSize, imgSize, true, true));
        } else if (carD == 2) {
            setImage(new Image("waterdeath2.png", imgSize, imgSize, true, true));
        } else if (carD == 3) {
            setImage(new Image("waterdeath3.png", imgSize, imgSize, true, true));
        } else if (carD == 4) {
            setImage(new Image("waterdeath4.png", imgSize, imgSize, true, true));
        } else if (carD == 5) {
            setX(300);
            setY(679.8 + movement);
            waterDeath = false;
            carD = 0;
            setImage(new Image(ui.getEquipped_frog(), imgSize, imgSize, true, true));
            noMove = false;
            if (points > 50) {
                points -= 50;
            }
        }
    }

    public void setSprite(boolean jump, KeyEvent event) {
        keyCodesVert[0] = KeyCode.W;
        keyCodesHor[0] = KeyCode.A;
        keyCodesVert[1] = KeyCode.S;
        keyCodesHor[1] = KeyCode.D;

        if (Objects.equals(ui.getEquipped_frog(), "frog-angry-up.png")) {
            movement = 13.3333333 * 4;
        } else if (Objects.equals(ui.getEquipped_frog(), "frog-anti-up.png")) {
            keyCodesVert[1] = KeyCode.W;
            keyCodesHor[1] = KeyCode.A;
            keyCodesVert[0] = KeyCode.S;
            keyCodesHor[0] = KeyCode.D;
        }

        if (jump) {
            if (event.getCode() == keyCodesVert[0]) {
                move(0, movement);
                ui.addScore(10);
                setImage(imgW2);
            } else if (event.getCode() == keyCodesHor[0]) {
                move(movementX, 0);
                setImage(imgA2);
            } else if (event.getCode() == keyCodesVert[1]) {
                move(0, -movement);
                setImage(imgS2);
            } else if (event.getCode() == keyCodesHor[1]) {
                move(-movementX, 0);
                setImage(imgD2);
            }
            jumpFlag = true;
        } else {
            if (event.getCode() == keyCodesVert[0]) {
                move(0, movement);
                ui.addScore(10);
                setImage(imgW1);
            } else if (event.getCode() == keyCodesHor[0]) {
                move(movementX, 0);
                setImage(imgA1);
            } else if (event.getCode() == keyCodesVert[1]) {
                move(0, -movement);
                setImage(imgS1);
            } else if (event.getCode() == keyCodesHor[1]) {
                move(-movementX, 0);
                setImage(imgD1);
            }
            jumpFlag = false;
        }
        movement = 13.3333333 * 2;
    }
}
