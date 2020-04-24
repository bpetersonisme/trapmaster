import java.awt.image.BufferedImage;

/**
 * @author Joseph Grieser
 */
public final class TreasureTile extends Tile_tm{

    private int treasure;

    public TreasureTile(BufferedImage texture, double xPos, double yPos) {
        super(texture, xPos,yPos);
        treasure = 0;
    }

    public TreasureTile(BufferedImage texture, double xPos, double yPos, int treasure){
        super(texture, xPos, yPos);
        this.treasure = treasure;
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
