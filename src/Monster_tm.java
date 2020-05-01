public class Monster_tm extends RenderObj {

	protected int health;
	protected int loot;
	protected int attack;
	int count = 0;
	boolean lastMonster = false;
	

	/**
	 * Method that allows monsters to take damage.
	 * 
	 * @param dmgTaken
	 *            - The amount of damage the monster will take.
	 */

	public void takeDmg(int dmgTaken) {
		this.health -= dmgTaken;
		if (this.health <= 0) {
			 death();
		}
	}

	/**
	 * Takes a treasure tile object and calls its method to take loot. 
	 * Loot taken is based on the damage dealt to the treasure.
	 * 
	 * @param chest
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
	 * Sets the death animation once monster dies. 
	 * This method assumes that giveGold() is called before it.
	 * Sets the monster's loot to 0 to ensure loot isn't added twice. 
	 * If statement is used to make the monster stay on screen before disappearing.
	 */
	public void death() {
		cycleAnimation(4, 0, 1);
		this.loot = 0;
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

}
