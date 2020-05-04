import java.util.ArrayList;
import java.util.Scanner; 
import java.util.TreeMap;

/**
 * The Mapper, given a string representing the map, can create a map. A map can support a maximum of 65535 tiles. 
 * @author Bradley Peterson
 */
public final class Mapper {
 
	public static final char NORTH = 'N';
	public static final char SOUTH = 'S';
	public static final char EAST = 'E';
	public static final char WEST = 'W';
	
	private final static int O_IN = 0;
	private final static int N_IN = 1;
	private final static int S_IN = 2;
	private final static int M_IN = 3;
	/*
    public static final int SPAWN = 32767;
	    public static final int NORTHERLIES = 16383;
			public static final int NORTHWEST = 0;
		    public static final int NORTHEAST = 16384;
    	public static final int SOUTHERLIES = 49150;
	        public static final int SOUTHEAST = 32768;
	        public static final int SOUTHWEST = 49151;
	        public static final int MAX = 65535;
	 */
    /**
     * Given a string tileList and monsterList, returns a TreeMap containing the map's tiles, sorted by direction
     * There are two types of token in tileList- Doubles and Strings. A Double token should only appear twice, at the 
     * beginning of the file. If it appears more than once, there is a problem.  A String token will appear as many times 
     * as there are tiles. Form is (R)BC, where R is the optional reset flag, B is the direction, and C is the tile type.
     * R may or may not be present. 
     * @param bounds The maximum distance between the origin and the edge of the map
     * @param tileList A list of Tiles- used to construct a map
     * @param monsterList A list of monsters- used for spawning monsters from the spawnTile.
     */
    public static Map_tm buildMap(int bounds, String tileList, String monsterList)  {
    	
    	/*
    	 * All key values may be guaranteed unique by the formula k = sqrt(x^2 + (y+x)^2), an injective function. 
    	 * where x and y are the double's x and y coordinates. 
    	 * This is further assured by putting k into  
    	 */
    	/*
    	final int MAX = (int)Math.floor(Math.pow(Math.floorDiv(bounds*2, Tile_tm.SIZE), 2));
    	
    	final int SPAWN = Math.floorDiv(MAX, 2);
    	final int NORTHERLIES = Math.floorDiv(SPAWN, 2);
	    	final int NORTHWEST = 0;
	    	final int NORTHEAST = NORTHERLIES + 1;
	    final int SOUTHERLIES = SPAWN + Math.floorDiv(SPAWN, 2);
	    	final int SOUTHEAST = SPAWN+1;
	    	final int SOUTHWEST = SOUTHERLIES + 1;
	   */
    	final double MAX = (int)keygen(bounds, bounds); //This is the largest value a key can have
    	final double ORIGIN = 0; //This is the origin, the midpoint 
    		final double NORTHERLIES = Math.floorDiv((int)-MAX, 2); 
    			final double NORTHWEST = -MAX; //The northwest region holds keys between [-MAX, NORTHERLIES)
    			final double NORTHEAST = NORTHERLIES; //The northeast region holds keys between [NORTHERLIES, ORIGIN)
    		final double SOUTHERLIES = Math.floorDiv((int)MAX, 2);
    			final double SOUTHEAST = ORIGIN; //The southeast region holds keys between [ORIGIN, SOUTHERLIES)
    			final double SOUTHWEST = SOUTHERLIES; //The southwest region holds keys between [SOUTHERLIES, MAX)
    	
	    	
    	 
	    //int controls[] = {SPAWN, NORTHERLIES, SOUTHERLIES, MAX};
    		double controls[] = {ORIGIN, NORTHERLIES, SOUTHERLIES, MAX};
    		    
		Map_tm map = null; 
 	
    	if(tileList != null) {
 
        	String tileToken; 
        	Tile_tm prevTile = null;
        	Tile_tm currTile = null;
        	SpawnTile spawn = null;
        	char dirType; 
        	char currDirection = NORTH; //currDirection is relative to whatever the previous tile was
        	char doorDirection = NORTH;
        	boolean hasSpawned = false; 
        	int treasureNumber = 0; 
        	
        	int tileNumber = -1;
        	double tileKey;
        	/*
        	int tileKeyNorthwest = NORTHWEST;
        	int tileKeyNortheast = NORTHEAST;
        	int tileKeySoutheast = SOUTHEAST;
        	int tileKeySouthwest = SOUTHWEST;
	    	*/
        	double tileKeyNorthwest = NORTHWEST;
        	double tileKeyNortheast = NORTHEAST;
        	double tileKeySoutheast = SOUTHEAST;
        	double tileKeySouthwest = SOUTHWEST;
	    	Scanner s = new Scanner(tileList);
	    	
	    	
	    	//TreeMap<Integer, Tile_tm> tiles = new TreeMap<Integer, Tile_tm>();
	    	TreeMap<Double, Tile_tm> tiles = new TreeMap<Double, Tile_tm>();
	    	ArrayList<DoorTile> doorTiles = new ArrayList<DoorTile>();
	    	ArrayList<TreasureTile> treasureTiles = new ArrayList<TreasureTile>();
	    	
	    	
	    	while(s.hasNext()) {
	    		if(s.hasNextDouble() && hasSpawned == false) {
	    			spawn = new SpawnTile(s.nextDouble(), s.nextDouble(), monsterList, s.next().charAt(0));
	    			System.out.println("SPAWN'S KEY IS " + keygen(spawn.getXPosWorld(), spawn.getYPosWorld(), controls));
	    			prevTile = spawn;
	    			currTile = prevTile;
	    			//tileKey = keygen(spawn.getXPosWorld(), spawn.getYPosWorld(), controls);
	    			//tiles.put(tileKey, spawn);
	    			//tiles.put(ORIGIN, null);
	    			//tiles.put(NORTHWEST, null);
	    			//tiles.put(SOUTHEAST, null);
	    			hasSpawned = true;
	    		}
	    		else {
	    			tileToken = s.next(); 
	    			 
	    			
	    			dirType = Character.toUpperCase(tileToken.charAt(0));
	    			 
	    			switch(dirType) {
	    				case 'N': currDirection = NORTH; break;
	    				case 'E': currDirection = EAST; break;
	    				case 'W': currDirection = WEST; break;
	    				case 'S': currDirection = SOUTH; break;
	    				default: System.out.println("INVALID CHARACTER FOUND");
	    				System.exit(0);
	    			}
	    			
	    			if(prevTile.getNeighborX(currDirection) > 5) {
	    				
	    			}
	    			
	    			dirType = Character.toUpperCase(tileToken.charAt(1));
	    			switch(dirType) {
	    			case 'D': //D for door
	    				switch(currDirection) {
	    				case NORTH: doorDirection = SOUTH; break;
	    				case SOUTH: doorDirection = NORTH; break;
	    				case WEST: doorDirection = EAST; break;
	    				case EAST: doorDirection = WEST; break;
	    				} 
	    				currTile = new DoorTile(prevTile.getNeighborX(currDirection), prevTile.getNeighborY(currDirection), doorDirection); 
	    				doorTiles.add((DoorTile)currTile);
	    			break;
	    			case 'T': //T for treasure
	    				currTile = new TreasureTile(prevTile.getNeighborX(currDirection), prevTile.getNeighborY(currDirection), treasureNumber++); 
	    				treasureTiles.add((TreasureTile)currTile);
	    			break;
	    			case 'B': //B for basic  
	    			default: 
	    				currTile = new TileBasic(prevTile.getNeighborX(currDirection), prevTile.getNeighborY(currDirection));	    	 
	    			}
	    		}
	    		if(currTile != null) {
	    			/*
		    		if(currTile.getXPosWorld() > 0) {
		    			if(currTile.getYPosWorld() > 0) { 
		    				tileNumber = ++tileKeySoutheast;
		    			}
		    			else { 
		    				tileNumber = ++tileKeyNortheast;
		    			}
		    		}
		    		else {
		    			if(currTile.getYPosWorld() > 0) { 
		    				tileNumber = ++tileKeySouthwest;
		    			}
		    			else { 
		    				tileNumber = ++tileKeyNorthwest;
		    			}
		    		}
	    		 	*/

	    		
	    			tileKey = keygen(currTile.getXPosWorld(), currTile.getYPosWorld(), controls); 
		    		//if(tileKeyNorthwest == NORTHERLIES || tileKeyNortheast == SPAWN || tileKeySoutheast == SOUTHERLIES || tileKeySouthwest == MAX) {
		    		if(tileKeyNorthwest == NORTHERLIES || tileKeyNortheast == ORIGIN || tileKeySoutheast == SOUTHERLIES || tileKeySouthwest == MAX) { 
		    			System.exit(0);
		    		}
	    		
	    			//tiles.put(tileNumber, currTile);
		    		if(tiles.get(tileKey) == null) {
		    			tiles.put(tileKey, currTile); 
		    			prevTile = currTile;
		    		}
		    		else {
		    			prevTile = tiles.get(tileKey);
		    		}
	    		}
	    		
	    	}
	    	s.close();
	    	//map = new Map_tm((SpawnTile)tiles.get(SPAWN), tiles, doorTiles, treasureTiles, controls);
	    	map = new Map_tm(spawn, tiles, doorTiles, treasureTiles, controls);
    	}
    	
    	return map;
    }
    

    
    /**
     * Given ordered pair (x,y), returns its unique key. NOTE: Due to precision
     * limits, the uniqueness of two keys cannot be absolutely guaranteed. This 
     * won't be a problem, however, unless you're trying to overlap tiles. 
     * @param x The x coordinate from which the key will be generated
     * @param y The y coordinate from which the key will be generated
     */
    public static double keygen(double x, double y) {
    	return keygen(x, y, null);
    }
    
	 
    /**
     * Given ordered pair (x,y), returns its unique key. NOTE: Due to precision
     * limits, the uniqueness of two keys cannot be absolutely guaranteed 
     * @param x The x coordinate from which the key will be generated
     * @param y The y coordinate from which the key will be generated
     * @param controls The dividing values of the coordinate pairs 
     * @return A key unique to the ordered pair given
     */
    public static double keygen(double x, double y, double[] controls) { 
    	double result = Math.hypot(x, y);
    	double offset = 0;
    	if(controls != null) {
	    	 if(x < 0 && y < 0) { //Meaning Northwest region
	    		offset = -controls[M_IN];
	    	 }
	    	 else if(x > 0 && y > 0) { //Meaning Southeast region
	    		 offset = controls[O_IN]; //Just looks so incomplete without it.
	    	 }
	    	else {
	    		if(x < 0) {//Implies y > 0; meaning Southwest region
	    			offset = controls[S_IN];
	    		} 
	    		else {//Meaning northeast region.
	    			offset = controls[N_IN];
	    		}
	    	}
    	}
    	return result + offset;
    }
    
     
}
