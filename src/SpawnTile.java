import java.awt.image.BufferedImage;
import java.util.Timer;

public final class SpawnTile extends Tile_tm{

    private Timer timer;

    public SpawnTile(Tile_tm[] neighbors, BufferedImage texture, int[] treasureDist, int[] entranceDist) {
        super(neighbors, texture, treasureDist, entranceDist);
        timer = new Timer();
    }

    public void spawn(){

    }
}
