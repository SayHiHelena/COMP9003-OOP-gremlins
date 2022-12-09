package gremlins;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Background {

    private String layout;
    private List<Stone> stoneTiles = new ArrayList<Stone>();
    private List<Brick> brickTiles = new ArrayList<Brick>();
    private List<Gremlin> gremlins = new ArrayList<Gremlin>();
    private List<Slime> slimes = new ArrayList<Slime>();
    private List<Fireball> fireballs = new ArrayList<Fireball>();
    private List<Exit> exitList = new ArrayList<Exit>(); // only one exit per level
    private List<Potion> potionList = new ArrayList<Potion>(); // only one potion shown at a time
    private List<Portal> portals = new ArrayList<Portal>();

    private double potionIntervalFrame = 1.0; 
    private double potionInterval = 8.0; // set the initial delay interval as 8s

    private boolean freezeToggle = false;
    private double freezeCountFrame = Potion.LASTING * App.FPS;

    private static final Random randomGenerator = new Random();
    

    /** 
    * Create a new Background object with an empty string stored as layout.
    */
    public Background() {
        this.layout = "";
    }

    /** 
    * Create a new Background object with specified layout String.
    *
    * @param layout  the file name of which contains the level's layout.
    */
    public Background(String layout) {
        this.layout = layout;
    }

    /** 
    * Run the logic for all the ColliableObj objects (except the Wizard) in the game.
    */
    public void logic(App app) {

        // generate a freeze potion if there's none
        if(potionList.size() == 0) {
            //set a generation interval between 8 - 12s
            if(potionIntervalFrame == 0) {
                potionInterval = randomGenerator.nextInt(5) + 8;
            }

            if(potionIntervalFrame >= potionInterval * App.FPS) {
                List<int[]> availableTile = this.getAvailableTile(app);
                List<int[]> tooCloseTile = new ArrayList<int[]>();

                int wizX = app.getWiz().getX();
                int wizY = app.getWiz().getY();

                // delete the tiles that are too close (within 10 grids) to the wizard
                for(int[] position: availableTile) {
                    if(app.getBg().distance(position[0], position[1], wizX, wizY) < 10 * CollidableObj.SIZE) {
                        tooCloseTile.add(position);
                    }
                }
                availableTile.removeAll(tooCloseTile);

                // choose a random position from the available tiles and create a potion
                int random = randomGenerator.nextInt(availableTile.size()); 
                potionList.add(new Potion(availableTile.get(random)[0], availableTile.get(random)[1], app.potion));

                // reset interval count
                potionIntervalFrame = -1;
            }
            potionIntervalFrame++;
        }




        
        
        // freeze logic
        if(this.freezeToggle) {
            if(freezeCountFrame == Potion.LASTING * App.FPS) {
                for(Slime slm: slimes) {
                    slm.freeze();
                }
                for(Gremlin g: gremlins){
                    g.freeze();
                }
            }
            else if(this.freezeToggle && freezeCountFrame <= 0) {
                for(Slime slm: slimes) {
                    slm.unfreeze();
                }
                for(Gremlin g: gremlins){
                    g.unfreeze();
                }
                freezeCountFrame = Potion.LASTING * App.FPS + 1;
                this.setFreezeToggle(false);
            }
            freezeCountFrame--;
        } 
        

        // when a fireball hit a brickwall
        ArrayList<Brick> toDelBrick = new ArrayList<Brick>();
        ArrayList<Fireball> toDelFireball = new ArrayList<Fireball>();
        for(Brick b: brickTiles){

            // if a brick has been marked as destroyed
            // start destroy count for the animation
            if(b.isDestroyed()){
                if(b.getDestroyCount() == 0)
                    b.setDestroyImg(app.brickDestroy0);
                else if(b.getDestroyCount() == 4)
                    b.setDestroyImg(app.brickDestroy1);
                else if(b.getDestroyCount() == 8)
                    b.setDestroyImg(app.brickDestroy2);
                else if(b.getDestroyCount() == 12)
                    b.setDestroyImg(app.brickDestroy3);
                // only delete the brick after the destroy animation
                else if(b.getDestroyCount() == 15)
                    toDelBrick.add(b);

                b.destroyCountPlus();
            }
            else { // the brick has not been destroyed
                for(Fireball f: fireballs) {
                    if(b.collide(b, f)) {
                        toDelFireball.add(f);
                        b.destroy();
                    }
                }
            }
            
        }
        brickTiles.removeAll(toDelBrick);
        fireballs.removeAll(toDelFireball);

        // when a fireball hit a stonewall
        toDelFireball = new ArrayList<Fireball>();
        for(Stone s: stoneTiles){
            for(Fireball f: fireballs) {
                if(s.collide(s, f)) {
                    toDelFireball.add(f);
                }
            }
        }
        fireballs.removeAll(toDelFireball);

        // when a fireball hit a gremlin
        toDelFireball = new ArrayList<Fireball>();
        for(Gremlin g: gremlins){
            g.logic(app);
            for(Fireball f: fireballs) {
                if(g.collide(g, f)) {
                    toDelFireball.add(f);
                    g.respawn(app);
                }
            }
        }
        fireballs.removeAll(toDelFireball);

        // when a fireball hit a slime
        toDelFireball = new ArrayList<Fireball>();
        ArrayList<Slime>toDelSlime = new ArrayList<Slime>();
        for(Slime slm: slimes){
            for(Fireball f: fireballs) {
                if(slm.collide(slm, f)) {
                    toDelFireball.add(f);
                    toDelSlime.add(slm);
                }
            }
        }
        fireballs.removeAll(toDelFireball);
        slimes.removeAll(toDelSlime);

        // when a slime hit a stonewall
        toDelSlime = new ArrayList<Slime>();
        for(Stone s: stoneTiles){
            for(Slime slm: slimes) {
                if(s.collide(s, slm)) {
                    toDelSlime.add(slm);
                }
            }
        }
        slimes.removeAll(toDelSlime);

        // when a slime hit a brickwall
        toDelSlime = new ArrayList<Slime>();
        for(Brick b: brickTiles){
            for(Slime slm: slimes) {
                if(b.collide(b, slm)) {
                    toDelSlime.add(slm);
                }
            }
        }
        slimes.removeAll(toDelSlime);
        
    }

    /** 
    * Draw the all the ColliableObj objects (except the Wizard) in the game.
    */
    public void draw(App app) {
        // draw stone tile
        for(Stone s: stoneTiles){
            s.draw(app);
        }
        // draw brick tile
        for(Brick b: brickTiles){
            b.draw(app);
        }

        // draw the exit
        for(Exit e: exitList){
            e.draw(app);
        }

        // draw portal
        for(Portal p: portals){
            p.draw(app);
        }

        // draw wizard's fireball
        for(Fireball f: fireballs) {
            f.logic();
            f.draw(app);
        }

        // draw slime
        for(Slime slm: slimes) {
            slm.logic();
            slm.draw(app);
        }

        // draw gremlin
        for(Gremlin g: gremlins){
            g.draw(app);
        }

        // draw potion
        for(Potion frz: potionList){
            frz.draw(app);
        }
        
    }

    /* 
     * Generate the level map according to the set layout.
     */
    public void createLayout(App app){
        try{
            File levelMap = new File(this.layout);
            Scanner read = new Scanner(levelMap);
            String mapRow;
            char mapGrid;
            int portalNum = 0;
            
            // assume a valid 33x36 map is given
            for(int y = 0; y < 33; y++) {
                mapRow = read.nextLine();
                for(int x = 0; x < 36; x++) {
                    mapGrid = mapRow.charAt(x);

                    // create new Stone objects
                    if(mapGrid == 'X') {
                        stoneTiles.add(new Stone(x * CollidableObj.SIZE, y * CollidableObj.SIZE, app.stonewall));   
                    }

                    // create new Brick objects
                    else if(mapGrid == 'B') {
                        brickTiles.add(new Brick(x * CollidableObj.SIZE, y * CollidableObj.SIZE, app.brickwall));
                    }

                    // create new Gremlin objects
                    else if(mapGrid == 'G') {
                        gremlins.add(new Gremlin(x * CollidableObj.SIZE, y * CollidableObj.SIZE, app.gremlin));
                    }

                    // create a new Wizard object
                    else if(mapGrid == 'W') {
                        if(app.getWiz() == null) // first level
                            app.setWiz(new Wizard(x * CollidableObj.SIZE, y * CollidableObj.SIZE, app.wizRight));
                        else {
                            app.getWiz().teleport(x * CollidableObj.SIZE, y * CollidableObj.SIZE);
                            app.setKeyDetect(false);
                        }
                    }
    
                    // create a new Exit object
                    else if(mapGrid == 'E') {
                        exitList.add(new Exit(x * CollidableObj.SIZE, y * CollidableObj.SIZE, app.exit));
                        if(exitList.size() > 1) {
                            System.out.println("Invalid Map: more than one exit.");
                            System.exit(0);
                        }
                    }

                    // create a new Portal object
                    else if(mapGrid == 'P') {
                        portals.add(new Portal(x * CollidableObj.SIZE, y * CollidableObj.SIZE, app.portal0, app.portal));
                        portalNum++;
                    }
                }
            }

            read.close();

            // maybe create a new exception could be better?
            if(portalNum == 1) {
                System.out.println("The map has invalid number of portal.");
                System.exit(0);
            }

        } catch(FileNotFoundException e) {
            System.out.println("File \"" + layout + "\" not found.");
            System.exit(0);
        } catch(NoSuchElementException e) {
            System.out.println("The map has invalid number of rows.");
            System.exit(0);
        } catch(IndexOutOfBoundsException e) {
            System.out.println("The map has invalid number of columns.");
            System.exit(0);
        }
        
    }

    /* 
     * Return a list of all the Stone objects in the current map.
     */
    public List<Stone> getStoneTiles(){
        return this.stoneTiles;
    }

    /* 
     * Return a list of all the Brick objects in the current map.
     */
    public List<Brick> getBrickTiles(){
        return this.brickTiles;
    }

    /* 
     * Return a list of all the Gremlin objects in the current map.
     */
    public List<Gremlin> getGremlins(){
        return this.gremlins;
    }

    /* 
     * Return a list of all the Slime objects in the current map.
     */
    public List<Slime> getSlimes(){
        return this.slimes;
    }

    /* 
     * Return a list of all the Fireball objects in the current map.
     */
    public List<Fireball> getFireballs(){
        return this.fireballs;
    }

    /* 
     * Return a list containing the Exit object in the current map.
     */
    public List<Exit> getExit(){
        return this.exitList;
    }

    /* 
     * Reset the list of Potion objects to a new empty list.
     */
    public void resetPotion() {
        this.potionList = new ArrayList<Potion>();
    }

    /* 
     * Return a list containing the Potion object in the current map.
     */
    public List<Potion> getPotion(){
        return this.potionList;
    }

    /* 
     * Return a list of all the Portal objects in the current map.
     */
    public List<Portal> getPortals(){
        return this.portals;
    }

    /* 
     * Set the freeze toggle to the given boolean value.
     */
    public void setFreezeToggle(boolean bool) {
        this.freezeToggle = bool;
    }
    
    /* 
     * Return a boolean value telling if the freeze potion is currently in effect.
     */
    public boolean getFreezeToggle() {
        return this.freezeToggle;
    }

    /* 
     * Return a double value of the countdown of the freeze potion in frame.
     */
    public double getFreezeCount() {
        return this.freezeCountFrame;
    }

    /**
     * Return a boolean value telling if any of the CollideObj from the target list at the given position.
     *
     * @param x  the x coordinate, measured in pixels.
     * @param y  the y coordinate, measured in pixels.
     * @param targetObjList  a list containing lists of CollidableObj objects.
     */
    private boolean hasObj(int x, int y, List<List<? extends CollidableObj>> targetObjList) { 
            
        for(List<? extends CollidableObj> tarList: targetObjList) {
            for(CollidableObj tar: tarList) {
                int tarX = tar.getX();
                int tarY = tar.getY();
                
                if(x == tarX && y == tarY) {
                    return true;
                }
            } 
        }
        return false;    
    }

    /**
     * Return a list of int arrays containing the x and y value of the tile which 
     * has not been occupied by any Stone, Brick, Exit, Portal.
     */
    public List<int[]> getAvailableTile (App app)  {
        List<int[]> availableTile = new ArrayList<int[]>();

        // a list of all the tiles to be tested
        List<List<? extends CollidableObj>> targetObjList = new ArrayList<List<? extends CollidableObj>>();
        targetObjList.add(stoneTiles);
        targetObjList.add(brickTiles);
        targetObjList.add(exitList);
        targetObjList.add(portals);

        for(int y = 0 ; y < App.HEIGHT - App.BOTTOMBAR; y += CollidableObj.SIZE) {
            for(int x = 0; x < App.WIDTH; x += CollidableObj.SIZE) {
                if(!hasObj(x, y, targetObjList)) {
                    //if an tile empty, store the x & y as an int array
                    availableTile.add(new int[] {x, y});
                }
            }
        }
        return availableTile;
    }

    /**
     * Calculate the distance between the given x and y position and the target x and y position
     * 
     * @param x  the current x coordinate, measured in pixels.
     * @param y  the current y coordinate, measured in pixels.
     * @param tarX  the target x coordinate, measured in pixels.
     * @param tarY  the target y coordinate, measured in pixels.
     */
    public double distance(int x, int y, int tarX, int tarY) {
		double dist = Math.sqrt(Math.pow(x - tarX, 2) + Math.pow(y - tarY, 2));
		return dist;
	}

}