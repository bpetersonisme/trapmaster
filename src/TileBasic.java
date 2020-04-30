/**
 * TileBasic is the basic tile. It has no new methods persay from Tile_tm, 
 * merely a new constructor
 * @author Bradley Peterson
 */
public class TileBasic extends Tile_tm {
	
	/**
	 * Creates a new TileBasic at (xPos, yPos). 
	 * @param xPos The x (horizontal) position of the tile's center
	 * @param yPos The y (vertical) position of the tile's center
	 */
	public TileBasic(double xPos, double yPos) {
		super(importImage("/basic_tile.png"), xPos, yPos, 1, 1);
	}
	
	
}
