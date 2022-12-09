package gremlins;

public interface Movable {

    /** 
    * Move the Movable object to left.
    */
    public void left();

    /** 
    * Move the Movable object to right.
    */
    public void right();

    /** 
    * Move the Movable object to up.
    */
    public void up();

    /** 
    * Move the Movable object to down.
    */
    public void down();

    /**
     * Return the moving speed of the Movable object in the form of an int value.
     */
    public int getSpeed();
}