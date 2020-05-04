/**
 * Just a quicky-holder for some velocity values 
 * @author Bradley Peterson
 *
 */
public class LinearVector {

	private double xMag;
	private double yMag;
	
	public LinearVector(double x, double y) {
		xMag = x;
		yMag = y;
	}
	
	/**
	 * Compares two vectors. 
	 */
	public boolean equals(LinearVector other) {
		boolean result = false;
		if(other.getX() == getX() && other.getY() == getY())
			result = true;
		
		return result;
	}
	
	/**
	 * @return the x (horizontal) magnitude of the vector
	 */
	public double getX() {
		return xMag;
	}
	/**
	 * Sets the x magnitude of the vector to x
	 * @param x The new x magnitude of the vector
	 */
	public void setX(double x) {
		xMag = x;
	}
	/**
	 * @return the y (vertical) magnitude of the vector. 
	 */
	public double getY() {
		return yMag;
	}
	/**
	 * Sets the y magnitude of the vector to y
	 * @param y The new y magnitude of the vector
	 */
	public void setY(double y) {
		yMag = y;
	}
	
	 
}
