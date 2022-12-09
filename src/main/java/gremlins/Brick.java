package gremlins;

import processing.core.PImage;

public class Brick extends Tile {

    private boolean destroyed;
    private int destroyCount = 0;
    
    /** 
    * Create a new Brick object at the given position with set image.
    *
    * @param x  the x coordinate, measured in pixels.
    * @param y  the y coordinate, measured in pixels.
    * @param img the set image of the object.
    */
    public Brick(int x, int y, PImage img) {
        super(x, y, img);
        this.destroyed = false;
    }

    /** 
    * Create a new Brick object at the given position with set image.
    *
    * @param img the set image of the object.
    */
    public void setDestroyImg(PImage img) {
        this.img = img;
    }

    /** 
    * Set the destroyed of the Brick object to true.
    */
    public void destroy(){
        this.destroyed = true;
    }

    /** 
    * Return a boolean value indicating if the Brick object is destroyed.
    */
    public boolean isDestroyed(){
        return this.destroyed;
    }

    /** 
    * Increase the frame count once a Brick object gets hit by a Fireball.
    */
    public void destroyCountPlus() {
        this.destroyCount++;
    }

    /** 
    * Return an int value indicating the frame count once a Brick object gets hit by a Fireball.
    */
    public int getDestroyCount() {
        return this.destroyCount;
    }
}
