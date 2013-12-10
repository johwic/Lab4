package lab4C;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.*;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class BoxView extends JPanel {
	
	private static final int X = 0;
	private static final int Y = 1;
	
	private int[]		screenDim		= {500,500};	
	private	Dimension	preferredSize	= new Dimension(screenDim[X], screenDim[Y]);
	
	private	Color		BLACK			= Color.BLACK;
	private	Color		WHITE			= Color.WHITE; 
	private	Color		BLUE			= Color.BLUE; 
	private	Color		RED				= Color.RED;
	
	private double		diameter		= 2;
	
	private BoxModel	box;
	
	public BoxView(BoxModel boxIn) {
		box = boxIn;
		setPreferredSize(preferredSize);
		setBorder(BorderFactory.createLineBorder(BLACK));
		setBackground(WHITE);
	}
	
        /**
         * The paint callback. Draws particle system.
         */
	public void paint(Graphics g) {
            
                super.paint(g);
		Graphics2D g2D = (Graphics2D) g;
                
		
		/* This is really like in the oracle example.
		 * Are we supposed to create new ellipses all the time? 
		 * Wouldnt it be smarter to have them in an array and reuse them?
		 */
		int count = box.getParticleCount();
		BoxModel.Particle[] particles = box.getParticles();
		for (int i = 0; i < count; i++) {	// TODO map to pixelspace
			if ( particles[i].isStuck ) {
				g2D.setColor(RED);
			} else {
				g2D.setColor(BLUE);
			}
			g2D.draw(particles[i]);
		}
		
	}
}
