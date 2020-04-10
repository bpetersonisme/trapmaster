import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


/**
 * RenderObj is the superclass to all the visible objects in game. 
 * This abstract class makes a few fundamental assumptions: 
 * - That an object uses a sprite sheet for its graphics 
 * - That all sprites in a sprite sheet are the same size 
 * - That successive sprites in an animation are ordered left to right, top to bottom
 * Render objects are also designed to work with two coordinate systems- the RenderEngine system,
 * And the world system. The RenderEngine system is the Java standard, while the world system places
 * Its origin at its center. RenderObj keeps track of its position in the world space. It 
 * may seamlessly transition from worldspace to viewport space using the renderXPos and renderYPos
 * Systems
 * @author Bb Peterson
 *
 */
public abstract class RenderObj {
	private BufferedImage spriteSheet; //The spriteSheet of an object- i.e., all states of its animation.  
	private BufferedImage currSprite; //The currently displayed sprite of an object, including its rotation. 
	
	private int spriteSheetCols; //The number of columns on the sprite sheet
	private int spriteSheetRows; //The number of rows on the sprite sheet 
	
	private int currSpriteCol; //The column currSprite is from on spriteSheet- goes from 0 to (spriteSheetCols-1)
	private int currSpriteRow; //The row currSprite is from on spriteSheet- goes from 0 to (spriteSheetRows-1)
	private int currSpriteHeight; //The (y) height of each sprite with respect to its angle 
	private int currSpriteWidth;  //The (x) height of the current sprite with respect to its angle
	
	private int modelSpriteHeight; //The (y) height of each sprite on the sprite sheet
	private int modelSpriteWidth; //The (x) width of each sprite on the sprite sheet
	
	private double xPosWorld; 
	private double yPosWorld;
	
	private double xPos; //The horizontal (x) position of the object 
	private double yPos; //The vertical (y) position of the object 
	private int zPos; //The "depth" (z?) position of the object; used to determine the draw order
	
	private double angle; //The angle of the sprite- can be any number between 0 and 360 degrees
	
	private int collisionType; 
	
	private final int TYPE_RECT = 1;
	private final int TYPE_CIRCLE = 2;
	private final int TYPE_OVAL = 3;
	
	
	
	/**
	 * Returns the sprite sheet of the renderObj- protected function, potential use for a debugging method.
	 * @return The sprite sheet, which holds all frames of animation/states for renderObj
	 */
	protected BufferedImage getSpriteSheet() {
		return spriteSheet;
	}
	

	/**
	 * Changes the sprite sheet to newSS, which has numRows rows- each row being spriteHeight tall,
	 * and numCols columns- each column being spriteWidth wide
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
		modelSpriteWidth = spriteWidth;
		modelSpriteHeight = spriteHeight;
		currSpriteWidth = spriteWidth;
		currSpriteHeight = spriteHeight;	
		currSpriteRow = 0;
		currSpriteCol = 0;
		setCurrSprite(0, 0);
		if(angle > 0)
			rotateCurrSprite();
	}
	
	/**
	 * Changes the sprite sheet to newSS- it only has one sprite in it, and its width/height is the width/height of newSS
	 * @param newSS The new sprite sheet
	 */
	protected void setSpriteSheet(BufferedImage newSS) {
		setSpriteSheet(newSS, 1, 1, newSS.getWidth(), newSS.getHeight());
	}
	
	
	/**
	 * Iterates from one frame of animation to the next, going left to right, top to bottom. 
	 * @return 1 on a normal cycle, 2 if it has cycled to the last sprite in a row, 
	 * and 3 if it has cycled to the last sprite in a sheet
	 */
	public int  cycleAnimation() {
		int ret = 1;
		if(currSpriteCol + 2 == spriteSheetCols) {
			if(currSpriteRow + 1 == spriteSheetRows) {
				ret = 3;
			}
			else
				ret = 2;
		}
		else if(currSpriteCol + 1 == spriteSheetCols) {
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
		return ret;
	}
	
	
	/**
	 * Returns the current sprite.
	 * @return The currently displayed sprite of an object, possibly rotated from its normal 
	 */
	public BufferedImage getCurrSprite() {
		return currSprite;
	}
	
	/**
	 * Sets the current sprite to row and col (also changes currSpriteRow and currSpriteCol)
	 * @param row
	 * @param col
	 */
	public void setCurrSprite(int row, int col) {
		if(row > spriteSheetRows || row < 0 || col > spriteSheetCols || col < 0) {
			System.out.println("INVALID SPRITE INPUT, NO CHANGE MADE");
		}
		else {
			int spriteSheetY = modelSpriteHeight * row;
			int spriteSheetX = modelSpriteWidth * col;
			currSprite = spriteSheet.getSubimage(spriteSheetX, spriteSheetY, spriteSheetX + modelSpriteWidth, spriteSheetY + modelSpriteHeight);
			rotateCurrSprite();
		}
	}
 
	
	
	
	
	/**
	 * Returns whether or not this object is colliding with other. 
	 * @return True is other is colliding with this object, false otherwise
	 */
	public boolean isColliding(RenderObj other) {
		boolean collision = false;
		if(collisionType == TYPE_RECT) {
			
		}
		else if(collisionType == TYPE_CIRCLE) {
			
		}
		else if (collisionType == TYPE_OVAL){
			
		}
		else {
			System.out.println("INVALID TYPE SELECTION, USING TYPE_OVAL");
			collisionType = TYPE_OVAL;
			collision = isColliding(other);
		}
		
		return collision;
	}
	
	/**
	 * Returns the number of columns on the current sprite sheet
	 * @return The number of columns on the sprite sheet
	 */
	protected int getSpriteSheetCols() {
		return spriteSheetCols;
	}

	/**
	 * Returns the number of rows on the current sprite sheet
	 * @return The number of rows on the current sprite sheet
	 */
	protected int getSpriteSheetRows() {
		return spriteSheetRows;
	}
 
	
	/**
	 * Returns the column of the current sprite 
	 * @return The number of the current sprite, between zero and one less spriteSheetCols
	 */
	protected int getCurrSpriteCol() {
		return currSpriteCol;
	}

	/**
	 * Sets the column of the current sprite. Could be useful to 
	 * Swap between rows, if you made each row correspond to one
	 * animation
	 * @param currSpriteCol The new column of the current sprite
	 */
	protected void setCurrSpriteCol(int newSpriteCol) {
		currSpriteCol = newSpriteCol;
		setCurrSprite(currSpriteRow, currSpriteCol);
	}
	
	/**
	 * Gets the row of the current sprite
	 * @return The numeric value of the current sprite row, between zero and one less spriteSheetRows
	 */
	protected int getCurrSpriteRow() {
		return currSpriteRow;
	}
	
	/**
	 * Sets the row of the current sprite to newSpriteRow. Could be
	 * useful to skip frames of an animation
	 * @param newSpriteRow The new row sprite 
	 */
	protected void setCurrSpriteRow(int newSpriteRow) {
		currSpriteRow = newSpriteRow;
		setCurrSprite(currSpriteRow, currSpriteCol);
	}
	

	/**
	 * Rotates the sprite by degree degrees from its normal position
	 * @param degree The number of degrees that the sprite is to be rotated
	 */
	protected void rotateCurrSprite(double degree) {
		BufferedImage modelSprite = getModelSprite();
		degree = degree % 360;
		if(degree < 0) 
			degree = 360 + degree;
		angle = degree;
	
		
		double radsAngle = Math.toRadians(degree);
		double sin = Math.abs(Math.sin(radsAngle));
		double cos = Math.abs(Math.cos(radsAngle));
		int w = modelSpriteWidth;
		int h = modelSpriteHeight; 
		int newWidth = (int)Math.floor(w*cos + h*sin);
		int newHeight = (int)Math.floor(h*cos + w*sin);
	//	System.out.println("Sin is: " + sin + " and cos is: " + cos); 
		
		BufferedImage rotatedSprite = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB); 
		Graphics2D g2D = rotatedSprite.createGraphics(); 
		AffineTransform rotator = new AffineTransform();
		rotator.translate((newWidth - w)/2, (newHeight-h)/2);
		rotator.rotate(radsAngle, w/2, h/2);
		g2D.setTransform(rotator);
		g2D.drawImage(modelSprite, 0, 0, null);
		g2D.setColor(Color.RED);
		g2D.drawRect(0, 0, rotatedSprite.getWidth(), rotatedSprite.getHeight());
		g2D.dispose();
		currSprite = rotatedSprite;
		
		currSpriteWidth = (int) newWidth;
		currSpriteHeight = (int) newHeight; 
 
		 
	}
	
	protected void rotateCurrSprite() {
		rotateCurrSprite(angle);
	}

	/**
	 * GetModelSprite returns an unrotated version of the current sprite
	 * @return The model sprite, an unrotated version of the current sprite
	 */
	private BufferedImage getModelSprite() {
			
			int spriteSheetY = modelSpriteHeight * currSpriteRow;
			int spriteSheetX = modelSpriteWidth * currSpriteCol;
			return spriteSheet.getSubimage(spriteSheetX, spriteSheetY, spriteSheetX + modelSpriteWidth, spriteSheetY + modelSpriteHeight);
	}
 

	//NOTE TO SELF: ADJUST POSITIONAL COORDINATES TO ACCOUNT FOR RECENTERING IN OBJECT RENDERING- AKA, VIEWPORT COORDINATES AND OBJECT COORDINATES 
	// ARE OFF
	
	/**
	 * Returns the ideal, unrotated height of the sprite
	 * @return The sprite's height
	 */
	public int getSpriteHeight() {
		return modelSpriteHeight;
	}

	/**
	 * Returns the ideal, unrotated width of the sprite
	 * @return The sprite's width
	 */
	public int getSpriteWidth() {
		return modelSpriteWidth;
	}
	
	/**
	 * Returns the 'actual' height of the sprite, rotation included
	 * @return The sprite's height including its bounding box
	 */
	public int getRotatedSpriteHeight() {
		return currSpriteHeight;
	}

	/**
	 * Returns the 'actual' width of the sprite, rotation included
	 * @return The sprite's width, including its bounding box
	 */
	public int getRotatedSpriteWidth() {
		return currSpriteWidth;
	}

	/**
	 * Due to the way RenderEngine works, rotation needs to be accounted for 
	 * By moving the sprite by (-spriteWidth/2)^2+(-spriteHeight/2)^2 to make sure
	 * Rotations actually look the way they ought to 
	 * @return The 'apparent' position of the sprite along the x axis 
	 */
	public double getXPosRender() {
		double xPosRotated = xPos;
		xPosRotated -= currSprite.getWidth();
		xPosRotated /= 2;
		System.out.println("But now, it's " + xPosRotated);
		return xPosRotated;
	}
	
	/**
	 * Due to the way RenderEngine works, rotation needs to be accounted for 
	 * By moving the sprite by (-spriteWidth/2)^2+(-spriteHeight/2)^2 to make sure
	 * Rotations actually look the way they ought to 
	 * @return The 'apparent' position of the sprite along the x axis 
	 */
	public double getXPosRender(double viewportXPos) {
		double xPosRotated = Math.abs(viewportXPos - xPosWorld);
		xPosRotated -= currSprite.getWidth()/2;
		//xPosRotated /= 2; 
		return xPosRotated;
	}
	
	
	
	/**
	 * Due to the way RenderEngine works, rotation needs to be accounted for 
	 * By moving the sprite by (-spriteWidth/2)^2+(-spriteHeight/2)^2 to make sure
	 * Rotations actually look the way they ought to 
	 * @return The 'apparent' position of the sprite along the y axis 
	 */
	public double getYPosRender() {
		double yPosRotated = yPos;
		yPosRotated -= currSprite.getHeight()/2;
		yPosRotated /= 2; 
		return yPosRotated;
	}
	
	/**
	 * Due to the way RenderEngine works, rotation needs to be accounted for 
	 * By moving the sprite by (-spriteWidth/2)^2+(-spriteHeight/2)^2 to make sure
	 * Rotations actually look the way they ought to 
	 * @return The 'apparent' position of the sprite along the y axis 
	 */
	public double getYPosRender(double viewportYPos) {
		double yPosRotated = Math.abs(viewportYPos - yPosWorld);
		yPosRotated -= currSprite.getHeight()/2;
		//yPosRotated /= 2;
		//System.out.println("But now, it's " + yPosRotated);
		return yPosRotated;
	}
	
	
	/**
	 * Returns the horizontal world position of RenderObj's upper left hand corner
	 * @return The x position of the RenderObj's upper left hand corner
	 */
	public double getXPosWorld() {
		return xPosWorld;
	}
	
	
	/**
	 * Sets the horizontal world position of RenderObj's upper left hand corner
	 * @param The new x position of renderObj's upper left hand corner
	 */
	public void setXPosWorld(double newXPosWorld) {
		xPosWorld = newXPosWorld;
	}
	
	
	/**
	 * Returns the vertical world position of RenderObj's upper left hand corner
	 * @return The y position of the RenderObj's upper left hand corner
	 */
	public double getYPosWorld() {
		return yPosWorld;
	}
	
	/**
	 * Sets the vertical world position of RenderObj's upper left hand corner
	 * @param The new y position of renderObj's upper left hand corner
	 */
	public void setYPosWorld(double newYPosWorld) {
		yPosWorld = newYPosWorld;
	}
	
	
	
	/**
	 * Returns the horizontal position of sprite's upper left hand corner
	 * @return The x position of the sprite's upper left hand corner
	 */
	public double getPosX() {
		return xPos;
	}

	/**
	 * Changes the horizontal position of sprite's upper left hand corner 
	 * @param newXPos The new x position of sprite's upper left hand corner
	 */
	public void setPosX(double newXPos) {
		xPos = newXPos;
	}

	
	
	/**
	 * Returns the vertical position of sprite's upper left hand corner
	 * @return The y position of the sprite's upper left hand corner
	 */
	public double getPosY() {
		return yPos;
	}

	/**
	 * Changes the vertical position of sprite's upper left hand corner 
	 * @param newXPos The new x position of sprite's upper left hand corner
	 */
	public void setPosY(double newYPos) {
		yPos = newYPos;
	}
	
	/**
	 * Returns the depth position of sprite- determines draw order/hierarchy
	 * @return
	 */
	public int getZPos() {
		return zPos;
	}
	
	/**
	 * Sets the depth position of sprite
	 * @param newZPos The new depth of sprite- higher numbers are drawn over lower ones
	 */
	public void setZPos(int newZPos) {
		zPos = newZPos;
	}
	
	/**
	 * Returns the angle of current sprite from the normal, clockwise. 
	 * Goes between 0 and 360 degrees.
	 * @return
	 */
	public double getAngle() {
		return angle;
	}
	
	/**
	 * Sets the angle of the current sprite to degree
	 * @param degree The new angle of the current sprite
	 */
	public void setAngle(double degree) {
		if(degree == 0) {
			resetSprite(); 
		}
		else {
			rotateCurrSprite(degree);
		}
	}
	
	/**
	 * ResetSprite returns the current sprite back to an angle of zero, usually so it can be rotated once more. 
	 */
	protected void resetSprite() {
		setCurrSprite(currSpriteCol, currSpriteRow);
		currSpriteWidth = modelSpriteWidth;
		currSpriteHeight = modelSpriteHeight;
		angle = 0;
	}


}
