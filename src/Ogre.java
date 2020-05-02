import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public abstract class Ogre /*extends Monster_tm*/ {
	int frameDelay = 300; 
	String filename = "Ogre.png";
	boolean up = false; //All booleans variables are used to determine direction.
	boolean down = false;
	boolean left = false;
	boolean right = false;
	int flag = 0; 
	int count = 0;
	long lastAtk = 0; //Used for cooldown method. Keeps time of last attack.
	long cdTime = 1000; //Sets cooldown of attack.
	/*
	public Ogre(int rows, int columns, int width, int height, int xWorld, int yWorld){
		try {
			BufferedImage buf = ImageIO.read(this.getClass().getResourceAsStream(filename));
			setSpriteSheet(buf, rows, columns, width, height);
		}catch(IOException e) {
			System.out.println("Could not find file " + filename);
		}
		Random rmd = new Random();
		health = 200;
		loot = rmd.nextInt(220) + 80;
		attack = 50;
		setXPosWorld(xWorld);
		setYPosWorld(yWorld);
	}
	

	
	//This is used for testing to make the monster attack the chest. I'm using the Elf as a "chest".
	//Since elf is used as the "chest" I used this chance to also test to see if death method worked properly.
	//Count is used as a testing variable to check how many times plunder was called.
	//This method can be deleted after testing is finished. 
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
		//This part of code is what checks if death method works. 
		ev.health -= this.attack;
		if(ev.getHealth() <= 0) {
			ev.death();
		}
		count++;
		System.out.println("Times plunder was called: " + count + " Loot in chest: " + ev.loot);
	}

	/**
	 * Method that makes monster attack. Currently, it just makes monster attack the chest.
	 * The flag statement makes sure that plunder is called on the second frame of the attack animation.
	 * This makes sure loot is taken only when monster "hits" the target.
	 * @param chest - TreasureTile object that monster attacks
	 * @param flag - Flag variable.
	 */
	/*public void attack(TreasureTile chest, int flag) {
		if (right) {
			atkRAni();
			if (flag == 2) {
				plunder(chest);
			}
		}
		if (left) {
			atkLAni();
			if (flag == 2) {
				plunder(chest);
			}
		}
		if (up) {
			atkUpAni();
			if (flag == 2) {
				plunder(chest);
			}
		}
		if (down) {
			atkDAni();
			if (flag == 2) {
				plunder(chest);
			}
		}
	}*/
	/*
	//Testing method. This uses an elf as a chest. 
	public void attack(Elf ev, int flag) {
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
	
	/**
	 * Method that makes monster move toward chest. 
	 * Partially works as of now. 
	 * Once monster and chest collide, calls cooldown method which causes monster to attack.
	 * @param chest
	 */
	/*public void move(TreasureTile chest) {
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
	/*
	
	//Testing method. Moves monster to the chest.
	//Also takes in a monster object which I used as a "wall". 
	//Tried making monster walk around the "wall" but instead it phases through.
	//The booleans are set to true depending on the direction its facing.
	//These are reset every time so only the last direction is flagged as true.
	public void move(Elf chest, Monster_tm m) {
		if (isColliding(this, chest) == false) {
			up = false;
			down = false;
			left = false;
			right = false;
			if (getXPosWorld() < chest.getPosX()) {
				walkRightAni();
				if(isColliding(this,m)) {
					move(chest, m);
				}
				right = true;
			} else if (getXPosWorld() > chest.getPosX()){
				walkLeftAni();
				if(isColliding(this,m)) {
					move(chest, m);
				}
				left = true;
			} else if (getYPosWorld() < chest.getPosY()){
				walkDownAni();
				System.out.println("Collding?: " + isColliding(this,m));
				if(isColliding(this, m)) {
					move(chest, m);
				}
				down = true;
			} else if (getYPosWorld() > chest.getPosY()){
				walkUpAni();
				if(isColliding(this,m)) {
					move(chest, m);
				}
				up = true;
			}
		} else {
			cooldown(chest);
		}
	};

	/**
	 * Method that controls the delay of the frames of the movement animations. 
	 * This was taken from Testbed_tm
	 * @param timeSinceFrame -Time from last frame. 
	 * @param chest - TreasureTile object used to be sent into the move function.
	 * @return
	 */
	/*public int animate(int timeSinceFrame, TreasureTile chest) {
		int newTime = (int) (System.nanoTime() / 1000000);
		if (newTime - timeSinceFrame > frameDelay) {
			move(ev);
		} else
			newTime = timeSinceFrame;
		return newTime;
	}*/
	/*
	//Tester method. Uses monster as to be sent to move to act as a "chest".
	public int delay(int timeSinceFrame, Elf ev, Monster_tm m) {
		int newTime = (int) (System.nanoTime() / 1000000);
		if (newTime - timeSinceFrame > frameDelay) {
			move(ev, m);
		} else
			newTime = timeSinceFrame;
		return newTime;
	}

	

	/**
	 * Similar to the animate method, however this controls the attack frames.
	 * @param chest - Param is needed to be sent to attack in order to let monster attack chest.
	 */
	/*public void cooldown(TreasureTile chest) {
		long time = System.currentTimeMillis();
		if (flag == 2) {
			flag = 0;
		}
		if (time > lastAttack + cdTime) {
			flag++;
			attack(chest, flag);
			lastAttack = time;
		}
	}*/
	/*
	//Function used for attack cooldowns
	//Tester method.
	public void cooldown(Elf chest) {
		long time = System.currentTimeMillis();
		if (flag == 2) {
			flag = 0;
		}
		if (time > lastAtk + cdTime) {
			flag++;
			attack(chest, flag);
			lastAtk = time;
		}
	}

	//All the functions below control the animations based on direction. 
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
	*/
}
