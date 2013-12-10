package lab4E;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.*;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class BoxView extends JPanel {
	
	private final int			X		= 0;
	private final int			Y		= 1;
	
	private int[]		screenDim		= {500,500};	
	private	Dimension	preferredSize	= new Dimension(screenDim[X], screenDim[Y]);
	
	private	Color		BLACK			= Color.BLACK;
	private	Color		WHITE			= Color.WHITE; 
	private	Color		BLUE			= Color.BLUE; 
	private	Color		RED				= Color.RED;
	
	private double		diameter		= 4;
	
	private BoxModel	box;
	
	public BoxView (BoxModel boxIn) {
		box = boxIn;
		
		setPreferredSize(preferredSize);
		// setBorder(BorderFactory.createLineBorder(BLACK));
		setBackground(BLACK);
	}
	
	/** The paint callback. Draws particle system. */
	public void paint(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		g2D.setColor(RED);
		/* This is really like in the oracle example.
		 * Are we supposed to create new ellipses all the time? 
		 * Wouldnt it be smarter to have them in an array and reuse them?
		 */
		int count = box.getParticleCount();
		double[][] coords = box.getParticlePos2D();
		for (int i = 0; i < count; i++) {	// TODO map to pixelspace
			g2D.draw(new Ellipse2D.Double(coords[i][X]*screenDim[X],
					coords[i][Y]*screenDim[Y],
					diameter,
					diameter));
		}
		
	}
}

//private int			circleCount		= 0;
//private	Ellipse2D.Double[]	circles;

///* This is really like in the oracle example.
// * Bad to create new ellipses all the time? 
// * Wouldnt it be smarter to have them in an array and reuse them?
// */
//int count = box.getParticleCount();
//double[][] coords = box.getParticlePos2D();
//for (int i = 0; i < count; i++) {
//	g2D.draw(new Ellipse2D.Double(coords[i][X], coords[i][Y], diameter, diameter));
//}

///* create the ellipse objects 
// * so we dont have to make new ones at rendertime */
//circleCount = box.getParticleCount();
//circles = new Ellipse2D.Double[circleCount];
//for (int i = 0; i < circleCount; i++) {
//	circles[i] = new Ellipse2D.Double(-10, -10, diameter, diameter);
//}