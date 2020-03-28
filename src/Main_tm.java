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
		 
		RenderEngine_tm gameFramer = new RenderEngine_tm(1920, 1080, 16);
		game_frame.getContentPane().add(gameFramer, BorderLayout.CENTER);		
 
		
		 
		gameFramer.startRender(); 
		
		
	}
	
	


	
	
	
}



