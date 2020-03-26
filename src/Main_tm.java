import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;


public class Main_tm {

	private JFrame game_frame;

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
		game_frame.setBounds(100, 100, 450, 300);
		game_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		gamePanel_tm gameFramer = new gamePanel_tm();
		game_frame.getContentPane().add(gameFramer, BorderLayout.CENTER);
		
		
	}

}



