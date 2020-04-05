import java.util.Random;

public class Ogres extends Monster_tm{

	
	public Ogres() {
		Random rmd = new Random();
		health = 200;
		loot = rmd.nextInt(220) + 80; 
		attack = 50;
	}
	
	public void attack() {};
	public void move(){};
	
	
	
}
