package gremlins;

import processing.core.PImage;

public class Exit extends Tile{
    
    /** 
    * Create a new Exit object at the given position with set image.
    *
    * @param x  the x coordinate, measured in pixels.
    * @param y  the y coordinate, measured in pixels.
    * @param img the set image of the object.
    */
    public Exit(int x, int y, PImage img) {
        super(x, y, img);
    }
}
