
public class Ballista_Bolt extends Ammo_tm{

	public Ballista_Bolt(int damage, int range, double xPos, double yPos, int facing, Monster_tm target) {
		super("Bolt.png", damage, range, xPos, yPos, facing, .1, target);
	}
	
}
