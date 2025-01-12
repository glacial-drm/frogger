package uk.ac.nott.cs.comp2013.frogger.controller;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import uk.ac.nott.cs.comp2013.frogger.view.MyStage;
import uk.ac.nott.cs.comp2013.frogger.model.BackgroundImage;
import uk.ac.nott.cs.comp2013.frogger.model.Digit;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


/**
 * This class handles UI elements that aren't to do with placing obstacles / the player on the screen
 * it focuses on presenting data that is as a result of the users interaction with the program, e.g. scores
 * Works in conjunction with the {@link uk.ac.nott.cs.comp2013.frogger.controller.Level} class  to form
 * the controller
 *
 * @author psyda6
 *
 */
public class FroggerUI{
    private int score/*, hscore*/; //hscore (local highscore) is beyond scope, data is not retained between instances
    private ArrayList<Digit> scoreDigits = new ArrayList<Digit>(), hscoreDigits = new ArrayList<Digit>();

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

    /**
     * Default Constructor
     */
    public FroggerUI() {
    }

    /**
     *  Loads the splash screen / character and difficulty select screen for Frogger, displays options for playable
     *  characters (affecting control scheme) and difficulty (affecting speed of obstacles).
     *  Handles the logic for moving between selecting character and difficulty using the frog_point image, with the
     *  enter key finalising the selection for the user, and moving to the next screen via the startLevelScreen() method
     *
     * @param background specifies that background to add UI elements to
     *
     */
    public void startSetupScreen(MyStage background){
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

        // SETUP EVENT HANDLER FOR KEYBOARD EVENTS (MOVING THE FROG POINTER AROUND THE SCREEN)
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
                    equipped_difficulty = difficulty[0].getPath();
                    equipped_frog = frog_chara[0].getPath();

                    startLevelScreen(background);

                    background.remove(difficulty[0]);
                    background.remove(frog_chara[0]);
                    background.remove(frog_point);
                }
            }
        });
    }

    /**
     * Used to create the logic for a rotating array of sprites, necessary for indicating difficulty
     * or playable characters before the user locks in their selection
     *
     * @param arr contains the list of paths for related sprites, such as sprites for difficulty
     * @param image contains a reference to the image intended to be modified
     * @param background contains the stage the image is to be loaded on
     *
     * @return BackgroundImage
     */
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

    /**
     * Starts the game section of Frogger, where a level is instantiated, allowing the user to control their chosen
     * character
     * Writes the initial user score to the background
     * Starts the timer countdown (game ends if time is up)
     *
     * @param background is the stage for elements to be added to
     */
    public void startLevelScreen(MyStage background){

        background.add(new BackgroundImage("screengame.png"));
        level_obj = new Level(background, this);
        setDifficulty(equipped_difficulty);

        score = 0;
        //level = 1;
        setBackground(background);

        // (High)Score Digits
        writeScore(background, scoreDigits, 25, 25, false);
        writeScore(background, hscoreDigits, 200, 25, false);

        // Handling the timer
        startTimer(background);
    }

    /**
     * Writes an Arraylist of Digit actors to a chosen set of coordinates, displaying the score at the desired location
     *
     * @param background the stage for actors to be written to
     * @param arrDigits Arraylist of Digit Actors to used to display score images
     * @param startPosX Determines the starting point of Digit writing in the x-axis
     * @param startPosY Determines the starting point of Digit writing in the y-axis
     * @param inst Flag used to determine if digits already exist (should new digits be created)
     */
    public void writeScore(MyStage background, ArrayList<Digit> arrDigits, int startPosX, int startPosY, boolean inst){
        for(int i = 1; i < 6; i++){
            if(!inst){ // If digits for array are not instantiated
                arrDigits.add(new Digit(0, 25, startPosX+(i*25), startPosY));
            }
            else{
                arrDigits.get(i-1).setX(startPosX+(i*25));
                arrDigits.get(i-1).setY(startPosY);
            }
            background.add(arrDigits.get(i-1));
        }
    }

    /**
     * Schedules a timer bar that decrements on a given interval and changes colour at certain breakpoints
     *
     * @param background specifies the stage to add the timer to
     */
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

    /**
     * Carries out the changing of colour of the time bar specified above, used to remove duplicate code
     *
     * @param background specifies the stage to add Actors to
     * @param bar_progress integer that specifies the x coordinate of the bar, determining what state (colour) it should
     *                     be
     */
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

    /**
     * Loads the end screen, replacing the gameplay screen with a displayed score
     * and statistics
     *
     * @param background is the stage the end screen is loaded on
     */
    public void startEndScreen(MyStage background){
        level_obj.clearLevel();
        background.removeClassObjects(BackgroundImage.class);
        background.removeClassObjects(Digit.class);

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
                    background.removeClassObjects(Digit.class);
                    background.remove(endImg);
                    startSetupScreen(background);
                }
            }
        });
    }

    /**
     * Created in the event the timer needs to be stopped in any situation
     */
    public void endTimer(){
        getBackground().remove(bar_col);
        getBackground().remove(bar_timer);
        timer.cancel();
    }

    /**
     * Utilises the above endTimer() function to restart the timer when a Frog enters a home
     */
    public void restartTimer(){
        endTimer();
        startTimer(background);
    }

    /**
     * Used by external classes to add to the player's score, calls setScore() to write the updated score
     *
     * @param n represents the number to be added to the cumulative score
     */
    public void addScore(int n){
        score += n;
        setScore(score, false);
    }

    /**
     * Writes the score to the stage digit by digit
     *
     * @param n represents the current score
     * @param highscore flag that determines if the score is written to the highscore or regular score section
     */
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

    /**
     * Getter for the file path of the image for the equipped frog  (playable character) selected by the user
     *
     * @return equipped_frog returns the image specified above
     */
    public String getEquipped_frog(){
        return equipped_frog;
    }

    /**
     * Getter for the integer value for the chosen difficulty selected by the user
     *
     * @return difficulty returns an integer to be used as a modifier for the speed of Actors in
     * {@link  uk.ac.nott.cs.comp2013.frogger.controller.Level}
     */
    public int getDifficulty(){
        return difficulty;
    }

    /**
     * Parses a file path string corresponding to the displayed difficulty image, converting it to an integer value to
     * be used as a modifier as specified in the above Javadoc
     *
     * @param diff is the file path specifying the chosen difficulty
     */
    public void setDifficulty(String diff){
        for(int i = 0; i < sprite_path_diff.length; i++){
            if(sprite_path_diff[i].equals(diff)){
                difficulty = i;
            }
        }
    }

    /**
     * Setter for the main MyStage object
     */
    public void setBackground(MyStage stage){
        background = stage;
    }
    /**
     * Getter for the main MyStage object
     */
    public MyStage getBackground(){
        return background;
    }


}