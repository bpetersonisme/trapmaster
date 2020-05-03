import java.io.IOException;

/**
 * @author Joseph Grieser
 */
public final class TreasureTile extends Tile_tm implements Damageable {

    private int treasure;
    public static char BOX = 'B';
    public static char TREASURE = 'T';
    private int TID;
    /**
     * Creates a new TreasureTile at (xPos, yPos). Will have a Mapper-assigned TID
     * @param xPos The x (horizontal) position of the Treasure tile
     * @param yPos The y (vertical) position of the Treasure tile
     * @param tid The Treasure ID of 
     * @throws IOException
     */
    public TreasureTile(double xPos, double yPos, int tid) {
        super(importImage("/treasure_tile.png"), xPos,yPos, 1, 1);
        treasure = 0;
        TID = tid;
        addTreasureBounds();
        setObjName("Treasure Tile");
        
    }

    /**
     * Returns the treasureTile's tid 
     * @return tid The treasure ID of the treasureTile
     */
    public int getTID() {
    	return TID;
    }
    
    /**
     * Sets the treasureTile's tid
     * @param nuTID The NEW treasure ID
     */
    public void setTID(int nuTID) {
    	TID = nuTID;
    }
    

    /**
     * decrements the amount of treasure in this; sets treasure at 0 if treasure goes below 0
     * @param amount amount of treasure removed from treasure
     */
    public int loseTreasure(int amount){
        int loss = amount;
    	if(getTreasure() < loss) {
    		loss = getTreasure();
    	}
    	setTreasure(getTreasure() - loss);
        return loss;
    }

    /**
     * increments the amount of treasure in this
     * @param amount amount of treasure added to treasure
     */
    public void addTreasure(int amount){
        treasure += amount;
    }

    /**
     * checks if this has treasure
     * @return true if treasure does not equal 0, false otherwise
     */
    public boolean hasTreasure(){
        return treasure != 0;
    }

    /**
     * gets treasure in this
     * @return treasure in this
     */
    public int getTreasure(){
        return treasure;
    }
    /**
     * Sets the amount of treasure to treas
     * @param treas The new amount of treasure in this TreasureTile
     */
     public void setTreasure(int treas) {
    	 if(treas < 0)
    		 treas = 0;
    	 else
    		 treasure = treas;
    	 
     }
     /**
      * Adds a bounding box around the treasure- a colliding one to keep 
 	  * mobs from walking over the chest, and a non-colliding one to take 
 	  * loot from 
      */
     public void addTreasureBounds() {
    	 
    	 putHitbox(BOX, ActionBox.makeActionBox(getXPosWorld(), getYPosWorld() - 3, (int)(SIZE* (116.0/256)), (int)(SIZE * (58.0/256))));
    	 getHitbox(BOX).setEnabled(true);
    	 putHitbox(TREASURE, ActionBox.makeActionBox(getXPosWorld(), getYPosWorld() - 3, (int)(SIZE* (116.0/256)) + 10, (int)(SIZE * (58.0/256))+10));
    	 getHitbox(TREASURE).setEnabled(true);
     }

	public int getHealth() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getHealthMax() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setHealth(int healthVal) {
		// TODO Auto-generated method stub
		
	}

	public void setHealthMax(int nuMax) {
		// TODO Auto-generated method stub
		
	}

	public int takeDamage(int hit) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getHealed(int help) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setAttack(int dmg) {
		// TODO Auto-generated method stub
		
	}

	public int getAttack() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void doEffect(RenderObj collider) {
		// TODO Auto-generated method stub
		
	}

	public boolean isDead() {
		if(getHealth() <= 0) 
			return true;
		return false;
	}
}
