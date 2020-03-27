import java.awt.image.BufferedImage;

/**
 * RenderObj is the superclass to all the visible objects in game. 
 * This abstract class makes a few fundamental assumptions: 
 * - That an object uses a sprite sheet for its graphics 
 * - That all sprites are the same size 
 * - That successive sprites are ordered left to right, top to bottom
 * @author Bb Peterson
 *
 */
public abstract class RenderObj {
	private BufferedImage spriteSheet; //For use as a sprite sheet
	private BufferedImage currSprite; //The current sprite of the object
	
	private int spriteSheetCols; //The number of sprite columns in the sprite sheet
	private int spriteSheetRows;
	private int currSpriteCol;
	private int currSpriteRow;
	
	private int spriteHeight; //The (y) height of the sprite 
	private int spriteWidth; //The (x) width of the sprite 
	
	private double posX; //The horizontal (x) position of the object 
	private double posY; //The vertical (y) position of the object 
	private int posZ; //The "depth" (z?) position of the object; used to determine the draw order
	
	//Assuming your sequence of sprites is 
	public void cycleAnim() {
		if(currSpriteCol + 1 == spriteSheetCols) {
			currSpriteCol = 0;
			if(currSpriteRow + 1 == spriteSheetRows) {
				currSpriteRow = 0;
			}
			else 
				currSpriteRow++;
		}
		else
			currSpriteCol++;
		setCurrSprite(currSpriteRow, currSpriteCol);
		
	}
	
 /**
  * Getters and setters
  */
	
	//Curr Sprite getters and setters
	public BufferedImage getCurrSprite() {
		return currSprite;
	}
	
	public void setCurrSprite(int row, int col) {
		if(row > spriteSheetRows || row < 0 || col > spriteSheetCols || col < 0) {
			System.out.println("INVALID SPRITE INPUT, NO CHANGE MADE");
		}
		else {
			int spriteSheetY = spriteHeight * row;
			int spriteSheetX = spriteWidth * col;
			currSprite = spriteSheet.getSubimage(spriteSheetX, spriteSheetY, spriteSheetX + spriteWidth, spriteSheetY + spriteHeight);
		}
	}

	//End CurrSprite
	
	//getSpriteSheet getter 
	protected BufferedImage getSpriteSheet() {
		return spriteSheet;
	}
	
	/**
	 * Swaps over to a new spriteSheet, with associated information to match
	 * With this, you could have separate sprite sheets for different character actions
	 * @param newSS The new sprite sheet
	 * @param numRows The number of rows in the new sprite sheet
	 * @param numCols The number of columns in the new sprite sheet
	 * @param spriteWidth The width of sprites on the new sprite sheet
	 * @param spriteHeight The height of sprites on the new sprite sheet
	 */
	protected void setSpriteSheet(BufferedImage newSS, int numRows, int numCols, int spriteWidth, int spriteHeight) {
		spriteSheet = newSS;
		spriteSheetRows = numRows;
		spriteSheetCols = numCols;
		this.spriteWidth = spriteWidth;
		this.spriteHeight = spriteHeight;
		currSpriteRow = 0;
		currSpriteCol = 0;
		setCurrSprite(0, 0);
	}
	
	/**
	 * Returns whether or not this object is colliding with other
	 * @return True is other is colliding with this object, false otherwise
	 */
	public boolean isColliding(RenderObj other) {
		boolean collision = false;
		
		return collision;
	}
	
	//Returns how many columns are on the sprite sheet- It's just for internal use
	protected int getSpriteSheetCols() {
		return spriteSheetCols;
	}
	//Returns how many rows are on the sprite sheet- it's basically just for internal use
	protected int getSpriteSheetRows() {
		return spriteSheetRows;
	}
 
	
	//Returns the current column of the current sprite- for internal use
	protected int getCurrSpriteCol() {
		return currSpriteCol;
	}

	//Sets the current column of the current sprite- will also 
	protected void setCurrSpriteCol(int currSpriteCol) {
		this.currSpriteCol = currSpriteCol;
		setCurrSprite(currSpriteRow, currSpriteCol);
	}

	protected int getCurrSpriteRow() {
		return currSpriteRow;
	}

	protected void setCurrSpriteRow(int currSpriteRow) {
		this.currSpriteRow = currSpriteRow;
		setCurrSprite(currSpriteRow, currSpriteCol);
	}


	public int getSpriteHeight() {
		return spriteHeight;
	}

	public int getSpriteWidth() {
		return spriteWidth;
	}


	public double getPosX() {
		return posX;
	}

	public void setPosX(double newX) {
		posX = newX;
	}

	public double getPosY() {
		return posY;
	}

	public void setPosY(double newY) {
		posY = newY;
	}
	
	public int getPosZ() {
		return posZ;
	}
	
	public void setPosZ(int newZ) {
		posZ = newZ;
	}
}
