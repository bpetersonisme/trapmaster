
public class Monster_tm {
	
	protected int health;
	protected int loot;
	protected int attack;
	
	public void takeDmg(int dmgTaken, Player p){
		this.health -= dmgTaken;
		if(this.health <= 0) {
			death(p);
		}
	}
	
	public void plunder(TreasureTile chest) {
		chest.loseTreasure(attack);
		this.loot += attack;
	}
	
	public void death(Player p) {
		p.money += this.loot;
	}
	
	public void attack() {}
	
	public void move() {}
	
	public void direction() {}
	
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
