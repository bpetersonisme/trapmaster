import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.MouseInfo;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JLabel;

import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.GridLayout;


public class Testbed_tm {

	private JFrame game_frame;
	private testRender tester, tester2;
	private Thread testThread;
	private Thread monsterThread;
	private Thread trapThread;
	private Thread mapThread;
	int gamex = 1920;
	int gamey = 1080;
	int i;
	JLabel mousePos, entPosLabel;
	private JLabel entAngleLabel;
	private JLabel entDimLabel;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Testbed_tm window = new Testbed_tm();
					window.game_frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Testbed_tm() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		game_frame = new JFrame();
		game_frame.setBounds(100, 100, gamex, gamey);
		game_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tester = null;
		tester2 = null;
		try { 
			tester = new testRender("/test.png", 1, 1, 200, 200);
			tester2 = new testRender("/Enterprise.jpg", 1, 1, 1000, 500); 
			}
		catch(IOException e) {
			e.printStackTrace();
		}
		game_frame.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		
		RenderEngine_tm gameFramer = new RenderEngine_tm(gamex, gamey, 16);
		
		
		
		game_frame.getContentPane().add(gameFramer);		
		gameFramer.setLayout(null);
		
		mousePos = new JLabel("MOUSe");
		mousePos.setBounds(934, 5, 300, 155);
		gameFramer.add(mousePos);
		
		entPosLabel = new JLabel("also here");
		entPosLabel.setBounds(934, 165, 300, 22);
		gameFramer.add(entPosLabel);
		
		entAngleLabel = new JLabel("New label");
		entAngleLabel.setBounds(934, 193, 312, 14);
		gameFramer.add(entAngleLabel);
		
		entDimLabel = new JLabel("df");
		entDimLabel.setBounds(934, 217, 372, 14);
		gameFramer.add(entDimLabel);
		gameFramer.addRenderObj(tester);
		gameFramer.addRenderObj(tester2);
		
		gameFramer.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent arg0) {
				mousePos.setText("Mouse x is: " + MouseInfo.getPointerInfo().getLocation().getX() + " Mouse y is: " +
						MouseInfo.getPointerInfo().getLocation().getY());
			}
		});
		 
		
		Thread testThread = new Thread() {
			public void run() {
				double i = 0; 
				while(true) {
					System.out.println("\n");
					
						tester.move(0, 0, gamex, gamey);
						tester2.move(0, 0, gamex, gamey);

						tester.rotateCurrSprite(i);
						tester2.rotateCurrSprite(i);
						i += 1;
						entPosLabel.setText("Ent pos: (" + tester2.getPosX() + ", " + tester2.getPosY() + ")"); 
						entAngleLabel.setText("Ent angle: " + tester2.getAngle());
						entDimLabel.setText("Ent width: " + tester2.getRotatedSpriteWidth() + " Ent Height: " + tester2.getRotatedSpriteHeight());
					
					try {
						sleep(20);
					}
					catch(InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		testThread.start();
		gameFramer.startRender();  
		
		
	}
	
	


	
	private class testRender extends RenderObj {
		private double xRate;
		private double yRate;
		public testRender(String fileName, int sheetRows, int sheetCols, int x, int y) throws IOException {
			BufferedImage buf = ImageIO.read(this.getClass().getResourceAsStream(fileName));
		 
			int newWidth = buf.getWidth(); 
			int newHeight = buf.getHeight(); 
			
			System.out.println("Width is: " + buf.getWidth() + " Height is: " + buf.getHeight());
			if(buf.getWidth() > 256 || buf.getHeight() > 256) {
				
 
				int div; 
				if(newWidth > newHeight) {
					div = (int)Math.floor(newWidth/256);
				}
				else {
					div = (int)Math.floor(newHeight/256);
				}
				System.out.println("Div is: " + div);
				newWidth /= div;
				newHeight /= div;
				
				
				System.out.println("NewWidth is : " + newWidth + " and newHeight is: " + newHeight);
				BufferedImage newBuf = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
				Graphics2D g2d = newBuf.createGraphics();
				int test = 2; 
				
				g2d.drawImage(buf.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_DEFAULT), null, null);
				g2d.dispose();
				buf = newBuf;
			} 
			
			setSpriteSheet(buf, 1, 1, newWidth, newHeight);
			
			System.out.println("Width: " + getCurrSprite().getWidth() + " Height: " + getCurrSprite().getHeight());
			System.out.println("X is : " + getPosX() + "So Width is: " + getPosX() + getCurrSprite().getWidth());
			setPosX(x);
			setPosY(y);
			setPosZ(0);
			xRate = Math.random() * 5.0 - 5.0;
			yRate = Math.random() * 5.0 - 5.0;
			setAngle(0);
		}
		public void move(int x1, int y1, int x2, int y2) {
			setPosX(getPosX() + xRate);
			setPosY(getPosY() + yRate);
		 
			if(getPosX() + getSpriteWidth() > x2 || getPosX() < x1) {
				xRate *= -1.0;
				if(Math.random() > .5)
					yRate += Math.random() * 2 - 2;
				else 
					yRate -= Math.random() * 2 - 2;
				if(getPosX() + getSpriteWidth() > x2) {
					setPosX(x2 - getSpriteWidth());
				}
				else {
					setPosX(0);
				}
			}
			
			if(getPosY() + getSpriteHeight() > y2 || getPosY() < y1) {
				yRate *= -1.0;
				if(Math.random() > .5)
					xRate += Math.random() * 2 - 2;
				else 
					xRate -= Math.random() * 2 - 2;
				if(getPosY() + getSpriteHeight() > y2) {
					setPosY(y2 - getSpriteHeight());
				}
				else {
					setPosY(0);
				}
			}
		}
	}
}



