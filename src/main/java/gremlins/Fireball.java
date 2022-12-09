package gremlins;

import processing.core.PImage;

public class Fireball extends Projectile{
    
    /** 
    * Create a new Brick object at the given position with set image.
    *
    * @param x  the x coordinate, measured in pixels.
    * @param y  the y coordinate, measured in pixels.
    * @param img the set image of the object.
    */
    public Fireball(int x, int y, int dir, PImage img) {
        super(x, y, dir, img);
    }

    /** 
    * Draw the Fireball object.
    */
    @Override
    public void draw(App app) {
        app.image(this.img, this.x, this.y);
    }

}
