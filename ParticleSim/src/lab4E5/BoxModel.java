package lab4E5;

import java.awt.geom.*;

public class BoxModel {

	private static final int X = 0;
	private static final int Y = 1;
	private double L;
	private int[] dim = {500, 500};

	private int particleCount = 0;
	private Particle[] particleArray;	// TODO maybe a tree instead
	// private ParticleData particleData;

	// TODO a hashtable that keeps track of particles in a grid in space for collision
	// TODO collision detection

	public BoxModel(int particleCountIn) {
		particleCount = particleCountIn;
		genParticles();
	}

	private void genParticles() {
		particleArray = new Particle[particleCount];		
		for (int i=0; i < particleCount; i++) {
			particleArray[i] = new Particle();
		}
		
		// particleData = new ParticleData(particleCount);
	}

	public void updatePosition() {
		for (int i=0; i < particleCount; i++) {
			if (!particleArray[i].isStuck) {
				particleArray[i].moveBrownian2D();
			}
		}
	}

	public double getL() {
		return L;
	}

	public void setL(double LIn) {
		L = LIn;
	}

	public int getParticleCount() {
		return particleCount;
	}

	public BoxModel.Particle[] getParticles() {

		return particleArray;
	}

	/** Returns a 2D array with copy of xy coordinates */
	public double[][] getParticlePos2D() {
		double[][] positions = new double[particleCount][2];
		for (int i=0; i < particleCount; i++) {
			positions[i][X] = particleArray[i].getX();
			positions[i][Y] = particleArray[i].getY();
		}
		return positions;
	}

	protected class Particle extends Point2D.Double {

		public boolean isStuck = false;
		private static final double diameter = 6;
		private static final double radius = diameter/2;
		private Ellipse2D.Double ellipse;

		/** Generates random start coordinates. */
		private Particle() {
			this((dim[X])*Math.random(), (dim[Y])*Math.random());
		}

		/** Creates particle at absolute x,y, getBoxDim() first. */
		private Particle(double xIn, double yIn) {
			super(xIn, yIn);
			
			ellipse = new Ellipse2D.Double(getX()-radius, getY()-radius, diameter, diameter);
		}

		/** moves particle at index one brownian step */
		private void moveBrownian2D() {
			double theta = Math.random()*2*Math.PI;
			// TODO Separate collision detection and other processing
			// into methods, or maybe separate object(s) that we put
			// in process queue and run particles through that.
			double x = getX() + L*Math.cos(theta);
			double y = getY() + L*Math.sin(theta);
			
			setLocation(x, y);
			
			/* detect box edge collision */
			if ( getX() < 0) {
				setLocation(0, getY());
				isStuck = true;
			}
			if ( getY() < 0) {
				setLocation(getX(), 0);
				isStuck = true;
			}
			if ( getX() > (dim[X])) {
				setLocation((dim[X]), getY());
				isStuck = true;
			}
			if ( getY() > (dim[Y])) {
				setLocation(getX(), (dim[Y]));
				isStuck = true;
			}
			
			ellipse.setFrame(getX()-radius, getY()-radius, diameter, diameter);
		}

		public Ellipse2D.Double getEllipse() {
			return ellipse;
		}
	}
	
	protected class ParticleData {
		/* X Y theta */
		private double[][] coords = null;
		
		ParticleData (int sizeIn) {
			coords = new double[sizeIn][3];
		}
	}
}
