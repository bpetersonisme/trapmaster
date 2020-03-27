import java.awt.EventQueue;

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


public class Main_tm {

	private JFrame game_frame;
	private testRender tester, tester2;
	private Thread testThread;
	private Thread monsterThread;
	private Thread trapThread;
	private Thread mapThread;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main_tm window = new Main_tm();
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
	public Main_tm() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		game_frame = new JFrame();
		game_frame.setBounds(100, 100, 700, 580);
		game_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tester = null;
		tester2 = null;
		try {
			tester = new testRender("/test.png", 1, 1, 200, 200);
			tester2 = new testRender("/test.png", 1, 1, 200, 200);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		RenderEngine_tm gameFramer = new RenderEngine_tm(600, 480, 16);
		game_frame.getContentPane().add(gameFramer, BorderLayout.CENTER);		
		gameFramer.addRenderObj(tester);
		gameFramer.addRenderObj(tester2);
		
		Thread testThread = new Thread() {
			public void run() {
				while(true) {
					tester.move(0, 0, 1000, 1000);
					tester2.move(0, 0, 600, 480);
					
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
			setSpriteSheet(ImageIO.read(this.getClass().getResourceAsStream(fileName)), 1, 1, 25, 25);
			setPosX(x);
			setPosY(y);
			setPosZ(0);
			xRate = Math.random() * 5.0 - 5.0;
			yRate = Math.random() * 5.0 - 5.0;
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



