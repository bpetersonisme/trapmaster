import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.Timer;

/**
 * @author Joseph Grieser/Bradley Peterson
 */
public final class SpawnTile extends Tile_tm{


    private String monsterList;
    private int currMonster;
    private long nextMonsterTime;
    Scanner s;
    private char orientation;
    private int numTreasure; //Returns the number of treasureTiles on the current map
    /**
     * Creates a new Tile_tm of spriteSheet texture, at position (xPos, yPos), with numRows animations,
     * Each numCols long, and a monsterList  
     * @param xPos The xPosition of the tile's center
     * @param yPos The yPostiion of the tile's center 
     * @param list A list of monsters 
     * @param orient The direction the spawnTile will face. Uses Mapper orientations
     * @throws IOException
     */
    public SpawnTile(double xPos, double yPos, String list, char orient)  {
        super(RenderObj.importImage("/spawn_tile.png"), xPos, yPos, 1, 1);
        monsterList = list; 
        nextMonsterTime = System.currentTimeMillis() + 10000;
        numTreasure = 0;
        orientation = orient;
        switch(orient) {
	        case 'N': setAngle(180); break;
	        case 'E': setAngle(90); break;
	        case 'W': setAngle(-90); break;
	        default: 
	    }
        setType(TILE_SPAWN);
        s = new Scanner(monsterList);
        
    }
    
    /**
     * Returns the "monster list," which is composed of the following token format: "{timeInMilliseconds}{character}"
     * This is repeated until the last monster is through. One monsterList translates to a single wave of monsters. 
     * @return The monster list, described above
     */
    public String getMonsterList() {
    	return monsterList;
    }
    /**
     * Sets the "monster list" to newList. The function of the monster list is described in getMonsterList. This also re-sets the Scanner. 
     * @param newList The new monsterList
     */
    public void setMonsterList(String newList) { 
    		monsterList = newList;
    		s = new Scanner(monsterList); 
    }

    /**
     * spawns monsters at the "spawn point," which is the far-southern position of the spawn tile, 
     * provided that it is time to spawn a monster.  
     * @return The next monster in the monster list, if there IS a next monster. 
     */
    public Monster_tm spawn(){ 
    	Monster_tm newMonster = null;
    	System.out.println("nextMonsterTime is " + nextMonsterTime);
    	System.out.println("Curr Time is " + System.currentTimeMillis());
        if(System.currentTimeMillis() >= nextMonsterTime) { 
        	if(s.hasNext()) {
        		String cur = s.next();
        		System.out.println("Spawning a monster of type " + cur);
        		newMonster = spawnMonster(cur.charAt(0));
        	}
        	else { //If this is false, something went wrong. 
        		System.out.println("AAAA");
        	}
        	if(s.hasNextInt()) {
        		int next = s.nextInt();
        		System.out.println("Delay is: " + next);
        		nextMonsterTime = System.currentTimeMillis() + next;
        		System.out.println("Next monster time is " + nextMonsterTime);
        	}
        	else {
        		System.out.println("This is the last monster");
        		newMonster.setLastMonster(true);
        		s.close();
        		s = null;
        	}
        } 
        return newMonster;
    }

    /**
     * Makes walls appear on any unconnected edge 
     */
    public void makeDecoration(BufferedImage currSprite) {
    	Graphics2D g2d = currSprite.createGraphics();
    	int wallThickness =(int)(SIZE * (32.0/256.0)); 
    	g2d.setColor(new Color(74, 54, 51)/*makes kind of a brown. Same color as the door walls.*/); 
    	if(getNeighbor(NORTH) == null && orientation != Mapper.NORTH) {
    		g2d.fillRect(0, 0, SIZE, wallThickness);
    	}
    	if(getNeighbor(EAST) == null && orientation != Mapper.EAST) {
    		g2d.fillRect(SIZE-wallThickness, 0, SIZE, SIZE);
    	}
    	if(getNeighbor(SOUTH) == null && orientation != Mapper.SOUTH) {
    		g2d.fillRect(0, SIZE-wallThickness, SIZE, SIZE);
    	}
    	if(getNeighbor(WEST) == null && orientation != Mapper.NORTH) {
    		g2d.fillRect(0, 0, wallThickness, SIZE);
    	}
    	
    	
    	g2d.dispose();
    }
    
    /**
     * Creates a new monster dependent on the char selected by choice 
     * @param choice The new monster 
     * @return A new monster, type indicated by choice 
     */
    private Monster_tm spawnMonster(char choice) {
    	Monster_tm newMonster = null;
    	//If you want to do more with it, make it tied to orientation
    	double newMonsterXPos = getXPosWorld();
    	double newMonsterYPos = getYPosWorld() + SIZE/2 - wallThickness*4; 
    	switch(Character.toLowerCase(choice)) {
    		case 'k': //'K' is for kobolds
    		default: newMonster = new Kobold(newMonsterXPos, newMonsterYPos);
    	}
    	 
    	
    	return newMonster;
    }
    
  
   
    /**
     * Returns the number of treasure tiles on the current map
     * @return The number of treasure tiles 
     */
    public int getNumberOfTreasures() {
    	return numTreasure;
    }
    
    /**
     * Sets the number of treasure tiles to nuNumTre
     * @param nuNumTre The number of treasure tiles on the map
     */
    public void setNumberOfTreasures(int nuNumTre) {
    	numTreasure = nuNumTre;
    }
    
    public String getObjName() {
    	return "Spawn Tile"; 
    }
    
    public String toString() {
    	return getObjName() + " (" + (int)getXPosWorld() + ", " + (int)getYPosWorld() + ", " + getZPosWorld() + ")";
    }
}
