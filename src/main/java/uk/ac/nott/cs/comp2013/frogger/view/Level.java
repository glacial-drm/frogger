package uk.ac.nott.cs.comp2013.frogger.view;

import uk.ac.nott.cs.comp2013.frogger.controller.MyStage;
import uk.ac.nott.cs.comp2013.frogger.model.*;

public class Level {
    MyStage background;
    FroggerUI ui;

    int level;

    public Level(MyStage stage, FroggerUI froggerUI){
        background = stage;
        ui = froggerUI;
        level = 1;

        startLevel(level);
    }


    public void clearLevel(){
        //background.remove();
    }

    public void startLevel(int level){
        // Logs
        background.add(new Log("log3.png", 150, 0, 166, -1));
        background.add(new Log("log3.png", 150, 220, 166, -1));
        background.add(new Log("log3.png", 150, 440, 166, -1));
        background.add(new Log("log3.png", 150, 0, 166, -1));
        background.add(new Log("logs.png", 300, 0, 276, -2));
        background.add(new Log("logs.png", 300, 400, 276, -2));
        background.add(new Log("logs.png", 300, 800, 276, -2));
        background.add(new Log("log3.png", 150, 50, 329, -1));
        background.add(new Log("log3.png", 150, 270, 329, -1));
        background.add(new Log("log3.png", 150, 490, 329, -1));

        // Turtles
        background.add(new Turtle(500, 376, 1, 130, 130));
        background.add(new Turtle(300, 376, 1, 130, 130));
        background.add(new WetTurtle(700, 376, 1, 130, 130));
        background.add(new WetTurtle(600, 217, 1, 130, 130));
        background.add(new WetTurtle(400, 217, 1, 130, 130));
        background.add(new WetTurtle(200, 217, 1, 130, 130));

        // Controllable Frog
        background.add(new Animal(ui));

        // Frog Homes
        background.add(new End(13,96, ui));
        background.add(new End(141,96, ui));
        background.add(new End(141 + 141-13,96, ui));
        background.add(new End(141 + 141-13+141-13+1,96, ui));
        background.add(new End(141 + 141-13+141-13+141-13+3,96, ui));

        // Cars / Trucks
        background.add(new Obstacle("truck1Left.png", 0, 649, 1, 120, 120));
        background.add(new Obstacle("truck1Left.png", 300, 649, 1, 120, 120));
        background.add(new Obstacle("truck1Left.png", 600, 649, 1, 120, 120));
        background.add(new Obstacle("truck1Left.png", 720, 649, 1, 120, 120));
        background.add(new Obstacle("truck2Left.png", 0, 540, 1, 200, 200));
        background.add(new Obstacle("truck2Left.png", 500, 540, 1, 200, 200));

        background.add(new Obstacle("car1Right.png", 100, 597, -1, 50, 50));
        background.add(new Obstacle("car1Right.png", 250, 597, -1, 50, 50));
        background.add(new Obstacle("car1Right.png", 400, 597, -1, 50, 50));
        background.add(new Obstacle("car1Right.png", 550, 597, -1, 50, 50));
        background.add(new Obstacle("car1Right.png", 500, 490, -5, 50, 50));
    }

    public int getLevel(){
        return level;
    }
    public void setLevel(int n){
        level = n;
    }
}
