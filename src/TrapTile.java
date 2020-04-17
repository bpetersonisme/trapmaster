import java.awt.image.BufferedImage;

public final class TrapTile extends Tile_tm{

    private Trap_tm trap;

    public TrapTile(Tile_tm[] neighbors, BufferedImage texture, int xPos, int yPos, int treasureDist, int entranceDist) {
        super(neighbors, texture, xPos, yPos, treasureDist, entranceDist);
        this.trap = null;
    }

    public TrapTile(Tile_tm[] neighbors, BufferedImage texture, int xPos, int yPos, int treasureDist, int entranceDist, Trap_tm trap) {
        super(neighbors, texture, xPos, yPos, treasureDist, entranceDist);
        this.trap = trap;
    }

    public void addTrap(Trap_tm trap){
        this.trap = trap;
    }

    public void removeTrap(){
        trap = null;
    }

    public boolean hasTrap(){
        return trap != null;
    }

    public Trap_tm getTrap() {
        return trap;
    }
}
