import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JPanel;
import javax.swing.Timer;


/**
 * This is our 2D graphical rendering engine. Its coordinate system matches to that 
 * of the Java standard- that is to say, coordinates start at (0,0) from the upper-left hand corner
 * And grow downwards and to the right. Anything above or to the left of the viewport is negative. 
 * @author theon
 *
 */
@SuppressWarnings("serial")
public class RenderEngine_tm extends JPanel{ 
	private double viewportXPos; 
	private double viewportYPos;
	private int viewportWidth;
	private int viewportHeight;
	private BufferedImage viewport;
	private Graphics2D gameGraphics;
	private Timer gameTimer; 
	private ArrayList<RenderObj> renderables;
	private int delay;
	private int runTime; 
	private Color refreshColor;
	/**
	 * Creates a new RenderEngine_tm of size x and y, which makes a frame every frameDelay ms
	 * @param x The width of the viewport
	 * @param y The height of the viewport
	 * @param frameDelay the time between frames 
	 */
	public RenderEngine_tm(int x, int y, int frameDelay) {
		viewportXPos = 0.0 - x/2;
		viewportYPos = 0.0 - y/2;
		viewportWidth = x;
		viewportHeight = y;
		setSize(new Dimension(x, y));
		setPreferredSize(new Dimension(x, y));
		viewport = new BufferedImage(viewportWidth, viewportHeight, BufferedImage.TYPE_INT_ARGB);
		gameGraphics = viewport.createGraphics();
		delay = frameDelay;
		gameTimer = new Timer(delay, new framePaintingListener());
		renderables = new ArrayList<RenderObj>();
		refreshColor = new Color(180, 105, 100);
	}
	
	/**
	 * Sets the color for the refresh- what the background of the scene will be.
	 * @param c The new color
	 */
	public void setRefreshColor(Color c) {
		refreshColor = c;
	}
	
	/**
	 * Overloading for getPreferredSize- necessary for formatting reasons.
	 */
	public Dimension getPreferredSize() {
		return new Dimension(viewportWidth, viewportHeight);
	}
	
	/**
	 * startRender begins the rendering process. It will redraw every gameDelay seconds
	 */
	public void startRender() {
		if(!gameTimer.isRunning())
			gameTimer.start();
	}
	
	/**
	 * stopRender Stops the rendering process. 
	 */
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
			while(j > 0 && jObjLessOne.getZPos() > jObj.getZPos()) {
				
 
				renderables.set(j, jObjLessOne);
				renderables.set(j-1, jObj);
				j = j-1;
				if(j > 0) {
					jObj = renderables.get(j);
					jObjLessOne = renderables.get(j-1);
				}
			}
			i++;
		}
		
 
	}
	public double getViewportX() {
		return viewportXPos;
	}
	public double getViewportY() {
		return viewportYPos;
	}
	
	public void setViewportX(double newX) {
		viewportXPos = newX;
	}
	public void setViewportY(double newY) {
		viewportYPos = newY;
	}
	
	public double getRelX(double absX) {
		return absX - viewportXPos;
	}
	public double getRelY(double absY) {
		return absY - viewportYPos;
	}
	
	/**
	 * Returns the width of the viewport
	 * @return The viewport's width
	 */
	public int getViewportWidth() {
		return viewportWidth;
	}
	
	/**
	 * Returns the hieght of the viewport
	 * @return The viewport's height
	 */
	public int getViewportHeight() {
		return viewportHeight;
	}
	
	
	public double getRunTime() {
		return runTime;
	}
	
	public void paintComponent(Graphics g) {
		
		int i;
		gameGraphics.setColor(refreshColor);
		gameGraphics.fillRect(0, 0, viewportWidth, viewportHeight);
		int renderSize = renderables.size();
		RenderObj curr;
		for(i = 0; i < renderSize; i++) {
			curr = renderables.get(i); 
			gameGraphics.drawImage(curr.getCurrSprite(), (int)curr.getXPosRender(viewportXPos), (int)curr.getYPosRender(viewportYPos), null);
		}
		
		g.drawImage(viewport,  0,  0, viewportWidth, viewportHeight, null);
	
	}
	
	private class framePaintingListener implements ActionListener {
		public synchronized void actionPerformed(ActionEvent arg0) {
			long nanoTime = System.nanoTime();
	
			repaint();
			runTime = (int)(System.nanoTime() - nanoTime)/1000000;
			if(delay <= runTime) { 
				setDelay(runTime);
			} 
		}
	}
 
	
}




