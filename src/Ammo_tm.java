import java.io.IOException;

public abstract class Ammo_tm extends RenderObj {

	private int damage;					//Damage of projectile
	private int range;					//Range of projectile
	private int facing;					//Facing of the projectile
	private Monster_tm target;			//target of the projectile
	private double speed;				//Speed of the projectile
	
	public Ammo_tm(String sprite, int damage, int range, double xPos, double yPos, int facing, double speed, Monster_tm target) {
		try {
			this.setSpriteSheet(sprite, 1, 4, 64, 64);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.damage = damage;
		this.range = range;
		setXPosWorld(xPos);
		setYPosWorld(yPos);
		this.facing = facing;
		setCurrSpriteCol(facing);
		this.target = target;
	}
	
	public void fire(){
		while(!isColliding(this,target)) {
			if(getXPosWorld() < target.getXPosWorld()) {
				setXPosWorld(getXPosWorld() + speed);
			} else if(getXPosWorld() > target.getXPosWorld()) {
				setXPosWorld(getXPosWorld() - speed);
			} else {
				
			}
			
			if(getYPosWorld() < target.getYPosWorld()) {
				setXPosWorld(getYPosWorld() + speed);
			} else if(getYPosWorld() > target.getYPosWorld()) {
				setXPosWorld(getYPosWorld() - speed);
			} else {
				
			}
		}
		
		target.takeDmg(damage);
	}
	
}
