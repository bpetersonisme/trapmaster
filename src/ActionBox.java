
/**
 * ActionBox is a specialized variant on RenderObj
 * It is made as a sort of trigger, rather than an actual object in the world 
 * It is fully compatible with RenderObj's collision detection functions 
 * And does not use a sprite sheet
 * @author Bradley 'bb' Peterson
 *
 */
public class ActionBox extends RenderObj {
	
	private double boxDistance;
	private RenderObj parent; 
	
 
	/**
	 * Creates a new ActionBox at (worldXPos, worldYPos). It is boxWidth by boxHeight and has no parent.
	 * @param worldXPos The center horizontal coordinate of the ActionBox
	 * @param worldYPos The central vertical coordinate of the ActionBox
	 * @param boxWidth The width of the ActionBox
	 * @param boxHeight The height of the ActionBox
	 */
	public ActionBox(double worldXPos, double worldYPos, int boxWidth, int boxHeight) {
		
	}
	/**
	 * Creates a new ActionBox at (worldXPos, worldYPos). It is boxWidth by boxHeight and maintains a constant distance
	 * And orientation to parent 
	 	 * @param worldXPos The center horizontal coordinate of the ActionBox
	 * @param worldYPos The central vertical coordinate of the ActionBox
	 * @param boxWidth The width of the ActionBox
	 * @param boxHeight The height of the ActionBox
	 * @param parent The parent of the actionBox, whose actions it monitors
	 */
	public ActionBox(double worldXPos, double worldYPos, int boxWidth, int boxHeight, RenderObj parent) {
		setSpriteSheet(null);
	}
	 
}
