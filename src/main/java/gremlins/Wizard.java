package gremlins;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import processing.core.PImage;

public class Wizard extends Character{

    private int xVel = 0;
    private int yVel = 0;
    
    // movement attributes
    private ArrayList<Integer> moveHistory = new ArrayList<Integer>();
    private boolean leftToggle = false;
    private boolean rightToggle = false;
    private boolean upToggle = false;
    private boolean downToggle = false;
    // keep moving to the middle of the tile when key is released
    private boolean toLeftTile = false;
    private boolean toRightTile = false;
    private boolean toUpTile = false;
    private boolean toDownTile = false;

    // skill attributes
    private boolean fireToggle = true;
    private boolean teleportToggle = false;
    private double teleportCountFrame;
    private List<Portal> availablePortals; 

    // life attribute
    private int numOfLives;

    // random generator
    private static final Random randomGenerator = new Random();


    /**
     * Constructor.
     * Set the initial position, image, and number of lives.
     * positive intiger number of lives
     */
    public Wizard(int x, int y, PImage sprite) {
        super(x, y, sprite);
        this.speed = 2;
        //this.iniX = x;
        //this.iniY = y;

        // initialise moveHistory
        moveHistory.add(0);
        moveHistory.add(0);
    };

    /** 
     * Set the number of lives of a Wizard object to the given int value.
     */
    public void setNumOfLives(int numOfLives) {
        this.numOfLives = numOfLives;
    }

    /** 
     * Get a int value of the current number of lives left of a Wizard object.
     */
    public int getLivesNum() {
        return this.numOfLives;
    }

    /** 
     * Run the moving, firing, and colliding logic for a Wizard objects.
     */
    public void logic(App app) {

        // if number of lives <= 0, game over
        if(this.numOfLives <= 0) {
            app.setGameState(-1);
            app.keepStill();
        }

        // toggle for movements
        if(leftToggle || toLeftTile) {
            this.left(app);
        }
        
        if(rightToggle || toRightTile) {
            this.right(app);
        } 

        if(upToggle || toUpTile) {
            this.up(app);
        }
        
        if(downToggle || toDownTile) {
            this.down(app);
        }

        // countdown to turn on fire toggle

        if(this.fireToggle == false) {
            if(this.cooldownFrame >= app.getWizCooldown() * App.FPS) { // when the countdown reaches max mana, set toggle back to true, reset cooldown
                this.fireToggle = true;
            } 
            else { // while the toggle is still off, keep counting down 1 per frame
                this.cooldownFrame++;
           }
        }

        
        // collide with gremlins
        if(this.collide(this, app.getBg().getGremlins())) {
            numOfLives--;
            if(numOfLives > 0) {
                app.loadGame(false);
            }
            this.setAllMoveToggle(false);
            
        }

        // collide with slimes
        if(this.collide(this, app.getBg().getSlimes())) {
            this.numOfLives--;
            if(numOfLives > 0) {
                app.loadGame(false);
            }
            this.setAllMoveToggle(false);
        }


        // collide with freeze potion
        if(this.collide(this, app.getBg().getPotion())) {
            app.getBg().setFreezeToggle(true);
            app.getBg().resetPotion();
        }

        
        // collide with exit
        // only when overlap
        for(Exit e: app.getBg().getExit()) {
            if(this.overlap(this, e)) {
                // if this is the last level, set the state to 0: win.
                if(app.getCurrentLevel() == app.getNumOfLevels()) {
                    app.setGameState(0);
                    app.keepStill();
                }
                // if not the last level, go to the next level
                 else {
                    app.setLevel(app.getCurrentLevel() + 1);
                    app.getLevelConf();
                    app.loadGame(false);
                    this.setAllMoveToggle(false);
                }
            }
        }
        

        // collide with portal
        // only when overlap
        if(this.touchBoundary(this, app.getBg().getPortals()) && teleportToggle)  {
            this.teleportToggle = false;
            this.teleportCountFrame = 0;
        }

        for(Portal p: app.getBg().getPortals()) { 
            if(this.overlap(this, p)) {
                if(!teleportToggle) { // just reached a portal, haven't started the teleport process
                    availablePortals = new ArrayList<Portal>();  
                    availablePortals.addAll(app.getBg().getPortals()); 
                    availablePortals.remove(p);

                    teleportCountFrame = 0;
                    teleportToggle = true; 
                }
    
                else if(teleportCountFrame == Portal.COUNTDOWN) {
                    int random = randomGenerator.nextInt(availablePortals.size()); // choose a random portal that the player is currently not on
                    this.teleport(availablePortals.get(random).getX(), availablePortals.get(random).getY(), app);  // teleport to the new location
                }
                teleportCountFrame++;
            } 
            
        }

    }

    /** 
     * Draw the Wizard object according to the direction it's currently moving towards.
     */
    @Override
    public void draw(App app) {
        if(moveHistory.get(1) == 0 || moveHistory.get(1) == 39)
            this.img = app.wizRight;
        else if(moveHistory.get(1) == 37)
            this.img = app.wizLeft;
        else if(moveHistory.get(1) == 38)
            this.img = app.wizUp;
        else if(moveHistory.get(1) == 40)
            this.img = app.wizDown;

        app.image(this.img, this.x, this.y);
    }

    /** 
     * Teleport a Wizard object to the given x and y coordinates. 
     * Turn off all the moving toggle so the Wizard object stays still after teleporting.
     * 
     * @param x x coordinate, measured in pixels.
     * @param y y coordinate, measured in pixels.
     * 
     * Overloading
     */
    public void teleport(int x, int y, App app) {
        this.x = x;
        this.y = y;
        this.setAllMoveToggle(false);
        app.keepStill();
    }

    /** 
     * Move the Wizard object to the left at a certain speed if there's no tile blocking the way.
     * 
     * Overloading
     */
    public void left(App app){

        // move only if the Wizard is not touching any tile's right boundary
        if(!this.touchBoundary(this, app.getBg().getBrickTiles(), "left") 
        && !this.touchBoundary(this, app.getBg().getStoneTiles(), "left")) {

            // when key is released, keep moving till the next tile
            if(toLeftTile && this.x % 20 != 0) {
                this.xVel = -speed;
                this.x += xVel;
            }
            // when reached the next tile, turn off toLeftTile toggle
            else if(toLeftTile && this.x % 20 == 0) {
                this.xVel = 0;
                this.toLeftTile = false;
            }
            // when key is pressed, move left
            else {
                this.xVel = -speed;
                this.x += xVel;
            }
        }

    }

    /** 
     * Move the Wizard object to the right at a certain speed if there's no tile blocking the way.
     * 
     * Overloading
     */
    public void right(App app){

        // move only if the Wizard is not touching any tile's left boundary
        if(!this.touchBoundary(this, app.getBg().getBrickTiles(), "right") 
        && !this.touchBoundary(this, app.getBg().getStoneTiles(), "right")) {

            // when key is released, keep moving till the next tile
            if(toRightTile && this.x % 20 != 0) {
                this.xVel = speed;
                this.x += xVel;
            }
            // when reached the next tile, turn off toRightTile toggle
            else if(toRightTile && this.x % 20 == 0) {
                this.xVel = 0;
                this.toRightTile = false;
            }
            // when key is pressed, move right
            else{
                this.xVel = speed;
                this.x += xVel;
            }
        }
    }

    /** 
     * Move the Wizard object up at a certain speed if there's no tile blocking the way.
     * 
     * Overloading
     */
    public void up(App app){
        // sprite = app.wizUp;

        // if collide with a tile, movevback to the centre of the grid
        //if(this.collide(this, app.getBg().getBrickTiles()) || this.collide(this, app.getBg().getStoneTiles())) 
        // move only if the Wizard is not touching any tile's bottom boundary
        if(!this.touchBoundary(this, app.getBg().getBrickTiles(), "up") 
        && !this.touchBoundary(this, app.getBg().getStoneTiles(), "up")) {
        
            // when key is released, keep moving till the next tile
            if(toUpTile && this.y % 20 != 0) {
                this.yVel = -speed;
                this.y += yVel;
            }
            // when reached the next tile, turn off toUpTile toggle
            else if(toUpTile && this.y % 20 == 0) {
                this.yVel = 0;
                this.toUpTile = false;
            }
            // when key is pressed, move up
            else{
                this.yVel = -speed;
                this.y += yVel;
            }
        }
    }

    /** 
     * Move the Wizard object down at a certain speed if there's no tile blocking the way.
     * 
     * Overloading
     */
    public void down(App app){
        //sprite = app.wizDown;
        
        // move only if the Wizard is not touching any tile's top boundary
        if(!this.touchBoundary(this, app.getBg().getBrickTiles(), "down") 
        && !this.touchBoundary(this, app.getBg().getStoneTiles(), "down") ) {

            // when key is released, keep moving till the next tile
            if(toDownTile && this.y % 20 != 0) {
                this.yVel = speed;
                this.y += yVel;
            }
            // when reached the next tile, turn off toDownTile toggle
            else if(toDownTile && this.y % 20 == 0) {
                this.yVel = 0;
                this.toDownTile = false;
            }
            // when key is pressed, move down
            else{
                this.yVel = speed;
                this.y += yVel;
            }
        }
    }

    /**
     * Set the state of movement toggles based on key input.
     */
    public void setMoveToggle(int key, String event) {
        if (key == 37) { //left arrow
            if(event.equals("press")) {
                // add the last moving direction at index 1
                if(leftToggle == false){
                    moveHistory.remove(0);
                    moveHistory.add(key);
                }
                this.leftToggle = true;
                this.rightToggle = false;
                this.upToggle = false;
                this.downToggle = false;

                this.toLeftTile = false;
                this.toRightTile = false;
            }
            else if(event.equals("release")) {
                this.leftToggle = false;

                this.toLeftTile = true;
                this.toRightTile = false;
            }
        } 
        else if (key == 39) { //right arrow
            if(event.equals("press")){
                
                if(rightToggle == false){
                    moveHistory.remove(0);
                    moveHistory.add(key);
                }
                
                this.leftToggle = false;
                this.rightToggle = true;
                this.upToggle = false;
                this.downToggle = false;

                this.toLeftTile = false;
                this.toRightTile = false;
            }
            else if(event.equals("release")) {
                this.rightToggle = false;

                this.toLeftTile = false;
                this.toRightTile = true;
            }
        } 
        else if (key == 38) { //up arrow
            if(event.equals("press")){
                if(upToggle == false){
                    moveHistory.remove(0);
                    moveHistory.add(key);
                }
                this.leftToggle = false;
                this.rightToggle = false;
                this.upToggle = true;
                this.downToggle = false;
                
                this.toUpTile = false;
                this.toDownTile = false;
            }
            else if(event.equals("release")) {
                this.upToggle = false;

                this.toUpTile = true;
                this.toDownTile = false;
            }
        } 
        else if (key == 40) { //down arrow
            if(event.equals("press")){
                if(downToggle == false){
                    moveHistory.remove(0);
                    moveHistory.add(key);
                }
                this.leftToggle = false;
                this.rightToggle = false;
                this.upToggle = false;
                this.downToggle = true;

                this.toUpTile = false;
                this.toDownTile = false;
            }
            else if(event.equals("release")) {
                this.downToggle = false;

                this.toUpTile = false;
                this.toDownTile = true;
            }
        } 
    }

    /**
     * Set the state of all the movement toggles to the given boolean parameter.
     */
    public void setAllMoveToggle(boolean bool) {
        leftToggle = bool;
        rightToggle = bool;
        upToggle = bool;
        downToggle = bool;
        toLeftTile = bool;
        toRightTile = bool;
        toUpTile = bool;
        toDownTile = bool;
    }

    /** 
     * Get a boolean value indicating if the Wizard object is moving towards left using arrow key.
     */
    public boolean getLeftToggle() {
        return this.leftToggle;
    }

    /** 
     * Get a boolean value indicating if the Wizard object is moving towards right using arrow key.
     */
    public boolean getRightToggle() {
        return this.rightToggle;
    }

    /** 
     * Get a boolean value indicating if the Wizard object is moving up using arrow key.
     */
    public boolean getUpToggle() {
        return this.upToggle;
    }

    /** 
     * Get a boolean value indicating if the Wizard object is moving down using arrow key.
     */
    public boolean getDownToggle() {
        return this.downToggle;
    }

    /** 
     * Get a boolean value indicating if the Wizard object is moving to the middle of the left tile while the key is released.
     */
    public boolean getToLeftTile() {
        return this.toLeftTile;
    }

    /** 
     * Get a boolean value indicating if the Wizard object is moving to the middle of the right tile while the key is released.
     */
    public boolean getToRightTile() {
        return this.toRightTile;
    }

    /** 
     * Get a boolean value indicating if the Wizard object is moving to the middle of the up tile while the key is released.
     */
    public boolean getToUpTile() {
        return this.toUpTile;
    }

    /** 
     * Get a boolean value indicating if the Wizard object is moving to the middle of the down tile while the key is released.
     */
    public boolean getToDownTile() {
        return this.toDownTile;
    }

    /** 
     * Fire if the mana is ready.
     * Create a new Fireball object to the Background object's Fireball List.
     * Once fired, reload mana before next firing.
     */
    @Override
    public void fire(App app) {
        if(fireToggle) {
            app.getBg().getFireballs().add(new Fireball(this.x, this.y, this.moveHistory.get(1), app.fireball));
            fireToggle = false;
            this.cooldownFrame = 0;
        }
        
    }

    /** 
     * Get a boolean value indicating if the Wizard object is ready to fire.
     */
    public boolean getFireToggle() {
        return this.fireToggle;
    }

    /** 
     * Get a boolean value indicating if Wizard object is going through the teleporting process.
     */
    public boolean getTeleportToggle() {
        return this.teleportToggle;
    }

    /** 
     * Get a double value indicating the count of the teleporting process measured in frame.
     */
    public double getTeleportCount() {
        return this.teleportCountFrame;
    }
    
    /* 
     * Return a list of Integers representing the key code to keep the records of the latest moving direction history.
     */
    public List<Integer> getMoveHistory() {
        return this.moveHistory;
    }

}
