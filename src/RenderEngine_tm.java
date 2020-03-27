import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JPanel;
import javax.swing.Timer;


/**
 * This is our 2D graphical rendering engine. 
 * @author theon
 *
 */
@SuppressWarnings("serial")
public class RenderEngine_tm extends JPanel{ 
	private int X_SCREEN_WIDTH;
	private int Y_SCREEN_HEIGHT;
	private BufferedImage viewport;
	private Graphics gameGraphics;
	private Timer gameTimer; 
	private ArrayList<RenderObj> renderables;
	private int delay;
	private long runTime; 
	/**
	 * Creates a new RenderEngine_tm of size x and y, which makes a frame every frameDelay ms
	 * @param x The width of the viewport
	 * @param y The height of the viewport
	 * @param frameDelay the time between frames 
	 */
	public RenderEngine_tm(int x, int y, int frameDelay) {
		X_SCREEN_WIDTH = x;
		Y_SCREEN_HEIGHT = y;
		viewport = new BufferedImage(X_SCREEN_WIDTH, Y_SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);
		gameGraphics = viewport.getGraphics();
		delay = frameDelay;
		gameTimer = new Timer(delay, new framePaintingListener());
		renderables = new ArrayList<RenderObj>();
		
	}
	
	public void startRender() {
		if(!gameTimer.isRunning())
			gameTimer.start();
	}
	
	public void stopRender() {
		if(gameTimer.isRunning())
			gameTimer.stop();
	}
	
	public void addRenderObj(RenderObj newObj) {
		renderables.add(newObj);
		sortRenderables();	 
	}
	
	public void addRenderObjs(Collection<RenderObj> newObjs) {
		renderables.addAll(newObjs);
		sortRenderables();
	}
	
	public boolean removeRenderObj(RenderObj target) {
		renderables.remove(target);
		if(renderables.contains(target)) {
			return false;
		}
		return true;
	}
	
	public void setDelay(int newTime) {
		delay = newTime;
		gameTimer.setDelay(delay);
	}
	
	private void sortRenderables() {
		int i, j;
		i = 1;
		RenderObj jObjLessOne, jObj;
		while(i < renderables.size()) {
			j = i;
			jObj = renderables.get(j);
			jObjLessOne = renderables.get(j-1);
			while(j > 0 && jObjLessOne.getPosZ() > jObj.getPosZ()) {
				renderables.set(j, jObjLessOne);
				renderables.set(j-1, jObj);
				j = j-1;
			}
			i++;
		}
	}
	
	
	public void paintComponent(Graphics g) {
		g.drawImage(viewport,  0,  0, X_SCREEN_WIDTH, Y_SCREEN_HEIGHT, null);
	
	}
	
	private class framePaintingListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			runTime = System.nanoTime();
			int i;
			gameGraphics.setColor(new Color(180, 105, 100));
			gameGraphics.fillRect(0, 0, X_SCREEN_WIDTH, Y_SCREEN_HEIGHT);
			int renderSize = renderables.size();
			RenderObj curr;
			for(i = 0; i < renderSize; i++) {
				curr = renderables.get(i);
				gameGraphics.drawImage(curr.getCurrSprite(), (int)curr.getPosX(), (int)curr.getPosY(), null);
			}
			repaint();
			runTime = (System.nanoTime() - runTime)/1000000;
			if(delay < runTime) {
				setDelay((int)runTime);
			}
		}
	}
 
	
}




