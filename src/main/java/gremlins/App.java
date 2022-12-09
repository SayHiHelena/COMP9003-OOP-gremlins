package gremlins;

import java.io.File;
//import java.io.FileNotFoundException;
//import java.util.*;


import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONArray;
import processing.data.JSONObject;
import processing.event.KeyEvent;


public class App extends PApplet {

    public static final int WIDTH = 720;
    public static final int HEIGHT = 720;
    public static final int BOTTOMBAR = 60;
    public static final int MANABAR = 90;

    public static final int FPS = 60;

    private String configPath;
    private int state = 1;

    private int numOfLevels;
    private int currentLevel = 1;
    private int numOfLives;
    private String layout;
    private double wizCooldown;
    private double enemyCooldown;

    private boolean keyDetect = true; // help stay stationary after loading a level or respawn
    private boolean keyIsPressed = false;
    
    public PImage brickwall;
    public PImage brickDestroy0;
    public PImage brickDestroy1;
    public PImage brickDestroy2;
    public PImage brickDestroy3;
    public PImage stonewall;
    public PImage portal0;
    public PImage portal;
    public PImage exit;
    public PImage gremlin;
    public PImage slime;
    public PImage fireball;
    public PImage potion;
    public PImage wizRight;
    public PImage wizLeft;
    public PImage wizUp;
    public PImage wizDown;

    private Wizard wiz;
    private Background bg;
    

    
    /** 
    * Class constructor with default configPath set to "config.json".
    */
    public App() {
        this.configPath = "config.json";
    }

    /**
     * Initialise the setting of the window size.
    */
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    /**
     * Load all resources such as images. Initialise the elements such as the player, enemies and map elements.
    */
    public void setup() {

        frameRate(FPS);

        // Load images during setup
        this.stonewall = loadImage(this.getClass().getResource("stonewall.png").getPath().replace("%20", ""));
        this.brickwall = loadImage(this.getClass().getResource("brickwall.png").getPath().replace("%20", ""));
        this.brickDestroy0 = loadImage(this.getClass().getResource("brickwall_destroyed0.png").getPath().replace("%20", ""));
        this.brickDestroy1 = loadImage(this.getClass().getResource("brickwall_destroyed1.png").getPath().replace("%20", ""));
        this.brickDestroy2 = loadImage(this.getClass().getResource("brickwall_destroyed2.png").getPath().replace("%20", ""));
        this.brickDestroy3 = loadImage(this.getClass().getResource("brickwall_destroyed3.png").getPath().replace("%20", ""));
        this.portal0 = loadImage(this.getClass().getResource("portal0.png").getPath().replace("%20", ""));
        this.portal = loadImage(this.getClass().getResource("portal.png").getPath().replace("%20", ""));
        this.exit = loadImage(this.getClass().getResource("exit.png").getPath().replace("%20", ""));
        this.gremlin = loadImage(this.getClass().getResource("gremlin.png").getPath().replace("%20", ""));
        this.slime = loadImage(this.getClass().getResource("slime.png").getPath().replace("%20", ""));
        this.fireball = loadImage(this.getClass().getResource("fireball.png").getPath().replace("%20", ""));
        this.potion = loadImage(this.getClass().getResource("potion.png").getPath().replace("%20", ""));
        this.wizRight = loadImage(this.getClass().getResource("wizard1.png").getPath().replace("%20", ""));
        this.wizLeft = loadImage(this.getClass().getResource("wizard0.png").getPath().replace("%20", ""));
        this.wizUp = loadImage(this.getClass().getResource("wizard2.png").getPath().replace("%20", ""));
        this.wizDown = loadImage(this.getClass().getResource("wizard3.png").getPath().replace("%20", ""));

        // read configuration data from the json file
        this.getLevelConf();
        this.loadGame(true);
        this.setKeyDetect(true);
        
    }


    /**
     * Draw all elements in the game by current frame. 
	 */
    public void draw() {
        // fill the beige background colour & text
        background(192, 159, 143);
        fill(255);
        
        // game state -1: lose 
        if(state == -1) {
            textSize(40);
            this.text("GAME OVER", 248, 320);
            textSize(20);
            this.text("Press any key to restart", 250, 370);
        }

        // game state 0: win
        else if(state == 0) {
            textSize(40);
            this.text("YOU WIN!", 270, 320);
            textSize(20);
            this.text("Press any key to restart", 250, 370);
        }

        // game state 1: playing
        else if(state == 1)  {
            // bottom bar Lives & Levels
            fill(255);
            textSize(20);
            this.text("Lives: ", WIDTH/36, (float) (HEIGHT - BOTTOMBAR * 0.4)); // (float) (HEIGHT - BOTTOMBAR/2.5));
            this.text("Level " + currentLevel + "/"+ numOfLevels, (float) (WIDTH * 0.24), ( (float) (HEIGHT - BOTTOMBAR * 0.4)));
            for(int i = 0; i < wiz.getLivesNum(); i++) {
                this.image(wizRight, WIDTH/9 + i * 20, (float) (HEIGHT - BOTTOMBAR * 0.7)); //HEIGHT - 40);
            }

            // mana bar
            this.image(fireball, (float) (WIDTH * 0.41), (float) (HEIGHT - BOTTOMBAR * 0.7));
            
            noStroke();
            fill(255, 100);
            this.rect((float) (WIDTH * 0.45), (float) (HEIGHT - BOTTOMBAR * 0.7), MANABAR, 20);
            
            fill(255,70,150);
            this.rect((float) (WIDTH * 0.45), (float) (HEIGHT - BOTTOMBAR * 0.7), (float) (MANABAR * ((wiz.getCooldown() / FPS) / this.wizCooldown)), 20);
            noFill();
            stroke(255);
            strokeWeight(2);
            this.rect((float) (WIDTH * 0.45), (float) (HEIGHT - BOTTOMBAR * 0.7), MANABAR, 20);

            // teleport bar
            if(wiz.getTeleportToggle() && wiz.getTeleportCount() > 1 && wiz.getTeleportCount() <= Portal.COUNTDOWN) {
                this.image(portal, (float) (WIDTH * 0.61), (float) (HEIGHT - BOTTOMBAR * 0.7));

                noStroke();
                fill(255, 100);
                this.rect((float) (WIDTH * 0.65), (float) (HEIGHT - BOTTOMBAR * 0.7), MANABAR, 20);
                
                fill(190,75,225);
                this.rect((float) (WIDTH * 0.65), (float) (HEIGHT - BOTTOMBAR * 0.7), (float) (MANABAR * ((wiz.getTeleportCount() / Portal.COUNTDOWN))), 20);
                
                noFill();
                stroke(255);
                strokeWeight(2);
                this.rect((float) (WIDTH * 0.65), (float) (HEIGHT - BOTTOMBAR * 0.7), MANABAR, 20);
            }
            

            // potion bar count down
            
            if(bg.getFreezeToggle() && bg.getFreezeCount() >= 0) {
                this.image(potion, (float) (WIDTH * 0.81), (float) (HEIGHT - BOTTOMBAR * 0.7));

                noStroke();
                fill(255, 100);
                this.rect((float) (WIDTH * 0.85), (float) (HEIGHT - BOTTOMBAR * 0.7), MANABAR, 20);
                
                fill(50,135,200);
                this.rect((float) (WIDTH * 0.85), (float) (HEIGHT - BOTTOMBAR * 0.7), (float) (MANABAR * ((bg.getFreezeCount() / FPS) / Potion.LASTING)), 20);
                
                noFill();
                stroke(255);
                strokeWeight(2);
                this.rect((float) (WIDTH * 0.85), (float) (HEIGHT - BOTTOMBAR * 0.7), MANABAR, 20);
            }
            
            // draw background (tiles, enemies, projectiles)
            bg.logic(this);
            bg.draw(this);

            // draw wizard
            wiz.logic(this);
            wiz.draw(this);
            
        }

    }


    /**
     * Receive key pressed signal from the keyboard.
     * Invoke the moving or firing function of the Wizard object or restart the game.
    */
    public void keyPressed(KeyEvent e){
        if(!keyIsPressed)
            keyIsPressed = true;

        
        int key = e.getKeyCode();

        if(keyDetect) {
            // if has won/lost press any key to restart
            if(this.state == 0 || this.state == -1) {

                state = 1;
                this.setLevel(1);
                this.getLevelConf();
                this.loadGame(true);
                return;
            }

            else if (key == 32) { //fire
                wiz.fire(this);
            }
            else{ // movement
                wiz.setMoveToggle(key, "press");
            }
        }
        


    }
    
    /**
     * Receive key released signal from the keyboard.
     * When movement key released, keep the Wizard object move to the next tile.
    */
    public void keyReleased(KeyEvent e){

        keyIsPressed = false;
        this.setKeyDetect(true);

        int key = e.getKeyCode();
        wiz.setMoveToggle(key, "release");
    }
    
    /** 
     * Disable the keyboard input detection if the game enters a new game state with a key being pressed.
     */
    public void keepStill() {
        if(this.keyIsPressed) {
            this.setKeyDetect(false);
        }
    }
    
    /** 
     * Set the keyDetect to the given boolean value to make available/diable keyboard input detection.
     */
    public void setKeyDetect(boolean bool) {
        this.keyDetect = bool;
    }
    
    /** 
     * Read the file as the cofigPath suggest and get level configuration info.
     */
    public void getLevelConf() {
        // may throw RuntimeException: FileNotFountException
        JSONObject conf = loadJSONObject(new File(this.configPath));

        JSONArray levelArray = conf.getJSONArray("levels");

        // load number of levels and lives only once
        if(currentLevel == 1) {
            numOfLevels = levelArray.size();
            numOfLives = conf.getInt("lives");
        }
        
        // double check valid level entered
        if(currentLevel >= 1 && currentLevel <= numOfLevels) {
            JSONObject levelConf = (JSONObject) levelArray.get(currentLevel - 1);
            layout = levelConf.getString("layout");
            wizCooldown = levelConf.getDouble("wizard_cooldown");
            enemyCooldown = levelConf.getDouble("enemy_cooldown");
        }
        
    }

    /** 
     * Load key configuration info and create the map according to the level configuration info.
     * 
     * @param reload boolean value indicating whether to reset the number of lives for the Wizard object.
     */
    public void loadGame(boolean reload) {
        this.bg = new Background(layout);
        this.bg.createLayout(this);

        if(reload) {
            this.wiz.setNumOfLives(numOfLives);
        } 
        this.wiz.setCooldown(this.getWizCooldown() * App.FPS);

        this.keepStill();
    }

    /* 
     * Return the Background object of the current map.
     */
    public Background getBg(){
        return this.bg;
    }

    /* 
     * Set the wiz to the given Wizard object.
     */
    public void setWiz(Wizard wiz){
        this.wiz = wiz;
    }

    /* 
     * Return the Wizard object of the current game.
     */
    public Wizard getWiz(){
        return this.wiz;
    }
 
    /* 
     * Set the game state to the given state.
     * 
     * @param state int value indicating win:0, lose: -1, play: 1.
     */
    public void setGameState(int state){
        this.state = state;
    }

    /* 
     * Return an int value indicating the current game state.
     */
    public int getGameState(){
        return this.state;
    }

    /* 
     * Return an int value indicating the number of levels the config file contains.
     */
    public int getNumOfLevels() {
        return this.numOfLevels;
    }

    /* 
     * Set the level to the given level value.
     * 
     * @param level int value indicating game level starting from 1.
     */
    public void setLevel(int level) {
        this.currentLevel = level;
        if(level <= 0 || level > numOfLevels)  {
            System.out.println("level not exist");
            System.exit(0);
        }
    }

    /* 
     * Return an int value indicating current number of level.
     */
    public int getCurrentLevel() {
        return this.currentLevel;
    }

    /* 
     * Return an int value indicating the max number of lives for the Wizard object according to the cofig file.
     */
    public int getNumOfLives() {
        return this.numOfLives;
    }

    /* 
     * Return a double value indicating the cooldown time in seconds 
     * that the Wizard object needs between firings.
     */
    public double getWizCooldown() {
        return this.wizCooldown;
    }

    /* 
     * Return a double value indicating the cooldown time in seconds 
     * that the Gremlin object needs between firings.
     */
    public double getEnemyCooldown() {
        return this.enemyCooldown;
    }

    public static void main(String[] args) {
        PApplet.main("gremlins.App");
    }
}
