import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.TreeMap;

import sun.misc.Queue;

/**
 * @author Bradley Peterson
 */
public final class Map_tm {

 
    private TreeMap<Double, Tile_tm> tiles; 
    private ArrayList<DoorTile> doorTiles;
    private ArrayList<TreasureTile> treasureTiles;
    private SpawnTile spawnTile; 
    private double[] controls;
    
    private final int ORIGIN_INDEX = 0;
    private final int NOR_INDEX = 1;
    private final int SOU_INDEX = 2;
    private final int MAX_INDEX = 3;
    
     
    /**
     * Creates a new map object, starting at spawn, with all tiles. Doors and treasures from all are referenced where you'd expect
     * @param spawn The spawn tile for the map object
     * @param all The treeMap which holds all tiles
     * @param doors The arrayList which holds references to all doorTiles
     * @param treasures The arrayList which holds references to all TreasureTiles
     * @param pivotVals The "control" values on the key map- SPAWN, NORTHERLIES, SOUTHERLIES, and MAX
     */
    public Map_tm(SpawnTile spawn, TreeMap<Double, Tile_tm> all, ArrayList<DoorTile> doors, ArrayList<TreasureTile> treasures, double[] pivotVals) {
        spawnTile = spawn;
        tiles = all;
        doorTiles = doors;
        treasureTiles = treasures;
        controls = pivotVals; 
        mapTiles(); 
    } 
    
    
    /**
     * Returns the spawn Tile
     * @return The spawn tile, from which monsters (and the map) come
     */
    public SpawnTile getSpawnTile() {
    	return spawnTile;
    }
    
    /**
     * Returns the tile tree, which holds all the tiles. Good for searching!
     * @return The tile tree 
     */
    public TreeMap<Double, Tile_tm> getTiles() {
    	return tiles;
    }
    
    /**
     * Returns the doorTile arrayList, which holds references to all the door tiles.
     * @return The doorTile tree
     */
    public ArrayList<DoorTile> getDoorTiles() {
    	return doorTiles;
    }

    /**
     * Returns the treasureTile array list, which holds references to all the treasure tiles.
     * @return The TreasureTile tree
     */
    public ArrayList<TreasureTile> getTreasureTiles() {
    	return treasureTiles;
    }
    
    /**
     * @return Origin's key in the tree
     */
    public double getSpawnKey() {
    	return controls[ORIGIN_INDEX];
    }
    
    /**
     * @return The key of northerly, a control value
     */
    public double getNortherlyKey() {
    	return controls[NOR_INDEX];
    }
    /**
     * @return The key of Southerly, a control value
     */
    
    public double getSoutherlyKey() {
    	return controls[SOU_INDEX];
    }
    /**
     * @return Returns the array which holds the control values for the tree
     */
    public double[] getControls() {
    	return controls;
    }
    /**
     * @return The value of max
     */
    public double getMax() {
    	return controls[MAX_INDEX];
    }
    
  
    
    /**
     * A class which sets the relationships between all tiles up. Despite this simple description,
     * It does a lot. 
     */
    public void mapTiles() {
    	//Will be used for all steps
    	double currKey = tiles.firstKey(); 
    	Tile_tm currTile = tiles.get(currKey);  
    	
    	//For Step One
    	double possibleNeighborKey; 
    	Tile_tm neighborTile;
    	char dir = Mapper.NORTH; 
    	
    	 
    	/*
    	 * Step One: Connect all neighbors 
    	 */
    	//For each tile...
    	while(currTile != null) {  
    		//Generate what its neighbor's key WOULD be
    		possibleNeighborKey = Mapper.keygen(currTile.getNeighborX(dir), currTile.getNeighborY(dir), controls);
    		//If its neighbor exists, connect them
    		if((neighborTile = tiles.get(possibleNeighborKey)) != null) {
    			currTile.connectNeighbors(neighborTile);  
				currTile.redrawCurrSprite();
    		}
    		//Then go to the next direction. 
    		switch(dir) {
    		case Mapper.NORTH: dir = Mapper.EAST; break;
    		case Mapper.EAST: dir = Mapper.SOUTH; break;
    		case Mapper.SOUTH: dir = Mapper.WEST; break;
    		case Mapper.WEST: 
    			dir = Mapper.NORTH;
    			//Once all four cardinals have been checked, move onto the next tile
    			if(tiles.higherKey(currKey) != null) {
    				currKey = tiles.higherKey(currKey);
    				currTile = tiles.get(currKey);
    			}
    			else {
    				currTile = null;
    			} break;
    		default: System.out.println("How did this happen? We got an unrecognized direction, " + dir + "!"
    				+ " Please tell a programmer you did this, and also how you did this.");
    		}
    	}
    	/*
    	 * Step two: Calculate real distances between adjacent (defined as within maxTreasureDist) tiles and treasure tiles
    	 */
    	currKey = tiles.firstKey(); 
    	currTile = tiles.get(currKey);    	
    	while(currTile != null) {
    		currTile.setTreasureDist(treasureTiles);
    		
    		if(tiles.higherKey(currKey) != null) {
				currKey = tiles.higherKey(currKey);
				currTile = tiles.get(currKey);
			}
			else {
				currTile = null;
			}
    	}
    	
    	
    	/*
    	 * Step three: Step four: Calculate route optimality between adjacent tiles and treasure Tiles
    	 */
    	currKey = tiles.firstKey(); 
    	currTile = tiles.get(currKey);
    	while(currTile != null) { 
    		if(currTile.getNearestTID() != -1) { 
    			
    			
    			resetFScores(bbAStar(currTile, treasureTiles.get(currTile.getNearestTID())), Tile_tm.TREASURE); 
    			
    			
    			  
    		}
    		
    		
    		if(tiles.higherKey(currKey) != null) {
				currKey = tiles.higherKey(currKey);
				currTile = tiles.get(currKey);
			}
			else {
				currTile = null;
			}
    	}
    	
    	 
    	
    	/*
    	 * Step Four: Calculate real distances between tiles and the spawn tile 
    	 */
    	currKey = tiles.firstKey(); 
    	currTile = tiles.get(currKey);
    	while(currTile != null) {
    		currTile.setEntranceDist(spawnTile);
    		if(tiles.higherKey(currKey) != null) {
				currKey = tiles.higherKey(currKey);
				currTile = tiles.get(currKey);
			}
			else {
				currTile = null;
			}
    	}
    	/*
    	 * Step Five: Calculate route optimality between adjacent tiles and spawn tile
    	 */
    	
    	currKey = tiles.firstKey(); 
    	currTile = tiles.get(currKey);
    	while(currTile != null) {
    		
    		 
    		resetFScores(bbAStar(currTile, spawnTile), Tile_tm.SPAWN); 
    		 
    		
    		if(tiles.higherKey(currKey) != null) {
				currKey = tiles.higherKey(currKey);
				currTile = tiles.get(currKey);
			}
			else {
				currTile = null;
			}
    	}
    	
    	
    	
    	
    	
    }
    
    	/**
    	 * 'Resets' the FScores of each value in the ArrayList given, provided
    	 * That the f-score is lower than what it already has. 
    	 * @param path The ArrayList to be accessed
    	 */
      public void resetFScores(ArrayList<Tile_tm> path, char type) { 
    	  	 
	    	 int i, size, distance; 
	    	 size = path.size();
	    	 Tile_tm curr; 
	    	 distance = Tile_tm.SIZE * size - 128; 
	    	 
	    	 for(i = 0; i < size; i++) {
	    		 curr = path.get(i);  
	    		 if(distance < curr.getFScore(type)) {  
	    			 curr.setFScore(type, distance); 
	    		 }
	    		 distance -= Tile_tm.SIZE;
	    	 } 
      }
    
      
     /**
      * Utilizes a modified A* search algorithm to find a route between two tiles 
      * @param start The tile we're coming from
      * @param goal The tile we're going towards
      */
      public ArrayList<Tile_tm> bbAStar(Tile_tm start, Tile_tm goal) {  
    	  
    	  
    	  int i = 0;
    	  char dir = Mapper.NORTH;
    	  double nuFScore;
    	  ArrayList<Tile_tm> neighbors = new ArrayList<Tile_tm>(); 
    	  PriorityQueue<Tile_tm> q = new PriorityQueue<Tile_tm>(new tileComparator()); 
    	  q.add(start);
    	  
    	  HashMap<Tile_tm, ArrayList<Tile_tm>> prevTiles = new HashMap<Tile_tm, ArrayList<Tile_tm>>();
    	  
    	  HashMap<Tile_tm, Double> gScores = new HashMap<Tile_tm, Double>();
    	  
    	  
    	  
    	  gScores.put(start, 0.0);  
    	  double possibleGScore = 0;
    	  
    	  HashMap<Tile_tm, Double> fScores = new HashMap<Tile_tm, Double>();
    	  fScores.put(start, getHeuristic(start, goal));
    	  start.setFScore(Tile_tm.COMP, getHeuristic(start, goal));
    	  
    	  Tile_tm curr, neighbor;
    	  
    	  while(q.peek() != null) {
    		  
    		  curr = q.remove(); 
    		  if(curr == goal) {
				 return reconstructPath(prevTiles, curr, goal);
			 }
			 
			 
			 
			 dir = Mapper.NORTH;
			 do {
				 
				 neighbor = curr.getNeighbor(dir);
				 if(neighbor != null) {
					 if(gScores.get(neighbor) == null) {
						 gScores.put(neighbor,  Double.POSITIVE_INFINITY);
					 }
					 
					 possibleGScore = gScores.get(curr) + RenderObj.getDistance(curr, neighbor); 
					 if(possibleGScore < gScores.get(neighbor)) {
						 prevTiles = addToPrevious(prevTiles, neighbor, curr);
						 gScores.put(neighbor,  possibleGScore);
						 nuFScore = gScores.get(neighbor) + getHeuristic(neighbor, goal);
						 fScores.put(neighbor, nuFScore);
						 neighbor.setFScore(Tile_tm.COMP, nuFScore);
						 if(q.contains(neighbor) == false)
							 q.add(neighbor);
						 
					 }
					 
				 }
				 dir = directionCycler(dir);
			 }while(dir != 'D');
			 
    	  }
    	  
    	  
    	  return null;
    	
      }
      
      /**
       * Helper method for A*
       */
      public static char directionCycler(char dir) {
    	  switch(dir) {
    	  case Mapper.NORTH: return Mapper.EAST; 
    	  case Mapper.EAST: return Mapper.SOUTH;
    	  case Mapper.SOUTH: return Mapper.WEST;
    	  case Mapper.WEST: return 'D';
    	  default: return Mapper.NORTH;
    	  }
      }
      
      /**
       * This is to simplify some multi-key things. It's late, I don't want to explain.
       * @param prevTiles
       * @param key
       * @param value
       * @return
       */
      private HashMap<Tile_tm, ArrayList<Tile_tm>> addToPrevious(HashMap<Tile_tm, ArrayList<Tile_tm>> prevTiles, Tile_tm key, Tile_tm value) {
    	  HashMap<Tile_tm, ArrayList<Tile_tm>> result = prevTiles;
    	  
    	  if(result.containsKey(key) == false) {
    		  result.put(key, new ArrayList<Tile_tm>());
    	  } 
    	  result.get(key).add(value); 
    	  return result;
      }
      
      /**
       * Helper method for A*
       */
      private ArrayList<Tile_tm> reconstructPath(HashMap<Tile_tm, ArrayList<Tile_tm>> prev, Tile_tm current, Tile_tm goal) {
    	  
    	  
    	  
    	  ArrayList<Tile_tm> path = new ArrayList<Tile_tm>();
    	  ArrayList<Tile_tm> vals;
    	  path.add(current);
    	  while(prev.get(current) != null && prev.get(current).size() > 0) {
    		  vals = prev.get(current);
    		  current = vals.remove(vals.size() - 1);
    		  path.add(0, current);
    		  
    	  }
    		  
	    	  
 
    	  
    	  return path;
      }
       /**
        * Our A* heuristic is calculating the distance, for now.
        * @param curr The "current" node
        * @param goal The goal node 
        * @return The estimated cost of going between them
        */
      public double getHeuristic(Tile_tm curr, Tile_tm goal) {

    	  return (double)RenderObj.getDistance(curr, goal);
      }
      
      /**
       * Note: This comparator imposes orderings that are inconsistent with equals.
       * @author theon
       *
       */
      private class tileComparator implements Comparator<Tile_tm> {

		public int compare(Tile_tm arg0, Tile_tm arg1) {
			int result = 0;
			result = (int)(arg0.getFScore(Tile_tm.COMP) - arg1.getFScore(Tile_tm.COMP));
			return result;
		}
    	  
      }

      /**
       * Given (xPosWorld, yPosWorld), returns which tile contains it- or null if no tile contains it
       * @param xPosWorld The x coordinate
       * @param yPosWorld The y coordinate
       * @return The tile containing (xPosWorld, yPosWorld), or null if nothing contains it
       */
	public Tile_tm getTile(double xPosWorld, double yPosWorld) {
		Tile_tm ans = null, curr;
		double key = 0, coordKey; 
		coordKey = Mapper.keygen(xPosWorld, yPosWorld, controls);
		
		if(tiles.ceilingKey(coordKey) != null)
			key = tiles.ceilingKey(coordKey);
		else if(tiles.floorKey(coordKey) != null)
			key = tiles.floorKey(coordKey);
		else if(tiles.floorKey(coordKey) == null && tiles.ceilingKey(coordKey) == null) {
			System.out.println("Why are you trying to call this method on an empty map? This is an error state.");
		}
		
		curr = tiles.get(key);
		
		if(curr.contains(xPosWorld, yPosWorld)) {
			ans = curr;
		}
		
		if(ans == null) {
			key = tiles.firstKey();
			curr = tiles.get(key);
			while(curr != null) {
				if(curr.contains(xPosWorld, yPosWorld)) {
					ans = curr;
					curr = null;
				}
					
				if(tiles.higherKey(key) != null) {
					key = tiles.higherKey(key);
					curr = tiles.get(key);
				}
				else 
					curr = null;
				 
			}
		}
		
		return ans;
	}
      
      
      
}
