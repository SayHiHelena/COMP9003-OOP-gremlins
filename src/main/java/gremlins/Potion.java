package gremlins;

import processing.core.PImage;

public class Potion extends CollidableObj{
    
    public static final double LASTING = 6.0; // the effect last for 6 secs

    /** 
    * Create a new Potion object at the given position with set image.
    *
    * @param x  the x coordinate, measured in pixels.
    * @param y  the y coordinate, measured in pixels.
    * @param img the set image of the object.
    */
    public Potion(int x, int y, PImage img) {
        super(x, y, img);
    }

    /** 
    * Draw the Potion object.
    */
    @Override
    public void draw(App app) {
        app.image(this.img, this.x, this.y);
    }

}
