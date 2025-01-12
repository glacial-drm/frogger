package uk.ac.nott.cs.comp2013.frogger.controller;


import javafx.application.Platform;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import uk.ac.nott.cs.comp2013.frogger.view.MyStage;
import uk.ac.nott.cs.comp2013.frogger.model.*;

class LevelTest {
    MyStage stage;
    FroggerUI froggerUI;
    Level level;

    @BeforeAll
    public static void setUp(){
        Platform.startup(() -> {});

    }

    @Test
    void testClearLevel() {
        Platform.runLater(() -> {
            stage = new MyStage();
            froggerUI = new FroggerUI();
            level = new Level(stage, froggerUI);


            Assertions.assertTrue(stage.getObjects(Log.class).isEmpty());
            Assertions.assertTrue(stage.getObjects(Turtle.class).isEmpty());
            Assertions.assertTrue(stage.getObjects(WetTurtle.class).isEmpty());
            Assertions.assertTrue(stage.getObjects(Animal.class).isEmpty());
            Assertions.assertTrue(stage.getObjects(End.class).isEmpty());
            Assertions.assertTrue(stage.getObjects(Obstacle.class).isEmpty());
        });
    }

    @Test
    void testStartLevel() {
        Platform.runLater(() -> {
            stage = new MyStage();
            froggerUI = new FroggerUI();
            level = new Level(stage, froggerUI);
            level.clearLevel();
            level.startLevel(1);

            Assertions.assertFalse(stage.getObjects(Log.class).isEmpty());
            Assertions.assertFalse(stage.getObjects(Turtle.class).isEmpty());
            Assertions.assertFalse(stage.getObjects(WetTurtle.class).isEmpty());
            Assertions.assertFalse(stage.getObjects(Animal.class).isEmpty());
            Assertions.assertFalse(stage.getObjects(End.class).isEmpty());
            Assertions.assertFalse(stage.getObjects(Obstacle.class).isEmpty());
        });
    }

}