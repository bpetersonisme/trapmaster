import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.ClassLoader;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class Trap_Ballista extends Trap_tm{
	
	private int timer;					//Used to keep track of cooldown
	private int tr_cooldown;			//Trap cooldown/firerate
	private Ammo_tm ammo;
	
	public Trap_Ballista(double xPos, double yPos, int facing) {
		super(xPos, yPos, facing);
		this.setTr_maxHealth(100);
		this.setTr_currentHealth(100);
		this.setTr_range(10);
		this.setTr_damage(50);
		this.setTr_cost(50);
		this.setTr_cooldown(150);
		this.tr_cooldown = 150;
		this.timer = this.tr_cooldown;
		this.setTarget(null);
		BufferedImage buf = null;
		try {
			buf = ImageIO.read(this.getClass().getResourceAsStream("JankBallista.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setSpriteSheet(buf, 2, 4, 64, 64);
		this.setCurrSpriteCol(facing);
		
	}
	
	public int getTr_cooldown() {
		return tr_cooldown;
	}
	public void setTr_cooldown(int tr_cooldown) {
		this.tr_cooldown = tr_cooldown;
	}
	
	public Ammo_tm tr_attack(ArrayList<Monster_tm> monsters) {
		if (timer == 0) {
			if (acquireTarget(monsters)) {
				ammo = new Ballista_Bolt(getTr_damage(), getTr_range(), getXPosWorld(), getYPosWorld(), get_facing(), getTarget());
				timer = tr_cooldown;
				setCurrSpriteRow(1);
				return ammo;
			} else {
				return null;
			}
		} else if (timer == (int)(tr_cooldown / 4)) {
			setCurrSpriteRow(0);
			timer = timer - 1;
			return null;
		} else {
			timer = timer - 1;
			return null;
		}
	}

	public int getHealth() {
		return getTr_currentHealth();
	}

	public int getHealthMax() {
		return getTr_maxHealth();
	}

	public void setHealth(int healthVal) {
		setTr_currentHealth(healthVal);
	}

	public void setHealthMax(int nuMax) {
		setTr_maxHealth(nuMax);
	}

	public int takeDamage(int hit) {
		setTr_currentHealth(getTr_currentHealth() - hit);
		return getTr_currentHealth();
	}

	public int getHealed(int help) {
		setTr_currentHealth(getTr_currentHealth() + help);
		return getTr_currentHealth();
	}

	public void setAttack(int dmg) {
		setTr_damage(dmg);
	}

	public int getAttack() {
		return getTr_damage();
	}

	public HashMap<Character, ActionBox> getHitboxes() {
		// TODO Auto-generated method stub
		return null;
	}

	public void makeHitboxes() {
		// TODO Auto-generated method stub
		
	}
}
