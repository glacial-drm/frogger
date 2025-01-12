package uk.ac.nott.cs.comp2013.frogger.model;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import uk.ac.nott.cs.comp2013.frogger.view.MyStage;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ActorTests {

    @BeforeAll
    public static void setUp(){
        Platform.startup(() -> {});

    }

    @Test
    public void testIntersectEmpty() {
        MyStage stage = new MyStage();

        Actor actor = new Actor() {
            @Override
            public void act(long now) {
            }
        };
        stage.add(actor);
        actor.setX(100);
        assert(actor.getIntersectingObjects(Actor.class).isEmpty());
    }

    @Test
    public void testIntersectingObjects_OneIntersectingObject() {
        MyStage stage = new MyStage();

        Actor actor = new Actor() {
            @Override
            public void act(long now) {
            }
        };
        Actor intersect = new Actor() {
            @Override
            public void act(long now) {
            }
        };

        stage.add(actor);
        stage.add(intersect);

        actor.setX(100);
        actor.setY(100);
        intersect.setX(100);
        intersect.setY(100);

        List<Actor> result = actor.getIntersectingObjects(Actor.class);

        assertTrue(result.contains(intersect));
    }

    @Test
    public void testIntersectingEmpty() {
        MyStage stage = new MyStage();

        Actor actor = new Actor() {
            @Override
            public void act(long now) {
            }
        };
        Actor nonIntersect = new Actor() {
            @Override
            public void act(long now) {
            }
        };

        stage.add(actor);
        stage.add(nonIntersect);

        nonIntersect.setX(100);

        List<Actor> result = actor.getIntersectingObjects(Actor.class);

        assertTrue(result.isEmpty());
    }
}
