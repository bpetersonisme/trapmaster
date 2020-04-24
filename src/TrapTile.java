import java.awt.image.BufferedImage;

/**
 * @author Joseph Grieser
 */
public final class TrapTile extends Tile_tm{

    private Trap_tm trap;

    public TrapTile(BufferedImage texture, double xPos, double yPos) {
        super(texture, xPos, yPos);
        this.trap = null;
    }

    public TrapTile(BufferedImage texture, double xPos, double yPos, Trap_tm trap) {
        super(texture, xPos, yPos);
        this.trap = trap;
    }

    /**
     * adds trap to this; can only have one trap at a time
     * @param trap trap to be added to this
     */
    public void addTrap(Trap_tm trap){
        if(!hasTrap()) {
            this.trap = trap;
        }
    }

    /**
     * Removes trap from this
     */
    public void removeTrap(){
        trap = null;
    }

    /**
     * checks if this has a trap
     * @return true if this has a trap; false otherwise
     */
    public boolean hasTrap(){
        return trap != null;
    }

    /**
     * gets trap in this
     * @return trap in this; null if no trap
     */
    public Trap_tm getTrap() {
        return trap;
    }
}
