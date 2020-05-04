import java.util.HashMap; 
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * @author Bradley Peterson/Joseph Grieser
 */
public abstract class Tile_tm extends RenderObj implements Collideable  {

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
    private HashMap<Character, ActionBox> boundaries; 
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
    public static final char COMP = 'C';
    public static final int N_IN = 0;
    public static final int E_IN = 1;
    public static final int S_IN = 2;
    public static final int W_IN = 3;
    public ArrayList<Character> boundaryKeys;

	public final int wallThickness =(int)(SIZE * (32.0/256.0)); 
    private int maxTreasureDist; 
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
    	setPosX(xPos);
        setPosY(yPos);
        boundaryKeys = new ArrayList<Character>();
        boundaryKeys.add(NORTH);
        boundaryKeys.add(SOUTH);
        boundaryKeys.add(EAST);
        boundaryKeys.add(WEST);
        boundaries = new HashMap<Character, ActionBox>();
        makeHitboxes();
        
        setType(TILE);
        
        
        setSpriteSheet(texture,numRows,numCols,SIZE,SIZE);
        
        treasureDist = -1;
        entranceDist = -1;
        treasureRouteSet = false;
        entranceRouteSet = false; 
        maxTreasureDist = SIZE*maxTreasureDistance;
        setObjName("Tile");
        AStarF = Double.POSITIVE_INFINITY;
        AStarFS = Double.POSITIVE_INFINITY;
        AStarFT = Double.POSITIVE_INFINITY;
    }
    
 
  
    
    public double getFScore(char which) {
    	switch(which) {
    	case TREASURE: return AStarFT; 
    	case SPAWN: return AStarFS;
    	case COMP: return AStarF;
    	default: return Double.NEGATIVE_INFINITY;
    	}
    }
    
    
 
    
    public void setFScore(char which, double f) {
    	switch(which) {
    	case TREASURE: AStarFT = f; break;
    	case SPAWN: AStarFS = f; break;
    	case COMP: AStarF = f; break;
    	default: 
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
     * @param Which FScore you're looking for
     * @return The direction of the neighbor with the lowest FScore
     */
    public char getMostValuableNeighbor(char which) {
    
    	char dir = NORTH;
    	char currDir = NORTH;
    	double lowestFScore = Double.POSITIVE_INFINITY;
    	Tile_tm neighbor; 
    	while(currDir != 'D') {
    		neighbor = getNeighbor(currDir);
    		if(neighbor != null) {
    			if(neighbor.getFScore(which) < lowestFScore) {
    				lowestFScore = neighbor.getFScore(which);
    				dir = currDir;
    			}
    		}
    		currDir = Map_tm.directionCycler(currDir);
    	}
    	
    	 
    	
    	return dir;
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

    public char getNeighborDir(Tile_tm neighbor) {
    	if(neighbor.equals(neighbors.get(NORTH)))
    		return NORTH;
    	if(neighbor.equals(neighbors.get(EAST)))
    		return EAST;
    	if(neighbor.equals(neighbors.get(SOUTH)))
    		return SOUTH;
    	if(neighbor.equals(neighbors.get(WEST)))
    		return WEST;
    	
    	
    	return 'D';
    }
    
    
    public ArrayList<Tile_tm> getNeighborList() {
    	ArrayList<Tile_tm> list = new ArrayList<Tile_tm>();
    	
    	if(getNeighbor(NORTH) != null)
    		list.add(getNeighbor(NORTH));
    	if(getNeighbor(EAST) != null)
    		list.add(getNeighbor(EAST));
    	if(getNeighbor(SOUTH) != null)
    		list.add(getNeighbor(SOUTH));
    	if(getNeighbor(WEST) != null)
    		list.add(getNeighbor(WEST));
    	return list;
    	
    	
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
    	setHitbox(neighborDir, false);
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
    
    public HashMap<Character, ActionBox> getHitboxes() {
    	return boundaries;
    }
    
    
    public ArrayList<ActionBox> getHitboxAsList() {	
    	ArrayList<ActionBox> list = new ArrayList<ActionBox>();
    	int i = 0;
    	ActionBox currBound;
    	for(i = 0; i < boundaryKeys.size(); i++) {
    		currBound = boundaries.get(boundaryKeys.get(i));
    		if(currBound != null)
    			list.add(currBound);
    	}
    	
    	 
    	
    	return list;
    }
    
    /**
     * Sets the boundary corresponding to boxkey to isEnabled
     * @param boxKey The char key of the actionbox being enabled/disabled
     * @param isEnabled True if the actionbox should be enabled- (i.e. collide), and false otherwise
     */
    public void setHitbox(char boxKey, boolean isEnabled) {
    	if(boundaries.get(boxKey) != null) 
    		boundaries.get(boxKey).setEnabled(isEnabled);
    }
    
    /**
     * A translation of the hashmap get method
     * @param key The key of the hitbox we need
     * @return The hitbox tied to key. Null if no such hitbox exists.
     */
    public ActionBox getHitbox(Character key) {
    	return boundaries.get(key);
    }
    /**
     * A translation of the hashmap put method
     * @param key The key of the hitbox we're adding. May overwrite if it already exists
     * @param newHitbox The hitbox to be added. 
     */
    public void putHitbox(Character key, ActionBox newHitbox) {
    	boundaries.put(key, newHitbox);
    	if(boundaryKeys.contains(key) == false) {
    		boundaryKeys.add(key);
    	}
    }
    /**
     * A translation of the hashmap remove method
     * @param key The key of the hitbox we're removing.
     * @return The removed hitbox, or null if it isn't there.
     */
    public ActionBox removeHitbox(Character key) {
    	return boundaries.remove(key);
    }
    
    public void makeHitboxes() { 
    	int pos = wallThickness/2;
    	boundaries.put(NORTH, ActionBox.makeActionBox(getXPosWorld(), getYPosWorld() - SIZE/2 + pos, SIZE, wallThickness));
    	boundaries.get(NORTH).setEnabled(true);
    	boundaries.put(EAST, ActionBox.makeActionBox(getXPosWorld() + SIZE/2 - pos, getYPosWorld(), wallThickness, SIZE));
    	boundaries.get(EAST).setEnabled(true);
    	boundaries.put(SOUTH, ActionBox.makeActionBox(getXPosWorld(), getYPosWorld() + SIZE/2 - pos, SIZE, wallThickness));
    	boundaries.get(SOUTH).setEnabled(true);
    	boundaries.put(WEST, ActionBox.makeActionBox(getXPosWorld() - SIZE/2 + pos, getYPosWorld(), wallThickness, SIZE));
    	boundaries.get(WEST).setEnabled(true);

    }
    
    
    
 }
