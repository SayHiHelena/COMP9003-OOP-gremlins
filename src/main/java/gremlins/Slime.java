package gremlins;

import processing.core.PImage;

public class Slime extends Projectile implements Freezable{
    
    /** 
    * Create a new Potion object at the given position moving to the given direction with set image.
    *
    * @param x  the x coordinate, measured in pixels.
    * @param y  the y coordinate, measured in pixels.
    * @param dir the moving direction of the slime, in the form of an int value.
    * @param img the set image of the object.
    */
    public Slime(int x, int y, int dir, PImage img) {
        super(x, y, dir, img);
    }

    /** 
    * Set the moving speed to 0.
    */
    public void freeze(){
        this.speed = 0;
    }

    /** 
    * Set the moving speed back to default (4).
    */
    public void unfreeze(){
        this.speed = 4;
    }

    /** 
    * Draw the Slime object.
    */
    @Override
    public void draw(App app) {
        app.image(this.img, this.x, this.y);
        
    }
}
