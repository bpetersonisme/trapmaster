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
	
	private int posX; //The horizontal (x) position of the object "in the world"
	private int posY; //The vertical (y) position of the object "in the world"
	
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
		int xPos = spriteWidth * col;
		int yPos = spriteHeight * row;
		currSprite = spriteSheet.getSubimage(xPos, yPos, xPos + spriteWidth, yPos + spriteHeight);
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
	
	
	
	protected int getspriteSheetCols() {
		return spriteSheetCols;
	}
	
	protected int getspriteSheetRows() {
		return spriteSheetRows;
	}
 
	
	
	protected int getCurrSpriteCol() {
		return currSpriteCol;
	}

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


	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}
}
