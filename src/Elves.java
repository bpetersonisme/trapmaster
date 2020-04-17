import java.util.Random;

public class Elves extends Monster_tm{
	
	public Elves() {
		Random rmd = new Random();
		health = 200;
		loot = rmd.nextInt(150) + 50; 
		attack = 25;
	}
	
	
	public void attack() {};
	public void move(){};
	
}
