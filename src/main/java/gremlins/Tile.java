package gremlins;

import processing.core.PImage;

public abstract class Tile extends CollidableObj{

    /** 
    * Constructor of the abstract class Tile at the given position with set image.
    *
    * @param x  the x coordinate, measured in pixels.
    * @param y  the y coordinate, measured in pixels.
    * @param img the set image of the object.
    */
    public Tile(int x, int y, PImage img) {
        super(x, y, img);
    }

    /** 
    * Draw the Tile object.
    */
    @Override
    public void draw(App app) {
        app.image(this.img, this.x, this.y);
    }
}
