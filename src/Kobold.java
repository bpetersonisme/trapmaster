import java.util.Random;

public class Kobold extends Monster_tm{
	
	public static final int WALK_ONE = 0;
	public static final int WALK_TWO = 1;
	public static final int WALK_THREE = 2;
	public static final int WALK_SOUTH = 0;
	public static final int WALK_EAST = 1;
	public static final int WALK_NORTH = 2;
	public static final int WALK_WEST = 3;
	public static final int DEAD = 4;
	public static final int ATTACK_SOUTH = 5;
	public static final int ATTACK_NORTH = 6;
	public static final int ATTACK_EAST = 7;
	public static final int ATTACK_WEST = 8;
	
	
	public Kobold(double xPos, double yPos) {
		super("/kobold.png", xPos, yPos, 9, 3, 39, 35, 100, (int)(Math.random() * 10) + 1, 5);
		setMaxLoot(50);
		setCurrSpriteRow(2);
		setObjName(nameGenerator() + ", Kobold");
		setWalkFrameDelay(250);
		setAttackFrameDelay(200);
		setType(MONSTER_KOBOLD);
	}
	
	
	/**
	 * Just builds a random kobold name. 
	 * @return The 'name' of this kobold
	 */
	private String nameGenerator() {
		String result = "Anara";
		Random rand = (new Random());
		switch(rand.nextInt(7)) {
		case 1: result = "Skitesh"; break;
		case 2: result = "Ranher"; break;
		case 3: result = "Narlr"; break;
		case 4: result = "Ssahl"; break;
		case 5: result = "Cherno"; break;
		case 6: result = "Kolu"; break;
		case 7: result = "Anara";break;
		default: result = "Nar"; break;
		}
		result = result.concat("'");
		switch(rand.nextInt(7)) {
		case 1: result = result.concat("Huour"); break;
		case 2:  result = result.concat("Gol"); break;
		case 3:  result = result.concat("Tyan"); break;
		case 4:  result = result.concat("Zhur"); break;
		case 5:  result = result.concat("Ooal"); break;
		case 6:  result = result.concat("Luw"); break;
		case 7:  result = result.concat("Qion"); break;
		default: result = result.concat("Thuul"); break;
		}
		 

		
		return result;
	}
	
	
	public void deathAnim() {
		setCurrSprite(DEAD, 0);
	}
	 
	
	
	
	public void walkingAnim(char dir) {
		 
		if(System.currentTimeMillis() - getLastWalkTime() > getWalkFrameDelay()) { 
			int walkFrame = getCurrSpriteCol() + 1;
			int walkDir = WALK_NORTH;
			if(walkFrame > WALK_THREE) 
				walkFrame = WALK_ONE;
			
		switch(dir) {
			case STILL:
				walkFrame = WALK_ONE;
				walkDir = WALK_NORTH;
			break;
			case NORTH:
				walkDir = WALK_NORTH;
			break;
			case EAST:
				walkDir = WALK_EAST;
			break;
			case SOUTH:
				walkDir = WALK_SOUTH;
			break;
			case WEST:
				walkDir = WALK_WEST;
			break;
			}
		
		
		if(getVelocity().getX() == 0 && getVelocity().getY() == 0)
			walkFrame = WALK_ONE;
		
		setCurrSprite(walkDir, walkFrame);

		setLastWalkTime(System.currentTimeMillis());
		}
		
		
	}
	
	public void attackAnim(char dir) {

			if(System.currentTimeMillis() - getLastAttackTime() > getAttackFrameDelay()) {
				int attackFrame = getCurrSpriteCol() + 1;
				int attackDir = ATTACK_NORTH;
				if(attackFrame > WALK_ONE) 
					attackFrame = WALK_ONE;
				
			switch(dir) {
				case STILL:
					attackFrame = WALK_ONE;
					attackDir = ATTACK_NORTH;
				break;
				case NORTH:
					attackDir = ATTACK_NORTH;
				break;
				case EAST:
					attackDir = ATTACK_EAST;
				break;
				case SOUTH:
					attackDir = ATTACK_SOUTH;
				break;
				case WEST:
					attackDir = ATTACK_WEST;
				break;
				}
			setCurrSprite(attackDir, attackFrame);
	
			setLastAttackTime(System.currentTimeMillis());
	
			}
		
	}


 

 
	
}
