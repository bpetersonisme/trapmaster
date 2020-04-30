import java.awt.image.BufferedImage;
import java.io.IOException;


/**
 * @author Joseph Grieser/Bradley Peterson
 */
public final class DoorTile extends Tile_tm{

    private boolean isOpen;

    /**
     * Creates a new DoorTile at (xPos, yPos), oriented to orient. Door is closed.
     * @param xPos The x (horizontal) position of DoorTile
     * @param yPos The y (vertical) position of DoorTile
     * @param orient The orientation (NOT angle! See Mapper) of doorTile 
     * @throws IOException
     */
    public DoorTile(double xPos, double yPos, int orient) {
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
    public DoorTile(double xPos, double yPos, int orient, boolean isOpen) {
        super(importImage("/door_tile.png"), xPos, yPos, 4, 2); 
        switch(orient) {
	        case Mapper.NORTH: setCurrSpriteRow(0); break;
	        case Mapper.EAST: setCurrSpriteRow(1); break;
	        case Mapper.SOUTH: setCurrSpriteRow(2); break;
	        case Mapper.WEST: setCurrSpriteRow(3); break;
	        default: 
	    }	
        if(isOpen)
        	open();
        else
        	close();
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
    }

    /**
     * Closes the doorTile
     */
    public void close(){
        isOpen = false;
        setCurrSpriteCol(0);
    }
}
