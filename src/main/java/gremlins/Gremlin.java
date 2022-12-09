package gremlins;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import processing.core.PImage;

public class Gremlin extends Character implements Freezable{

    private int cooldownSpeed = 1; // 1 per frame
    private ArrayList<String> moveHistory = new ArrayList<String>();
    private static final Random randomGenerator = new Random();
    

    /** 
    * Create a new Gremlin object at the given position with set image.
    *
    * @param x  the x coordinate, measured in pixels.
    * @param y  the y coordinate, measured in pixels.
    * @param sprite the set image of the object.
    */
    public Gremlin(int x, int y, PImage sprite) {
        super(x, y, sprite);

        moveHistory.add("dir");
        moveHistory.add("dir");
    }

    /** 
    * Run the moving and firing logic for all Gremlin objects.
    */
    public void logic(App app) {
        /// move logic

        if(this.moveHistory.get(1).equals("dir")) {
            List<String> availableDir = getAvailableDir(app);
            // start move only if there is at least one available direction
            if(availableDir.size() > 0) {
                int random = randomGenerator.nextInt(availableDir.size());
                moveHistory.remove(0);
                moveHistory.add(availableDir.get(random));
                this.moveCurrentDir();
            }
        }
        // once the gremlin has started moving
        else{
            // if it touches any tile
            if(this.touchBoundary(this, app.getBg().getBrickTiles(), this.moveHistory.get(1)) 
            || this.touchBoundary(this, app.getBg().getStoneTiles(), this.moveHistory.get(1))) {
                List<String> availableDir = getAvailableDir(app); // whose size is between 1 (the way it came) to 3 (touch one tile)
                // only one direction to go
                if(availableDir.size() == 1) {
                    moveHistory.remove(0);
                    moveHistory.add(availableDir.get(0));
                }
                // remove the way it came, then only one direction left
                else if(availableDir.size() >= 2) {
                    if(moveHistory.get(1).equals("left")) {
                        availableDir.remove("right");  
                    }
                    else if(moveHistory.get(1).equals("right")) {
                        availableDir.remove("left");
                    }
                    else if(moveHistory.get(1).equals("up")) {
                        availableDir.remove("down");
                    }
                    else if(moveHistory.get(1).equals("down")) {
                        availableDir.remove("up");
                    }

                    int random = randomGenerator.nextInt(availableDir.size());
                    moveHistory.remove(0);
                    moveHistory.add(availableDir.get(random));
                }
                this.moveCurrentDir();
            }
            // as long as it doesn't touch any tile on the moving direction, keep moving
            else{
                this.moveCurrentDir();
            }
        }

        /// fire logic
        if(this.cooldownFrame >= app.getEnemyCooldown() * App.FPS) { 
            this.fire(app);
            this.cooldownFrame = 0;
        } 
        else {
            this.cooldownFrame += cooldownSpeed; // countdown decrease 1 per frame
        }

    }

    /** 
    * Draw the Gremlin object.
    */
    @Override
    public void draw(App app) {
        app.image(this.img, this.x, this.y);
        
    }

    /** 
    * Move to the direction according to the moveHistory. 
    * Scaffolding method for moving logic.
    */
    private void moveCurrentDir() {
        if(this.getMoveHistory().get(1).equals("left"))
            this.left();
        else if(this.getMoveHistory().get(1).equals("right"))
            this.right();
        else if(this.getMoveHistory().get(1).equals("up"))
            this.up();
        else if(this.getMoveHistory().get(1).equals("down"))
            this.down();
    }

    /* 
     * Return a list of Strings indicating currently available moving directions for the Gremlin object,
     * that is, has no touching tile in the direction.
     */
    private List<String> getAvailableDir(App app) {

        List<String> availableDir = new ArrayList<String>();
        String[] direction = {"left", "right", "up", "down"};
        for(String mode: direction) {
            if(!this.touchBoundary(this, app.getBg().getBrickTiles(), mode) 
            && !this.touchBoundary(this, app.getBg().getStoneTiles(), mode)) {
                availableDir.add(mode);
            }
        }
        return availableDir;
    }

    /* 
     * Convert the given direction String to corresponding int key event value.
     * 
     * @param direction  String that indicate one of the moving directions ("left", "right", "up", "down").
     */
    private int dirToInt(String direction) {
        if(direction.equals("left"))
            return 37;
        else if(direction.equals("right"))
            return 39;
        else if(direction.equals("up"))
            return 38;
        else if(direction.equals("down"))
            return 40;
        return 0;
    }

    /* 
     * Return a list of Strings keeping the records of the latest moving direction history.
     */
    public List<String> getMoveHistory() {
        return this.moveHistory;
    }

    /* 
     * Respawn a Grenlin object at a random position outside the 10 tiles radius.
     */
    public void respawn(App app) {
        List<int[]> availableTile = app.getBg().getAvailableTile(app);
        List<int[]> tooCloseTile = new ArrayList<int[]>();

        int wizX = app.getWiz().getX();
        int wizY = app.getWiz().getY();

        for(int[] position: availableTile) {
            if(app.getBg().distance(position[0], position[1], wizX, wizY) < 10 * CollidableObj.SIZE) {
                tooCloseTile.add(position);
            }
        }
        // delete the tiles that are too close to the wizard
        availableTile.removeAll(tooCloseTile);
        
        int random = randomGenerator.nextInt(availableTile.size()); // choose a random position from the available tiles
        this.teleport(availableTile.get(random)[0], availableTile.get(random)[1]);  // respawn as a new location

        this.setCooldown(0); // reset the firing count
    }

    /* 
     * Create a new Slime object to the Background object's Slime List.
     */
    public void fire(App app) {
        app.getBg().getSlimes().add(new Slime(this.x, this.y, this.dirToInt(moveHistory.get(1)), app.slime));
    }

    /** 
     * Set the moving speed and fire cooldown speed to 0.
     */
    public void freeze(){
        this.speed = 0;
        this.cooldownSpeed = 0;
    }

    /** 
     * Set the moving speed and fire cooldown speed back to default (1).
     */
    public void unfreeze(){
        this.speed = 1;
        this.cooldownSpeed = 1;
    }


}
