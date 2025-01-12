package uk.ac.nott.cs.comp2013.frogger.view;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import uk.ac.nott.cs.comp2013.frogger.model.Actor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MyStage extends Pane {
    MediaPlayer mediaPlayer;
    private AnimationTimer t;

    public MyStage() {

        sceneProperty().addListener(new ChangeListener<Scene>() {

            @Override
            public void changed(ObservableValue<? extends Scene> observable, Scene oldValue, Scene newValue) {
                if (newValue != null) {
                    newValue.setOnKeyReleased(new EventHandler<KeyEvent>() {

                        @Override
                        public void handle(KeyEvent event) {
                            if (getOnKeyReleased() != null) getOnKeyReleased().handle(event);
                            List<Actor> myActors = getObjects(Actor.class);
                            for (Actor anActor : myActors) {
                                if (anActor.getOnKeyReleased() != null) {
                                    anActor.getOnKeyReleased().handle(event);
                                }
                            }
                        }

                    });

                    newValue.setOnKeyPressed(new EventHandler<KeyEvent>() {

                        @Override
                        public void handle(KeyEvent event) {
                            if (getOnKeyPressed() != null) getOnKeyPressed().handle(event);
                            List<Actor> myActors = getObjects(Actor.class);
                            for (Actor anActor : myActors) {
                                if (anActor.getOnKeyPressed() != null) {
                                    anActor.getOnKeyPressed().handle(event);
                                }
                            }
                        }

                    });
                }

            }

        });
    }

    public void playMusic() {
        String musicFile = "src/main/resources/Snake_Charmer.mp3";
        Media sound = new Media(new File(musicFile).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }

    public void stopMusic() {
        mediaPlayer.stop();
    }

    public void createTimer() {
        t = new AnimationTimer() {
            @Override
            public void handle(long now) {
                act(now);
                List<Actor> actors = getObjects(Actor.class);

                for (Actor anActor : actors) {
                    anActor.act(now);
                }

            }
        };
    }

    public void start() {
        createTimer();
        t.start();
    }

    public void stop() {
        t.stop();
    }

    public void add(Actor actor) {
        Platform.runLater(() -> {
            getChildren().add(actor);
        });
    }

    public void remove(Actor actor) {
        getChildren().remove(actor);
    }

    public <A extends Actor> void removeClassObjects(Class<A> cls) {
        Platform.runLater(() -> {
            for (Actor n : getObjects(cls)) {
                if (cls.isInstance(n)) {

                    getChildren().remove(n);
                    //remove(n);
                }
            }
        });
    }

    public <A extends Actor> List<A> getObjects(Class<A> cls) {
        ArrayList<A> someArray = new ArrayList<A>();
        for (Node n : getChildren()) {
            if (cls.isInstance(n)) {
                someArray.add((A) n);
            }
        }
        return someArray;
    }

    public void act(long now) {
    }

}
