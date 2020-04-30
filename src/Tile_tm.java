import java.util.List;
import java.util.ArrayList;
import java.awt.image.BufferedImage;

/**
 * @author Bradley Peterson/Joseph Grieser
 */
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
    private int[] valueToTreasure;
    private int[] valueToDoor;
    private List<Monster_tm> monsters; 
    private int treasureDist; //Distance to the nearest treasure
    private int entranceDist; //Distance to the entrance 
    public static final int SIZE = 128;
    private int TID; //The TID of the nearest TreasureTile

    /**
     * Creates a new Tile_tm of spriteSheet texture, at position (xPos, yPos), with numRows animations,
     * Each numCols long
     * @param texture The sprite sheet for the Tile
     * @param xPos The xPosition of the tile's center
     * @param yPos The yPostiion of the tile's center
     * @param numRows The number of animations available
     * @param numCols The number of frames per animation
     */
    public Tile_tm(BufferedImage texture, double xPos, double yPos, int numRows, int numCols) {
        neighbors = new Tile_tm[4];
        monsters = new ArrayList<Monster_tm>(); 
        setSpriteSheet(texture,numRows,numCols,SIZE,SIZE);
        setPosX(xPos);
        setPosY(yPos);
        treasureDist = -1;
        entranceDist = -1;
        valueToTreasure = new int[4];
        valueToDoor = new int[4]; 
    }

    /**
     * Sets the value to the treasure for all four directions. Value will be 
     * negative if the edge in question is inaccessible 
     * @param vals The 'value,' that is the distance, between the treasure and the adjacent tiles
     */
    public void setTreasureVals(int[] vals) {
    	if(vals.length >= 4) {
    		for(int i = 0; i < vals.length; i++) {
    			valueToTreasure[i] = vals[i];
    		}
    	}
    }
    
    /**
     * Sets the value to the treasure for dir. Value will be negative if the edge
     * in question is inaccessible 
     * @param val The 'value,' that is, the distance between the treasure and the adjacent tile
     * @param dir The direction, represented by a single character. 
     */
    public void setTreasureVal(int val, char dir) {
    	switch(dir) {
    	case 'N': valueToTreasure[0] = val; break;
    	case 'E': valueToTreasure[1] = val; break;
    	case 'S': valueToTreasure[2] = val; break;
    	case 'W': valueToTreasure[3] = val; break;
    	default: System.out.println("UNKNOWN CHARACTER- NO ACTION TAKEN"); 
    	}
    }
    
    /**
     * Sets the value to the spawn for all four directions. Value will be negative
     * if the edge in question is inaccessible. 
     * @param vals The 'values,' that is the distance, between the source and the adjacent tiles
     */
    public void setSpawnVal(int[] vals) {
    	if(vals.length >= 4) {
    		for(int i = 0; i < vals.length; i++) {
    			valueToDoor[i] = vals[i];
    		}
    	}
    }
    
    /**
     * Sets the value to the spawn for a specific direction. Value will be negative
     * if the edge in question is inaccessible. 
     * @param vals The 'values,' that is the distance, between the source and the adjacent tiles
     * @param dir The direction being altered
     */
    public void setSpawnVal(int val, char dir) {
    	switch(Character.toUpperCase(dir)) {
    	case 'N': valueToDoor[0] = val; break;
    	case 'E': valueToDoor[1] = val; break;
    	case 'S': valueToDoor[2] = val; break;
    	case 'W': valueToDoor[3] = val; break;
    	default: System.out.println("UNKNOWN CHARACTER- NO ACTION TAKEN"); 
    	}
    }
    
    /**
     * Returns the world X position of the neighbor at dir, even if it has no neighbor in that direction.
     * Uses mapper's final direction ints. 
     * @param dir The direction required. 
     */
    public double getNeighborX(int dir) {
    	double result = 0;
    	switch(dir) {
    	case Mapper.NORTH: 
    	case Mapper.SOUTH:  
    		result = getXPosWorld(); 
    	break;
    	case Mapper.EAST: 
    		result = getXPosWorld() + SIZE; 
    	break;
    	case Mapper.WEST:
    		result = getXPosWorld() - SIZE;
    	break;
   		default: System.out.println("UNKNOWN DIRECTION");
    	}
    	return result;
    }
    
    /**
     * Returns the world Y position of the neighbor at dir, even if it has no neighbor in that direction.
     * Uses mapper's final direction ints. 
     * @param dir The direction required. 
     */
    public double getNeighborY(int dir) {
    	double result = 0;
    	switch(dir) {
    	case Mapper.NORTH: 
    		result = getYPosWorld() - SIZE; 
    	break;
    	case Mapper.SOUTH:   
    		result = getYPosWorld() + SIZE;
    	break;
    	case Mapper.EAST: 
    	case Mapper.WEST:
    		result = getYPosWorld();
    	break;
   		default: System.out.println("UNKNOWN DIRECTION");
    	}
    	return result;
    }
    
    
    
  //===================================================================
    
    
    /**
     * adds non-null monster to this
     * @param monster monster to be added to monsters
     */
    public final void addMonster(Monster_tm monster){
        if(monster == null) return;
        monsters.add(monster);
    }

    
    
    
    
    
    /**
     * passes monster from this to neighbor
     * @param monsterIndex index of monster to be passed in monsters
     * @param neighborIndex index of neighbor to be passed to; may replace with enum
    
    public final void giveMonster(int monsterIndex, Map_tm.Direction neighborIndex){
        //if(monsterIndex < 0 || monsterIndex >= monsters.size() || neighborIndex < 0 || neighborIndex >= 4) throw new IndexOutOfBoundsException();
        //if(monsters.get(monsterIndex) == null || neighbors[neighborIndex] == null) return;
        //neighbors[neighborIndex].addMonster(monsters.remove(monsterIndex));
        neighbors[neighborIndex.index()].addMonster(monsters.remove(monsterIndex));
    }
 */
    /**
     * gets monsters in this
     * @return monsters in this
     */
    public final List<Monster_tm> getMonsters(){
        return monsters;
    }

    //public final BufferedImage getTexture(){
    //    return texture;
    //}

    /**
     * gets tiles surrounding this
     * @return tiles surrounding this
     */
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

    //public final double getStartXPos(){
    //    return getPosX();
    //}

    //public final double getStartYPos(){
    //    return getPosY();
    //}

    /**
     * gets x-coordinate of end of tile
     * @return x-coordinate of end of tile
     */
    public final double getEndXPos(){
        return getPosX() + SIZE;
    }

    /**
     * gets y-coordinate of end of tile
     * @return y-coordinate of end of tile
     */
    public final double getEndYPos(){
        return getPosY() + SIZE;
    }

    /**
     * gets number of tiles between this and tile with treasure
     * @return number of tiles between this and tile with treasure
     */
    public final int getTreasureDist(){
        return treasureDist;
    }

    /**
     * gets number of tiles between this and spawn tile
     * @return number of tiles between this and spawn tile
     */
    public final int getEntranceDist(){
        return entranceDist;
    }

    /**
     * set number of tiles between this and tile with treasure
     * @param newTreasureDist new number of tiles between this and tile with treasure
     */
    public final void setTreasureDist(int newTreasureDist){
        treasureDist = newTreasureDist;
    }

    /**
     * set number of tiles between this and spawn tile
     * @param newEntranceDist new number of tiles between this and spawn tile
     */
    public final void setEntranceDist(int newEntranceDist){
        entranceDist = newEntranceDist;
    }

    /**
     * Sets other tile as neighbor of this and sets this as neighbor of the other tile
     * @param neighbor Tile to set as neighbor
     * @param index direction of neighbor from this
     
    public final void setNeighbor(Tile_tm neighbor, Map_tm.Direction index){
        //this.neighbors = neighbors;
        //if(index < 0 || index >= 4) return;
        //switch(index){
        //    case NORTH:
        //        neighbor.neighbors[2] = this;
        //        neighbors[0] = neighbor;
        //        break;
        //    case EAST:
        //        neighbor.neighbors[3] = this;
        //        neighbors[1] = neighbor;
        //        break;
        //    case SOUTH:
        //        neighbor.neighbors[0] = this;
        //        neighbors[2] = neighbor;
        //        break;
        //    case WEST:
        //        neighbor.neighbors[1] = this;
        //        neighbors[3] = neighbor;
        //        break;
            //default:
            //    return;
        //}
        //neighbors[index] = neighbor;
        neighbor.neighbors[index.reverse()] = this;
        neighbors[index.index()] = neighbor;
    }
*/
 }
