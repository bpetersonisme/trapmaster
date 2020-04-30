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
    	
    	
    	final int MAX = (int)Math.floor(Math.pow(Math.floorDiv(bounds*2, Tile_tm.SIZE), 2));
    	
    	final int SPAWN = Math.floorDiv(MAX, 2);
    	final int NORTHERLIES = Math.floorDiv(SPAWN, 2);
	    	final int NORTHWEST = 0;
	    	final int NORTHEAST = NORTHERLIES + 1;
	    final int SOUTHERLIES = SPAWN + Math.floorDiv(SPAWN, 2);
	    	final int SOUTHEAST = SPAWN+1;
	    	final int SOUTHWEST = SOUTHERLIES + 1;
	   
	    int controls[] = {SPAWN, NORTHERLIES, SOUTHERLIES, MAX};
	    
		Map_tm map = null; 
    	
    	
    	if(tileList != null) {
    		
    		
        	String tileToken; 
        	Tile_tm prevTile = null;
        	Tile_tm currTile = null;
        	char dirType;
        	int originDirection = NORTH; //originDirection is relative to the origin. Obviously. 
        	char currDirection = NORTH; //currDirection is relative to whatever the previous tile was
        	char doorDirection = NORTH;
        	
        	boolean hasSpawned = false;
        	boolean atSpawn = false;
        	int treasureNumber = 0; 
        	int tileNumber = -1;
        	int tileKeyNorthwest = NORTHWEST;
        	int tileKeyNortheast = NORTHEAST;
        	int tileKeySoutheast = SOUTHEAST;
        	int tileKeySouthwest = SOUTHWEST;
	    	Scanner s = new Scanner(tileList);
	    	
	    	
	    	TreeMap<Integer, Tile_tm> tiles = new TreeMap<Integer, Tile_tm>();
	    	ArrayList<DoorTile> doorTiles = new ArrayList<DoorTile>();
	    	ArrayList<TreasureTile> treasureTiles = new ArrayList<TreasureTile>();
	    	
	    	
	    	while(s.hasNext()) {
	    		if(s.hasNextDouble() && hasSpawned == false) {
	    			prevTile = new SpawnTile(s.nextDouble(), s.nextDouble(), monsterList, s.next().charAt(0));
	    			currTile = prevTile;
	    			tiles.put(SPAWN, prevTile);
	    			tiles.put(NORTHWEST, null);
	    			tiles.put(SOUTHEAST, null);
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
	    			break;
	    			case 'T': //T for treasure
	    				currTile = new TreasureTile(prevTile.getNeighborX(currDirection), prevTile.getNeighborY(currDirection), treasureNumber++); 
	    			break;
	    			case 'B': //B for basic  
	    			default: 
	    				currTile = new TileBasic(prevTile.getNeighborX(currDirection), prevTile.getNeighborY(currDirection));	    	 
	    			}
	    		}
	    		if(currTile != null) {
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
	    		 
		    		if(tileKeyNorthwest == NORTHERLIES || tileKeyNortheast == SPAWN || tileKeySoutheast == SOUTHERLIES || tileKeySouthwest == MAX) {
		    			System.out.println("TOO MANY TILES");
		    			System.exit(0);
		    		}
	    		
	    			tiles.put(tileNumber, currTile);
	    			prevTile = currTile;
	    		}
	    		
	    	}
	    	s.close();
	    	map = new Map_tm((SpawnTile)tiles.get(SPAWN), tiles, doorTiles, treasureTiles, controls);
    	}
    	
    	return map;
    }
     
    
     
}
