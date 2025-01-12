package uk.ac.nott.cs.comp2013.frogger.view;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import uk.ac.nott.cs.comp2013.frogger.model.Actor;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class MyStageTests extends  ApplicationTest{

    @Test
    public void removeClassObjects() {
        MyStage stage = new MyStage();
        assert stage.getObjects(Actor.class).isEmpty();

        stage.add(new Actor() {
            @Override
            public void act(long now) {

            }
        });

        stage.removeClassObjects(Actor.class);
        assert stage.getObjects(Actor.class).isEmpty();
    }

    @Test
    public void addClassObjects() {
        MyStage stage = new MyStage();
        assert stage.getObjects(Actor.class).isEmpty();

        stage.add(new Actor() {
            @Override
            public void act(long now) {

            }
        });
        assertNotNull(stage.getObjects(Actor.class));

    }
}