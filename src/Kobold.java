import java.util.Random;

public class Kobold extends Monster_tm{
	
	
	public Kobold() {
		Random rmd = new Random();
		health = 100;
		loot = rmd.nextInt(10) + 1; 
		attack = 5;
	}
	
	public void attack() {};
	public void move(){};
	
	
}
