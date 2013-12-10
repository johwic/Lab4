package lab4C;

import java.awt.geom.*;

public class BoxModel {

	private static final int X = 0;
	private static final int Y = 1;
	private double L;
	private int[] dim = {500, 500};

	private int particleCount = 0;
	private Particle[] particleArray;

	public BoxModel(int particleCountIn) {
		particleCount = particleCountIn;
		genParticles();
	}

	private void genParticles() {
		particleArray = new Particle[particleCount];
		for (int i=0; i < particleCount; i++) {
			particleArray[i] = new Particle();
		}
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

	/**
         * Returns a 2D array with copy of xy coordinates
         * @return double[][]
         */
	public BoxModel.Particle[] getParticles() {

		return particleArray;
	}

	public class Particle extends Ellipse2D.Double {

        public boolean isStuck = false;
        private static final int d = 2;

		/** Generates random start coordinates. */
		private Particle() {
			this((dim[X]-d-1)*Math.random(), (dim[Y]-d-1)*Math.random());
		}

		/** Creates particle at absolute x,y, getBoxDim() first. */
		private Particle(double xIn, double yIn) {
			super(xIn, yIn, d, d);
		}

		/** moves particle at index one brownian step */
		private void moveBrownian2D() {
			double theta = Math.random()*2*Math.PI;
			setFrame(getX() + L*Math.cos(theta), getY() + L*Math.sin(theta), d, d);
			if ( getX() < 0) {
				setFrame(0, getY(), d, d);
				isStuck = true;
			}
			if ( getY() < 0) {
				setFrame(getX(), 0, d, d);
				isStuck = true;
			}
			if ( getX() > (dim[X]-d-1)) {
				setFrame((dim[X]-d-1), getY(), d, d);
				isStuck = true;
			}
			if ( getY() > (dim[Y]-d-1)) {
				setFrame(getX(), (dim[Y]-d-1), d, d);
				isStuck = true;
			}
		}

	}
}
