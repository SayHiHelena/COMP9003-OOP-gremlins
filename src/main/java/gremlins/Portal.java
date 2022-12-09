package gremlins;

import processing.core.PImage;

public class Portal extends Tile{
    
    private PImage activeImg;
    public static final double COUNTDOWN = 90.0; // it takes 90 frames to activate the portal

    /** 
    * Create a new Portal object at the given position with set images.
    *
    * @param x  the x coordinate, measured in pixels.
    * @param y  the y coordinate, measured in pixels.
    * @param img the default image of the object.
    * @param activeImg the image of the object with it overlaps with the Wizard object.
    */
    public Portal(int x, int y, PImage img, PImage activeImg) {
        super(x, y, img);
        this.activeImg = activeImg;
    }

    /** 
    * Draw the Portal object depending on the overlap state.
    */
    @Override
    public void draw(App app) {
        if(this.overlap(this, app.getWiz()))
            app.image(this.activeImg, this.x, this.y);
        else
            app.image(this.img, this.x, this.y);
    }


}
