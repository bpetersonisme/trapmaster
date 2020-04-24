public class Monster_tm extends RenderObj{
	
	protected int health;
	protected int loot;
	protected int attack;
	
	/*This takes a int that signifies how much damage monster will take.
	The player object is just a basic class I made to test the function.
	It passes the player option so once enemy dies, it goes to the death
	function and adds the monster loot to player money pool.*/
	
	public void takeDmg(int dmgTaken, Player p){
		this.health -= dmgTaken;
		if(this.health <= 0) {
			//death(p);
		}
	}
	
	/*Takes a treasure tile object and calls its method to take loot.
	 * Loot taken is based on the damage dealt to the treasure.
	 */
	public void plunder(TreasureTile ev) {
		if (ev.getTreasure() > 0) {
			if (this.attack >= ev.getTreasure()) {
				this.loot += ev.getTreasure();
				ev.loseTreasure(this.attack);
				return;
			}
			ev.loseTreasure(this.attack);
			this.loot += attack;
		} else {
			System.out.println("Looted everything, ogre now has: " + this.getLoot());
		}
	}
	
	/*Called when monster dies. Add monster loot to player money pool.
	 * Not sure how to delete the sprite off the screen when it dies. 
	 *
	public void death(Player p) {
		p.money += this.loot;
	}*/
	
	
	int count = 0;
	public void death() {
		cycleAnimation(4, 0, 1);
		if (count == 3) {
			this.setXPosWorld(-12200);
		}
		count++;
	}
	
	public void attack() {}
	
	public void move() {}
	
	public void direction() {}
	
	/*Checks if monster is alive. */
	public boolean isAlive() {
		if(this.health > 0) {
			return true;
		}
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


}
