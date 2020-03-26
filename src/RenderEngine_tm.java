import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Timer;

import javax.swing.JPanel;


/**
 * This is our 2D graphical rendering engine. 
 * @author theon
 *
 */
public class RenderEngine_tm extends JPanel{ 
	private final int X_SCREEN_WIDTH;
	private final int Y_SCREEN_HEIGHT;
	private BufferedImage viewport;
	private Graphics gameGraphics;
	private Timer gameTimer; 
	
	/**
	 * Creates a new RenderEngine_tm 
	 */
	public RenderEngine_tm(int x, int y) {
		X_SCREEN_WIDTH = x;
		Y_SCREEN_HEIGHT = y;
		viewport = new BufferedImage(X_SCREEN_WIDTH, Y_SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);
		
		
	}
	 
	public void paintComponent(Graphics g) {
		g.drawImage(viewport,  0,  0, X_SCREEN_WIDTH, Y_SCREEN_HEIGHT);
	
	}
	
}




