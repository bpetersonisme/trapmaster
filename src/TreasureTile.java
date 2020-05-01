import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author Joseph Grieser
 */
public final class TreasureTile extends Tile_tm{

    private int treasure;
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
        setObjName("Treasure Tile");
        System.out.println("TREASURE TILE WITH TID " + tid);
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
    public void loseTreasure(int amount){
        treasure -= amount;
        if(treasure < 0) treasure = 0;
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
}
