package gremlins;

import processing.core.PImage;

public abstract class Character extends CollidableObj implements Movable {

    // default moving speed: 1 pixel per frame. By setting it as an int, the sprite need to move whole pixels
    protected int speed = 1; 
    protected double cooldownFrame = 0;

    /** 
    * Create a new Character object at the given position with set image.
    *
    * @param x  the x coordinate, measured in pixels.
    * @param y  the y coordinate, measured in pixels.
    * @param sprite the set image of the object.
    */
    public Character(int x, int y, PImage sprite) {
        super(x, y, sprite);
    }

    /** 
    * Move the Character object to the left at a certain speed.
    */
    @Override
    public void left(){
        this.x -= this.speed;
    }

    /** 
    * Move the Character object to the right at a certain speed.
    */
    @Override
    public void right(){
        this.x += this.speed;
    }

    /** 
    * Move the Character object to the up at a certain speed.
    */
    @Override
    public void up(){
        this.y -= this.speed;
    }

    /** 
    * Move the Character object to the down at a certain speed.
    */
    @Override
    public void down(){
        this.y += this.speed;
    }

    /** 
    * Make the Character fire and shoot a Fireball.
    */
    public abstract void fire(App app);


    /** 
    * Teleport the Character to the given x and y coordinate.
    */
    public void teleport(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Set the cooldown frame of the Character object to the given double value.
     */
    public void setCooldown(double cooldownFrame) {
        this.cooldownFrame = cooldownFrame;
    }

    /**
     * Return the cooldown frame of the Character object in the form of an double value.
     */
    public double getCooldown() {
        return this.cooldownFrame;
    }

    /**
     * Return the moving speed of the Character object in the form of an int value.
     */
    @Override
    public int getSpeed() {
        return this.speed;
    }
}
