import java.awt.image.BufferedImage;


/**
 * @author Joseph Grieser
 */
public final class DoorTile extends Tile_tm{

    private boolean isOpen;

    public DoorTile(BufferedImage texture, double xPos, double yPos) {
        super(texture, xPos, yPos);
        isOpen = false;
    }

    public DoorTile(BufferedImage texture, double xPos, double yPos, boolean isOpen) {
        super(texture, xPos, yPos);
        this.isOpen = isOpen;
    }

    /**
     * checks if door is open
     * @return true if door is open; false otherwise
     */
    public boolean isOpen(){
        return isOpen;
    }

    /**
     * opens door
     */
    public void open(){
        isOpen = true;
    }

    /**
     * closes door
     */
    public void close(){
        isOpen = false;
    }
}
