import java.awt.image.BufferedImage;
import javax.swing.Timer;

/**
 * @author Joseph Grieser
 */
public final class SpawnTile extends Tile_tm{

    private Timer timer;

    public SpawnTile(BufferedImage texture, double xPos, double yPos) {
        super(texture, xPos, yPos);
        timer = new Timer(0,null);
    }

    /**
     * spawns monsters into this; may need to be changed to better version
     * @param num number of monsters to be spawned
     */
    public void spawn(int num){
        for(int i = 0; i < num; i++){
            addMonster(new Monster_tm(){});
            timer.start();
        }
    }

    /**
     * sets the delay time on timer
     * @param ms number of milliseconds of new delay time
     */
    public void setDelay(int ms){
        timer.setInitialDelay(ms);
        timer.setDelay(ms);
    }
}
