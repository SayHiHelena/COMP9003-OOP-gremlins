package gremlins;

import processing.core.PImage;

public abstract class Projectile extends CollidableObj implements Movable{
    
    protected int speed = 4;
    protected int direction;

    /** 
    * Constructor for the abstract class Projectile at the given position with set images.
    *
    * @param x  the x coordinate, measured in pixels.
    * @param y  the y coordinate, measured in pixels.
    * @param direction the moving direction of the Projectile, in the form of an int value.
    * @param img the set image of the object.
    */
    public Projectile(int x, int y, int direction, PImage img) {
        super(x, y, img);
        this.direction = direction;
    }

    /** 
    * Run the moving logic for a Projectile object.
    */
    public void logic() {
        
        // toggle for movements
        if(direction == 0 || direction == 39) { // right
            this.right();
        }
        else if(direction == 37) { // left
            this.left();
        }
        else if(direction == 38) { // up
            this.up();
        }
        else if(direction == 40) { // down
            this.down();
        }

    }

    /** 
    * Move the Projectile object to left at the set speed.
    */
    @Override
    public void left(){
        this.x -= this.speed;
    }

    /** 
    * Move the Projectile object to right at the set speed.
    */
    @Override
    public void right(){
        this.x += this.speed;
    }

    /** 
    * Move the Projectile object to up at the set speed.
    */
    @Override
    public void up(){
        this.y -= this.speed;
    }

    /** 
    * Move the Projectile object to down at the set speed.
    */
    @Override
    public void down(){
        this.y += this.speed;
    }

    /**
     * Return the moving speed of the object in the form of an int value.
     */
    @Override
    public int getSpeed() {
        return this.speed;
    }
}
