import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.MouseInfo;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;

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
	private DecimalFormat formatter;
	int gamex = 1920;
	int gamey = 1080;
	int i;
	JLabel mousePos, entPosLabel;
	private JLabel entAngleLabel;
	private JLabel entDimLabel, entVelLabel;
	private JLabel centerLabel;
	private JLabel mousePos_1;
	private JLabel viewportPos;
	private 	RenderEngine_tm gameFramer;
	private JLabel lblViewportXPos;
	private JLabel lblCorners;
	private Insets bounds;
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
		bounds = game_frame.getInsets();
		game_frame.setBounds(100, 100, gamex + bounds.top + bounds.bottom, gamey); 
		game_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game_frame.setBounds(bounds.top, bounds.left, gamex, gamey);
		game_frame.setUndecorated(true);
		tester = null;
		tester2 = null;
		try { 
			tester = new testRender("/test.png", 1, 1, 200, -200, 0);
			tester2 = new testRender("/Enterprise.jpg", 1, 1, -1, 1, 1); 
			}
		catch(IOException e) {
			e.printStackTrace();
		}
		game_frame.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		
		gameFramer = new RenderEngine_tm(gamex, gamey, 16);
		
		
		formatter = new DecimalFormat("#.##");
		game_frame.getContentPane().add(gameFramer);		
		gameFramer.setLayout(null);
		
		mousePos = new JLabel("mouse x: nil mouse y: nil");
		mousePos.setBounds(29, 11, 459, 29);
		gameFramer.add(mousePos);
		
		entPosLabel = new JLabel("position");
		entPosLabel.setBounds(29, 124, 300, 22);
		gameFramer.add(entPosLabel);
		
		entAngleLabel = new JLabel("New label");
		entAngleLabel.setBounds(29, 197, 312, 14);
		gameFramer.add(entAngleLabel);
		
		entDimLabel = new JLabel("df");
		entDimLabel.setBounds(29, 222, 372, 14);
		gameFramer.add(entDimLabel);
		
		entVelLabel = new JLabel("velocities");
		entVelLabel.setBounds(29, 277, 285, 14);
		gameFramer.add(entVelLabel);
		
		 centerLabel = new JLabel("Center");
		centerLabel.setBounds(29, 157, 312, 29);
		gameFramer.add(centerLabel);
		
		mousePos_1 = new JLabel("mouse viewport x: nil mouse viewport y: nil");
		mousePos_1.setBounds(29, 84, 567, 29);
		gameFramer.add(mousePos_1);
		
		viewportPos = new JLabel("Viewport x pos: Viewport y pos: ");
		viewportPos.setBounds(1305, 311, 272, 14);
		gameFramer.add(viewportPos);
		
		JLabel lblViewportPos = new JLabel("Viewport Position: (" + gameFramer.getViewportX() + ", " + gameFramer.getViewportY() + ")");
		lblViewportPos.setBounds(29, 51, 285, 22);
		gameFramer.add(lblViewportPos);
		
		lblCorners = new JLabel("corners");
		lblCorners.setBounds(29, 311, 716, 14);
		gameFramer.add(lblCorners);
		gameFramer.addRenderObj(tester);
		gameFramer.addRenderObj(tester2);
		
		gameFramer.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				mousePos.setText("Mouse x is: " + ((gameFramer.getViewportX()) + e.getX()) + " Mouse y is: " +
						((gameFramer.getViewportY()) + e.getY()));
				
				mousePos_1.setText("Mouse viewport x is: " + (e.getX()) + 
						"Mouse viewport Y is: " + e.getY());
				
				if(e.getX() < 10) {
					gameFramer.setViewportX((double)(gameFramer.getViewportX() - 1));
				}
			}
			
		});
		


		 
		
		Thread testThread = new Thread() {
			public void run() {
				double i = -50; 
				double rightX, leftX, bottomY, topY, cos, sin;
				while(true) { 
					
					//	tester.move(0, 0, gamex, gamey);
						//tester2.move(-gamex/2 + tester2.getRotatedSpriteWidth()/2, -gamey/2 + tester2.getRotatedSpriteHeight()/2,
//								gamex/2 - tester2.getRotatedSpriteWidth()/2, gamey/2 - tester2.getRotatedSpriteWidth()/2);

						
						//tester.rotateCurrSprite(i);
						tester2.rotateCurrSprite(i);
						
						
						i -= 1;
						i %= 360;
						if(i < 0) {
							i = 360 + i;
						}
						
						rightX = (tester2.getXPosWorld() + tester2.getSpriteWidth()/2); 
						leftX = (tester2.getXPosWorld() - tester2.getSpriteWidth()/2);
						bottomY = (tester2.getYPosWorld() + tester2.getSpriteHeight()/2);
						topY = (tester2.getYPosWorld() - tester2.getSpriteHeight()/2);
						cos = Math.cos(Math.toRadians(i));
						sin = Math.sin(Math.toRadians(i));
						
						entPosLabel.setText("Ent pos: (" + formatter.format(tester2.getXPosWorld()) + ", " + formatter.format(tester2.getYPosWorld()) + ")"); 
						centerLabel.setText("Ent renderPos: (" + formatter.format(tester2.getXPosRender(gameFramer.getViewportX())) + ", " +
								formatter.format(tester2.getYPosRender(gameFramer.getViewportY())) + ")" +
								"- Ought to be (" + (tester2.getXPosWorld() - gameFramer.getViewportX()) + ", " + (tester2.getYPosWorld() - gameFramer.getViewportY()) + ")");
						entAngleLabel.setText("Ent angle: " + tester2.getAngle());
						entDimLabel.setText("Ent width: " + tester2.getRotatedSpriteWidth() + " Ent Height: " + tester2.getRotatedSpriteHeight());
						entVelLabel.setText("Ent velocity: " + tester2.getVelocity() + "(" + formatter.format(tester2.getXVel()) + ", " + 
						formatter.format(tester2.getYVel()) + ")"); 
						viewportPos.setText("Testers colliding? " + tester2.isColliding(tester));
						lblCorners.setText(
								"Upper Left: (" + (leftX*cos - topY*sin) + ", " + (leftX*sin + topY*cos) + ") " +
								"Upper Right: (" + (rightX*cos - topY*sin) + ", " + (rightX*sin + topY*cos) + ") " +
								"Lower Left: (" + (tester2.getXPosWorld() - tester2.getRotatedSpriteWidth()/2) + ", " + (tester2.getYPosWorld() + (tester2.getRotatedSpriteHeight()/2)) + ") " +
								"Lower Right: (" + (tester2.getXPosWorld() + tester2.getRotatedSpriteWidth()/2) + ", " + (tester2.getYPosWorld() + (tester2.getRotatedSpriteHeight()/2)) + ")");
						
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
	


	//testRender is special- it niavely assumes it has been given a one image sprite sheet. 
	private class testRender extends RenderObj {
		private double xRate;
		private double yRate;
		public testRender(String fileName, int sheetRows, int sheetCols, int x, int y, int z) throws IOException {
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
			
			setXPosWorld(x);
			setYPosWorld(y);
			setZPos(z);
			yRate = Math.random() * 5.0 - 5.0;
			
			xRate = Math.random() * 5.0;
			setAngle(0);
		}
		public String getVelocity() {
			String heading = "No motion"; 
			double velocity = Math.sqrt(Math.pow(xRate, 2) + Math.pow(yRate, 2));
			double direction = 0; //Position in degrees. 
			if(xRate != 0 || yRate != 0) {
				//If xRate AND yRate are less than zero or xRate AND yRate are greater than zero, take their absolute  
				if(xRate < 0 && yRate < 0 || xRate > 0 && yRate > 0) {
					direction = Math.toDegrees(Math.asin(Math.abs(yRate/velocity)));
					if(xRate < 0) {
						direction += 180;
					}
				}
				else if(xRate < 0) {
					direction = Math.toDegrees(Math.acos(xRate/velocity));
				}
				else {
					direction = Math.toDegrees(Math.asin(yRate/velocity));
					direction = 360 + direction;
				}
				DecimalFormat formatter = new DecimalFormat("#.##");
				heading = formatter.format(velocity) + " at " + formatter.format(direction) + " °";
			}
			return heading;
		}
		
		public double getXVel() {
			return xRate;
		}
		public double getYVel() {
			return yRate;
		}
		
		public void move(int x1, int y1, int x2, int y2) {
			
			
			setXPosWorld(getXPosWorld() + xRate);
			setYPosWorld(getYPosWorld() + yRate); 
			
			if(getXPosWorld() + getRotatedSpriteWidth() > x2 || getXPosWorld() < x1) {
				xRate *= -1.0;
				if(Math.random() > .5)
					xRate += Math.random() * 2 - 2;
				else 
					xRate -= Math.random() * 2 - 2;
				if(getXPosWorld() + getSpriteWidth() > x2) {
					setXPosWorld(x2 - getSpriteWidth());
				}
				 
			}
			
			if(getYPosWorld() + getRotatedSpriteHeight() > y2 || getYPosWorld() < y1) {
				yRate *= -1.0;
				if(Math.random() > .5)
					yRate += Math.random() * 2 - 2;
				else 
					yRate -= Math.random() * 2 - 2;
				if(getYPosWorld() + getRotatedSpriteHeight() > y2) {
					setYPosWorld(y2 - getRotatedSpriteHeight());
				}
			 
			}
		}
	}
}



