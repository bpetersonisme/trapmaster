import java.util.List;
import java.util.ArrayList;
import java.awt.image.BufferedImage;

public abstract class Tile_tm extends RenderObj  {

    /*
    Don't know if tiles will have matching width and height, but I assumed they will.
    Initially, neighbors' distance to treasure and entrance were kept in arrays, with
    the value at each index corresponding to neighbor at same index, but I realized
    each tile can store its own distances, so instead of arrays each tile stores its
    own distances and the neighbors' distances can be found by calling on getters.
    The original code is commented out in case it needs to be changed back.
     */
    private Tile_tm[] neighbors;
    private List<Monster_tm> monsters;
    private BufferedImage texture;
    //private int[] treasureDist;
    //private int[] entranceDist;
    private int treasureDist;
    private int entranceDist;
    private int xPos;
    private int yPos;
    private int size;

    /*
    Not sure how much of this needs to be passed in on initialization and how much can be added later with setters
     */
    public Tile_tm(Tile_tm[] neighbors, BufferedImage texture, int xPos, int yPos, int treasureDist, int entranceDist){
        this.neighbors = neighbors;
        monsters = new ArrayList<Monster_tm>();
        this.texture = texture;
        this.xPos = xPos;
        this.yPos = yPos;
        this.treasureDist = treasureDist;
        this.entranceDist = entranceDist;
        size = 256;
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

    /*
    public final int[] getTreasureDist(){
        return treasureDist;
    }

    public final int[] getEntranceDist(){
        return entranceDist;
    }

    public final int getTreasureDist(Tile_tm neighbor){
        for(int i = 0; i < neighbors.length; i++){
            if(neighbors[i].equals(neighbor)){
                return treasureDist[i];
            }
        }
        return -1;
    }

    public final int getEntranceDist(Tile_tm neighbor){
        for(int i = 0; i < neighbors.length; i++){
            if(neighbors[i].equals(neighbor)){
                return entranceDist[i];
            }
        }
        return -1;
    }

    public final void setTexture(BufferedImage image){
        texture = image;
    }
    */

    public final int getStartXPos(){
        return xPos;
    }

    public final int getStartYPos(){
        return yPos;
    }

    public final int getEndXPos(){
        return xPos + size;
    }

    public final int getEndYPos(){
        return yPos + size;
    }

    public final int getTreasureDist(){
        return treasureDist;
    }

    public final int getEntranceDist(){
        return entranceDist;
    }

    public final void setTreasureDist(int newTreasureDist){
        treasureDist = newTreasureDist;
    }

    public final void setEntranceDist(int newEntranceDist){
        entranceDist = newEntranceDist;
    }

    public final void setNeighbors(Tile_tm[] neighbors){
        this.neighbors = neighbors;
    }

 }
