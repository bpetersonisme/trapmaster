import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Bradley Peterson/Joshua Jiminez
 * The Generic Monster is has a few simple characteristics. 
 */
public class Monster_tm extends RenderObj implements Damageable{

	private int health;
	private int healthMax;
	private int loot;
	private int lootMax;
	private int attack;
	private double xRate;
	private double yRate;
	public static char NORTH = 'N';
	public static char EAST = 'E';
	public static char SOUTH = 'S';
	public static char WEST = 'W';
	int count = 0;
	boolean lastMonster = false;
	TreasureTile targetTreasure;
	
	
	/**
	 * Abstract constructor for all monsters
	 */ 
	public Monster_tm(BufferedImage spriteSheet,  double xPos, double yPos, int numRows, int numCols, int spriteWidth, int spriteHeight, int HP, int loot, int dmg) {
		setSpriteSheet(spriteSheet, numRows, numCols, spriteWidth, spriteHeight);
		setXPosWorld(xPos);
		setYPosWorld(yPos);
		setZPosWorld(1);
		setHealth(HP);
		healthMax = HP;
		setLoot(loot);
		lootMax = loot*4;
		dmg = attack;
		setObjName("Abstract Monster");
	}
	
	public Monster_tm(String spriteSheet,  double xPos, double yPos, int numRows, int numCols, int spriteWidth, int spriteHeight, int HP, int loot, int dmg) {
		this(importImage(spriteSheet), xPos, yPos, numRows, numCols, spriteWidth, spriteHeight, HP, loot, dmg);
	}
	

	
	/**
	 * Class to attack a target
	 * @param target
	 */
	public void attack(Damageable target) {
		 
		
	}
	
	
	/** 
	 * Forces the monster to take dmgTaken damage. If they take more damage
	 * Than they have health, they will die. Obviously.  
	 * @param dmgTaken - The amount of damage the monster will take.
	 */

	public void takeDmg(int dmgTaken) {
		dmgAnim();
		health -= dmgTaken;
		if (health <= 0) {
			 deathAnim();
		}
	}

	
	
 
	/**
	 * Takes a treasure tile object and calls its method to take loot. 
	 * Loot taken is based on the damage dealt to the treasure.
	 * 
	 * @param chest The treasureChest they are trying to plunder 
	 * @return The amount of treasure they took
	 */
	public int plunder(TreasureTile chest) {
		if (RenderObj.getDistance(chest, this) < Tile_tm.SIZE && chest.getTreasure() > 0){
			int amtTaken = lootAmt();
			return addLoot(chest.loseTreasure(amtTaken)); 
			
		}
		else 
			return 0;
	}

	
	
	
	public void attackAnim(char dir) {
		System.out.println("DEFAULT- NO ATTACK ANIMATION");
	}
	public void plunderAnim(char dir) {
		System.out.println("DEFAULT- NO PLUNDER ANIMATION");
	}
 
	public void idleAnim(char dir) {
		System.out.println("DEFAULT- NO IDLE ANIMATION");
	}
	
	/**
	 * The animation for walking- to be overridden
	 */
	public void walkingAnim(char dir) {
		System.out.println("DEFAULT- NO WALKING ANIMATION");
	}
	
	/**
	 * The animation for taking damage
	 */
	public void dmgAnim() {
		System.out.println("DEFAULT- NO DAMAGE ANIMATION");
	}
	
	/**
	 * The animation for death. 
	 */
	public void deathAnim() {
		System.out.println("DEFAULT- NO DEATH ANIMATION");
	}

	/**
	 * Checks if monster is alive.
	 * @return - true if alive, else false
	 */
	public boolean isAlive() {
		if (this.health > 0) {return true;}
		return false;
	}


	public int getHealth() {
		return health;
	}
	public int getHealthMax() {
		return healthMax;
	}

	public void setHealth(int nuHealth) {
		if(nuHealth > healthMax)
			health = healthMax;
		else 
			health = nuHealth;
	}

	public int getLoot() {
		return loot;
	}

	public void setLoot(int nuLoot) {
		if(nuLoot > lootMax) 
			loot = lootMax;
		else
			loot = nuLoot;
	}
	
	/**
	 * Adds booty to loot 
	 * @param booty The plunder to be added. Yar.
	 */
	public int addLoot(int booty) {
		setLoot(getLoot() + booty);
		return getLoot();
	}
	
	/**
	 * @return the amount of loot the monster has decided to take, up to filling its inventory
	 */
	public int lootAmt() {
		int take = (int)(Math.random() * 10 + 1);
		take %= (getMaxLoot() - getLoot());
		return take;
	}
	
	/**
	 * Sets the maxLoot to max
	 * @param max The new highest amount of loot the monster can hold
	 */
	public void setMaxLoot(int max) {
		lootMax = max;
	}
	
	/**
	 * @return The maximum loot the monster can hold 
	 */
	public int getMaxLoot() {
		return lootMax;
	}
	
	/**
	 * Returns whether or not this monster is the last one. Usually isn't.
	 * @return True if this is the last monster, false otherwise
	 */
	public boolean isLastMonster() {
		return lastMonster;
	}
	/**
	 * Sets whether or not this monster is the last one. Used for spawning purposes.
	 * @param isLast True if this is the last monster, false otherwise
	 */
	public void setLastMonster(boolean isLast) {
		lastMonster = isLast;
	}

	public void setHealthMax(int nuMax) {
		healthMax = nuMax;
		
	}

	public int takeDamage(int hit) {
		setHealth(getHealth() - hit);
		return getHealth();
	}

	public int getHealed(int help) {
		setHealth(getHealth() + help);
		return getHealth();
	}

	public void setAttack(int dmg) {
		attack = dmg;		
	}

	public int getAttack() {
		return (int)((Math.random()*attack) + 1);
	}

	public HashMap<Character, ActionBox> getHitboxes() {
		// TODO Auto-generated method stub
		return null;
	}

	public void makeHitboxes() {
		// TODO Auto-generated method stub
		
	}

}
