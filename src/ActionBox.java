import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


/**
 * ActionBox is a specialized variant on RenderObj
 * It is made as a sort of trigger, rather than an actual object in the world 
 * It is fully compatible with RenderObj's collision detection functions. 
 * Reflecting its status as a non-object in the world, ActionBox does not have 
 * a public constructor. Rather, it utilizes a factory method. 
 * And does not use a sprite sheet
 * @author Bradley 'bb' Peterson
 *
 */
public class ActionBox extends RenderObj {
	
 
	private boolean enabled;
	
	
	/**
	 * Creates a new ActionBox at (worldXPos, worldYPos). It is boxWidth by boxHeight at angle degrees
	 * @param worldXPos The center horizontal coordinate of the ActionBox
	 * @param worldYPos The central vertical coordinate of the ActionBox
	 * @param boxWidth The width of the ActionBox
	 * @param boxHeight The height of the ActionBox
	 * @param angle The angle of the actionBox
	 */
	public static ActionBox makeActionBox(double worldXPos, double worldYPos, int boxWidth, int boxHeight, double angle) {
		return new ActionBox(worldXPos, worldYPos, boxWidth, boxHeight, 0);
	}
 
	/**
	 * Creates a new ActionBox at (worldXPos, worldYPos). It is boxWidth by boxHeight. 
	 * @param worldXPos The center horizontal coordinate of the ActionBox
	 * @param worldYPos The central vertical coordinate of the ActionBox
	 * @param boxWidth The width of the ActionBox
	 * @param boxHeight The height of the ActionBox
	 */
	public static ActionBox makeActionBox(double worldXPos, double worldYPos, int boxWidth, int boxHeight) {
		return new ActionBox(worldXPos, worldYPos, boxWidth, boxHeight, 0);
	}
		
	/**
	 * Creates a new ActionBox at (worldXPos, worldYPos). It is boxDim by boxDim, at angle degrees
	 * @param worldXPos The center horizontal coordinate of the ActionBox
	 * @param worldYPos The central vertical coordinate of the ActionBox
	 * @param boxDim The dimensions of the box (more of a square)
	 * @param angle The angle of the box
	 */
	public static ActionBox makeActionBox(double worldXPos, double worldYPos, int boxDim, double angle) {
		return new ActionBox(worldXPos, worldYPos, boxDim, boxDim, angle);
	}
	
	/**
	 * Creates a new ActionBox at (worldXPos, worldYPos). It is boxDim by boxDim. 
	 * @param worldXPos The center horizontal coordinate of the ActionBox
	 * @param worldYPos The central vertical coordinate of the ActionBox
	 * @param boxDim The dimensions of the box (more of a square)
	 */
	public static ActionBox makeActionBox(double worldXPos, double worldYPos, int boxDim) {
		return new ActionBox( worldXPos, worldYPos, boxDim, boxDim, 0);
	}
	
	
	
	/**
	 * Creates a new ActionBox at (worldXPos, worldYPos). It is boxWidth by boxHeight and
	 	 * @param worldXPos The center horizontal coordinate of the ActionBox
	 * @param worldYPos The central vertical coordinate of the ActionBox
	 * @param boxWidth The width of the ActionBox
	 * @param boxHeight The height of the ActionBox
	 * @param parent The parent of the actionBox, whose actions it monitors
	 */
	private ActionBox(double worldXPos, double worldYPos, int boxWidth, int boxHeight, double angle) {
		setSpriteSheet((BufferedImage)null, 1, 1, boxWidth, boxHeight);
		setXPosWorld(worldXPos);
		setYPosWorld(worldYPos);
		setZPos(1000000);
		enabled = true;
		setObjName("ActionBox");
	}
	
	public void colorEdge(Graphics2D g2D) {
		if(isEnabled())
	 		g2D.setColor(Color.RED);
	 	else 
	 		g2D.setColor(Color.GREEN);
	}
	
	
	/**
	 * @return True if the actionBox is collision-enabled, and false otherwise
	 */
	public boolean isEnabled() {
		return enabled;
	}
	
	/**
	 * Sets whether or not it is enabled. If it is, it will detect collisions. 
	 * @param enabled Whether or not this actionBox is to be enabled.
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		redrawCurrSprite();
	}
	
	/**
	 * Returns whether or not this is colliding with other
	 * @param other The other RenderObj
	 * @return True if the two are colliding and the ActionBox is enabled, false otherwise
	 */
	public boolean isColliding(RenderObj other) {
		return super.isColliding(other) && isEnabled();
	}
	
	
	 
}
