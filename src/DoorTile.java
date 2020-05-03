import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * @author Joseph Grieser/Bradley Peterson
 */
public final class DoorTile extends Tile_tm implements Damageable{

    private boolean isOpen;
    private char orientation;
    public final static char DOOR = 'D';
    public final static char LEFTWALL = 'L';
    public final static char RIGHTWALL = 'R';
    private int hp;
    private int hpMax;

    /**
     * Creates a new DoorTile at (xPos, yPos), oriented to orient. Door is closed.
     * @param xPos The x (horizontal) position of DoorTile
     * @param yPos The y (vertical) position of DoorTile
     * @param orient The orientation (NOT angle! See Mapper) of doorTile 
     * @throws IOException
     */
    public DoorTile(double xPos, double yPos, char orient) {
    	this(xPos, yPos, orient, false);
    }
    

    /**
     * Creates a new DoorTile at (xPos, yPos), oriented to orient. Door isOpen
     * @param xPos The x (horizontal) position of DoorTile
     * @param yPos The y (vertical) position of DoorTile
     * @param orient The orientation (NOT angle! See Mapper) of doorTile 
     * @param isOpen Whether or not the door is open- true if it is open, false otherwise
     * @throws IOException
     */
    public DoorTile(double xPos, double yPos, char orient, boolean isOpen) {
        super(importImage("/door_tile.png"), xPos, yPos, 4, 2); 
        orientation = orient;
        switch(orient) {
	        case Mapper.NORTH: setCurrSpriteRow(0); break;
	        case Mapper.EAST: setCurrSpriteRow(1); break;
	        case Mapper.SOUTH: setCurrSpriteRow(2); break;
	        case Mapper.WEST: setCurrSpriteRow(3); break;
	        default: 
	    }	

        makeDoorHitboxes();
        if(isOpen)
        	open();
        else
        	close();
        
        
        System.out.println("\n\n\n\n");
        for(ActionBox curr: getHitboxAsList()) {
        	System.out.println("curr " + curr);
        }
        System.out.println("\n\n\n\n\n");
        hp = 100;
    	hpMax = 100;
    	setFocusable(true);
    	addStat(hp, hpMax, Color.RED, Color.GREEN);
    	setObjName("Door Tile");
    }
    

 
    
    /**
     * checks if door is open
     * @return true if door is open; false otherwise
     */
    public boolean isOpen(){
        return isOpen;
    }

    /**
     * Opens the DoorTile
     */
    public void open(){
        isOpen = true;
        setCurrSpriteCol(1);
        setHitbox(DOOR, false);
    }

    /**
     * Closes the doorTile
     */
    public void close(){
        isOpen = false;
        setCurrSpriteCol(0);
        setHitbox(DOOR, true);
    }
    
    /**
     * Opens if closed, closed if open
     */
    public void toggle() {
    	if(hp > 0) {
	    	if(isOpen)
	    		close();
	    	else
	    		open();
    	}
    	else {
    		open();
    	}
    }
    
    
    
    public void setBars() {
    	setBarVal(0, hp);
    }


	public int getHealth() {
		return hp;
	}


	public int getHealthMax() { 
		return hpMax;
	}

	public void setHealthMax(int nuMax) {
		hpMax = nuMax;
		
	}

	public void setHealth(int healthVal) {
		if(healthVal > hpMax)
			hp = hpMax;
		else
			hp = healthVal;
		
	}

	public int takeDamage(int hit) {
		setHealth(getHealth() - hit);
		return getHealth();
	}


	public int getHealed(int help) {
		setHealth(getHealth() + help);
		return getHealth();
	}

	/**
	 * Doors can't attack! ... Yet.
	 */
	public void setAttack(int dmg){};

	/**
	 * Doors can't attack! ... Yet.
	 */
	public int getAttack() { 
		return 0;
	}
	
	
 
	/**
	 * Need to override the ole hitbox generator on account of the door
	 */
	public void makeDoorHitboxes() { 
		 
		 
		int doorThickness = SIZE/2;
		double doorXPos = 0, doorYPos = 0;
		int doorWidth = 0, doorHeight = 0;
		int wallWidth = 0, wallHeight = 0;
		//Left and right relative to the outward face
		double leftWallXPos = 0, leftWallYPos = 0;
		
		double rightWallXPos = 0, rightWallYPos = 0;
		 
		
		if(orientation == NORTH || orientation == SOUTH) {
 
			doorXPos = getXPosWorld();
			doorWidth = doorThickness;
			doorHeight = wallThickness;
			wallWidth = (SIZE - doorThickness)/2;
			wallHeight = wallThickness;
			System.out.println("Wall: " + wallWidth/2);
			
			leftWallXPos = doorXPos - doorWidth + wallWidth/2;
			rightWallXPos = doorXPos + doorWidth - (wallWidth/2);
			
			if(orientation == NORTH) 
				doorYPos = getYPosWorld() - SIZE/2 + wallThickness/2;
			
			else 
				doorYPos = getYPosWorld() + SIZE/2 - wallThickness/2;
			
			
			leftWallYPos = doorYPos;
			rightWallYPos = doorYPos;
		} 
		else {
			doorYPos = getYPosWorld(); 
			doorWidth = wallThickness;
			doorHeight = doorThickness;
			wallWidth = wallThickness;
			wallHeight = (SIZE - doorThickness)/2;
			leftWallYPos = doorYPos + doorHeight - (wallHeight/2);
			rightWallYPos = doorYPos - doorHeight + (wallHeight/2);
			
			if(orientation == WEST) 
				doorXPos = getXPosWorld() - SIZE/2 + wallThickness/2;
			else
				doorXPos = getXPosWorld() + SIZE/2 - wallThickness/2;
			
			leftWallXPos = doorXPos;
			rightWallXPos = doorXPos;
		}
		
		putHitbox(DOOR, ActionBox.makeActionBox(doorXPos, doorYPos, doorWidth, doorHeight));
		getHitbox(DOOR).setEnabled(isOpen());
		getHitbox(DOOR).setZPos(getHitbox(DOOR).getZPos() + 1);
		putHitbox(LEFTWALL, ActionBox.makeActionBox(leftWallXPos, leftWallYPos, wallWidth, wallHeight));
		getHitbox(LEFTWALL).setEnabled(true);
		getHitbox(LEFTWALL).setZPos(getHitbox(LEFTWALL).getZPos() + 1);
		putHitbox(RIGHTWALL, ActionBox.makeActionBox(rightWallXPos, rightWallYPos, wallWidth, wallHeight));
		getHitbox(RIGHTWALL).setEnabled(true);
		getHitbox(RIGHTWALL).setZPos(getHitbox(RIGHTWALL).getZPos() + 1);
		
		removeHitbox(orientation);
	}
	
	public String toString() {
		return super.toString() + " Oriented " + orientation;
	}


	public void doEffect(RenderObj collider) {	}


	public boolean isDead() {
		if(getHealth() <= 0) 
			return true;
		return false;
	}

 
}
