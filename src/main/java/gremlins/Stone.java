package gremlins;

import processing.core.PImage;

public class Stone extends Tile{

    /** 
    * Create a new Stone object at the given position with set image.
    *
    * @param x  the x coordinate, measured in pixels.
    * @param y  the y coordinate, measured in pixels.
    * @param img the set image of the object.
    */
    public Stone(int x, int y, PImage img) {
        super(x, y, img);
    }
}
