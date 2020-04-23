import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.ClassLoader;

import javax.imageio.ImageIO;

public class Trap_Ballista extends Trap_tm{
	
	public Trap_Ballista(double xPos, double yPos, int facing) {
		super(xPos, yPos, facing);
		this.setTr_maxHealth(100);
		this.setTr_currentHealth(100);
		this.setTr_range(10);
		this.setTr_damage(50);
		this.setTr_cost(50);
		this.setTr_cooldown(150);
		this.setTarget(null);
		BufferedImage buf = null;
		try {
			buf = ImageIO.read(this.getClass().getResourceAsStream("Ballista.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setSpriteSheet(buf, 2, 4, 64, 64);
		this.setCurrSpriteCol(facing);
		
	}
	
}
