public class Monster_tm extends RenderObj {

	protected int health;
	protected int loot;
	protected int attack;
	int count = 0;
	

	/**
	 * Method that allows monsters to take damage.
	 * 
	 * @param dmgTaken
	 *            - The amount of damage the monster will take.
	 * @param p
	 *            - Player is needed so that the loot of monster is added to player
	 *            money pool.
	 */

	public void takeDmg(int dmgTaken, Player p) {
		this.health -= dmgTaken;
		if (this.health <= 0) {
			 //death(p);
		}
	}

	/*
	 * Takes a treasure tile object and calls its method to take loot. Loot taken is
	 * based on the damage dealt to the treasure.
	 */
	public void plunder(TreasureTile chest) {
		if (chest.getTreasure() > 0){
			if (this.attack >= chest.getTreasure()) {
				this.loot += chest.getTreasure();
				chest.loseTreasure(this.attack);
				return;
			}
			chest.loseTreasure(this.attack);
			this.loot += attack;
		}
	}

	/**
	 * Plays the death animation, waits, then "deletes" monster from screen. 
	 * Moves the monster's x position away from screen.
	 */
	public void death() {
		cycleAnimation(4, 0, 1);
		if(count == 3){
			setXPosWorld(-12000);
			count = 0;
		}
		count++;
	}

	/**
	 * Checks if monster is alive.
	 * @return - true if alive, else false
	 */
	public boolean isAlive() {
		if (this.health > 0) {return true;}
		return false;
	}

	public void attack() {}

	public void move() {}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getLoot() {
		return loot;
	}

	public void setLoot(int loot) {
		this.loot = loot;
	}

}
