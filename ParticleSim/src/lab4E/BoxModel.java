package lab4E;

public class BoxModel {

	private final int			X		= 0;
	private final int			Y		= 1;
	private double 				ell 		= 0.001;
	private double[]			boxDim	= {1,1,1}; 	//  box space, map to display later

	private double				dt		= 0.1;

	private int			particleCount	= 0;
	private Particle[]	particleArray;
	// TODO implement hashmap Map(?) for collision detection - LinkedList for each entry?
	// TODO implement tree (?) TreeSet (?) for groups of stuck particles
	// TODO implement array(?) for particle groups

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

	private void updatePosition() {
		for (int i=0; i < particleCount; i++) {
			particleArray[i].moveBrownian2D();
		}
	}
	
	public double getL() {
		return ell;
	}

	public void setL(double LIn) {
		ell = LIn;
	}
	
	public int getParticleCount() {
		return particleCount;
	}

	/** Returns a 2D array with copy of xy coordinates */
	public double[][] getParticlePos2D() {
		double[][] positions = new double[particleCount][2];
		for (int i=0; i < particleCount; i++) {
			positions[i][X] = particleArray[i].position[X];
			positions[i][Y] = particleArray[i].position[Y];
		}
		return positions;
	}
	
//	public Particle[] getParticles() {
//		return particleArray;
//	}

	public double[] getBoxDim() {
		return boxDim;
	}

	public void setBoxDim(double[] boxDim) {
		this.boxDim = boxDim;
	}

	private class Particle {

		private double[]	position		= {0,0,0};
		private double[]	velocity		= {0,0,0};
		private double 		theta 			= 0;
		//private boolean		updated 		= false;

		/** Generates random start coordinates. */
		private Particle() {
			this( Math.random()*boxDim[0], Math.random()*boxDim[1] );
		}

		/** Creates particle at absolute x,y, getBoxDim() first. */
		private Particle(double xIn, double yIn) {
			position[0] = xIn;
			position[1] = yIn;
		}

		/** moves particle at index one brownian step */
		private void moveBrownian2D() {

			/* x(t) = x(t-dt) + L*cos(theta), y(t) = y(t-dt) + L*sin(theta) */
			theta = Math.random()*Math.PI;
			position[X] = position[X] + ell*Math.cos(theta);
			position[Y] = position[Y] + ell*Math.sin(theta);
		}

	}

}

///** moves particle at index one brownian step */
//private void moveBrownian2D(int index) {
//
//	/* x(t) = x(t-dt) + L*cos(theta), y(t) = y(t-dt) + L*sin(theta) */
//	double theta = Math.random()*Math.PI;
//	particleArray[index].position[X] = particleArray[index].position[X] + L*Math.cos(theta);
//	particleArray[index].position[Y] = particleArray[index].position[Y] + L*Math.sin(theta);
//	particleArray[index].theta = theta;
//}

//private double genRandomX() {
//return Math.random()*boxAspect[0];
//}
//private double genRandomY() {
//return Math.random()*boxAspect[1];
//}
//
//private double[] genRandom2D() {
//double[] random2D = new double[2];
//random2D[0] = Math.random()*boxAspect[0];
//random2D[1] = Math.random()*boxAspect[1];
//return random2D;
//}