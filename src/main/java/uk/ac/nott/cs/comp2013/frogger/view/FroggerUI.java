package uk.ac.nott.cs.comp2013.frogger.view;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import uk.ac.nott.cs.comp2013.frogger.controller.MyStage;
import uk.ac.nott.cs.comp2013.frogger.model.BackgroundImage;
import uk.ac.nott.cs.comp2013.frogger.model.digit;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class derives from the UI and will provide the basic UI elements for
 * Frogger, including the current level and statistics of each Sprite.
 *
 * @author Phillip
 *
 */
public class FroggerUI{
    private int level;
    private int score, hscore;
    private int timer;

    private ArrayList<digit> scoreDigits = new ArrayList<digit>(), hscoreDigits = new ArrayList<digit>();

    private StackPane gameStartPane;
    private StackPane gameOverPane;

    BackgroundImage bar_timer = new BackgroundImage("bbar.png", 325, 22);;
    BackgroundImage bar_col = new BackgroundImage("gbar.png", 325, 22);;

    public FroggerUI() {
        gameOverPane = new StackPane();
        gameOverPane.setStyle("-fx-background-color: transparent;");
    }

    public void startGameScreen(MyStage background){
        // ADD FROG
        BackgroundImage frog;
        background.add(frog = new BackgroundImage("frog-up.png", 80, 80));
        frog.setX(360);
        frog.setY(375);
//        setOnKeyPressed(new EventHandler<KeyEvent>() {
//            public void handle(KeyEvent event) {
//
//            }
//        });

        // ADD DIFFICULTIES
        BackgroundImage difficulty;
        background.add(difficulty = new BackgroundImage("sprite-DIFF-easy.png", 500, 250));
        difficulty.setX(350);
        difficulty.setY(300);

        // REMOVE REFERENCES FROM BACKGROUND
    }

    public void startLevelScreen(MyStage background){
        level = 1;
        score = 0;

        // (High)Score Digits
        for(int i = 1; i < 6; i++){
            scoreDigits.add(new digit(0, 25, 25+(i*25), 25));
            background.add(scoreDigits.get(i-1));

            hscoreDigits.add(new digit(0, 25, 200+(i*25), 25));
            background.add(hscoreDigits.get(i-1));
        }

        // Handling the timer
        startTimer(background);
    }

    public void watchTimer(MyStage background, double bar_progress){
        Platform.runLater(() -> {
            if(bar_progress == 162.5){
                background.remove(bar_col);
                background.remove(bar_timer);

                bar_col = new BackgroundImage("ybar.png", 325, 22);
            }
            else if(bar_progress == 162.5 + 110.5){
                background.remove(bar_col);
                background.remove(bar_timer);

                bar_col = new BackgroundImage("rbar.png", 325, 22);
            }

            bar_col.setX(-149+325);
            bar_col.setY(775);
            background.add(bar_col);

            bar_timer.setX(-149 + bar_progress);
            bar_timer.setY(775);
            background.add(bar_timer);
        });
    }

    public void startTimer(MyStage background){
        watchTimer(background, 0);

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                bar_timer.setX(bar_timer.getX() + 6.5); // 50 seconds for completion

                if (bar_timer.getX() == -149 + 162.5) { // halfway to completion
                    watchTimer(background, 162.5);
                }
                else if (bar_timer.getX() == -149 + 162.5 + 110.5) { //
                    watchTimer(background, 162.5+110.5);
                }
                else if (bar_timer.getX() == -149 + 325) { //If bar at end
                    timer.cancel();

                    // End game
                }
            }
        };
        timer.scheduleAtFixedRate(task, 1000L, 1000L);

    }

    public void addScore(int n){
        score += n;
        writeScore(score, false);
    }

    public void writeScore(int n, boolean highscore){
        if(score > 99999){
         n = 99999;
        }

        for(int i = 0; i < 5; i++){ // Maximum 5 digits
            String number = String.valueOf(n);
            char[] digits = number.toCharArray();
            if(highscore){
                hscoreDigits.get(4-i).setDigit(digits[digits.length-1] - '0');
            }
            else{
                scoreDigits.get(4-i).setDigit(digits[digits.length-1] - '0');
            }
            n = n / 10;
        }
    }

    /**
     * Creates the game over screen.
     */
    public void createGameOver() {
        Text gameOverText = new Text("GAME OVER!\nPress SPACE to restart...");
        gameOverText.setTextAlignment(TextAlignment.CENTER);
        StackPane.setAlignment(gameOverText, Pos.CENTER);
        gameOverPane.getChildren().add(gameOverText);
        gameOverText.setFont(Font.font("Impact", FontWeight.BOLD, 60));

        // Make gameOverPane and base transparent
        gameOverPane.setStyle("-fx-background-color: rgba(255, 102, 129, 0.73);");
        //base.setStyle("-fx-background-color: rgba(255, 102, 129, 0.73);");
    }

    /**
     * Removes the game over screen.
     */
    public void removeGameOver() {
        gameOverPane.getChildren().remove(0);
        gameOverPane.setStyle("-fx-background-color: transparent;");
        //base.setStyle("-fx-background-color: transparent;");
    }


}