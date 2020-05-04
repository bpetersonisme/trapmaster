import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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
	private boolean despawned;
	
	
	private long lastAttackFrame;
	private long lastWalkFrame;
	private long walkFrameDelay;
	private long attackFrameDelay;
	private LinearVector currVelocity;
	private double oldXPosWorld;
	private double oldYPosWorld;
	HashMap<Character, ActionBox> hitboxes;
	private double xRate;
	private double yRate;
	
	private int response;
	
	public static final char NORTH = 'N';
	public static final char NW = 'T';
	public static final char EAST = 'E';
	public static final char NE = 'Y';
	public static final char SOUTH = 'S';
	public static final char SW = 'I';
	public static final char WEST = 'W';
	public static final char SE = 'O';
	public static final char STILL = '$';
	public static final char HIT = 'H';
	
	//Monster states
	private int state; 
	
	public static final int STAND_STILL = 0;
	public static final int ATTACK = 1;
	public static final int GET_TREASURE = 2;
	public static final int DESPAWN = 3;
	public static final int REQ_TREAS = 4; 
	
	public static final int MOVE = 5; 
	/**Monster Move State- Random Search: Monster picks a direction at random and moves */
	public static final int RANDOM_SEARCH = 6; 
	public static final int FIND_TREASURE = 7;
	public static final int FIND_SPAWN = 8;
	/**Monster Move State- Confirm Treasure: Monster Confirms that the treasure's there */
	public static final int CONFIRM_TREASURE = 9; 
	public static final int WALK_TO_CENTER = 10;
	//Monster state outcomes
	public static final int NORMAL = 0;
	public static final int FIGHTING = 1;
	public static final int KILLEDTARGET = 2; 
	public static final int SEARCHING_FOR_TREASURE = 3; 
	
	
	/**Monster State Replies*/
	public static final int STARTUP = -1;
	public static final int TARGET_HAS_TREASURE = 0;
	public static final int TARGET_NO_TREASURE = 1;
	
	private char currDir;
	private double hitOffsetX, hitOffsetY;
	private int weaponLength, weaponWidth;
	
	
	public static final int SEARCH_TREASURE = 5;
	private int count = 0;
	private boolean lastMonster = false;
	private boolean collided;
	private boolean looted;
	TreasureTile targetTreasure;
	Tile_tm prevTile, tileLastTick;
	
	
	/**
	 * Abstract constructor for all monsters
	 */ 
	public Monster_tm(BufferedImage spriteSheet,  double xPos, double yPos, int numRows, int numCols, int spriteWidth, int spriteHeight, int HP, int loot, int dmg) {
		setSpriteSheet(spriteSheet, numRows, numCols, spriteWidth, spriteHeight);
		state = STAND_STILL;
		weaponLength = 10;
		weaponWidth = 10;
		hitOffsetY = 10;
		hitOffsetX = 0;
		setType(MONSTER);
		hitboxes = new HashMap<Character, ActionBox>();
		makeHitboxes();
		currDir = NORTH;
		setXPosWorld(xPos);
		setYPosWorld(yPos);
		setZPosWorld(1);
		xRate = 5;
		yRate = 5;
		health = HP;
		healthMax = HP; 
		this.loot = loot;
		lootMax = loot*4;
		dmg = attack;
		currVelocity = new LinearVector(0, 0);
		setObjName("Abstract Monster");
		looted = false;
		lastWalkFrame = 0;
		lastAttackFrame = 0;
		walkFrameDelay = 20;
		attackFrameDelay = 20;
		response = -1;
		
		despawned = false;
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
	 * @return True if the monster collided with SOMETHING the previous tick, false otherwise
	 */
	public boolean getCollision() {
		return collided;
	}
	
	/**
	 * Move 
	 * @param collided Whether or not the previous move caused a collision, which would demand changing directions
	 * @param other, The thing we collided with
	 */
	public void move(LinearVector velocity) {


		/*
		if(tileLastTick == null) {
			tileLastTick = currTile;
			prevTile = currTile;
		}
		
		
		if(currTile.equals(tileLastTick) == false) {
			prevTile = tileLastTick;
		}
		
		LinearVector velocity = choice(other, currTile);
		*/
 
		setYPosWorld(getYPosWorld() + velocity.getY());
		setXPosWorld(getXPosWorld() + velocity.getX()); 
		
		if(velocity.getY() != 0 || velocity.getX() != 0) {
			if(Math.abs(velocity.getY()) > Math.abs(velocity.getX())) {
				if(velocity.getY() > 0) {
					currDir = SOUTH;
					hitOffsetX = getXPosWorld();
					hitOffsetY = getYPosWorld() + getRotatedSpriteHeight()/2 + weaponLength/2;
					 
				}
				else {
					currDir = NORTH;
					hitOffsetX = getXPosWorld();
					hitOffsetY = getYPosWorld() - getRotatedSpriteHeight()/2 - weaponLength/2;
				}
			}
			else {
				if(velocity.getX() > 0) {
					currDir = EAST;
					hitOffsetX = getXPosWorld() + getRotatedSpriteWidth()/2 + weaponLength/2;
					hitOffsetY = getYPosWorld();
				}
				else {
					currDir = WEST;
					hitOffsetX = getXPosWorld() - getRotatedSpriteWidth()/2 - weaponLength/2;
					hitOffsetY = getYPosWorld();
				}
			}
		}
		walkingAnim(currDir);
		
	}
	
	public void reply(int rep) {
		response = rep;
	}
	
	/**
	 * Choice makes a decision based on an input, and puts an output to announce the outcome.
	 * @param currTile The tile that it is currently standing on. Obviously.
	 * @param response 
	 * @return The outcome of these decisions. 
	 */
	public int choice(ActionBox other, Tile_tm currTile) {
		
	
		
		int outcome = 0;
		
		if(tileLastTick == null) {
			tileLastTick = currTile;
			prevTile = currTile;
		}
		
		if(currTile.equals(tileLastTick) == false) {
			prevTile = tileLastTick;
		}

		tileLastTick = currTile;
		
		System.out.println("Current tile is " + currTile + ", and previous tile is " + prevTile);
		
		
		if(currTile.getType() == TILE_SPAWN && prevTile.getType() != TILE_SPAWN) {
			System.out.println("Despawn");
			state = DESPAWN;
			setDespawned(true);
		}
		
		else {
			
			if(collided == true) {
				if(state == GET_TREASURE) { 
					if(loot == lootMax - 1) {
						System.out.println("move");
						state = MOVE;
					}
				}
				else  
					state = STAND_STILL;
				
			}
			else {
				state = MOVE;
			}
		}
		
		
		double xPos = 0;
		double yPos = 0; 
		
		LinearVector nuVelocity = null;
		
		System.out.print(this.getObjName() + " has decided to ");
		switch(state) {
		case MOVE: 
			System.out.print("go ");
			
			if(loot == lootMax - 1) {
				state = FIND_SPAWN;
			}
			else {
				if(currTile.getNearestTID() == -1) {
					state = RANDOM_SEARCH;
				}
				else {
					if(currTile.getType() == TILE_TREASURE) {
						state = WALK_TO_CENTER;
						
					}
					else {
						nuVelocity = currVelocity;
					
						if(response == TARGET_HAS_TREASURE)
							state = FIND_TREASURE;
						else if(response == TARGET_NO_TREASURE)
							state = RANDOM_SEARCH;
						else {
							state = STAND_STILL;
							outcome = CONFIRM_TREASURE;
						}
					}
				}
			}
			
			switch(state) {
			case FIND_TREASURE:
				System.out.println("find treasure");
				nuVelocity = goDirection(currTile.getMostValuableNeighbor(Tile_tm.TREASURE));
				
			break;
			case CONFIRM_TREASURE:
				System.out.println("gather their thoughts");
			break;
			case WALK_TO_CENTER: 
				System.out.println("Center themselves");
				nuVelocity = currVelocity;
			break;
			case RANDOM_SEARCH: 
				System.out.println("explore");
			break;
			case FIND_SPAWN:
				System.out.println("go home");
				nuVelocity =  goDirection(currTile.getMostValuableNeighbor(Tile_tm.SPAWN));
				
			break;
			default: System.out.println("... Stand there.");
			
			}
			
			
			if(nuVelocity == null)
				nuVelocity = new LinearVector(xPos, yPos);
			currVelocity = nuVelocity;
			move(nuVelocity); 
			
			break;
			
		case STAND_STILL:
			System.out.print("stand still ");
			xPos = 0;
			yPos = 0;
			currVelocity.setX(xPos);
			currVelocity.setY(yPos);
			walkingAnim(currDir);
			
			RenderObj par = ((RenderObj)other.getParent());
			
		 
			if(par != null) {
				if(par.getType() == TILE_TREASURE) {
					state = GET_TREASURE;
				}
				else {
					state = ATTACK;
				}
				
			}
			switch(state) {
				case ATTACK:
					System.out.println("and ATTACK!!!!");
					doAttack(other.getParent(), other);
					if(other.getParent().isDead()) {
						collided = false;
						outcome = 2;
					}
				break;
				case GET_TREASURE:
					System.out.println("Gather treasure");
				break;
			
			
			}
			break;
			case DESPAWN:
					setXPosWorld(-10000);
					setYPosWorld(-10000);
			
			break;
		}
		
		
		return outcome;
		
		
		 
		
	}
	
	/**
	 * Given a direction, returned a linear vector which corresponds to that direction
	 * @param dir The direction we'll be going 
	 * @return Some vector in that direction
	 */
	public LinearVector goDirection(char dir) {
		LinearVector course = null;
		double xPos = 0;
		double yPos = 0;
		double sideFactor = 8.0;
		double yRange, xRange;
		switch(dir) {
			case NORTH:
				xRange = xRate/sideFactor + xRate/sideFactor + 1;
				yRange = yRate*-1; 
				yPos = Math.random()*yRange - 1;
			break;
			case EAST:
				yRange = yRate/sideFactor + yRate/sideFactor + 1;
				xPos = Math.random()*xRate*-1 - 1; 
			break;
			case SOUTH:
				xRange = xRate/sideFactor + xRate/sideFactor + 1; 
				yPos = Math.random()*yRate +1;
			break;
			case WEST:
				yRange = yRate/sideFactor + yRate/sideFactor + 1;
				xPos = Math.random()*xRate + 1; 
			break;
				
		}
		System.out.println("Curr Velocity: (" + xPos + ", " + yPos + ")");
		course = new LinearVector(xPos, yPos);
		return course;
	}
	
	public LinearVector getVelocity() {
		return currVelocity;
	}
	
 
	/**
	 * Takes a treasure tile object and calls its method to take loot. 
	 * Loot taken is based on the damage dealt to the treasure.
	 * 
	 * @param chest The treasureChest they are trying to plunder 
	 * @return The amount of treasure they took
	 */
	public int plunder(TreasureTile chest) {
		if (chest.getTreasure() > 0){
			int amtTaken = lootAmt();
			return addLoot(chest.loseTreasure(amtTaken)); 
			
		}
		else 
			return 0;
	}
	
	
	public boolean isDespawned() {
		return despawned;
	}
	
	public void setDespawned(boolean des) {
		despawned = des;
	}
	
	
	public void doAttack(Damageable other, ActionBox coll) {
		getHitbox().setEnabled(true);
		
		if(((RenderObj)other).isColliding(getHitbox()) || coll.isColliding(getHitbox())) {
			 attackAnim(currDir);
			 attackAnim(currDir);
			 other.takeDamage(getAttack());
			 attackAnim(currDir);
		}
				
		getHitbox().setEnabled(false);
	}

	/**
	 * Sets xPosWorld to newXPosWorld. Also sets oldXPosWorld to the
	 * old world XPos
	 * @param newXPosWorld The new horizontal coordinate
	 */
	public void setXPosWorld(double newXPosWorld) {
		oldXPosWorld = getXPosWorld();
		super.setXPosWorld(newXPosWorld);
		hitboxes.get(HIT).setXPosWorld(hitOffsetX);
	}
	
	/**
	 * @return the immediate previous xPosWorld
	 */
	public double getOldX() {
		return oldXPosWorld;
	}
	
	/**
	 * Sets yPosWorld to newYPosWorld. Also sets oldYPosWorld to the
	 * old world YPos
	 * @param newYPosWorld The new vertical coordinate
	 */
	public void setYPosWorld(double newYPosWorld) {
		oldYPosWorld = getYPosWorld();
		super.setYPosWorld(newYPosWorld);
		hitboxes.get(HIT).setYPosWorld(hitOffsetY);
	}
	
	/**
	 * @return the immediate previous yPosWorld
	 */
	public double getOldY() {
		return oldYPosWorld;
	}
	
	public void revertWorldPos() {
		setXPosWorld(oldXPosWorld);
		setYPosWorld(oldYPosWorld);
		collided = true;
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



	public int getLoot() {
		return loot;
	}

	public void setLoot(int nuLoot) {
		if(nuLoot >= lootMax) 
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

	/**
	 * @return the time that the last attack frame was fired
	 */
	public long getLastAttackTime() {
		return lastAttackFrame;
	}
	
	/**
	 * @return The time that the last walking frame was fired
	 */
	public long getLastWalkTime() {
		return lastWalkFrame;
	}
	
	public void setLastWalkTime(long l) {
		lastWalkFrame = l;
	}
	
	public long getWalkFrameDelay() {
		return walkFrameDelay;
	}
	public void setWalkFrameDelay(long nuDelay) {
		walkFrameDelay = nuDelay;
	}
	
	public void setLastAttackTime(long l) {
		lastAttackFrame = l;
	}
	
	public long getAttackFrameDelay() {
		return walkFrameDelay;
	}
	public void setAttackFrameDelay(long nuDelay) {
		attackFrameDelay = nuDelay;
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
	
	
	public void setHealthMax(int nuMax) {
		healthMax = nuMax;
		
	}
	
	

	public int takeDamage(int hit) {
		dmgAnim();		
		setHealth(getHealth() - hit);
		if (health <= 0) {
			 deathAnim();
		}
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

	public ActionBox getHitbox() {
		return hitboxes.get(HIT);
	}
	
	public void makeHitboxes() {
		hitboxes.put(HIT, ActionBox.makeActionBox(getXPosWorld(), getYPosWorld() + 15, weaponWidth, weaponLength, this));
		hitboxes.get(HIT).setEnabled(false);
		hitboxes.get(HIT).setBlock(false);
		
	}

	public void doEffect(RenderObj collider) {
 
	}

	public boolean isDead() {
		System.out.println("Monster health is " + getHealth());
		if(getHealth() <= 0) 
			return true;
		return false;
	}
	
	/**
	 * @return True if the monster has been looted (i.e. its loot has been taken) and false otherwise
	 */
	public boolean isLooted() {
		return looted;
	}
	
	/**
	 * Sets whether or not the monster has been looted 
	 * @param beenLooted Whether or not the monster has been looted
	 */
	public void setLooted(boolean beenLooted) {
		looted = beenLooted;
	}

}
