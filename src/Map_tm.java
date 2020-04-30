import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * @author Bradley Peterson
 */
public final class Map_tm {

 
    private TreeMap<Integer, Tile_tm> tiles; 
    private ArrayList<DoorTile> doorTiles;
    private ArrayList<TreasureTile> treasureTiles;
    private SpawnTile spawnTile; 
    private int[] pivots;
    
    private final int SPAWN_INDEX = 0;
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
    public Map_tm(SpawnTile spawn, TreeMap<Integer, Tile_tm> all, ArrayList<DoorTile> doors, ArrayList<TreasureTile> treasures, int[] pivotVals) {
        spawnTile = spawn;
        tiles = all;
        doorTiles = doors;
        treasureTiles = treasures;
        pivots = pivotVals;
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
    public TreeMap<Integer, Tile_tm> getTiles() {
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
     * @return Spawn's key in the tree
     */
    public int getSpawnKey() {
    	return pivots[SPAWN_INDEX];
    }
    
    /**
     * @return The key of northerly, a control value
     */
    public int getNortherlyKey() {
    	return pivots[NOR_INDEX];
    }
    /**
     * @return The key of Southerly, a control value
     */
    
    public int getSoutherlyKey() {
    	return pivots[SOU_INDEX];
    }
    /**
     * @return The value of max
     */
    public int getMax() {
    	return pivots[MAX_INDEX];
    }
    
    

      
}
