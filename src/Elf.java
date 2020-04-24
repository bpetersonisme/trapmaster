import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Elf extends Monster_tm {
	String filename = "Elves.png";
	int frameDelay = 200;
	BufferedImage buf = ImageIO.read(this.getClass().getResourceAsStream(filename));
	boolean up = false;
	boolean down = false;
	boolean left = false;
	boolean right = false;

	// This is a method used for testing
	public void loseLoot(int amount) {
		this.loot -= amount;
		if (this.loot < 0)
			this.loot = 0;
	}

	public Elf(int rows, int columns, int width, int height, int xWorldPos, int yWorldPos) throws IOException {
		setSpriteSheet(buf, rows, columns, width, height);
		Random rmd = new Random();
		health = 200;
		loot = rmd.nextInt(150) + 50;
		attack = 25;
		setXPosWorld(xWorldPos);
		setYPosWorld(yWorldPos);
	}

	public void attack(boolean right, boolean left, boolean up, boolean down) {
		if (right) {
			atkRAni();
		}
		if (left) {
			atkLAni();
		}
		if (up) {
			atkUpAni();
		}
		if (down) {
			atkDAni();
		}
	};

	/*public void move(Ogre chest) {
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
			attack(right, left, up, down);
		}
	};*/

	public int animate(int timeSinceFrame, Ogre og) {
		int newTime = (int) (System.nanoTime() / 1000000);
		if (newTime - timeSinceFrame > frameDelay) {
			// move(og);
			this.death();
		} else
			newTime = timeSinceFrame;
		return newTime;
	}

	public void walkDownAni() {
		cycleAnimation(0, 0);
		setYPosWorld(getYPosWorld() + 10);
	}

	public void walkUpAni() {
		cycleAnimation(1, 0);
		setYPosWorld(getYPosWorld() - 10);
	}

	public void walkLeftAni() {
		cycleAnimation(2, 0);
		setXPosWorld(getXPosWorld() - 10);
	}

	public void walkRightAni() {
		cycleAnimation(3, 0);
		setXPosWorld(getXPosWorld() + 10);
	}

	public void atkDAni() {
		cycleAnimation(5, 0, 2);
	}

	public void atkUpAni() {
		cycleAnimation(6, 0, 2);
	}

	public void atkLAni() {
		cycleAnimation(7, 0, 2);
	}

	public void atkRAni() {
		cycleAnimation(8, 0, 2);
	}


}
