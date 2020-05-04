import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.ClassLoader;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class Trap_Ballista extends Trap_tm{
	
	private int timer;					//Used to keep track of cooldown
	private int cooldown;			//Trap cooldown/firerate
	private long previousFire;
	private Ammo_tm ammo;
	
	public Trap_Ballista(double xPos, double yPos, int facing) {
		super(xPos, yPos, facing);
		this.setTr_maxHealth(100);
		this.setTr_currentHealth(100);
		this.setTr_range(10);
		this.setTr_damage(50);
		this.setTr_cost(50);
		this.setTr_cooldown(150);
		//this.tr_cooldown = 150;
		//this.timer = this.tr_cooldown;
		this.setTarget(null);
		previousFire = 0;
		BufferedImage buf = null;
		try {
			buf = ImageIO.read(this.getClass().getResourceAsStream("JankBallista.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setSpriteSheet(buf, 2, 4, 64, 64);
		this.setCurrSpriteCol(facing);
		setType(TRAP_BALLISTA);
		
	}
	
	public int getTr_cooldown() {
		return cooldown;
	}
	public void setTr_cooldown(int tr_cooldown) {
		this.cooldown = tr_cooldown;
	}
	
	
	public boolean acquireTarget(ArrayList<Monster_tm> monsters) {
		boolean readyToFire = false;
		if(System.currentTimeMillis() > previousFire + cooldown) {
			readyToFire = super.acquireTarget(monsters);
			previousFire = System.currentTimeMillis();
		}
		
		return readyToFire;
	}
	
	public Ammo_tm tr_attack(ArrayList<Monster_tm> monsters) {
		if (timer == 0) {
			if (acquireTarget(monsters)) {
				System.out.println("POW");
				ammo = new Ballista_Bolt(getTr_damage(), getTr_range(), getXPosWorld(), getYPosWorld(), get_facing(), getTarget());
				timer = cooldown;
				setCurrSpriteRow(1);
				return ammo;
			} else {
				return null;
			}
		} else if (timer == (int)(cooldown / 4)) {
			setCurrSpriteRow(0);
			timer = timer - 1;
			return null;
		} else {
			timer = timer - 1;
			return null;
		}
	}

	
}
