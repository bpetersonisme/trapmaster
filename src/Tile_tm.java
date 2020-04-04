import java.util.List;
import java.util.ArrayList;
import java.awt.image.BufferedImage;

public abstract class Tile_tm extends RenderObj  {

    private Tile_tm[] neighbors;
    private List<Monster_tm> monsters;
    private BufferedImage texture;
    private int[] treasureDist;
    private int[] entranceDist;

    public Tile_tm(Tile_tm[] neighbors, BufferedImage texture, int[] treasureDist, int[] entranceDist){
        this.neighbors = neighbors;
        monsters = new ArrayList<Monster_tm>();
        this.texture = texture;
        this.treasureDist = treasureDist;
        this.entranceDist = entranceDist;
    }

    public final void addMonster(Monster_tm monster){
        if(monster == null) throw new NullPointerException();
        monsters.add(monster);
    }

    public final void giveMonster(int monsterIndex, int neighborIndex){
        if(monsterIndex < 0 || monsterIndex >= monsters.size() || neighborIndex < 0 || neighborIndex >= 4) throw new IndexOutOfBoundsException();
        if(monsters.get(monsterIndex) == null || neighbors[neighborIndex] == null) return;
        neighbors[neighborIndex].addMonster(monsters.remove(monsterIndex));
    }

    public final List<Monster_tm> getMonsters(){
        return monsters;
    }

    public final BufferedImage getTexture(){
        return texture;
    }

    public final Tile_tm[] getNeighbors(){
        return neighbors;
    }

    public final int[] getTreasureDist(){
        return treasureDist;
    }

    public final int[] getEntranceDist(){
        return entranceDist;
    }
}
