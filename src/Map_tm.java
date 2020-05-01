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
    			bbAStar(currTile, treasureTiles.get(currTile.getNearestTID()));
    			System.out.println("Hallo?");
    		}
    		
    		currTile.setTreasureVal(currTile.getNeighbor(Mapper.NORTH), Mapper.NORTH);
    		currTile.setTreasureVal(currTile.getNeighbor(Mapper.EAST), Mapper.EAST);
    		currTile.setTreasureVal(currTile.getNeighbor(Mapper.SOUTH), Mapper.SOUTH);
    		currTile.setTreasureVal(currTile.getNeighbor(Mapper.WEST), Mapper.WEST);
    		currTile.setFScore(Tile_tm.TREASURE, currTile.getFScore());
    		
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
    		
    		 
    			bbAStar(currTile, spawnTile); 
    		
    		
    		currTile.setSpawnVal(currTile.getNeighbor(Mapper.NORTH), Mapper.NORTH);
    		currTile.setSpawnVal(currTile.getNeighbor(Mapper.EAST), Mapper.EAST);
    		currTile.setSpawnVal(currTile.getNeighbor(Mapper.SOUTH), Mapper.SOUTH);
    		currTile.setSpawnVal(currTile.getNeighbor(Mapper.WEST), Mapper.WEST);
    		currTile.setFScore(Tile_tm.SPAWN, currTile.getFScore());
    		
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
      * Utilizes a modified A* search algorithm to find a route between two tiles 
      * @param start The tile we're coming from
      * @param goal The tile we're going towards
      */
      public ArrayList<Tile_tm> bbAStar(Tile_tm start, Tile_tm goal) { 
    	  
    	   
    	  PriorityQueue<Tile_tm> q = new PriorityQueue<Tile_tm>(new tileComparator());
    	  start.setFScore(getHeuristic(start, goal)); 
    	  q.add(start);
    	  ArrayList<Tile_tm> prevTiles = new ArrayList<Tile_tm>(); 
    	  Tile_tm curr;
    	  Tile_tm neighbor;
    	  int i = 1, cnt; 
    	  char dir = Mapper.NORTH; 
    	  while(q.peek() != null) {
    		  curr = q.remove();
    		   
    		  
    		  if(curr.toString().equals(goal.toString())) {
    			  return reconstructPath(prevTiles, curr);
    		  }
    		  
    		  i = 1;
    		  cnt = curr.countNeighbors();
    		  while(i != 0 && cnt > 0) { 
    			  
    			  neighbor = curr.getNeighbor(dir);
    			  
    			  if(neighbor != null) {
	    			  double possibleGScore = curr.getGScore() + Tile_tm.SIZE;
	    			  
	    			  if(possibleGScore < neighbor.getGScore()) {
	    				  prevTiles.add(curr);
	    				  neighbor.setGScore(possibleGScore);
	    				  neighbor.setFScore(neighbor.getGScore() + getHeuristic(neighbor, goal));
	    				  if(q.contains(neighbor) == false) {
	    					  q.add(neighbor);
	    				  }
	    			  }
    			  }
    	    	  switch(dir) {
	    	    	  case Mapper.NORTH: dir = Mapper.EAST; break;
	    	    	  case Mapper.EAST: dir = Mapper.SOUTH; break; 
	    	    	  case Mapper.SOUTH: dir = Mapper.WEST; break;
	    	    	  case Mapper.WEST:
	    	    		  dir = Mapper.NORTH;
	    	    		  i = 0;
	    	    		  break;
	    	    	  default:  dir = Mapper.NORTH;
    	    	  }
    		  }
    		  
    	  }
    	  
    	  return null;
    	  
      }
      
      /**
       * Helper method for A*
       */
      private char directionCycler(char dir) {
    	  switch(dir) {
    	  case Mapper.NORTH: return Mapper.EAST; 
    	  case Mapper.EAST: return Mapper.SOUTH;
    	  case Mapper.SOUTH: return Mapper.WEST;
    	  case Mapper.WEST: return Mapper.NORTH;
    	  default: return Mapper.NORTH;
    	  }
      }
      
      /**
       * Helper method for A*
       */
      private ArrayList<Tile_tm> reconstructPath(ArrayList<Tile_tm> prev, Tile_tm current) {
    	  ArrayList<Tile_tm> path = new ArrayList<Tile_tm>();
    	  path.add(current);
    	  while(prev.size() > 0) {
    		  current = prev.remove(prev.size()-1);
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
      public int getHeuristic(Tile_tm curr, Tile_tm goal) {
    	  return (int)RenderObj.getDistance(curr, goal);
      }
      
      /**
       * Note: This comparator imposes orderings that are inconsistent with equals.
       * @author theon
       *
       */
      private class tileComparator implements Comparator<Tile_tm> {

		public int compare(Tile_tm arg0, Tile_tm arg1) {
			int result = 0;
			result = (int)(arg0.getFScore() - arg1.getFScore());
			return result;
		}
    	  
      }
      
}
