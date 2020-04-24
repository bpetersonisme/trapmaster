import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Ogre extends Monster_tm {
	int looted = 0;
	int frameDelay = 300;
	String filename = "Ogre.png";
	BufferedImage buf = ImageIO.read(this.getClass().getResourceAsStream(filename));
	boolean up = false;
	boolean down = false;
	boolean left = false;
	boolean right = false;
	int flag = 0;
	int count = 0;

	public Ogre(int rows, int columns, int width, int height, int xWorld, int yWorld) throws IOException {
		setSpriteSheet(buf, rows, columns, width, height);
		Random rmd = new Random();
		health = 200;
		loot = rmd.nextInt(220) + 80;
		attack = 50;
		setXPosWorld(xWorld);
		setYPosWorld(yWorld);
	}


	
	//This is used for testing to make the monster attack the chest. I'm using the Elf as a "chest".
	public void plunder(Elf ev) {
		if (ev.loot > 0) {
			//Checks to see if the remaining treasure is less than monster attack.
			if (this.attack >= ev.loot) {
				this.loot += ev.loot;
				ev.loseLoot(this.attack);
				ev.health -= this.attack;
				if(ev.getHealth() <= 0) {
					ev.death();
				}
				return;
			}
			//Replace with chest lose treasure function
			ev.loseLoot(this.attack);
			this.loot += attack;
		} else {
			System.out.println("Looted everything, ogre now has: " + this.getLoot());
		}
		ev.health -= this.attack;
		if(ev.getHealth() <= 0) {
			ev.death();
		}
		count++;
		System.out.println("Times plunder was called: " + count + " Loot in chest: " + ev.loot);
	}

	/*Actual one
	public void attack(boolean right, boolean left, boolean up, boolean down, TreasureTile ev, int flag) {
		if (right) {
			atkRAni();
			if (flag == 2) {
				plunder(ev);
			}
		}
		if (left) {
			atkLAni();
			if (flag == 2) {
				plunder(ev);
			}
		}
		if (up) {
			atkUpAni();
			if (flag == 2) {
				plunder(ev);
			}
		}
		if (down) {
			atkDAni();
			if (flag == 2) {
				plunder(ev);
			}
		}
	}*/
	
	//Testing
	public void attack(boolean right, boolean left, boolean up, boolean down, Elf ev, int flag) {
		if (right) {
			atkRAni();
			if (flag == 2) {
				plunder(ev);
			}
		}
		if (left) {
			atkLAni();
			if (flag == 2) {
				plunder(ev);
			}
		}
		if (up) {
			atkUpAni();
			if (flag == 2) {
				plunder(ev);
			}
		}
		if (down) {
			atkDAni();
			if (flag == 2) {
				plunder(ev);
			}
		}
	}
	
	/*Actual one
	public void move(TreasureTile chest) {
		if (isColliding(this, chest) == false) {
			up = false;
			down = false;
			left = false;
			right = false;
			if (getXPosWorld() < chest.getPosX()) {
				walkRightAni();
				right = true;
			} else if (getXPosWorld() > chest.getPosX()) {
				walkLeftAni();
				left = true;
			} else if (getYPosWorld() < chest.getPosY()) {
				walkDownAni();
				down = true;
			} else if (getYPosWorld() > chest.getPosY()) {
				walkUpAni();
				up = true;
			}
		} else {
			cooldown(chest);
		}
	};*/
	
	
	//Testing method. Moves monster to the chest.  
	public void move(Elf chest) {
		if (isColliding(this, chest) == false) {
			up = false;
			down = false;
			left = false;
			right = false;
			if (getXPosWorld() < chest.getPosX()) {
				walkRightAni();
				right = true;
			} else if (getXPosWorld() > chest.getPosX()) {
				walkLeftAni();
				left = true;
			} else if (getYPosWorld() < chest.getPosY()) {
				walkDownAni();
				down = true;
			} else if (getYPosWorld() > chest.getPosY()) {
				walkUpAni();
				up = true;
			}
		} else {
			cooldown(chest);
		}
	};

	/*Actual one
	public int animate(int timeSinceFrame, TreasureTile ev) {
		int newTime = (int) (System.nanoTime() / 1000000);
		if (newTime - timeSinceFrame > frameDelay) {
			move(ev);
		} else
			newTime = timeSinceFrame;
		return newTime;
	}*/
	
	//Tester method. 
	public int animate(int timeSinceFrame, Elf ev) {
		int newTime = (int) (System.nanoTime() / 1000000);
		if (newTime - timeSinceFrame > frameDelay) {
			move(ev);
		} else
			newTime = timeSinceFrame;
		return newTime;
	}

	long lastAttack = 0;
	long cdTime = 1000;

	/*Actual one
	public void cooldown(TreasureTile chest) {
		long time = System.currentTimeMillis();
		if (flag == 2) {
			flag = 0;
		}
		if (time > lastAttack + cdTime) {
			flag++;
			attack(right, left, up, down, chest, flag);
			lastAttack = time;
		}
	}*/
	
	//Function used for attack cooldowns
	//Tester method.
	public void cooldown(Elf chest) {
		long time = System.currentTimeMillis();
		if (flag == 2) {
			flag = 0;
		}
		if (time > lastAttack + cdTime) {
			flag++;
			attack(right, left, up, down, chest, flag);
			lastAttack = time;
		}
	}

	public void walkUpAni() {
		cycleAnimation(0, 0);
		setYPosWorld(getYPosWorld() - 5);
	}

	public void walkRightAni() {
		cycleAnimation(1, 0);
		setXPosWorld(getXPosWorld() + 5);
	}

	public void walkDownAni() {
		cycleAnimation(2, 0);
		setYPosWorld(getYPosWorld() + 5);
	}

	public void walkLeftAni() {
		cycleAnimation(3, 0);
		setXPosWorld(getXPosWorld() - 5);
	}

	public void atkDAni() {
		cycleAnimation(8, 0, 2);
	}

	public void atkUpAni() {
		cycleAnimation(5, 0, 2);
	}

	public void atkLAni() {
		cycleAnimation(7, 0, 2);
	}

	public void atkRAni() {
		cycleAnimation(6, 0, 2);
	}
}
