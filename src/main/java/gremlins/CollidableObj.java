package gremlins;

import java.util.List;

import processing.core.PImage;

public abstract class CollidableObj {

    public final static int SIZE = 20; // 20 pixel for both height and width
    protected int x;
    protected int y;
    protected PImage img;

    /** 
    * Constructor of the abstract class CollidableObj at the given position with set image.
    *
    * @param x  the x coordinate, measured in pixels.
    * @param y  the y coordinate, measured in pixels.
    * @param img the set image of the object.
    */
    public CollidableObj(int x, int y, PImage img){
        this.x = x;
        this.y = y;
        this.img = img;
    }

    /** 
    * Return the size of the CollidableObj objects as an int value.
    */
    public int getSize() {
        return SIZE;
    }

    /** 
    * Return the x coordinate of the CollidableObj objects in pixels.
    */
    public int getX() {
        return this.x;
    }
    
    /** 
    * Return the y coordinate of the CollidableObj objects in pixels.
    */
    public int getY() {
        return this.y;
    }

    /** 
    * Return the PImage of the CollidableObj objects.
    */
    public PImage getImg(){
        return this.img;
    }

    /** 
    * Draw the ColliableObj objects.
    */
    public abstract void draw(App app);


    /**
     * Return a boolean value telling if the CollidableObj object is colliding with any CollideObj object from the target list.
     */
    public boolean collide(CollidableObj obj, List<? extends CollidableObj> targetList) {
        int objX = obj.getX();
        int objY = obj.getY();
        
        for(CollidableObj tar: targetList) {
            int tarX = tar.getX();
            int tarY = tar.getY();
            int tarSize = tar.getSize() ;
            
            if(objX > tarX - tarSize && objX < tarX + tarSize 
            && objY > tarY - tarSize && objY < tarY + tarSize) {
                return true;
            } 
        }
        return false;    
    }

     /**
     * Return a boolean value telling if the CollidableObj object is colliding with the target CollideObj obj.
     */
    public boolean collide(CollidableObj obj, CollidableObj tar) {
        int objX = obj.getX();
        int objY = obj.getY();
        
        
        int tarX = tar.getX();
        int tarY = tar.getY();
        int tarSize = tar.getSize() ;
            
        if(objX > tarX - tarSize && objX < tarX + tarSize 
        && objY > tarY - tarSize && objY < tarY + tarSize) {
            return true;
        } 
        
        return false;    
    }

    /**
     * Return a boolean value telling if the CollidableObj object is overlapping with the target CollideObj obj.
     */
    public boolean overlap(CollidableObj obj, CollidableObj tar) {
        if(obj.getX() == tar.getX() && obj.getY() == tar.getY()) {
            return true;
        } 
        
        return false;    
    }



    /**
     * Return a boolean value telling if the given object is touching the boundary of 
     * any CollideObj from the target list on the given direction
     * 
     * @param obj  a given CollidableObj object.
     * @param targetList  a list of target CollidableObj objects.
     * @param mode  a String indicating the touching direction of given object ("left", "right", "up", "down").
     */
    public boolean touchBoundary(CollidableObj obj, List<? extends CollidableObj> targetList, String mode) {
        int objX = obj.getX();
        int objY = obj.getY();

        for(CollidableObj tar: targetList) {
            int tarX = tar.getX();
            int tarY = tar.getY();
            int tarSize = tar.getSize();

            if(mode.equals("left")) { // touching the right boundary of a tile
                if(objX == tarX + tarSize && objY > tarY - tarSize && objY < tarY + tarSize)
                    return true;
            }

            else if(mode.equals("right")){ // touching the left boundary of a tile
                if(objX == tarX - tarSize && objY > tarY - tarSize && objY < tarY + tarSize)
                    return true;
            }

            else if(mode.equals("up")){ // touching the bottom boundary of a tile
                if(objX > tarX - tarSize && objX < tarX + tarSize && objY == tarY + tarSize)
                    return true;
            }

            else if(mode.equals("down")){ // touching the top boundary of a tile
                if(objX > tarX - tarSize && objX < tarX + tarSize && objY == tarY - tarSize) 
                    return true;
            }

        }

        return false;    
    }


    /**
     * Return a boolean value telling if the given object is touching a boundary of 
     * any CollideObj from the target list
     * 
     * @param obj  a given CollidableObj object.
     * @param targetList  a list of target CollidableObj objects.
     */
    public boolean touchBoundary(CollidableObj obj, List<? extends CollidableObj> targetList) {
        int objX = obj.getX();
        int objY = obj.getY();

        for(CollidableObj tar: targetList) {
            int tarX = tar.getX();
            int tarY = tar.getY();
            int tarSize = tar.getSize();

            if((objX == tarX + tarSize && objY > tarY - tarSize && objY < tarY + tarSize) // touching the right boundary of a tile
            || (objX == tarX - tarSize && objY > tarY - tarSize && objY < tarY + tarSize) // touching the left boundary of a tile
            || (objX > tarX - tarSize && objX < tarX + tarSize && objY == tarY + tarSize) // touching the bottom boundary of a tile
            || (objX > tarX - tarSize && objX < tarX + tarSize && objY == tarY - tarSize))// touching the top boundary of a tile
                return true;
        }

        return false;    
    }


}