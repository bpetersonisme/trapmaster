import java.util.ArrayList;


/**
 * The Damageable interface contains a number of methods useful for taking and dealing damage. 
 * @author Bradley Peterson
 *
 */
public interface Damageable extends Collideable {
	/**
	 * @return The amount of health a Damageable has presently
	 */
	public int getHealth();
	/**
	 * @return The maximum amount of health a Damageable can have
	 */
	public int getHealthMax();
	/**
	 * Sets the monster's health to healthVal. Probably shouldn't be greater than maximum.
	 * @param healthVal The creature's new health. 
	 */
	public void setHealth(int healthVal);
	/**
	 * Sets the Damageable's maximum health to nuMax.
	 * @param nuMax The new max health 
	 */
	public void setHealthMax(int nuMax);
	/**
	 * Takes hit out of health. 
	 * @return The Damageable's remaining health 
	 */
	public int takeDamage(int hit);
	/**
	 * Adds help to Health
	 * @param help The amount of health to be added 
	 * @return Remaining health
	 */
	public int getHealed(int help);
	/**
	 * Sets attack damage to dmg 
	 * @param dmg The new attack damage
	 */
	public void setAttack(int dmg);
	/**
	 * @return the attack damage of the Damageable
	 */
	public int getAttack();
	
	
}
