import java.util.Random;

public class Kobold extends Monster_tm{
	
	
	public Kobold(double xPos, double yPos) {
		super("/kobold.png", xPos, yPos, 9, 3, 39, 35, 100, (int)(Math.random() * 10) + 1, 5);
		setMaxLoot(50);
		setObjName("Kobold");
	}
	
	public void attack() {};
	public void move(){};
	

 
	
}
