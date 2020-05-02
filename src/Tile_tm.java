import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics2D;
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
    //private Tile_tm[] neighbors;
    private HashMap<Character, Tile_tm> neighbors;
    private double[] valueToTreasure;
    private double[] valueToDoor;
    private List<Monster_tm> monsters; 
    private double treasureDist; //Distance to the nearest treasure
    private boolean treasureRouteSet; 
    private boolean entranceRouteSet;
    private double entranceDist; //Distance to the entrance 
    public static final int SIZE = 128;
    public static final char NORTH = Mapper.NORTH;
    public static final char EAST = Mapper.EAST;
    public static final char SOUTH = Mapper.SOUTH;
    public static final char WEST = Mapper.WEST;
    public static final char TREASURE = 'T';
    public static final char SPAWN = 'D';
    public static final int N_IN = 0;
    public static final int E_IN = 1;
    public static final int S_IN = 2;
    public static final int W_IN = 3;
    private int maxTreasureDist;
    private double AStarG; //the gScore from A* search 
    private double AStarF, AStarFT, AStarFS; //The fScore from A* search.
    private int TID; //The TID of the nearest TreasureTile
   

    
    public Tile_tm(BufferedImage texture, double xPos, double yPos, int numRows, int numCols) {
    	this(texture, xPos, yPos, numRows, numCols, 10);
    	
    }
    /**
     * Creates a new Tile_tm of spriteSheet texture, at position (xPos, yPos), with numRows animations,
     * Each numCols long
     * @param texture The sprite sheet for the Tile
     * @param xPos The xPosition of the tile's center
     * @param yPos The yPostiion of the tile's center
     * @param numRows The number of animations available
     * @param numCols The number of frames per animation
     * @param maxTreasureDistance how many tiles treasure will be checked for
     */
    public Tile_tm(BufferedImage texture, double xPos, double yPos, int numRows, int numCols, int maxTreasureDistance) {
       /* neighbors = new Tile_tm[4];
        neighbors[N_INDEX] = null;
        neighbors[E_INDEX] = null;
        neighbors[S_INDEX] = null;
        neighbors[W_INDEX] = null;*/
    	neighbors = new HashMap<Character, Tile_tm>();
    	neighbors.put(Mapper.NORTH, null);
    	neighbors.put(Mapper.EAST,  null);
    	neighbors.put(Mapper.SOUTH, null);
    	neighbors.put(Mapper.WEST, null);
        monsters = new ArrayList<Monster_tm>(); 
        setSpriteSheet(texture,numRows,numCols,SIZE,SIZE);
        setPosX(xPos);
        setPosY(yPos);
        treasureDist = -1;
        entranceDist = -1;
        treasureRouteSet = false;
        entranceRouteSet = false;
        valueToTreasure = new double[4];
        valueToDoor = new double[4]; 
        maxTreasureDist = SIZE*maxTreasureDistance;
        setObjName("Tile");
        AStarFS = Double.POSITIVE_INFINITY;
        AStarFT = Double.POSITIVE_INFINITY;
    }
    
    public double getGScore() {
    	return AStarG;
    }
    
    public void setGScore(double g) {
    	AStarG = g;
    }
    
    public double getFScore() {
    	return AStarF;
    }
    public double getFScore(char which) {
    	switch(which) {
    	case TREASURE: return AStarFT; 
    	case SPAWN: return AStarFS;
    	default: return -50000;
    	}
    }
    
    
    public void setFScore(double f) {
    	AStarF = f;
    }
    public void setFScore(char which, double f) {
    	switch(which) {
    	case TREASURE: AStarFT = f; break;
    	case SPAWN: AStarFS = f; break;
    	default: AStarF = f;
    	}
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
    public void setTreasureVal(Tile_tm neighbor, char dir) { 
    	if(neighbor != null) {
    		switch(dir) {
    		case NORTH: valueToTreasure[N_IN] = neighbor.getFScore(TREASURE); break;
    		case EAST: valueToTreasure[E_IN] = neighbor.getFScore(TREASURE); break;
    		case SOUTH: valueToTreasure[S_IN] = neighbor.getFScore(TREASURE); break;
    		case WEST: valueToTreasure[S_IN] = neighbor.getFScore(TREASURE); break;
    		default:
    		}
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
    public void setSpawnVal(Tile_tm neighbor, char dir) {
    	if(neighbor != null) {
    		switch(dir) {
    		case NORTH: valueToTreasure[N_IN] = neighbor.getFScore(SPAWN); break;
    		case EAST: valueToTreasure[E_IN] = neighbor.getFScore(SPAWN); break;
    		case SOUTH: valueToTreasure[S_IN] = neighbor.getFScore(SPAWN); break;
    		case WEST: valueToTreasure[S_IN] = neighbor.getFScore(SPAWN); break;
    		default:
    		}
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
    
    /**
     * This checks to see if two tiles are neighbors. A tile cannot be its own neighbor.
     * @param other The other tile this will be checked against
     * @return The direction in which the two are neighbors, or the character '0' if they are not.
     */
    public char areNeighbors(Tile_tm other) {
    	char result = '0';
    	if(getYPosWorld() == other.getYPosWorld()) {
    		if(getNeighborX(Mapper.WEST) == other.getXPosWorld()) 
    			result = Mapper.WEST;
    		else if(getNeighborX(Mapper.EAST) == other.getXPosWorld()) {
    			result = Mapper.EAST;
    		}
    	}
    	else if(getXPosWorld() == other.getXPosWorld()) {
    		if(getNeighborY(Mapper.NORTH) == other.getYPosWorld())
    			result = Mapper.NORTH;
    		else if(getNeighborY(Mapper.SOUTH) == other.getYPosWorld())
    			result = Mapper.SOUTH;
    	}
    	return result;
    }
    
    /**
     * @return The neighbor array, which holds up to four neighbors to the tile. 
     */
    public HashMap<Character, Tile_tm> getNeighbors(){
        return neighbors;
    }

    /**
     * @return A count of how many neighbors the tile currently has (between 0 and 4)
     */
    public int countNeighbors() {
    	int count = 0;
    	if(neighbors.get(Mapper.NORTH) != null) {
    		count++;
    	}
    	if(neighbors.get(Mapper.EAST) != null) {
    		count++;
    	}
    	if(neighbors.get(Mapper.SOUTH)!= null) {
    		count++;
    	}
    	if(neighbors.get(Mapper.WEST)!= null) {
    		count++;
    	}
    	return count;
    	
    }
    
    /**
     * Sets the neighbor at dirIndex to neighbor
     * @param neighbor The tile's new neighbor
     * @param dirIndex the direction the new neighbor is in.
     */
    private void setNeighbors(Tile_tm neighbor, char dir) {
    	neighbors.replace(dir, neighbor);
    }
    
    
    public Tile_tm getNeighbor(char dir) {
    	return neighbors.get(dir);
    }
    
    
    
    /**
     * Checks if two tiles are neighbors. If they are, it connects them. Obviously.
     * @param other The tile that would-be a neighbor. 
     */
    public void connectNeighbors(Tile_tm other) {
    	char neighborDir = areNeighbors(other);
    	switch(neighborDir) {
    		case Mapper.NORTH: 
	    		setNeighbors(other, NORTH);
	    		other.setNeighbors(this, SOUTH);
    		break;
    		case Mapper.EAST:
    			setNeighbors(other, EAST);
    			other.setNeighbors(this, WEST);
    			
    		break;
    		case Mapper.SOUTH:
    			setNeighbors(other, SOUTH);
    			other.setNeighbors(this, NORTH);
    		break;
    		case Mapper.WEST:
    			setNeighbors(other, WEST);
    			other.setNeighbors(this, EAST);
   			break;
   			default:
    		
    	}
    }
    
    /**
     * @return the TID of the nearest treasureTile. If there is no treasure Tile nearby, returns -1.
     */
    public int getNearestTID() {
    	return TID;
    }
    
    /**
     * Sets the TID- that is, the TID of the tile's nearest treasureTile, to nuTID
     * @param nuTID The new TID
     */
    public void setNearestTID(int nuTID) {
    	TID = nuTID;
    }
    
    /**
     * Given list, sets treasureDist- will also set the TID.
     * @param list A list of all treasureTiles
     */
    public void setTreasureDist(ArrayList<TreasureTile> list) { 
    	int i, len; 
    	double leastDistance = Double.POSITIVE_INFINITY; 
    	double currDistance;
    	int leastDistantTID = -1; 
    	TreasureTile currTreasure;
    	len = list.size();
    	for(i = 0; i < len; i++) {
    		currTreasure = list.get(i);
    		currDistance = getDistance(this, currTreasure);
    		if(currDistance < leastDistance && currDistance <= maxTreasureDist) {
    			leastDistance = currDistance;
    			leastDistantTID = currTreasure.getTID();
    		}
    	}
    	treasureDist = leastDistance;
    	TID = leastDistantTID;
    }
    
     
    
    /**
     * Given spawn, sets entrance distance 
     * @param spawn The spawn tile for the map
     */
    public void setEntranceDist(SpawnTile spawn) {
    	entranceDist = getDistance(this, spawn);
    }
    
    /**
     * Makes walls appear on any unconnected edge 
     */
    public void makeDecoration(BufferedImage currSprite) {
    	Graphics2D g2d = currSprite.createGraphics();
    	int wallThickness =(int)(SIZE * (32.0/256.0)); 
    	g2d.setColor(new Color(74, 54, 51)/*makes kind of a brown. Same color as the door walls.*/); 
    	if(neighbors.get(Mapper.NORTH) == null) {
    		
    		g2d.fillRect(0, 0, SIZE, wallThickness);
    	}
    	if(neighbors.get(Mapper.EAST) == null) {
    		g2d.fillRect(SIZE-wallThickness, 0, SIZE, SIZE);
    	}
    	if(neighbors.get(Mapper.SOUTH) == null) {
    		g2d.fillRect(0, SIZE-wallThickness, SIZE, SIZE);
    	}
    	if(neighbors.get(Mapper.WEST) == null) {
    		g2d.fillRect(0, 0, wallThickness, SIZE);
    	}
    	
    	
    	g2d.dispose();
    }
    
    /**
     * Sets treasureRouteSet to setTreas
     * @param setTreas Whether its treasure route has been set
     */
    public void setTreasureRoute(boolean setTreas) {
    	treasureRouteSet = setTreas;
    }
    
    /**
     * @return True if it has been routed for treasure, false otherwise
     */
    public boolean isTreasureRouteSet() {
    	return treasureRouteSet;
    }
    /**
     * Sets entranceRouteSet to setEnt
     * @param setEnt Whether its treasure route has been set
     */
    public void setEntranceRoute(boolean setEnt) {
    	entranceRouteSet = setEnt;
    }
    
    /**	
     * @return True if it has been routed for entrance, false otherwise 
     */
    public boolean isEntranceRouteSet() {
    	return entranceRouteSet;
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
    public final double getTreasureDist(){
        return treasureDist;
    }

    /**
     * @return unit distance between this and spawn tile 
     */
    public final double getEntranceDist(){
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
