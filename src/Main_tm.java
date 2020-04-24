import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Insets;

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
import java.util.ArrayList;

import javax.swing.JSplitPane;

import java.awt.Color;
import java.awt.SystemColor;


public class Main_tm {

	private Insets bounds;
	private JFrame game_frame; 
	private Thread monsterThread;
	private Thread trapThread;
	private Thread mapThread;
	private ArrayList<Trap_tm> traps;
	private ArrayList<Monster_tm> monsters;
	private ArrayList<Tile_tm> tiles;
	private Map_tm gameMap;
	private boolean doMonsterThread, doTrapThread, doMapThread;
	/**
	 * Launch the application.
	 */
	
	private final int SCREEN_HEIGHT = 1080;
	private final int SCREEN_WIDTH = 1920;
	private RenderEngine_tm gameEngine;
	private Hud_tm gameHud; 
	
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
	 * @throws IOException 
	 */
	public Main_tm() throws IOException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	private void initialize() throws IOException {
		
		/***************************************************
		 *                  Frame Setup                    *
		 ***************************************************/
		game_frame = new JFrame();  
		//game_frame.setBounds(0, 0, 1920, 1080);
		game_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game_frame.getContentPane().setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
		game_frame.setResizable(false);
		
		doMonsterThread = true;
		doTrapThread = true;
		doMapThread = true;
		
		gameEngine = new RenderEngine_tm(1668, 1080, 16);
		gameEngine.setRefreshColor(Color.LIGHT_GRAY);
		game_frame.getContentPane().add(gameEngine); 
		
		gameHud = new Hud_tm(SCREEN_WIDTH, SCREEN_HEIGHT);
		game_frame.getContentPane().add(gameHud);
		game_frame.pack();
		
		/************************************************
		 *            Game Object References            *
		 ************************************************/
		
		traps = new ArrayList<Trap_tm>(); 
		/*
		 * Some code/method call to populate the traps
		 */
		
		tiles = new ArrayList<Tile_tm>(); 
		/*
		 * Some code/method call to populate the tiles
		 */
		
		monsters = new ArrayList<Monster_tm>();
		
		int i, arraySize;
		
		Trap_tm currTrap; 
		Tile_tm currTile;
		Monster_tm currMonster;
		Elf ev = new Elf(9, 3, 30,42, -200, 100);
		monsters.add(ev);
		Ogre og = new Ogre(8,3, 50,60, -250, -100);
		monsters.add(og);
		
		
		arraySize = traps.size();
		for(i = 0; i < arraySize; i++) {
			currTrap = traps.get(i);
			gameEngine.addRenderObj(currTrap);
		}
		
		arraySize = tiles.size(); 
		for(i = 0; i < arraySize; i++) {
			currTile = tiles.get(i);
			gameEngine.addRenderObj(currTile);
		}
		
		arraySize = monsters.size();
		for(i = 0; i < arraySize; i++) {
			currMonster = monsters.get(i);
			gameEngine.addRenderObj(currMonster);
		}
		
		System.out.println("Initial ogre loot: " + og.loot);
		System.out.println("Initial elf loot: " + ev.loot);
		/******************************************************
		 *              GAME EXECUTION THREADS                *
		 ******************************************************/
		//The monster thread handles everything related to monsters- their movements, their health, possible additions or removals...
		monsterThread = new Thread() {
			public void run() {
				int time = (int)(System.nanoTime()/1000000);
				while(doMonsterThread) {
					//time = ev.animate(time, og); //Tests death of monster. 
					//time = og.animate(time, ev); //Tests walking and attacking chest.
					try {
						sleep(20);
					}
					catch(InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		//Trap Thread handles trap working- placing or removing damage zones, etc. 
		trapThread = new Thread() {
			public void run() {
				while(doTrapThread) {
					try {
						sleep(20);
					}
					catch(InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		//mapThread handles the workings of the map- routing, viewport adjustments, all that. 
		mapThread = new Thread() {
			public void run() {
				while(doMapThread) {


					try {
						sleep(20);
					}
					catch(InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		
		gameEngine.startRender(); 
		monsterThread.start();
		trapThread.start();
		mapThread.start();
	}
}
