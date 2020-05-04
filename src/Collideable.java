import java.util.HashMap;


public interface Collideable {
	/**
	 * @return An arrayList of the Damageable's hitboxes, if any should exist
	 */
	public HashMap<Character, ActionBox> getHitboxes();
	
	/**
	 * It simply creates whatever hitboxes are needed 
	 */
	public void makeHitboxes();
}
