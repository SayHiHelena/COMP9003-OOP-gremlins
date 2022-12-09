package gremlins;

public interface Freezable {

    /** 
    * Freeze the movement of the Freezable object.
    */
    public void freeze();

    /** 
    * Unfreeze the movement of the Freezable object back to default.
    */
    public void unfreeze();
}
