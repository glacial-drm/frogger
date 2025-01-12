package uk.ac.nott.cs.comp2013.frogger.controller;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import uk.ac.nott.cs.comp2013.frogger.view.MyStage;
import uk.ac.nott.cs.comp2013.frogger.model.BackgroundImage;
import uk.ac.nott.cs.comp2013.frogger.model.digit;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


/**
 * This class derives from the UI and will provide the basic UI elements for
 * Frogger, including the current level and statistics of each Sprite.
 *
 * @author Phillip ************************************* DON'T FORGET ABOUT ME ****************************************
 *
 */
public class FroggerUI{
    private int score/*, hscore*/; //hscore (local highscore) is beyond scope, data is not retained between instances
    private ArrayList<digit> scoreDigits = new ArrayList<digit>(), hscoreDigits = new ArrayList<digit>();

    private Timer timer;

    private String equipped_frog, equipped_difficulty;
    private int difficulty; // the above converted to int, affecting speed of obstacles

    private Level level_obj;

    //private int level; // Running out of time...
    //private digit levelDigit;

    private MyStage background;

    private String[] sprite_path_diff = {"Sprite-DIFF-easy.png","Sprite-DIFF-mid.png","Sprite-DIFF-hard.png","Sprite-DIFF-extr.png"};
    private String[] sprite_path_frog = {"frog-normal-up.png", "frog-anti-up.png", "frog-angry-up.png"};

    BackgroundImage bar_timer = new BackgroundImage("bbar.png", 325, 22);;
    BackgroundImage bar_col = new BackgroundImage("gbar.png", 325, 22);;

    public FroggerUI() {
    }

    public void startEndScreen(MyStage background){
        level_obj.clearLevel();
        background.removeClassObjects(BackgroundImage.class);
        background.removeClassObjects(digit.class);

        BackgroundImage endImg, endFrog, endDifficulty;
        background.add(endImg = new BackgroundImage("screenend.png"));;

        writeScore(background, scoreDigits,280, 423, true);

        background.add(endDifficulty = new BackgroundImage(equipped_difficulty, 500, 250));
        endDifficulty.setX(280);
        endDifficulty.setY(523);

        background.add(endFrog = new BackgroundImage(equipped_frog, 80, 80));
        endFrog.setX(280);
        endFrog.setY(553);

        endImg.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    background.removeClassObjects(BackgroundImage.class);
                    background.removeClassObjects(digit.class);
                    background.remove(endImg);
                    startGameScreen(background);
                }
            }
        });
    }

    public BackgroundImage changeSprite(String[] arr, BackgroundImage image, MyStage background){
        for(int i = 0; i < arr.length; i++){
            if(image.getPath().equals(arr[i])){
                double x = image.getX();
                double y = image.getY();

                background.remove(image);

                if(i == arr.length-1){
                    image = new BackgroundImage(arr[0], image.getDim('x'), image.getDim('y') );
                }
                else{
                    image = new BackgroundImage(arr[i+1], image.getDim('x'), image.getDim('y'));
                }

                background.add(image);
                image.setX(x);
                image.setY(y);
                break;
            }
        }
        return image;
    }

    public String getEquipped_frog(){
        return equipped_frog;
    }

    public int getDifficulty(){
        return difficulty;
    }

    public void setDifficulty(String diff){
        for(int i = 0; i < sprite_path_diff.length; i++){
            if(sprite_path_diff[i].equals(diff)){
                difficulty = i;
            }
        }
    }

    public void endStartScreen(BackgroundImage diff, BackgroundImage frog, MyStage background){
        equipped_difficulty = diff.getPath();
        equipped_frog = frog.getPath();

        background.add(new BackgroundImage("screengame.png"));
		level_obj = new Level(background, this);
        setDifficulty(equipped_difficulty);
        startLevelScreen(background);
    }

    public void startGameScreen(MyStage background){
        background.add(new BackgroundImage("screenstart.png"));

        // ADD DIFFICULTIES
        final BackgroundImage[] difficulty = new BackgroundImage[1];
        background.add(difficulty[0] = new BackgroundImage(sprite_path_diff[0], 500, 250));
        difficulty[0].setX(350);
        difficulty[0].setY(300);

        // ADD FROG
        final BackgroundImage[] frog_chara = new BackgroundImage[1];
        background.add(frog_chara[0] = new BackgroundImage(sprite_path_frog[0], 80, 80));
        frog_chara[0].setX(360);
        frog_chara[0].setY(375);

        // ADD FROG POINTER
        BackgroundImage frog_point;
        background.add(frog_point = new BackgroundImage("sprite-life.png", 20, 20));
        frog_point.setX(30);
        frog_point.setY(300);

        frog_point.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.W){
                    if(frog_point.getY() == 300){
                        frog_point.setX(160);
                        frog_point.setY(400);
                    }
                    else{
                        frog_point.setX(30);
                        frog_point.setY(300);
                    }
                }
                else if (event.getCode() == KeyCode.S){
                    if(frog_point.getY() == 400){
                        frog_point.setX(30);
                        frog_point.setY(300);
                    }
                    else{
                        frog_point.setX(160);
                        frog_point.setY(400);
                    }
                }
                else if (event.getCode() == KeyCode.D){
                    if(frog_point.getY() == 300){
                        difficulty[0] = changeSprite(sprite_path_diff, difficulty[0], background);
                    }
                    else{
                        frog_chara[0] = changeSprite(sprite_path_frog, frog_chara[0], background);
                    }
                }
                else if (event.getCode() == KeyCode.ENTER){
                    endStartScreen(difficulty[0], frog_chara[0], background);
                    background.remove(difficulty[0]);
                    background.remove(frog_chara[0]);
                    background.remove(frog_point);
                }
            }
        });
    }

    public void startLevelScreen(MyStage background){
        score = 0;
        //level = 1;
        setBackground(background);

        // (High)Score Digits
        writeScore(background, scoreDigits, 25, 25, false);
        writeScore(background, hscoreDigits, 200, 25, false);

        // Handling the timer
        startTimer(background);
    }

    public void writeScore(MyStage background, ArrayList<digit> arrDigits, int startPosX, int startPosY, boolean inst){
        for(int i = 1; i < 6; i++){
            if(!inst){ // If digits for array are not instantiated
                arrDigits.add(new digit(0, 25, startPosX+(i*25), startPosY));
            }
            else{
                arrDigits.get(i-1).setX(startPosX+(i*25));
                arrDigits.get(i-1).setY(startPosY);
            }
            background.add(arrDigits.get(i-1));
        }
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

    public void setBackground(MyStage stage){
        background = stage;
    }

    public MyStage getBackground(){
        return background;
    }

    public void endTimer(){
        getBackground().remove(bar_col);
        getBackground().remove(bar_timer);
        timer.cancel();
    }

    public void restartTimer(){
        endTimer();
        startTimer(background);
    }

    public void startTimer(MyStage background){
        watchTimer(background, 0);

        timer = new Timer();
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
                    startEndScreen(background);
                }
            }
        };
        timer.scheduleAtFixedRate(task, 1000L, 1000L);

    }

    public void addScore(int n){
        score += n;
        setScore(score, false);
    }

    public void setScore(int n, boolean highscore){
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
}