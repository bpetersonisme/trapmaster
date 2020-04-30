import java.awt.Color;
import java.util.ArrayList;
/**
 * Trap_tm is the superclass for all individual trap classes.
 * It provides the necessary variables for all traps as well as 
 * methods for controlling trap behavior.
 * Extends the RenderObj class.
 * @author Max Martin
 *
 */

public abstract class Trap_tm extends RenderObj{
	
	static int trap_count = 0;
	private int tr_maxHealth;			//Trap maximum health
	private int tr_currentHealth;		//Trap current health
	private int tr_range;				//Trap range
	private int tr_damage;				//Trap damage
	private int tr_cost;				//Trap cost
	private int tr_ID;					//numerical ID of the trap
	private int facing;					//direction trap is facing. 0 = north, 1 = east, 2 = south, 3 = west
	private Monster_tm target;			//The target of the trap
	private ActionBox AOE;				//The tiles covered by this trap.
	
	/**
	 * Abstract constructor. Just to make things easier for the actual trap classes.
	 */
	public Trap_tm(double xPos, double yPos, int facing) {
		this.setXPosWorld(xPos);
		this.setYPosWorld(yPos);
		this.setZPos(2);
		this.facing = facing;
		this.setFocusable(true);
		instantiateStats();
		addStat(tr_currentHealth, tr_maxHealth, Color.red, Color.green);
		tr_ID = trap_count;
		trap_count++;
	}
	
	
	
	/**
	 * Getters and setters.
	 */
	public int getTr_maxHealth() {
		return tr_maxHealth;
	}
	public void setTr_maxHealth(int tr_maxHealth) {
		this.tr_maxHealth = tr_maxHealth;
	}
	public int getTr_currentHealth() {
		return tr_currentHealth;
	}
	public void setTr_currentHealth(int tr_currentHealth) {
		this.tr_currentHealth = tr_currentHealth;
	}
	public int getTr_range() {
		return tr_range;
	}
	public void setTr_range(int tr_range) {
		this.tr_range = tr_range;
	}
	public int getTr_damage() {
		return tr_damage;
	}
	public void setTr_damage(int tr_damage) {
		this.tr_damage = tr_damage;
	}
	public int getTr_cost() {
		return tr_cost;
	}
	public void setTr_cost(int tr_cost) {
		this.tr_cost = tr_cost;
	}
	public int getTr_ID() {
		return tr_ID;
	}
	public int get_facing() {
		return facing;
	}
	public void set_facing(int facing) {
		if (facing >= 0 && facing < 4) {
			this.facing = facing;
		} else {
			this.facing = 0;
			System.out.println("Unexceptable facing received. " + facing);
		}
	}
	public Monster_tm getTarget() {
		return target;
	}
	public void setTarget(Monster_tm target) {
		this.target = target;
	}
	public ActionBox getAOE() {
		return AOE;
	}
	
	/**
	 * Allows a trap to select a monster to begin attacking.
	 * @param monsters The list of spawned monsters from Map_tm
	 * @return true if target found and false otherwise.
	 */
	public boolean acquireTarget (ArrayList<Monster_tm> monsters) {
		Monster_tm tempmonster;
		
		for(int i = 0; i < monsters.size(); i++) {
			tempmonster = monsters.get(i);
			if(AOE.contains(tempmonster.getXPosWorld(), tempmonster.getYPosWorld())) {
				target = tempmonster;
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Checks if the traps target is still valid. If yes,
	 * triggers an attack from the trap to its target.
	 * If no, acquires a new target.
	 */
	public abstract Ammo_tm tr_attack (ArrayList<Monster_tm> monsters);
	
	/**
	 * Called when the trap is sold.
	 * Gives the player treasure equal to cost * (current health / max health).
	 * Returns the gold received as an int.
	 */
	public int tr_sell() {	
		double total = (double)this.tr_cost * ((double)this.tr_currentHealth / (double)this.tr_maxHealth);
		return (int)total;
	}
	
	/**
	 * Creates the trap's action box "AOE" when placed.
	 */
	public void tr_place() {
		
		if (facing == 0) {
			AOE = ActionBox.makeActionBox(this.getXPosWorld(), this.getYPosWorld() - (tr_range * 32), 64, tr_range * 64);
		} else if (facing == 1) {
			AOE = ActionBox.makeActionBox(this.getXPosWorld() + (tr_range * 32), this.getYPosWorld(), tr_range * 64, 64);
		} else if (facing == 2) {
			AOE = ActionBox.makeActionBox(this.getXPosWorld(), this.getYPosWorld() + (tr_range * 32), 64, tr_range * 64);
		} else {
			AOE = ActionBox.makeActionBox(this.getXPosWorld() - (tr_range * 32), this.getYPosWorld(), tr_range * 64, 64);
		}

	}
	
	public void setBars() {
		setBarVal(0, tr_currentHealth);
	}
}
